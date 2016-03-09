package io.github.fengyouchao.seehttp;

import io.github.fengyouchao.seehttp.models.HttpMessageModel;

/**
 * The class <code></code>
 *
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 03,2016 10:18 PM
 */
public class ApplicationManager {

  private static Main application;
  private static HttpMessageModel selectedHttpMessageModel;

  public static void setApplication(Main main){
    application = main;
  }

  public static Main getApplication() {
    return application;
  }

  public static HttpMessageModel getSelectedHttpMessageModel() {
    return selectedHttpMessageModel;
  }

  public static void setSelectedHttpMessageModel(HttpMessageModel selectedHttpMessageModel) {
    ApplicationManager.selectedHttpMessageModel = selectedHttpMessageModel;
  }
}
