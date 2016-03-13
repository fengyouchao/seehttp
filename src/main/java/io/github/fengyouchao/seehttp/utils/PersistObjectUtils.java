package io.github.fengyouchao.seehttp.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * The class <code></code>
 *
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 03,2016 8:38 PM
 */
public class PersistObjectUtils {

  private static final Logger logger = LoggerFactory.getLogger(PersistObjectUtils.class);

  public static boolean write(Object object, String path) {
    try(ObjectOutputStream out= new ObjectOutputStream(new FileOutputStream(path))) {
      out.writeObject(object);
      return true;
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
    return false;
  }

  public static <T> T read(String path, Class<T> clazz) {
    try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
      Object o = in.readObject();
      return (T) o;
    } catch (IOException | ClassNotFoundException e) {
      logger.error(e.getMessage());
    }
    return null;
  }
}
