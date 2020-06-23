package com.pc.demo.jetty;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
/**
 * 8. 内嵌 Servlets
 * Servlet是提供处理HTTP请求的应用程序逻辑的标准方法。 
 * Servlet类似于Jetty Handler，但请求对象不可变，因此无法修改。 
 * Servlet由ServletHandler在Jetty中处理。 
 * 它使用标准路径映射来将Servlet与请求进行匹配; 设置请求servletPath和pathInfo; 
 * 将请求传递给servlet，可能通过 Filters 生成响应。
 * */
public class MinimalServlets {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8090);

    // ServletHandler 是创建 context handler 的简单方法，
    // 由 Servlet 实力支持。
    // 这个 handler 对象需要被注册给 Server 对象。
    ServletHandler handler = new ServletHandler();
    server.setHandler(handler);

    // 传递的 Servlet 类允许 jetty 实例化并且挂载到给定的上下文路径上。// 重要:
    // 这是一个原始的 Servlet, 不是一个通过 web.xml 或者 @WebServlet注解或者其他类似方式配置的servlet。
    handler.addServletWithMapping(HelloServlet.class, "/*");

    // 启动服务
    server.start();

    // 使用 server.join() 加入当前线程// 在
    // http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
    // 查看更多。
    server.join();
  }

  @SuppressWarnings("serial")
  public static class HelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
      response.setContentType("text/html");
      response.setStatus(HttpServletResponse.SC_OK);
      response.getWriter().println("<h1>Hello from HelloServlet</h1>");
    }
  }
}