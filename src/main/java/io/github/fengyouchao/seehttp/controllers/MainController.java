package io.github.fengyouchao.seehttp.controllers;

import io.github.fengyouchao.httpparse.HttpRequest;
import io.github.fengyouchao.httpparse.HttpResponse;
import io.github.fengyouchao.seehttp.ApplicationManager;
import io.github.fengyouchao.seehttp.HexUtils;
import io.github.fengyouchao.seehttp.HttpPipeListener;
import io.github.fengyouchao.seehttp.PersistObjectUtils;
import io.github.fengyouchao.seehttp.models.HttpMessageModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.cell.PropertyValueFactory;
import sockslib.server.BasicSocksProxyServer;
import sockslib.server.SocksProxyServer;
import sockslib.server.SocksServerBuilder;

import java.io.IOException;
import java.net.URL;
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
  private ToggleButton proxyButton;
  @FXML
  private TableView<HttpMessageModel> messageTable;
  @FXML
  private TableColumn<HttpMessageModel, Integer> indexCol;
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

  public void clearAction() {
    messageTableData.clear();
    if (messageTable == null) {
      System.out.println("=====");
    }
    HttpPipeListener.index = 0;
  }

  public void sendToRepeatView() {
    TableView.TableViewSelectionModel<HttpMessageModel> selectionMode =
        messageTable.getSelectionModel();
    int index = selectionMode.getSelectedIndex();
    if (index != -1) {
      ApplicationManager.setSelectedHttpMessageModel(selectionMode.getSelectedItem());
    }
    ApplicationManager.getApplication().showRepeater();
  }


  public void startProxyAction() throws IOException {
    int port = Integer.parseInt(portField.getText());
    if (!proxyRunning) {
      proxyButton.setText("正在启动");
      SocksServerBuilder builder =
          SocksServerBuilder.newSocks5ServerBuilder().setBindPort(port).setPipeInitializer(pipe -> {
            pipe.addPipeListener(new HttpPipeListener(messageTableData));
            return pipe;
          });
      builder.setExecutorService(Executors.newFixedThreadPool(20));
      proxyServer = builder.build();
      proxyServer.setDaemon(true);
      proxyServer.start();
      proxyRunning = true;
      proxyButton.setText("终止代理");
    } else {
      proxyButton.setText("正在停止");
      stopProxy();
      proxyButton.setText("启动");
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

    indexCol.setCellValueFactory(new PropertyValueFactory<>("index"));
    timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
    sourceCol.setCellValueFactory(new PropertyValueFactory<>("source"));
    sourcePortCol.setCellValueFactory(new PropertyValueFactory<>("sourcePort"));
    destinationCol.setCellValueFactory(new PropertyValueFactory<>("destination"));
    destinationPortCol.setCellValueFactory(new PropertyValueFactory<>("destinationPort"));
    domainCol.setCellValueFactory(new PropertyValueFactory<>("domain"));
    methodCol.setCellValueFactory(new PropertyValueFactory<>("method"));
    pathCol.setCellValueFactory(new PropertyValueFactory<>("path"));
    versionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
    statusCodeCol.setCellValueFactory(new PropertyValueFactory<>("statusCode"));
    statusMessageCol.setCellValueFactory(new PropertyValueFactory<>("statusMessage"));
    messageTable.setItems(messageTableData);
    TableView.TableViewSelectionModel<HttpMessageModel> selectionModel =
        messageTable.getSelectionModel();
    selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue != null) {
        HttpRequest request =
            PersistObjectUtils.read(newValue.getHttpRequestObjectPath(), HttpRequest.class);
        HttpResponse response =
            PersistObjectUtils.read(newValue.getHttpResponseObjectPath(), HttpResponse.class);
        requestTextTextArea.setText(request.toString());
        requestHeaderTextArea.setText(request.headerString());
        requestHexTextArea.setText(HexUtils.hexString(request.getBody()));
        responseTextTextArea.setText(response.toString());
        responseHeaderTextArea.setText(response.headerString());
        responseHexTextArea.setText(HexUtils.hexString(response.getBody()));
      } else {
        requestTextTextArea.setText("");
        requestHeaderTextArea.setText("");
        requestHexTextArea.setText("");
        responseTextTextArea.setText("");
        responseHeaderTextArea.setText("");
        responseHexTextArea.setText("");
      }
      //todo
    });
  }


}
