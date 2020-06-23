package com.pc.demo.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

/**
 * ContextHandler是一个ScopedHandler，它只响应具有与配置的上下文路径匹配的URI前缀的请求。
 *  与上下文路径匹配的请求会相应地更新其路径方法，并且上下文范围可用，可选择：
 *  
 * - 在请求处理期间为Thread context 设置 Classloader 。
 * - 一组可通过ServletContext API获得的属性。
 * - 一组可通过ServletContext API获得的init参数。
 *  -基本资源，通过ServletContext API用作静态资源请求的文档根。
 * - 一组虚拟主机名。
 * */
public class OneContext {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8090);

    // 增加一个唯一的 handler 给 context "/hello"
    ContextHandler context = new ContextHandler();
    context.setContextPath("/hello");
    context.setHandler(new HelloWorldHandler());

    // 在 http://localhost:8080/hello 访问服务

    server.setHandler(context);

    // 启动服务
    server.start();
    server.join();
  }
}