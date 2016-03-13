package io.github.fengyouchao.seehttp.services;

import io.github.fengyouchao.httpparse.HttpRequest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 10, 2016 11:00 AM
 */
public class SendHttpRequestService extends Service<String> {

  private HttpRequest httpRequest;
  private String host;
  private int port;
  private StringProperty headerValue = new SimpleStringProperty();
  private StringProperty bodyHex = new SimpleStringProperty();
  private StringProperty buttonText = new SimpleStringProperty("Send");
  private StringProperty responseTimeText = new SimpleStringProperty();

  @Override
  protected Task<String> createTask() {
    SendHttpRequestTask task = new SendHttpRequestTask(httpRequest, host, port);
    headerValue.unbind();
    headerValue.bind(task.headerValueProperty());
    bodyHex.unbind();
    bodyHex.bind(task.bodyHexProperty());
    buttonText.unbind();
    buttonText.bind(task.buttonTextProperty());
    responseTimeText.unbind();
    responseTimeText.bind(task.responseTimeTextProperty());
    return task;

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
