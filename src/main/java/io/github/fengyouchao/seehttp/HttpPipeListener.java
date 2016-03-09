package io.github.fengyouchao.seehttp;

import io.github.fengyouchao.httpparse.HttpMessage;
import io.github.fengyouchao.httpparse.HttpMessageParser;
import io.github.fengyouchao.httpparse.HttpParseException;
import io.github.fengyouchao.httpparse.HttpRequest;
import io.github.fengyouchao.httpparse.HttpResponse;
import io.github.fengyouchao.seehttp.models.HttpMessageModel;
import javafx.collections.ObservableList;
import sockslib.server.io.Pipe;
import sockslib.server.io.PipeListener;
import sockslib.server.io.SocketPipe;

import java.io.File;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 08, 2016 5:04 PM
 */
public class HttpPipeListener implements PipeListener {

  public static int index = 0;
  private static String ATTR_REQUEST = "REQUEST_MESSAGE";
  private String cacheDirectory = "temp";
  private HttpMessageParser httpMessageParse = new HttpMessageParser();
  private ObservableList<HttpMessageModel> httpMessageModels;
  private SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

  public HttpPipeListener(ObservableList<HttpMessageModel> httpMessageModels) {
    this.httpMessageModels = httpMessageModels;
    File file = new File(cacheDirectory);
    if (!file.exists()) {
      file.mkdirs();
    }
  }

  public HttpPipeListener() {
  }


  @Override
  public void onStart(Pipe pipe) {

  }

  @Override
  public void onStop(Pipe pipe) {
  }

  @Override
  public void onTransfer(Pipe pipe, byte[] buffer, int bufferLength) {
    try {
      httpMessageParse.write(buffer, bufferLength);
      Pipe parentPipe = (Pipe) pipe.getAttribute(SocketPipe.ATTR_PARENT_PIPE);
      if (httpMessageParse.isComplete()) {
        Socket sourceSocket = (Socket) pipe.getAttribute(SocketPipe.ATTR_SOURCE_SOCKET);
        Socket destinationSocket = (Socket) pipe.getAttribute(SocketPipe.ATTR_DESTINATION_SOCKET);
        HttpMessage httpMessage = httpMessageParse.getHttpMessage();
        String sourceAddress = sourceSocket.getInetAddress().toString();
        String destinationAddress = destinationSocket.getInetAddress().toString();
        String sourceIP = sourceAddress.split("/")[1];
        String[] domainIP = destinationAddress.split("/");
        String destinationIP = domainIP[1];
        String destinationDomain = domainIP[0];
        if (httpMessage instanceof HttpRequest) {
          String dataPath = cacheDirectory + "/" + UUID.randomUUID().toString() + ".request";
          HttpMessageModel messageModel = new HttpMessageModel();
          messageModel.setTime(format.format(new Date()));
          messageModel.setSource(sourceIP);
          messageModel.setSourcePort(sourceSocket.getPort());
          messageModel.setDestination(destinationIP);
          messageModel.setDestinationPort(destinationSocket.getPort());
          messageModel.setDomain(destinationDomain);
          messageModel.setMethod(((HttpRequest) httpMessage).getMethod().toString());
          messageModel.setPath(((HttpRequest) httpMessage).getRequestPath());
          messageModel.setVersion(httpMessage.getVersion());
          messageModel.setHttpRequestObjectPath(dataPath);
          //          httpMessageModels.add(0, messageModel);
          PersistObjectUtils.write(httpMessage, dataPath);
          parentPipe.setAttribute(ATTR_REQUEST, messageModel);
        } else if (httpMessage instanceof HttpResponse) {
          String dataPath = cacheDirectory + "/" + UUID.randomUUID().toString() + ".response";
          PersistObjectUtils.write(httpMessage, dataPath);
          HttpMessageModel httpMessageModel =
              (HttpMessageModel) parentPipe.getAttribute(ATTR_REQUEST);
          httpMessageModel.setStatusCode(((HttpResponse) httpMessage).getResponseCode());
          httpMessageModel.setStatusMessage(((HttpResponse) httpMessage).getResponseReason());
          httpMessageModel.setHttpResponseObjectPath(dataPath);
          httpMessageModel.setIndex(++index);
          httpMessageModels.add(httpMessageModel);
        }
        httpMessageParse.reset();
      }
    } catch (HttpParseException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onError(Pipe pipe, Exception exception) {

  }

  public String getCacheDirectory() {
    return cacheDirectory;
  }

  public void setCacheDirectory(String cacheDirectory) {
    this.cacheDirectory = cacheDirectory;
  }
}
