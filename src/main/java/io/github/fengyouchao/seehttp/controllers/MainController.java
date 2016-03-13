package io.github.fengyouchao.seehttp.controllers;

import io.github.fengyouchao.seehttp.ApplicationManager;
import io.github.fengyouchao.seehttp.HttpPipeListener;
import io.github.fengyouchao.seehttp.models.HttpMessageModel;
import io.github.fengyouchao.seehttp.services.LoadCacheFileService;
import io.github.fengyouchao.seehttp.utils.ConfigurationUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sockslib.server.BasicSocksProxyServer;
import sockslib.server.SocksProxyServer;
import sockslib.server.SocksServerBuilder;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 09, 2016 9:22 AM
 */
public class MainController implements Initializable {

  @FXML
  private TextField portField;
  @FXML
  private Button proxyButton;
  @FXML
  private TableView<HttpMessageModel> messageTable;
  @FXML
  private TableColumn<HttpMessageModel, String> statusCol;
  @FXML
  private TableColumn<HttpMessageModel, String> timeCol;
  @FXML
  private TableColumn<HttpMessageModel, String> sourceCol;
  @FXML
  private TableColumn<HttpMessageModel, Integer> sourcePortCol;
  @FXML
  private TableColumn<HttpMessageModel, String> destinationCol;
  @FXML
  private TableColumn<HttpMessageModel, Integer> destinationPortCol;
  @FXML
  private TableColumn<HttpMessageModel, String> domainCol;
  @FXML
  private TableColumn<HttpMessageModel, String> methodCol;
  @FXML
  private TableColumn<HttpMessageModel, String> pathCol;
  @FXML
  private TableColumn<HttpMessageModel, String> versionCol;
  @FXML
  private TableColumn<HttpMessageModel, String> serverCol;
  @FXML
  private TableColumn<HttpMessageModel, String> titleCol;
  @FXML
  private TableColumn<HttpMessageModel, Integer> statusCodeCol;
  @FXML
  private TableColumn<HttpMessageModel, String> statusMessageCol;
  @FXML
  private TextArea requestTextTextArea;
  @FXML
  private TextArea requestHeaderTextArea;
  @FXML
  private TextArea requestHexTextArea;
  @FXML
  private TextArea responseTextTextArea;
  @FXML
  private TextArea responseHeaderTextArea;
  @FXML
  private TextArea responseHexTextArea;
  private final ObservableList<HttpMessageModel> messageTableData =
      FXCollections.observableArrayList();
  private SocksProxyServer proxyServer;
  private boolean proxyRunning = false;
  private LoadCacheFileService loadCacheFileService = new LoadCacheFileService();

  public void clearAction() {
    Platform.runLater(messageTableData::clear);
  }

  public void sendToRepeatView() {
    ApplicationManager.getApplication().showRepeater();
  }

  public void openBrowserAction(){
    String cmd = String.format("java -DsocksProxyHost=localhost -DsocksProxyPort=%s -jar plugins/browser/seehttp-browser.jar", portField.getText());
    try {
      Process ss = Runtime.getRuntime().exec(cmd);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }


  public void startProxyAction() {
    if (!proxyRunning) {
      int port = 1080;
      try {
        port = Integer.parseInt(portField.getText());
      } catch (NumberFormatException e) {
        new Alert(Alert.AlertType.ERROR, "Port Range(1~65535)").show();
        return;
      }
      proxyButton.setText("Starting");
      SocksServerBuilder builder =
          SocksServerBuilder.newSocks5ServerBuilder().setBindPort(port).setPipeInitializer(pipe -> {
            pipe.addPipeListener(new HttpPipeListener(messageTableData));
            return pipe;
          });
      builder.setExecutorService(Executors.newFixedThreadPool(20));
      proxyServer = builder.build();
      proxyServer.setDaemon(true);
      try {
        proxyServer.start();
        proxyRunning = true;
        proxyButton.setText("Stop");
      } catch (IOException e) {
        proxyButton.setText("Start");
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.CLOSE);
        alert.show();
      }
    } else {
      proxyButton.setText("Stopping");
      stopProxy();
      proxyButton.setText("Start");
    }

  }

  public void stopProxy() {
    if (proxyServer instanceof BasicSocksProxyServer) {
      ExecutorService executorService = ((BasicSocksProxyServer) proxyServer).getExecutorService();
      executorService.shutdownNow();
    }
    proxyServer.shutdown();
    proxyRunning = false;
  }


  @Override
  public void initialize(URL location, ResourceBundle resources) {

    requestTextTextArea.textProperty().bind(loadCacheFileService.requestTextProperty());
    requestHeaderTextArea.textProperty().bind(loadCacheFileService.requestHeaderProperty());
    requestHexTextArea.textProperty().bind(loadCacheFileService.requestHexProperty());
    responseTextTextArea.textProperty().bind(loadCacheFileService.responseTextProperty());
    responseHeaderTextArea.textProperty().bind(loadCacheFileService.responseTextProperty());
    responseHexTextArea.textProperty().bind(loadCacheFileService.responseHexProperty());
    //    indexCol.setCellValueFactory(new PropertyValueFactory<>("index"));
    timeCol.setVisible(false);
    statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
    timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
    sourceCol.setCellValueFactory(new PropertyValueFactory<>("source"));
    sourcePortCol.setCellValueFactory(new PropertyValueFactory<>("sourcePort"));
    destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
    destinationPortCol.setCellValueFactory(new PropertyValueFactory<>("destinationPort"));
    domainCol.setCellValueFactory(new PropertyValueFactory<>("domain"));
    methodCol.setCellValueFactory(new PropertyValueFactory<>("method"));
    pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));
    versionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
    serverCol.setCellValueFactory(new PropertyValueFactory<>("server"));
    titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
    statusCodeCol.setCellValueFactory(new PropertyValueFactory<>("statusCode"));
    statusMessageCol.setCellValueFactory(new PropertyValueFactory<>("statusMessage"));
    messageTable.setItems(messageTableData);
    messageTable.getSelectionModel().
        selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      ApplicationManager.setSelectedHttpMessageModel(newValue);
      loadCacheFileService.setHttpMessageModel(newValue);
      if (loadCacheFileService.getState() == Worker.State.READY) {
        loadCacheFileService.start();
      } else if (loadCacheFileService.getState() == Worker.State.SUCCEEDED) {
        loadCacheFileService.restart();
      }
    });
    initColumnConfiguration();
    HttpPipeListener.deleteCacheFiles();
  }

  public void initColumnConfiguration() {
    Properties properties = ConfigurationUtils.getProperties();
    ObservableList<TableColumn<HttpMessageModel, ?>> columns = messageTable.getColumns();

    for (int i = 0; i < columns.size(); i++) {
      TableColumn<HttpMessageModel, ?> column = columns.get(i);
      Object value = properties.get(getColumnIndexPropertyName(columns.get(i)));
      if(value instanceof String){
        int index = Integer.parseInt((String) value);
        messageTable.getColumns().remove(i);
        messageTable.getColumns().add(index,column);
      }

    }

    for (TableColumn column : messageTable.getColumns()) {
      boolean visible = true;
      Object value = properties.get(getColumnVisiblePropertyName(column));
      if (value instanceof String) {
        visible = Boolean.parseBoolean((String) value);
      }
      column.setVisible(visible);
      double width = 110;
      value = properties.get(getColumnWidthPropertyName(column));
      if (value instanceof String) {
        width = Double.parseDouble((String) value);
      }
      column.setPrefWidth(width);

      column.visibleProperty().addListener(new ColumnVisibleChangedListener(column));
      column.widthProperty().addListener(new ColumnWidthChangedListener(column));
    }
    messageTable.getColumns().addListener((ListChangeListener<TableColumn<HttpMessageModel, ?>>)
        c -> {
      ObservableList<? extends TableColumn<HttpMessageModel, ?>> list = c.getList();
      for (int i = 0; i < list.size(); i++) {
        ConfigurationUtils.getProperties().put(getColumnIndexPropertyName(list.get(i)), i);
      }
      ConfigurationUtils.store();
    });
  }


  class ColumnVisibleChangedListener implements ChangeListener<Boolean> {

    private TableColumn column;

    public ColumnVisibleChangedListener(TableColumn column) {
      this.column = column;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean
        newValue) {
      ConfigurationUtils.getProperties().put(getColumnVisiblePropertyName(column), newValue);
      ConfigurationUtils.store();
    }
  }


  class ColumnWidthChangedListener implements ChangeListener<Number> {
    private TableColumn column;

    public ColumnWidthChangedListener(TableColumn column) {
      this.column = column;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number
        newValue) {
      ConfigurationUtils.getProperties().put(getColumnWidthPropertyName(column), newValue);
      ConfigurationUtils.store();
    }
  }

  private String getColumnWidthPropertyName(TableColumn column) {
    return "main.view." + messageTable.getId() + "." + column.getId() + ".width";
  }

  private String getColumnVisiblePropertyName(TableColumn column) {
    return "main.view." + messageTable.getId() + "." + column.getId() + ".visible";
  }

  private String getColumnIndexPropertyName(TableColumn column) {
    return "main.view." + messageTable.getId() + "." + column.getId() + ".index";
  }


}
