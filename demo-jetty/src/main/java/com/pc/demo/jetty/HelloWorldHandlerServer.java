package com.pc.demo.jetty;

import org.eclipse.jetty.server.Server;

/**
 * 
 * 在Jetty中一般有一个或者多个handler来处理所有的请求。
 * 有些handler会选择其他指定的handler去做处理
 * （例如ContextHandlerCollection会通过context path来选择ContextHandler）; 
 * 还有一些handler通过应用程序逻辑生成响应（例如，ServletHandler将请求传递给应用Servlet），
 * 其他的handler则执行与生成响应无关的任务（例如，RequestLogHandler或StatisticsHandler）。
 * 
 * 后面的章节描述了你可以像切面一样组合多个handler。 
 * 您可以在org.eclipse.jetty.server.handler包中看到Jetty中的一些可用的Handlers。
 * */
public class HelloWorldHandlerServer {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8090);
    server.setHandler(new HelloWorldHandler());

    server.start();
    server.join();
  }
}

/**
 * 4. Handler Collections and Wrappers
 * 复杂请求处理通常由多个Handler构建，您可以通过各种方式进行组合。 Jetty有几个HandlerContainer接口的实现：
HandlerCollection
　　　　保存其他 handler 集合并按顺序调用每个handler。这对于将统计信息和日志记录 handler 与生成响应的 handler 相结合非常有用。

HandlerList
　　　　一个Handler 集合，它依次调用每个 handler，直到抛出异常，响应被提交或request.isHandled() 返回true。您可以使用它来组合handler有选择的处理请看清，例如调用多个Context上下文直到与虚拟主机匹配。

HandlerWrapper
一个Handler基类，可用于以面向切面编程的方式将处理程序连接在一起。例如，标准Web应用程序由上下文，会话，安全性和servlet处理程序链实现。

ContextHandlerCollection
　　一个专门的HandlerCollection，它使用请求URI的最长前缀（contextPath）来选择一个包含的ContextHandler来处理请求。
 * 
 * */
/**
5. Scoped Handlers
Jetty中的大部分标准Servlet容器都是使用HandlerWrappers实现的，它们将handler 以链式组合在一起：
ContextHandler --> SessionHandler --> SecurityHandler --> ServletHandler。
 但是，由于servlet规范，这种 handler 链不能是纯粹的嵌套，因为外部 handler 有时需要内部 handler 的处理信息。 
 例如，当ContextHandler调用某些应用程序listener以通知它们进入上下文的请求时，
 它必须已经知道ServletHandler将向哪个servlet分派请求，以便servletPath方法返回正确的值。

HandlerWrapper是ScopedHandler抽象类，它支链式链scopes。 
例如，如果ServletHandler嵌套在ContextHandler中，则方法执行的顺序和嵌套是：

Server.handle(...)
  ContextHandler.doScope(...)
    ServletHandler.doScope(...)
      ContextHandler.doHandle(...)
        ServletHandler.doHandle(...)
          SomeServlet.service(...)
 * */
