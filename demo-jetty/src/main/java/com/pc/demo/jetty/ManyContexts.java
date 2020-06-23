package com.pc.demo.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;

public class ManyContexts {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8090);

    ContextHandler context = new ContextHandler("/");
    context.setContextPath("/");
    context.setHandler(new HelloWorldHandler("Root Hello"));

    ContextHandler contextFR = new ContextHandler("/fr");
    contextFR.setHandler(new HelloWorldHandler("Bonjoir"));

    ContextHandler contextIT = new ContextHandler("/it");
    contextIT.setHandler(new HelloWorldHandler("Bongiorno"));

    ContextHandler contextV = new ContextHandler("/");
    contextV.setVirtualHosts(new String[] { "127.0.0.2" });
    contextV.setHandler(new HelloWorldHandler("Virtual Hello"));

    ContextHandlerCollection contexts = new ContextHandlerCollection();
    contexts.setHandlers(new Handler[] { context, contextFR, contextIT, contextV });

    server.setHandler(contexts);

    server.start();
    server.join();
  }
}