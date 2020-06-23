package com.pc.demo.undertow;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * Hello world!
 *
 */
public class HelloWorldServer {

  public static void main(final String[] args) {
    HttpHandler httpHandler = new HttpHandler() {
      public void handleRequest(final HttpServerExchange exchange) throws Exception {
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Hello World");
        System.out.println("call ...");
      }
    };
    
    Undertow server = Undertow.builder()
        .addHttpListener(8080, "localhost")
        .setHandler(httpHandler).build();

    server.start();

  }
}
