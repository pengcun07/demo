package com.pc.demo.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ShutdownHandler;

/**
 * http://localhost:8080/shutdown?token=stop
 * POST
 * */
public class ShutdownHandlerServer {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);
    server.setHandler( new ShutdownHandler("stop", false, true));
    server.start();
  }
}
