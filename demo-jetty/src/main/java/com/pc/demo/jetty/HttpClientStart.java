package com.pc.demo.jetty;

import org.eclipse.jetty.client.HttpClient;

public class HttpClientStart {
  

  public static void main(String[] args) throws Exception {
    // Instantiate HttpClient
    HttpClient httpClient = new HttpClient();

    // Configure HttpClient, for example:
    httpClient.setFollowRedirects(false);

    // Start HttpClient
    httpClient.start();
  }
}
