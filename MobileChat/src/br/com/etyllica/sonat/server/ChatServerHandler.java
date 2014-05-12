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
		
		if(names.size() > 0) {
			tellNames(incoming);
		}
		
		for(Channel channel : channels) {
			channel.writeAndFlush("SERVER"+DELIMITER+ key + " has joined\n");
		}

		names.put(key, key);
		
		System.out.println("Users: " + names.size());

		channels.add(incoming);

	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel leaving = ctx.channel();

		for(Channel channel : channels) {
			channel.writeAndFlush("SERVER"+DELIMITER+ leaving.remoteAddress() + " has left\n");
		}

		names.remove(ctx.channel());
		
		channels.remove(ctx.channel());
		
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg)
			throws Exception {

		Channel incoming = ctx.channel();

		String key = names.get(incoming.remoteAddress().toString());
		
		if(msg.startsWith("/name")) {

			changeName(incoming, msg);
			
		} else {
			
			for(Channel channel : channels) {

				channel.writeAndFlush(key + DELIMITER + msg +"\n");

			}

		}
		
		//Print msg in Server Console
		System.out.println(key + DELIMITER + msg);

	}
	
	private void tellNames(Channel incoming) {
		
		StringBuilder builder = new StringBuilder();
		
		for(String name: names.values()) {
			
			builder.append(name);
			
			builder.append(" ");
			
		}
		
		incoming.writeAndFlush("/users "+builder.toString()+"\n");
		
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

}
