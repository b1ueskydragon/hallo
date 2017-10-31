package fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import tools.StringUtility;


public class Crypted {
  @FXML
  private Parent root;
  @FXML
  private TextField inputKey;
  @FXML
  private Button button;

  String commonKey = "aaa";

  public void handleInputAction() {
    String userKey = inputKey.getText();
    System.out.println(userKey);
    Alert alert;
    if (StringUtility.isNullOrEmpty(this.commonKey)) return;
    if (this.commonKey.equals(userKey)) {
      alert = new Alert(Alert.AlertType.INFORMATION);
      alert.setTitle("Success");
      alert.setHeaderText("Decryption Start");
      alert.showAndWait();
    } else {
      alert = new Alert(Alert.AlertType.ERROR);
      alert.setTitle("Failed");
      alert.setHeaderText("だめだめ");
      alert.showAndWait();
    }
  }


}
