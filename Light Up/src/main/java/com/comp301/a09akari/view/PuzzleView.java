package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.CellType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import com.comp301.a09akari.model.Puzzle;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;

public class PuzzleView implements FXComponent {
  private final ControllerImpl controller;

  public PuzzleView(ControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();
    Puzzle puzzle = controller.getActivePuzzle();

    for (int r = 0; r < puzzle.getHeight(); r++) {
      for (int c = 0; c < puzzle.getWidth(); c++) {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("cell");

        CellType type = puzzle.getCellType(r, c);
        switch (type) {
          case CLUE:
            Text clueText = new Text(String.valueOf(puzzle.getClue(r, c)));
            if (controller.isClueSatisfied(r, c)) {
              clueText.getStyleClass().add("clue-text-satisfied");
            } else {
              clueText.getStyleClass().add("clue-text");
            }
            cell.getStyleClass().add("clue-cell");
            cell.getChildren().add(clueText);
            break;
          case WALL:
            cell.getStyleClass().add("wall-cell");
            break;
          case CORRIDOR:
            cell.getStyleClass().add("corridor-cell");
            if (controller.isLit(r, c)) {
              cell.getStyleClass().add("lit-cell");
            }
            if (controller.isLamp(r, c)) {
              if (controller.isLampIllegal(r, c)) {
                cell.getStyleClass().add("illegal-lamp-cell");
              } else {
                cell.getStyleClass().add("lamp-cell");
              }
            }
            int finalR = r;
            int finalC = c;
            cell.setOnMouseClicked(event -> controller.clickCell(finalR, finalC));
            break;
        }

        final int cellSize = 50;
        cell.setMinSize(cellSize, cellSize);
        cell.setMaxSize(cellSize, cellSize);

        grid.add(cell, c, r);
      }
    }

    grid.setAlignment(Pos.CENTER);

    Text puzzleNumberText =
        new Text(
            "Puzzle "
                + (controller.getActivePuzzleIndex() + 1)
                + "/"
                + controller.getPuzzleLibrarySize());
    puzzleNumberText.getStyleClass().add("puzzle-number-text");
    puzzleNumberText.setTextAlignment(TextAlignment.CENTER);

    HBox puzzleNumberBox = new HBox(puzzleNumberText);
    puzzleNumberBox.setAlignment(Pos.CENTER);
    puzzleNumberBox.setPadding(new Insets(10, 0, 10, 0));

    Button backButton = new Button("Back");
    backButton.setOnAction(event -> controller.showTitleScreen());

    HBox backButtonBox = new HBox(backButton);
    backButtonBox.setAlignment(Pos.CENTER_LEFT);
    backButtonBox.setPadding(new Insets(10, 20, 20, 10));

    BorderPane borderPane = new BorderPane();
    VBox topBox = new VBox(backButtonBox, puzzleNumberBox);
    borderPane.setTop(topBox);
    borderPane.setCenter(grid);

    return borderPane;
  }
}
