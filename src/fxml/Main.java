package fxml;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {
  @FXML
  private WebView webView;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    WebEngine webEngine = webView.getEngine();
    webEngine.load("https://twitter.com");
    System.out.print("[log] web browser 起動");
  }
}
