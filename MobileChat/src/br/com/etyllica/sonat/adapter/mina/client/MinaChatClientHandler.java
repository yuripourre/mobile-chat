package br.com.etyllica.sonat.adapter.mina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import br.com.etyllica.sonat.client.ClientListener;

public class MinaChatClientHandler extends IoHandlerAdapter {

	private ClientListener listener;

	private static final String COMMAND_USERS = "/users ";//whiteSpacing

	private static final String COMMAND_MESSAGE = "/msg ";//whiteSpacing

	public MinaChatClientHandler(ClientListener listener) {
		this.listener = listener;
	}

	@Override
	public void sessionOpened(IoSession session) {
		//session.write();
		System.out.println("I am connected");
	}

	@Override
	public void messageReceived(IoSession session, Object obj) {

		String msg = obj.toString();

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

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		session.close();
	}

}