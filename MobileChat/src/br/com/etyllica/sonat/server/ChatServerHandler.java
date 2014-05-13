package br.com.etyllica.sonat.server;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

	private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	private Map<String, String> names;
	
	private final static String DELIMITER = " : ";
	
	public ChatServerHandler(Map<String, String> names) {
		super();
		
		this.names = names;
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();

		String key = incoming.remoteAddress().toString();
		
		names.put(key, key);
		
		System.out.println("Users: " + names.size());
		
		channels.add(incoming);
		
		tellNames();
		
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel leaving = ctx.channel();

		names.remove(leaving);
		
		System.out.println("Users: " + names.size());
		
		channels.remove(leaving);
		
		tellNames();
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {

		if(msg==null||"null".equals(msg)) {
			return;
		}
		
		Channel incoming = ctx.channel();

		String key = names.get(incoming.remoteAddress().toString());
		
		if(msg.startsWith("/name")) {

			changeName(incoming, msg);
		
			tellNames();
			//Print msg in Server Console
			System.out.println("(name)" +key + DELIMITER + msg);
			
		} else {
			
			tellAll(key + DELIMITER + msg);

			System.out.println(key + DELIMITER + msg);

		}
		
	}
	
	private void tellNames() {
		
		StringBuilder builder = new StringBuilder();
		
		for(String name: names.values()) {
			
			builder.append(name);
			
			builder.append(" ");
			
		}
		
		tellAll("/users "+builder.toString());		
		
	}
	
	private void changeName(Channel incoming, String msg) {
		
		String key = incoming.remoteAddress().toString();
		
		String[] parts = msg.split(" ");
		
		if(parts.length < 2) {
			return;
		}
		
		String name = msg.split(" ")[1];
		
		System.out.println(names.get(key)+" change the name to "+name);
		
		names.put(key, name);
		
	}
	
	private void tellAll(String message) {
		
		for(Channel channel : channels) {

			channel.writeAndFlush(message+"\n");

		}
		
	}

}
