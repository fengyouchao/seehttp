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
  private final StringProperty time = new SimpleStringProperty();
  private final StringProperty source = new SimpleStringProperty();
  private final IntegerProperty sourcePort = new SimpleIntegerProperty();
  private final StringProperty destination = new SimpleStringProperty();
  private final IntegerProperty destinationPort = new SimpleIntegerProperty();
  private final StringProperty domain = new SimpleStringProperty();
  private final StringProperty method = new SimpleStringProperty();
  private final StringProperty path = new SimpleStringProperty();
  private final StringProperty version = new SimpleStringProperty();
  private final IntegerProperty statusCode = new SimpleIntegerProperty();
  private final StringProperty statusMessage = new SimpleStringProperty();
  private String httpRequestObjectPath;
  private String httpResponseObjectPath;

  public HttpMessageModel() {
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

  public String getSource() {
    return source.get();
  }

  public StringProperty sourceProperty() {
    return source;
  }

  public void setSource(String source) {
    this.source.set(source);
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

  public int getIndex() {
    return index.get();
  }

  public IntegerProperty indexProperty() {
    return index;
  }

  public void setIndex(int index) {
    this.index.set(index);
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

  public int getStatusCode() {
    return statusCode.get();
  }

  public IntegerProperty statusCodeProperty() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode.set(statusCode);
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

  public String getVersion() {
    return version.get();
  }

  public StringProperty versionProperty() {
    return version;
  }

  public void setVersion(String version) {
    this.version.set(version);
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
}
