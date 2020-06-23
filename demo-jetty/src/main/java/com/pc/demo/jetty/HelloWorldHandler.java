package com.pc.demo.jetty;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
/**
 * 3. 使用 Handlers
 * 要生成对请求的响应，Jetty要求您在服务器上设置Handler。 处理程序可以：
 * 检查/修改HTTP请求。
 * 生成完整的HTTP响应。
 * 调用另一个Handler（参见HandlerWrapper）。
 * 选择一个或多个要调用的处理程序（请参阅HandlerCollection）。
 * 
 * 传递给handle方法的参数是：
 * target - 请求的目标，它是URI或命名调度程序的名称。
 * baseRequest - Jetty可变请求对象，没有被包装的request对象。
 * request - 不可变请求对象，可能已被filter或servlet包装。
 * response- 响应，可能已被过滤器或servlet包装。
 * handler设置响应状态，内容类型，并在使用writer生成响应主体之前将请求标记为已处理。
 * 
 * */
public class HelloWorldHandler extends AbstractHandler {
  final String greeting;
  final String body;

  public HelloWorldHandler() {
    this("Hello World");
  }

  public HelloWorldHandler(String greeting) {
    this(greeting, null);
  }

  public HelloWorldHandler(String greeting, String body) {
    this.greeting = greeting;
    this.body = body;
  }

  @Override
  public void handle(String target, Request baseRequest, HttpServletRequest request,
      HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html; charset=utf-8");
    response.setStatus(HttpServletResponse.SC_OK);

    PrintWriter out = response.getWriter();

    out.println("<h1>" + greeting + "</h1>");
    if (body != null) {
      out.println(body);
    }

    baseRequest.setHandled(true);
  }
}