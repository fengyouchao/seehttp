/*
 * Main.java
 * orderDishesSystem
 * Created by 冯 友超 on 13-6-26.
 * Copyright (c) 2013年 冯 友超. All rights reserved.
 */
package io.github.fengyouchao.seehttp;


import io.github.fengyouchao.seehttp.controllers.MainController;
import io.github.fengyouchao.seehttp.utils.ConfigurationUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * <code>Application</code>类是整个系统的启动类，它控制着系统各个界面
 * 的跳转。
 *
 * @author fengyouchao
 * @version 1.0
 * @see MainController
 * @since JDK8
 */
public class Application extends javafx.application.Application {

  private final static Logger log = LoggerFactory.getLogger(Application.class);

  private Stage mainStage;              //登录界面和主界面的Stage。
  private Stage modalStage;              //模式窗口的Stage
  private MainController mainController;

  @Override
  public void start(Stage stage) throws Exception {
    init(stage);
  }


  /**
   * 初始化。
   *
   * @param stage 来自start方法中的Stage
   */
  private void init(Stage stage) {
    ConfigurationUtils.init();
    ApplicationManager.setApplication(this);
    this.mainStage = stage;
    lanuchMainView();
    stage.setTitle("SeeHTTP");
    stage.show();
  }


  @Override
  public void stop() throws Exception {
    System.exit(0);
    super.stop();
  }

  /**
   * 显示主界面
   */
  public void lanuchMainView() {
    log.info("lanuch MainView");
    try {
      MainController mainController = (MainController) replaceSceneContent("/views/main.fxml");
      this.mainController = mainController;
      mainStage.setResizable(true);
      mainStage.setMinWidth(500);
      mainStage.setMinHeight(300);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

  public void showRepeater(){
    showView("/views/repeat.fxml");
    modalStage.setTitle("Repeater");
  }

  /**
   * 显示一个模式窗口。
   *
   * @param fxmlPath 需要显示的界面的fxml文件路径。
   */
  private void showModalView(String fxmlPath) {
    final Stage stage = new Stage();
    this.modalStage = stage;
    Parent root = null;
    try {
      root = FXMLLoader.load(getClass().getResource(fxmlPath));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.initOwner(this.mainStage);
    stage.show();
  }

  private void showView(String fxmlPath){
    final Stage stage = new Stage();
    this.modalStage = stage;
    Parent root = null;
    try {
      root = FXMLLoader.load(getClass().getResource(fxmlPath));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
    Scene scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * 替换布景的内容。
   *
   * @param fxml 需要显示界面的fxml文件路径。
   * @return Initializable
   * @throws Exception
   */
  private Initializable replaceSceneContent(String fxml) throws Exception {
    FXMLLoader loader = new FXMLLoader();
    InputStream in = Application.class.getResourceAsStream(fxml);
    loader.setBuilderFactory(new JavaFXBuilderFactory());
    loader.setLocation(Application.class.getResource(fxml));
    //		loader.setResources(ResourceBundle.getBundle(Constant.LANGUAGE_PACKAGE_NAME));
    AnchorPane page;
    try {
      page = (AnchorPane) loader.load(in);
    } finally {
      in.close();
    }

    Scene scene = new Scene(page);
    mainStage.setScene(scene);
    mainStage.centerOnScreen();
    mainStage.sizeToScene();

    return (Initializable) loader.getController();
  }

  private Initializable changeView(String fxml) throws Exception {
    FXMLLoader loader = new FXMLLoader();
    InputStream in = Application.class.getResourceAsStream(fxml);
    loader.setBuilderFactory(new JavaFXBuilderFactory());
    loader.setLocation(Application.class.getResource(fxml));
    //		loader.setResources(ResourceBundle.getBundle(Constant.LANGUAGE_PACKAGE_NAME));
    AnchorPane page;
    try {
      page = (AnchorPane) loader.load(in);
    } finally {
      in.close();
    }
    Scene scene = new Scene(page);
    mainStage.setScene(scene);
    mainStage.sizeToScene();


    return (Initializable) loader.getController();
  }



  /**
   * 获得主窗体的Stage。
   *
   * @return 当前窗体的Stage。
   */
  public Stage getMainStage() {
    return mainStage;
  }


  /**
   * 获得主界面的控制器。
   *
   * @return 主界面控制器。
   */
  public MainController getMainController() {
    return mainController;
  }

  /**
   * 获得模式窗口的Stage。
   *
   * @return 模式窗口的Stage。
   */
  public Stage getModalStage() {
    return modalStage;
  }

  public void setMainController(MainController mainController) {
    this.mainController = mainController;
  }

  /**
   * The main() method is ignored in correctly deployed JavaFX application.
   * main() serves only as fallback in case the application can not be
   * launched through deployment artifacts, e.g., in IDEs with limited FX
   * support. NetBeans ignores main().
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }



  public void showBrowser() {
    showView("/views/browser.fxml");
  }
}
