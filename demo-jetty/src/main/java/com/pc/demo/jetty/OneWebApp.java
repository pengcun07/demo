package com.pc.demo.jetty;

import java.io.File;
import java.lang.management.ManagementFactory;

import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
/**
 * 11. 内嵌 Applications
 * WebAppContext是ServletContextHandler的扩展，
 * 它使用 standard layout和web.xml从web.xml和/或注释配置servlet，filter和其他功能。 
 * 以下OneWebApp示例配置Jetty测试webapp。 Web应用程序可以使用容器提供的资源，
 * 在这种情况下，还需要LoginService并进行配置：
 * */
public class OneWebApp {
  public static void main(String[] args) throws Exception {
    Server server = new Server(8080);

    // 配置 JMX
    MBeanContainer mbContainer = new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
    server.addBean(mbContainer);

    // WebAppContext 是一个控制 web 应用生存环境的实体，
    // 在这个例子中 context path 设置为 “/”,因此它能够处理 root 上下文请求，然后我们能看到它设置war包路径。//
    // 还有许多其他配置, 从支持webapp注解扫描（通过PlusConfiguration）到选择webapp的解压位置。
    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    File warFile = new File("D:\\worksoft\\async-rest.war");
    webapp.setWar(warFile.getAbsolutePath());

    // WebAppContext 是一个 ContextHandler，因此它需要设置给 server，从而能够将请求发送到合适的位置sts.
    server.setHandler(webapp);

    // 启动服务
    server.start();

    server.dumpStdErr();// 在
                        // http://docs.oracle.com/javase/7/docs/api/java/lang/Thread.html#join()
                        // 查看更多
    server.join();
  }
}