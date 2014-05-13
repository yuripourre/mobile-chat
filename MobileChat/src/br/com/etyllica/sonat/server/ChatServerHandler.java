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
	
	private static final String COMMAND_USERS = "/users ";
	
	private static final String COMMAND_NAME = "/name ";
	
	private int count = 0;
	
	public ChatServerHandler(Map<String, String> names) {
		super();
		
		this.names = names;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {

		if(msg==null||"null".equals(msg)) {
			return;
		}
		
		Channel incoming = ctx.channel();

		String key = names.get(incoming.remoteAddress().toString());
		
		if(msg.startsWith(COMMAND_NAME)) {

			changeName(incoming, msg);
		
			tellNames();
						
		} else {
			
			tellMessage(key, msg);
						
		}
		
		System.out.println(key + DELIMITER + msg);
		
	}
	
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		
		count++;

		String key = incoming.remoteAddress().toString();
		
		names.put(key, "visitante"+count);
		
		System.out.println("Users: " + names.size());
		
		channels.add(incoming);
		
		incoming.writeAndFlush("Server"+DELIMITER+"Hello!\r\n");
		
		tellNames();
		
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel leaving = ctx.channel();

		String key = leaving.remoteAddress().toString();
		
		names.remove(key);
		
		System.out.println("Users: " + names.size());
		
		channels.remove(leaving);
		
		tellNames();
		
	}
	
	private void tellNames() {
		
		StringBuilder builder = new StringBuilder();
		
		for(String name: names.values()) {
			
			builder.append(name);
			
			builder.append(" ");
			
		}
				
		tellAll(COMMAND_USERS+builder.toString());
		
	}
	
	private void tellMessage(String name, String message) {
		
		tellAll("/msg "+name+" "+message);
		
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

			channel.writeAndFlush(message+"\r\n");

		}
		
	}

}
