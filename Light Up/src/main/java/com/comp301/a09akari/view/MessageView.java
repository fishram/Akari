package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ControllerImpl;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text; // Import the Text class

public class MessageView implements FXComponent {
  private final ControllerImpl controller;

  public MessageView(ControllerImpl controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox messageBox = new VBox(10);
    messageBox.setAlignment(Pos.CENTER);
    messageBox.getStyleClass().add("message-box");
    messageBox.setPadding(new Insets(12, 0, 12, 0));

    Text messageText = new Text();
    messageText.getStyleClass().add("message-text");

    if (controller.isSolved()) {
      messageText.setText("Nice! Puzzle Solved!");
      messageText.getStyleClass().add("congrats-message");
    } else {
      messageText.setText("");
      messageText.getStyleClass().remove("congrats-message");
    }

    messageBox.getChildren().add(messageText);

    return messageBox;
  }
}
