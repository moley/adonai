package org.adonai.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloWorld extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.setTitle("Hello World!");
    Button btn = new Button();
    btn.setText("Say 'Hello World'");
    btn.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        System.out.println("Hello World!");
      }
    });

    btn.setOnKeyReleased(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        System.out.println (event.getCode() + "-" + event.getText() + "- Shift " + event.isShiftDown()
          + "- Meta " + event.isMetaDown() + "- Control " + event.isControlDown() + "- Alt " + event.isAltDown() + "- SHortcut " + event.isShortcutDown());
        if (event.isControlDown() && event.getCode() == KeyCode.RIGHT) {
          System.out.println ("Control and right pressed");
        }

        if (event.isControlDown() && event.getCode() == KeyCode.LEFT) {
          System.out.println ("Control and left pressed");
        }

      }
    });

    StackPane root = new StackPane();
    root.getChildren().add(btn);
    primaryStage.setScene(new Scene(root, 300, 250));
    primaryStage.show();
  }
}