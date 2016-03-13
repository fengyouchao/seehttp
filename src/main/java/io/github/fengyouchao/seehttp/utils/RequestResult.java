package io.github.fengyouchao.seehttp.utils;

import io.github.fengyouchao.httpparse.HttpResponse;

/**
 * The class <code></code>
 *
 * @author Youchao Feng
 * @version 1.0
 * @date Mar 03,2016 8:52 PM
 */
public class RequestResult {
  private HttpResponse response;
  private long responseTime;

  public RequestResult(HttpResponse response, long responseTime) {
    this.response = response;
    this.responseTime = responseTime;

  }

  public HttpResponse getResponse() {
    return response;
  }

  public void setResponse(HttpResponse response) {
    this.response = response;
  }

  public long getResponseTime() {
    return responseTime;
  }

  public void setResponseTime(long responseTime) {
    this.responseTime = responseTime;
  }
}
