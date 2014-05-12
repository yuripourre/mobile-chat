package br.com.etyllica.sonat.server;


import java.util.HashMap;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
	
	private final int port;

	public Server(int port) {
		this.port = port;
	}
	
	public void init() throws Exception {
		
		System.out.println("Starting Server...");
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			
			ServerBootstrap bootstrap = new ServerBootstrap()
			.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChatServerInitializer(new HashMap<String, String>()));
			
			bootstrap.bind(port).sync().channel().closeFuture().sync();
		
		} finally {
			
			bossGroup.shutdownGracefully();
			
			workerGroup.shutdownGracefully();
			
		}
		
	}
	
}
