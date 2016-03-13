package io.github.fengyouchao.seehttp.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 09, 2016 9:56 AM
 */
public class HttpMessageModel {

  private final IntegerProperty index = new SimpleIntegerProperty();
  private final StringProperty status = new SimpleStringProperty();
  private final StringProperty time = new SimpleStringProperty();
  private final StringProperty source = new SimpleStringProperty();
  private final IntegerProperty sourcePort = new SimpleIntegerProperty();
  private final StringProperty destination = new SimpleStringProperty();
  private final IntegerProperty destinationPort = new SimpleIntegerProperty();
  private final StringProperty domain = new SimpleStringProperty();
  private final StringProperty method = new SimpleStringProperty();
  private final StringProperty path = new SimpleStringProperty();
  private final StringProperty version = new SimpleStringProperty();
  private final StringProperty server = new SimpleStringProperty();
  private final StringProperty title = new SimpleStringProperty();
  private final StringProperty statusCode = new SimpleStringProperty();
  private final StringProperty statusMessage = new SimpleStringProperty();
  private String httpRequestObjectPath;
  private String httpResponseObjectPath;

  public HttpMessageModel() {
  }

  public int getIndex() {
    return index.get();
  }

  public IntegerProperty indexProperty() {
    return index;
  }

  public void setIndex(int index) {
    this.index.set(index);
  }

  public String getStatus() {
    return status.get();
  }

  public StringProperty statusProperty() {
    return status;
  }

  public void setStatus(String status) {
    this.status.set(status);
  }

  public String getTime() {
    return time.get();
  }

  public StringProperty timeProperty() {
    return time;
  }

  public void setTime(String time) {
    this.time.set(time);
  }

  public String getSource() {
    return source.get();
  }

  public StringProperty sourceProperty() {
    return source;
  }

  public void setSource(String source) {
    this.source.set(source);
  }

  public int getSourcePort() {
    return sourcePort.get();
  }

  public IntegerProperty sourcePortProperty() {
    return sourcePort;
  }

  public void setSourcePort(int sourcePort) {
    this.sourcePort.set(sourcePort);
  }

  public void setStatusCode(String statusCode) {
    this.statusCode.set(statusCode);
  }

  public String getDestination() {
    return destination.get();
  }

  public StringProperty destinationProperty() {
    return destination;
  }

  public void setDestination(String destination) {
    this.destination.set(destination);
  }

  public int getDestinationPort() {
    return destinationPort.get();
  }

  public IntegerProperty destinationPortProperty() {
    return destinationPort;
  }

  public void setDestinationPort(int destinationPort) {
    this.destinationPort.set(destinationPort);
  }

  public String getDomain() {
    return domain.get();
  }

  public StringProperty domainProperty() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain.set(domain);
  }

  public String getMethod() {
    return method.get();
  }

  public StringProperty methodProperty() {
    return method;
  }

  public void setMethod(String method) {
    this.method.set(method);
  }

  public String getPath() {
    return path.get();
  }

  public StringProperty pathProperty() {
    return path;
  }

  public void setPath(String path) {
    this.path.set(path);
  }

  public String getVersion() {
    return version.get();
  }

  public StringProperty versionProperty() {
    return version;
  }

  public void setVersion(String version) {
    this.version.set(version);
  }

  public String getServer() {
    return server.get();
  }

  public StringProperty serverProperty() {
    return server;
  }

  public void setServer(String server) {
    this.server.set(server);
  }

  public String getStatusCode() {
    return statusCode.get();
  }

  public StringProperty statusCodeProperty() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode.set(String.valueOf(statusCode));
  }

  public String getStatusMessage() {
    return statusMessage.get();
  }

  public StringProperty statusMessageProperty() {
    return statusMessage;
  }

  public void setStatusMessage(String statusMessage) {
    this.statusMessage.set(statusMessage);
  }

  public String getHttpRequestObjectPath() {
    return httpRequestObjectPath;
  }

  public void setHttpRequestObjectPath(String httpRequestObjectPath) {
    this.httpRequestObjectPath = httpRequestObjectPath;
  }

  public String getHttpResponseObjectPath() {
    return httpResponseObjectPath;
  }

  public void setHttpResponseObjectPath(String httpResponseObjectPath) {
    this.httpResponseObjectPath = httpResponseObjectPath;
  }

  public String getTitle() {
    return title.get();
  }

  public StringProperty titleProperty() {
    return title;
  }

  public void setTitle(String title) {
    this.title.set(title);
  }
}
