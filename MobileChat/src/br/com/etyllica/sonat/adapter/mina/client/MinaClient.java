package br.com.etyllica.sonat.adapter.mina.client;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import br.com.etyllica.sonat.client.Client;
import br.com.etyllica.sonat.client.ClientImpl;

public class MinaClient extends ClientImpl implements Client {

	private IoConnector connector;
	
	private IoSession session;
	
	public MinaClient(String host, int port) {
		super(host, port);
	}

	public void init() {
		
		IoConnector connector = new NioSocketConnector();
		connector.getSessionConfig().setReadBufferSize(2048);

		connector.getFilterChain().addLast("logger", new LoggingFilter());
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

		connector.setHandler(new MinaChatClientHandler(listener));
		
		ConnectFuture future = connector.connect(new InetSocketAddress(host, port));
		future.awaitUninterruptibly();

		if (!future.isConnected()) {
			return;
		}
		
		session = future.getSession();
		
	}
	
	public void finish() {
		connector.dispose();
	}

	@Override
	public void sendMessage(String message) {
		session.write(message);
	}

}
