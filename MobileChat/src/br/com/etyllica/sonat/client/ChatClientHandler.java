package br.com.etyllica.sonat.client;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

	private ClientListener listener;
	
	private static final String DELIMITER = " : ";
	
	public ChatClientHandler(ClientListener listener) {
		super();
		
		this.listener = listener;		
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		
		if(msg.startsWith("/users")) {
			
			String[] names = msg.substring("/users ".length()).split(" "); 
			
			listener.updateNames(names);
			
		} else {
			
			String[] parts = msg.split(DELIMITER);
			
			String userName = parts[0];
			
			String message = msg.substring(userName.length()+DELIMITER.length());
			
			listener.receiveMessage(userName, message);
			
		}
		
	}
	
}
