package br.com.etyllica.sonat.adapter.mina.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import br.com.etyllica.sonat.chat.ChatServerHandler;

public class MinaChatServerHandler extends IoHandlerAdapter implements ChatServerHandler {
	
	private final static String UNIQUE_ID = "ID"; 
	
	private List<IoSession> sessions = new ArrayList<IoSession>();
	
	private Map<String, String> names = new HashMap<String, String>();

	private int count = 0;
	
	private final static String DELIMITER = " : ";
	
	private static final String COMMAND_USERS = "/users ";
	
	private static final String COMMAND_NAME = "/name ";

	@Override
	public void messageReceived(IoSession session, Object message) {
						
		String msg = message.toString();
		
		IoSession channel = session;

		String key = getKey(channel);
		
		String name = names.get(key);
		
		if(msg.startsWith(COMMAND_NAME)) {

			changeName(key, msg);
		
			tellNames();
						
		} else {
			
			tellMessage(name, msg);
									
		}
		
		System.out.println(name + DELIMITER + msg);
				
	}
	
	@Override
	public void sessionOpened(IoSession session) {

		// set idle time to 10 minutes
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 600);
		
		count++;

		session.setAttribute(UNIQUE_ID, count);
		
		String key = getKey(session);
		
		names.put(key, "guest"+count);
		
		sessions.add(session);

		tellNames();

	}
	

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		
		String key = getKey(session);
		
		names.remove(key);
		
		sessions.remove(session);
		
		System.out.println("Disconnecting the idle.");
		
		// disconnect an idle client
		session.close();
		
		tellNames();
		
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		// close the connection on exceptional situation
		session.close();
	}
	
	public String getKey(Object channel) {
		
		IoSession session = (IoSession) channel;
		
		return session.getAttribute(UNIQUE_ID).toString();
	}
	
	public void changeName(String key, String msg) {
		
		String[] parts = msg.split(" ");
		
		if(parts.length < 2) {
			return;
		}
		
		String name = msg.split(" ")[1];
		
		System.out.println(names.get(key)+" change the name to "+name);
		
		names.put(key, name);
		
	}
	
	public void tellMessage(String name, String message) {
		
		tellAll("/msg "+name+" "+message);
		
	}
	
	public void tellNames() {
		
		StringBuilder builder = new StringBuilder();
		
		for(String name: names.values()) {
			
			builder.append(name);
			
			builder.append(" ");
			
		}
				
		tellAll(COMMAND_USERS+builder.toString());
		
	}
		
	public void tellAll(String message) {
		
		for(IoSession session: sessions) {
			session.write(message);
		}
		
	}

}
