package br.com.etyllica.sonat.client;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String>{

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		
		if(msg.startsWith("/users")) {
			System.out.println("Who is in?...");
		}
		
		System.out.println(msg);
	}

	
}
