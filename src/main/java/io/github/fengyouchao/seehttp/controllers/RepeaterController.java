package io.github.fengyouchao.seehttp.controllers;

import io.github.fengyouchao.httpparse.HttpMethod;
import io.github.fengyouchao.httpparse.HttpParseException;
import io.github.fengyouchao.httpparse.HttpRequest;
import io.github.fengyouchao.httpparse.HttpRequestBuilder;
import io.github.fengyouchao.httpparse.HttpResponse;
import io.github.fengyouchao.seehttp.ApplicationManager;
import io.github.fengyouchao.seehttp.HexUtils;
import io.github.fengyouchao.seehttp.PersistObjectUtils;
import io.github.fengyouchao.seehttp.models.HttpMessageModel;
import io.github.fengyouchao.seehttp.utils.HttpClient;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The class <code></code>
 *
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 03,2016 10:16 PM
 */
public class RepeaterController implements Initializable {

  private static final Logger logger = LoggerFactory.getLogger(RepeaterController.class);

  @FXML
  private ChoiceBox<String> methodChoiceBox;

  @FXML
  private Button sendButton;
  @FXML
  private TextArea bodyTextArea;

  @FXML
  private TextArea headerTextArea;

  @FXML
  private TextField hostTextField;

  @FXML
  private TextField pathTextField;

  @FXML
  TextField portTextField;


  @FXML
  private TextArea responseTextTextArea;
  @FXML
  private TextArea responseHeaderTextArea;
  @FXML
  private TextArea responseHexTextArea;

  public void sendRequestAction() {
    sendButton.setText("Sending");
    responseTextTextArea.setText("");
    HttpMethod method = HttpMethod.valueOf(methodChoiceBox.getValue());
    String host = hostTextField.getText().trim();
    int port = Integer.parseInt(portTextField.getText().trim());
    String path = pathTextField.getText();
    String body = bodyTextArea.getText();
    String headers = headerTextArea.getText();
    HttpRequestBuilder builder = new HttpRequestBuilder().setMethod(method).setPath(path);
    for (String header : headers.split("\n")) {
      if (!header.equals("")) {
        String[] nameValue = header.split(":", 2);
        if (nameValue.length == 2) {
          builder.setHeader(nameValue[0], nameValue[1]);
        } else {
          logger.error("wrong header:" + header);
        }
      }
    }
    if (body.length() > 0) {
      builder.setBody(body.getBytes());
    }
    HttpRequest httpRequest = builder.build();
    try {
      HttpResponse httpResponse = HttpClient.request(httpRequest, host, port);
      responseTextTextArea.setText(httpResponse.toString());
      responseHeaderTextArea.setText(httpResponse.headerString());
      responseHexTextArea.setText(HexUtils.hexString(httpResponse.getBody()));
    } catch (IOException | HttpParseException e) {
      e.printStackTrace();
    }
    sendButton.setText("Send");
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    methodChoiceBox.setItems(FXCollections.observableArrayList("GET", "POST", "PUT", "PATCH",
        "DELETE", "HEAD", "OPTIONS"));
    methodChoiceBox.setValue("GET");
    methodChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue,
        newValue) -> {
      if (newValue.equals("GET") || newValue.equals("HEAD")) {
        bodyTextArea.setEditable(false);
        bodyTextArea.setDisable(true);
      } else {
        bodyTextArea.setEditable(true);
        bodyTextArea.setDisable(false);
      }
    });
    HttpMessageModel httpMessageModel = ApplicationManager.getSelectedHttpMessageModel();
    if(httpMessageModel != null){
      hostTextField.setText(httpMessageModel.getDestination());
      portTextField.setText(String.valueOf(httpMessageModel.getDestinationPort()));
      pathTextField.setText(httpMessageModel.getPath());
      HttpRequest request = PersistObjectUtils.read(httpMessageModel.getHttpRequestObjectPath(), HttpRequest.class);
      methodChoiceBox.setValue(request.getMethod().toString());
      headerTextArea.setText(request.headerString());
      bodyTextArea.setText(request.getText());
      ApplicationManager.setSelectedHttpMessageModel(null);
    }
  }
}
