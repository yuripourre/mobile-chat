package br.com.etyllica.sonat.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

	private final String host;

	private final int port;

	private Channel channel;

	private EventLoopGroup group;

	private ClientListener listener;

	public Client(String host, int port) {
		super();

		this.host = host;
		this.port = port;
	}

	public Client(String host, int port, ClientListener listener) {
		super();

		this.host = host;
		this.port = port;

		this.listener = listener;
	}

	public void init() throws Exception {

		group = new NioEventLoopGroup();

		Bootstrap bootstrap = new Bootstrap()
		.group(group)
		.channel(NioSocketChannel.class)
		.option(ChannelOption.TCP_NODELAY, true)
		.option(ChannelOption.SO_KEEPALIVE, true)
		.handler(new ChatClientInitializer(listener));

		channel = bootstrap.connect(host, port).sync().channel();

	}

	public void finish() {
		group.shutdownGracefully();
	}

	public void sendMessage(String message) {
		channel.writeAndFlush(message+"\r\n");
	}

	public ClientListener getListener() {
		return listener;
	}

	public void setListener(ClientListener listener) {
		this.listener = listener;
	}

}
