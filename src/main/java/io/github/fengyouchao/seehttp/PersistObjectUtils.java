package io.github.fengyouchao.seehttp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The class <code></code>
 *
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 03,2016 8:38 PM
 */
public class PersistObjectUtils {

  public static boolean write(Object object, String path) {
    ObjectOutputStream out = null;
    try {
      out = new ObjectOutputStream(new FileOutputStream(path));
      out.writeObject(object);
      return true;
    } catch (IOException io) {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return false;
  }

  public static <T> T read(String path, Class<T> clazz) {
    ObjectInputStream in = null;
    try{
      in = new ObjectInputStream(new FileInputStream(path));
      Object o = in.readObject();
      return (T) o;
    }catch (IOException | ClassNotFoundException e){
      e.printStackTrace();
    }
    return null;
  }
}
