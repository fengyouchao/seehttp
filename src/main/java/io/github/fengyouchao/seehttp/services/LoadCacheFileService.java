package io.github.fengyouchao.seehttp.services;

import io.github.fengyouchao.seehttp.models.HttpMessageModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * The class <code></code>
 *
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 03,2016 4:17 PM
 */
public class LoadCacheFileService extends Service<Void> {
  private StringProperty requestText = new SimpleStringProperty();
  private StringProperty requestHeader = new SimpleStringProperty();
  private StringProperty requestHex = new SimpleStringProperty();
  private StringProperty responseText = new SimpleStringProperty();
  private StringProperty responseHeader = new SimpleStringProperty();
  private StringProperty responseHex = new SimpleStringProperty();
  private HttpMessageModel httpMessageModel;


  @Override
  protected Task<Void> createTask() {
    LoadCacheFileTask task = new LoadCacheFileTask(httpMessageModel);
    requestText.unbind();
    requestHeader.unbind();
    requestHex.unbind();
    responseText.unbind();
    responseHeader.unbind();
    responseHex.unbind();
    requestText.bind(task.requestTextProperty());
    requestHeader.bind(task.requestHeaderProperty());
    requestHex.bind(task.requestHexProperty());
    responseText.bind(task.responseTextProperty());
    responseHeader.bind(task.responseHeaderProperty());
    responseHex.bind(task.responseHexProperty());
    return task;
  }

  public String getRequestText() {
    return requestText.get();
  }

  public StringProperty requestTextProperty() {
    return requestText;
  }

  public void setRequestText(String requestText) {
    this.requestText.set(requestText);
  }

  public String getRequestHeader() {
    return requestHeader.get();
  }

  public StringProperty requestHeaderProperty() {
    return requestHeader;
  }

  public void setRequestHeader(String requestHeader) {
    this.requestHeader.set(requestHeader);
  }

  public String getRequestHex() {
    return requestHex.get();
  }

  public StringProperty requestHexProperty() {
    return requestHex;
  }

  public void setRequestHex(String requestHex) {
    this.requestHex.set(requestHex);
  }

  public String getResponseText() {
    return responseText.get();
  }

  public StringProperty responseTextProperty() {
    return responseText;
  }

  public void setResponseText(String responseText) {
    this.responseText.set(responseText);
  }

  public String getResponseHeader() {
    return responseHeader.get();
  }

  public StringProperty responseHeaderProperty() {
    return responseHeader;
  }

  public void setResponseHeader(String responseHeader) {
    this.responseHeader.set(responseHeader);
  }

  public String getResponseHex() {
    return responseHex.get();
  }

  public StringProperty responseHexProperty() {
    return responseHex;
  }

  public void setResponseHex(String responseHex) {
    this.responseHex.set(responseHex);
  }

  public HttpMessageModel getHttpMessageModel() {
    return httpMessageModel;
  }

  public void setHttpMessageModel(HttpMessageModel httpMessageModel) {
    this.httpMessageModel = httpMessageModel;
  }
}
