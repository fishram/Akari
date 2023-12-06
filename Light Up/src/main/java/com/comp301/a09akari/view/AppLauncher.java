package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.ModelImpl;
import com.comp301.a09akari.model.PuzzleImpl;
import com.comp301.a09akari.model.PuzzleLibraryImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage primaryStage) {
    PuzzleLibraryImpl library = new PuzzleLibraryImpl();
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));

    ModelImpl model = new ModelImpl(library);
    ControllerImpl controller = new ControllerImpl(model);

    FXComponent titleView = new TitleView(controller);

    VBox root = new VBox();
    root.getChildren().add(titleView.render()); // Initially, show only the title view

    Scene scene = new Scene(root, 600, 800);
    scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Akari Puzzle Game");

    model.addObserver(
        (updatedModel) -> {
          if (updatedModel.isShowingTitleScreen()) {
            root.getChildren().setAll(new TitleView(controller).render());
          } else {
            root.getChildren()
                .setAll(
                    new PuzzleView(controller).render(),
                    new MessageView(controller).render(),
                    new ControlView(controller).render());
          }
        });

    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
