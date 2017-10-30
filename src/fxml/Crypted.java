package fxml;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import java.awt.*;

public class Crypted {
  @FXML
  private Parent parent;
  @FXML
  private TextField textField;
  @FXML
  private Button button;
  // TODO 本当は fx:id 名前指定したい

  public void handleInputAction(ActionEvent event) {
    String key = textField.getText();
    System.out.println(key);
  }


}
