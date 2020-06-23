package com.pc.demo.jetty;

import java.net.URL;

import org.eclipse.jetty.server.Server;

/**
 * 最简单的 Jetty 服务器 要嵌入Jetty服务器，以下典型的步骤，本教程中的示例说明了这些步骤： 1: 创建一个Server实例。 2:
 * 添加/配置连接器（Connectors）。 3: 添加/配置处理器（Handlers）、上下文（Contexts）、Servlets。 4: 启动服务器。
 * 5: 等待连接或用线程做其他事情。
 */
public class SimplestServer {
  /**
   * 程序运行一个http server在8090端口。 但是这个server没有任何用处，因为它没有任何handler，
   * 因此不管任何的请求都会返回404.
   */
  public static void main(String[] args) throws Exception {
    Server server = new Server(8090);
    server.start();
    server.dumpStdErr();
    server.join();

  }
}
