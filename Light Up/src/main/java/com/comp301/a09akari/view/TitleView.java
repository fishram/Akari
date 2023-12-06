package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TitleView implements FXComponent {
  private final ControllerImpl controller;

  public TitleView(ControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox titleBox = new VBox(10);
    titleBox.setAlignment(Pos.CENTER);
    titleBox.getStyleClass().add("title-box");

    ImageView imageView = new ImageView(new Image("akari-logo.png"));
    imageView.setPreserveRatio(true);
    imageView.setFitWidth(400);

    Text title = new Text("Select a Puzzle");
    title.getStyleClass().add("title-text");
    titleBox.getChildren().addAll(imageView, title);

    for (int i = 0; i < controller.getPuzzleLibrarySize(); i++) {
      final int puzzleIndex = i;
      Button puzzleButton = new Button("Puzzle " + (puzzleIndex + 1));
      puzzleButton.setOnAction(event -> controller.selectPuzzle(puzzleIndex));
      titleBox.getChildren().add(puzzleButton);
    }

    Button randomPuzzleButton = new Button("Random Puzzle");
    randomPuzzleButton.setOnAction(event -> controller.clickRandPuzzle());
    titleBox.getChildren().add(randomPuzzleButton);

    return titleBox;
  }
}
