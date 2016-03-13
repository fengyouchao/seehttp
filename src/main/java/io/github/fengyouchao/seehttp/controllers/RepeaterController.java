package io.github.fengyouchao.seehttp.controllers;

import io.github.fengyouchao.httpparse.HttpMethod;
import io.github.fengyouchao.httpparse.HttpRequest;
import io.github.fengyouchao.httpparse.HttpRequestBuilder;
import io.github.fengyouchao.seehttp.ApplicationManager;
import io.github.fengyouchao.seehttp.models.HttpMessageModel;
import io.github.fengyouchao.seehttp.services.SendHttpRequestService;
import io.github.fengyouchao.seehttp.utils.PersistObjectUtils;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private TextField portTextField;


  @FXML
  private Region veilRegion;

  @FXML
  private ProgressIndicator progressIndicator;


  @FXML
  private TextArea responseTextTextArea;
  @FXML
  private TextArea responseHeaderTextArea;
  @FXML
  private TextArea responseHexTextArea;
  @FXML
  Label responseTimeLabel;

  private SendHttpRequestService sendHttpRequestService = new SendHttpRequestService();

  public void sendRequestAction() {
    HttpMethod method = HttpMethod.valueOf(methodChoiceBox.getValue());
    String host = hostTextField.getText().trim();
    int port = 80;
    try {
      port = Integer.parseInt(portTextField.getText().trim());
    } catch (NumberFormatException e) {
      new Alert(Alert.AlertType.ERROR, "Port Range(1~65535)").show();
      return;
    }
    String path = pathTextField.getText();
    String body = bodyTextArea.getText();
    String headers = headerTextArea.getText();
    if (host.equals("")) {
      new Alert(Alert.AlertType.ERROR, "IP can't be empty").show();
      return;
    }
    if (path.equals("")) {
      new Alert(Alert.AlertType.ERROR, "Path can't be empty").show();
      return;
    }
    HttpRequestBuilder builder = new HttpRequestBuilder().setMethod(method).setPath(path);
    for (String header : headers.split("\n")) {
      if (!header.equals("")) {
        String[] nameValue = header.split(":", 2);
        if (nameValue.length == 2) {
          builder.setHeader(nameValue[0], nameValue[1]);
        } else {
          new Alert(Alert.AlertType.ERROR, "Error Header:" + header).show();
          logger.error("wrong header:" + header);
          return;
        }
      }
    }
    if (body.length() > 0) {
      builder.setBody(body.getBytes());
    }
    HttpRequest httpRequest = builder.build();
    sendHttpRequestService.setHttpRequest(httpRequest);
    sendHttpRequestService.setHost(host);
    sendHttpRequestService.setPort(port);
    if (sendHttpRequestService.getState() == Worker.State.READY) {
      sendHttpRequestService.start();
    } else if (sendHttpRequestService.getState() == Worker.State.SUCCEEDED) {
      sendHttpRequestService.restart();
    }
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {
    veilRegion.setVisible(false);
    progressIndicator.setVisible(false);
    progressIndicator.visibleProperty().bind(sendHttpRequestService.runningProperty());
    veilRegion.visibleProperty().bind(sendHttpRequestService.runningProperty());
    responseTextTextArea.textProperty().bind(sendHttpRequestService.valueProperty());
    responseHeaderTextArea.textProperty().bind(sendHttpRequestService.headerValueProperty());
    responseHexTextArea.textProperty().bind(sendHttpRequestService.bodyHexProperty());
    sendButton.textProperty().bind(sendHttpRequestService.buttonTextProperty());
    sendButton.disableProperty().bind(sendHttpRequestService.runningProperty());
    responseTimeLabel.textProperty().bind(sendHttpRequestService.responseTimeTextProperty());
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
    if (httpMessageModel != null) {
      hostTextField.setText(httpMessageModel.getDestination());
      portTextField.setText(String.valueOf(httpMessageModel.getDestinationPort()));
      pathTextField.setText(httpMessageModel.getPath());
      HttpRequest request =
          PersistObjectUtils.read(httpMessageModel.getHttpRequestObjectPath(), HttpRequest.class);
      methodChoiceBox.setValue(request.getMethod().toString());
      headerTextArea.setText(request.headerString());
      bodyTextArea.setText(request.getText());
      ApplicationManager.setSelectedHttpMessageModel(null);
    }
  }
}
