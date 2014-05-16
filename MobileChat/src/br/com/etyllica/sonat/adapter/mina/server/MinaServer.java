package br.com.etyllica.sonat.adapter.mina.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import br.com.etyllica.sonat.server.Server;
import br.com.etyllica.sonat.server.ServerImpl;

public class MinaServer extends ServerImpl implements Server {
	 
	public MinaServer(int port) {
		super(port);	
	}

	public void init() throws IOException {
		
		IoAcceptor acceptor = new NioSocketAcceptor();

		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

		acceptor.setHandler(new MinaChatServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 600);
		
		acceptor.bind(new InetSocketAddress(port));
	}
	
}
