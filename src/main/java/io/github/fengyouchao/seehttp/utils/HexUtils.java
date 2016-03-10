package io.github.fengyouchao.seehttp.utils;

/**
 * The class <code></code>
 *
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 03,2016 9:56 PM
 */
public class HexUtils {
  public static String hexString(byte[] b) {
    if (b == null) {
      return "";
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < b.length; i++) {
      String hex = Integer.toHexString(b[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      builder.append(hex.toUpperCase()).append(" ");
      if ((i + 1) % 30 == 0) {
        builder.append("\r\n");
      }
    }
    return builder.toString();
  }
}
