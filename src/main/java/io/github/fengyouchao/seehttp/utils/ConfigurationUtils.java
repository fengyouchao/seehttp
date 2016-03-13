package io.github.fengyouchao.seehttp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The class <code>ConfigurationUtils</code>
 *
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 03,2016 5:50 PM
 */
public class ConfigurationUtils {

  private static final Logger logger = LoggerFactory.getLogger(ConfigurationUtils.class);

  public static String STORE_COMMENTS =
      "Please do not modify this file by manual. if you want to reset SeeHTTP, just remove this "
          + "file";

  public static final String CONFIG_FILE_PATH = "conf/seehttp.properties";
  private static Properties properties = new Properties();


  private static void createConfigurationFile(File file) {
    File parent = file.getParentFile();
    if (!parent.exists()) {
      parent.mkdirs();
    }
    try {
      file.createNewFile();
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public static void init() {
    File file = new File(CONFIG_FILE_PATH);
    if (!file.exists()) {
      createConfigurationFile(file);
    }
    try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file))) {
      properties.load(inputStream);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public static void store() {
    try (FileOutputStream outputStream = new FileOutputStream(CONFIG_FILE_PATH)) {
      Properties temp = new Properties();
      for (Object key : properties.keySet()) {
        temp.put(key, properties.get(key).toString());
      }
      temp.store(outputStream, STORE_COMMENTS);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
  }

  public static Properties getProperties() {
    return properties;
  }

}
