package com.pc.demo.netty.example.discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Discards any incoming data.
 */
public class DiscardServer {
    
    private int port;
    
    public DiscardServer(int port) {
        this.port = port;
    }
    
    public void run() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup);
             b.channel(NioServerSocketChannel.class); // (3)
             b.childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(new DiscardServerHandler());
                 }
             });
             b.option(ChannelOption.SO_BACKLOG, 128) ;         // (5)
             b.childOption(ChannelOption.SO_KEEPALIVE, true); // (6)
    
            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(port).sync(); // (7)
            f.addListener(z-> System.out.println("123"));
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
    
    public static void main(String[] args) throws Exception {
        int port = 8090;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        new DiscardServer(port).run();
    }
}
/** 
1. NioEventLoopGroup is a multithreaded event loop that handles I/O operation. 
    --NioEventLoopGroup是处理I / O操作的多线程事件循环。
    Netty provides various EventLoopGroup implementations for different kind of transports. 
    --Netty为不同类型的传输提供了EventLoopGroup的各种实现。
    We are implementing a server-side application in this example, 
    --在此示例中，我们正在实现服务器端应用程序，
    and therefore two NioEventLoopGroup will be used. 
    --因此，使用两个NioEventLoopGroup。
    The first one, often called 'boss', accepts an incoming connection. 
    --第一个通常称为“boss”，接受传入的连接。
    The second one, often called 'worker', handles the traffic of the accepted connection 
    once the boss accepts the connection and registers the accepted connection to the worker. 
    --第二个通常称为“worker”，负责处理已接受连接的流量
    --一旦老板接受该连接并将注册的连接注册给worker。
    How many Threads are used and how they are mapped to the created Channels 
    depends on the EventLoopGroup implementation and may be even configurable via a constructor.
    --使用多少个线程  以及如何将它们映射到创建的通道  取决于EventLoopGroup实现，甚至可以通过构造函数进行配置。
    
2. ServerBootstrap is a helper class that sets up a server. You can set up the server using a Channel directly. 
    --ServerBootstrap是设置服务器的帮助程序类。 您可以直接使用Channel设置服务器。
    However, please note that this is a tedious process, and you do not need to do that in most cases.
    --但是，请注意，这是一个繁琐的过程，在大多数情况下您无需这样做。
    
3. Here, we specify to use the NioServerSocketChannel class which is used to instantiate a new Channel 
    to accept incoming connections.
    --在这里，我们指定使用NioServerSocketChannel类，该类用于实例化新的Channel来接受传入的连接。
    
4. The handler specified here will always be evaluated by a newly accepted Channel. 
    --此处指定的处理程序  将始终由新接受的Channel进行评估。
    The ChannelInitializer is a special handler that is purposed to help a user configure a new Channel. 
    --ChannelInitializer是一个特殊的处理程序，旨在帮助用户配置新Channel。
    It is most likely that you want to configure the ChannelPipeline of the new  Channel by adding some handlers 
    such as DiscardServerHandler to implement your network application. 
    --您很可能希望通过添加一些处理程序来配置新Channel的ChannelPipeline
    --例如DiscardServerHandler来实现您的网络应用程序。
    As the application gets complicated, it is likely that you will add more handlers to the pipeline 
    and extract this anonymous class into a top-level class eventually.
    --随着应用程序变得复杂，您可能会向管道添加更多处理程序，并最终将此匿名类提取到顶级类中。
    
5. You can also set the parameters which are specific to the Channel implementation. 
    --您还可以设置特定于Channel实现的参数。
    We are writing a TCP/IP server, so we are allowed to set the socket options such as tcpNoDelay and keepAlive. 
    --我们正在编写一个TCP / IP服务器，因此我们可以设置套接字选项，例如tcpNoDelay和keepAlive。
    Please refer to the apidocs of ChannelOption and the specific  ChannelConfig implementations to 
    get an overview about the supported ChannelOptions.
    --请参考ChannelOption的apidocs和特定的ChannelConfig实现，以获取有关受支持的ChannelOptions的概述。
    
6. Did you notice option() and childOption()? option() is for the NioServerSocketChannel that accepts incoming connections.
    -- 您是否注意到option（）和childOption（）？ option（）用于接受传入连接的NioServerSocketChannel。
    childOption() is for the Channels accepted by the parent ServerChannel, which is NioServerSocketChannel in this case.
    --childOption（）用于父级ServerChannel接受的Channels，在这种情况下为NioServerSocketChannel。
    
7. We are ready to go now. What's left is to bind to the port and to start the server. 
    --我们现在准备出发。 剩下的就是绑定到端口并启动服务器。
    Here, we bind to the port 8080 of all NICs (network interface cards) in the machine. 
    --在这里，我们绑定到计算机中所有NIC（网络接口卡）的端口8080。
    You can now call the bind() method as many times as you want (with different bind addresses.)
    --现在，您可以根据需要多次调用bind（）方法（使用不同的绑定地址）。
    
 * */