package io.github.fengyouchao.seehttp.services;

import io.github.fengyouchao.httpparse.HttpRequest;
import io.github.fengyouchao.httpparse.HttpResponse;
import io.github.fengyouchao.seehttp.utils.HexUtils;
import io.github.fengyouchao.seehttp.utils.HttpClient;
import io.github.fengyouchao.seehttp.utils.RequestResult;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

/**
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 10, 2016 11:03 AM
 */
public class SendHttpRequestTask extends Task<String> {

  private HttpRequest httpRequest;
  private String host;
  private int port;
  private StringProperty headerValue = new SimpleStringProperty();
  private StringProperty bodyHex = new SimpleStringProperty();
  private StringProperty buttonText = new SimpleStringProperty("Send");
  private StringProperty responseTimeText = new SimpleStringProperty();

  public SendHttpRequestTask(HttpRequest httpRequest, String host, int port) {
    this.httpRequest = httpRequest;
    this.host = host;
    this.port = port;
  }

  @Override
  protected String call() throws Exception {
    Platform.runLater(() -> buttonText.set("Sending"));
    try {
      RequestResult result = HttpClient.request(httpRequest, host, port);
      HttpResponse httpResponse = result.getResponse();
      Platform.runLater(() -> responseTimeText.set(result.getResponseTime() + "ms"));
      Platform.runLater(() -> buttonText.set("Send"));
      Platform.runLater(() -> headerValue.set(httpResponse.headerString()));
      Platform.runLater(() -> bodyHex.set(HexUtils.hexString(httpResponse.getBody())));
      return httpResponse.toString();
    } catch (Exception e) {
      Platform.runLater(() -> buttonText.set("Send"));
      Platform.runLater(() -> headerValue.set(""));
      Platform.runLater(() -> bodyHex.set(""));
      Platform.runLater(() -> new Alert(Alert.AlertType.ERROR, e.getMessage()).show());
      return "";
    }
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public HttpRequest getHttpRequest() {
    return httpRequest;
  }

  public void setHttpRequest(HttpRequest httpRequest) {
    this.httpRequest = httpRequest;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getHeaderValue() {
    return headerValue.get();
  }

  public StringProperty headerValueProperty() {
    return headerValue;
  }

  public void setHeaderValue(String headerValue) {
    this.headerValue.set(headerValue);
  }

  public String getBodyHex() {
    return bodyHex.get();
  }

  public StringProperty bodyHexProperty() {
    return bodyHex;
  }

  public void setBodyHex(String bodyHex) {
    this.bodyHex.set(bodyHex);
  }

  public String getButtonText() {
    return buttonText.get();
  }

  public StringProperty buttonTextProperty() {
    return buttonText;
  }

  public void setButtonText(String buttonText) {
    this.buttonText.set(buttonText);
  }

  public String getResponseTimeText() {
    return responseTimeText.get();
  }

  public StringProperty responseTimeTextProperty() {
    return responseTimeText;
  }

  public void setResponseTimeText(String responseTimeText) {
    this.responseTimeText.set(responseTimeText);
  }
}
