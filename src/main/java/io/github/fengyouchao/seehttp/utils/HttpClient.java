package io.github.fengyouchao.seehttp.utils;

import io.github.fengyouchao.httpparse.HttpMessageParser;
import io.github.fengyouchao.httpparse.HttpParseException;
import io.github.fengyouchao.httpparse.HttpRequest;
import io.github.fengyouchao.httpparse.HttpResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * The class <code></code>
 *
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 03,2016 10:42 PM
 */
public class HttpClient {

  public static RequestResult request(HttpRequest httpRequest, String host, int port) throws
      IOException, HttpParseException {
    long startTime = System.currentTimeMillis();
    HttpMessageParser httpMessageParser = new HttpMessageParser();
    Socket socket = new Socket();
    socket.connect(new InetSocketAddress(host, port));
    InputStream inputStream = socket.getInputStream();
    OutputStream outputStream = socket.getOutputStream();
    outputStream.write(httpRequest.getProtocolBytes());
    byte[] buffer = new byte[2048];
    int length = 0;
    while ((length = inputStream.read(buffer)) > 0) {
      httpMessageParser.write(buffer, length);
      if (httpMessageParser.isComplete()) {
        socket.close();
        break;
      }
    }
    HttpResponse response = (HttpResponse) httpMessageParser.getHttpMessage();
    return new RequestResult(response, System.currentTimeMillis() - startTime);
  }


}
