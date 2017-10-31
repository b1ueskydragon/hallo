package fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class _FxmlTestCrypted_ extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent root = FXMLLoader.load(getClass().getResource("Crypted.fxml"));
    Scene scene = new Scene(root);

    primaryStage.setTitle("hallo");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  public static void main(String [] args) {
    new JFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    launch(args);
  }
}
