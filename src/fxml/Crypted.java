package fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class Crypted {
  @FXML
  private Parent root;
  @FXML
  private TextField inputKey;
  @FXML
  private Button button;
  // TODO 本当は fx:id 名前指定したい

  public void handleInputAction(ActionEvent event) {
    String key = inputKey.getText();
    System.out.println(key);
  }


}
