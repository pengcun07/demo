package com.pc.demo.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * 简单的 Jetty 文件服务器 这是一个Jetty 文件服务器配置的简单示例
 */
public class FileServer {
  public static void main(String[] args) throws Exception {
    // 创建一个将在8090端口侦听的基本的服务器对象，注意，如果将端口设置为0
    // server 将会被分配一个随机可用的端口，你可以在日志中看到分配的端口,
    // 或者则测试用例中通过编程方式获取。
    Server server = new Server(8090);

    // 创建 ResourceHandler. 它是实际处理给定文件请求的对象。
    // 它是一个 Jetty Handler 对象，因此你将在其他示例中看到它能够和其他 handlers 进行链式处理。
    ResourceHandler resource_handler = new ResourceHandler();

    // 配置 ResourceHandler。设置提供文件服务的资源根目录。
    // 在这个例子中是当前目录，也可以配置成jvm有权限访问的任何目录。
    resource_handler.setDirectoriesListed(true);
    resource_handler.setWelcomeFiles(new String[] { "index.html" });
    resource_handler.setResourceBase(".");

    // 将 ResourceHandler 添加至 server。
    HandlerList handlers = new HandlerList();
    handlers.setHandlers(new Handler[] { resource_handler, new DefaultHandler() });
    server.setHandler(handlers);

    // 启动服务，通过使用 server.join() server 线程将 join 当前线程
    // 在"http://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Thread.html#join()"
    // 了解更多细节.
    server.start();
    server.join();
  }
}
