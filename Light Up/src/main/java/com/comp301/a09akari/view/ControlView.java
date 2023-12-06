package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class ControlView implements FXComponent {
  private final ControllerImpl controller;

  public ControlView(ControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox controlBox = new HBox(10);
    controlBox.getStyleClass().add("control-box");
    controlBox.setAlignment(Pos.CENTER);

    Button nextPuzzleButton = createButton("Next\nPuzzle", controller::clickNextPuzzle);
    nextPuzzleButton.setTextAlignment(TextAlignment.CENTER);
    Button prevPuzzleButton = createButton("Previous\nPuzzle", controller::clickPrevPuzzle);
    prevPuzzleButton.setTextAlignment(TextAlignment.CENTER);
    Button randomPuzzleButton = createButton("Random\nPuzzle", controller::clickRandPuzzle);
    randomPuzzleButton.setTextAlignment(TextAlignment.CENTER);
    Button resetPuzzleButton = createButton("Reset\nPuzzle", controller::clickResetPuzzle);
    resetPuzzleButton.setTextAlignment(TextAlignment.CENTER);

    controlBox.setPadding(new Insets(10, 10, 0, 10));
    controlBox
        .getChildren()
        .addAll(prevPuzzleButton, nextPuzzleButton, randomPuzzleButton, resetPuzzleButton);

    VBox rootBox = new VBox(controlBox);
    rootBox.setFillWidth(true);

    return rootBox;
  }

  private Button createButton(String text, Runnable action) {
    Button button = new Button(text);
    button.setWrapText(true);
    button.setPrefWidth(120);
    button.setOnAction(event -> action.run());
    return button;
  }
}
