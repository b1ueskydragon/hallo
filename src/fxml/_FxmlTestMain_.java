package fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class _FxmlTestMain_ extends Application{

  @Override
  public void start(Stage primaryStage) throws Exception {

    Parent parent = FXMLLoader.load(getClass().getResource("Main.fxml"));
    Scene scene = new Scene(parent);

    primaryStage.setTitle("Twitterへログイン");
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  public static void main(String [] args) {
    new JFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    launch(args);
  }
}
