package br.com.etyllica.sonat.client;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

	private ClientListener listener;
		
	private static final String COMMAND_USERS = "/users ";//whiteSpacing
	
	private static final String COMMAND_MESSAGE = "/msg ";//whiteSpacing
	
	public ChatClientHandler(ClientListener listener) {
		super();
		
		this.listener = listener;		
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		
		if(msg.startsWith(COMMAND_USERS)) {
			
			String[] names = msg.substring(COMMAND_USERS.length()).split(" "); 
			
			listener.updateNames(names);
			
		} else if (msg.startsWith(COMMAND_MESSAGE)) {
			
			String message = msg.substring(COMMAND_MESSAGE.length());
			
			String[] parts = msg.split(" ");
			
			String userName = parts[1];
			
			message = message.substring(userName.length()+" ".length());
			
			listener.receiveMessage(userName, message);
			
		}
		
	}
	
}
