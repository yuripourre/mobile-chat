package br.com.etyllica.sonat.client;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.bootstrap.Bootstrap;

public class Client {

	private final String host;
	
	private final int port;
	
	private Channel channel;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void init() throws Exception {
		
		EventLoopGroup group = new NioEventLoopGroup();

		try {
			
			Bootstrap bootstrap = new Bootstrap()
			.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChatClientInitializer());

			channel = bootstrap.connect(host, port).sync().channel();
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			
			//sendMessage("/name "+name);s
			
			while (true) {
				sendMessage(in.readLine());
			}

		} finally {
			
			group.shutdownGracefully();
			
		}
		
	}
	
	private void sendMessage(String message) {
		channel.writeAndFlush(message+ "\r\n");
	}

}
