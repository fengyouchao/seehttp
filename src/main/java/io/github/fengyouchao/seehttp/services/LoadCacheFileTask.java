package io.github.fengyouchao.seehttp.services;

import io.github.fengyouchao.httpparse.HttpRequest;
import io.github.fengyouchao.httpparse.HttpResponse;
import io.github.fengyouchao.seehttp.models.HttpMessageModel;
import io.github.fengyouchao.seehttp.utils.HexUtils;
import io.github.fengyouchao.seehttp.utils.PersistObjectUtils;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;

/**
 * The class <code></code>
 *
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 03,2016 4:18 PM
 */
public class LoadCacheFileTask extends Task<Void> {

  private StringProperty requestText = new SimpleStringProperty();
  private StringProperty requestHeader = new SimpleStringProperty();
  private StringProperty requestHex = new SimpleStringProperty();
  private StringProperty responseText = new SimpleStringProperty();
  private StringProperty responseHeader = new SimpleStringProperty();
  private StringProperty responseHex = new SimpleStringProperty();
  private HttpMessageModel httpMessageModel;

  public LoadCacheFileTask(HttpMessageModel httpMessageModel) {
    this.httpMessageModel = httpMessageModel;
  }

  @Override
  protected Void call() throws Exception {
    if (httpMessageModel != null) {
      String httpRequestObjectPath = httpMessageModel.getHttpRequestObjectPath();
      String httpResponseObjectPath = httpMessageModel.getHttpResponseObjectPath();

      if (httpRequestObjectPath != null) {
        HttpRequest request = PersistObjectUtils.read(httpRequestObjectPath, HttpRequest.class);
        Platform.runLater(() -> {
          requestText.set(request.toString());
          requestHeader.set(request.headerString());
          requestHex.set(HexUtils.hexString(request.getBody()));
        });
      }
      if (httpResponseObjectPath != null) {
        HttpResponse response = PersistObjectUtils.read(httpResponseObjectPath, HttpResponse.class);
        Platform.runLater(() -> {
          responseText.set(response.toString());
          responseHeader.set(response.headerString());
          responseHex.set(HexUtils.hexString(response.getBody()));
        });
      }
    }
    return null;
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

}
