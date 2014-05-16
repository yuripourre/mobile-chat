package br.com.etyllica.sonat.client;

public abstract class ClientImpl {

	protected String host;

	protected int port;
	
	protected ClientListener listener;
	
	public ClientImpl(String host, int port) {
		super();

		this.host = host;
		this.port = port;
	}
	
	public ClientImpl(String host, int port, ClientListener listener) {
		super();

		this.host = host;
		this.port = port;

		this.listener = listener;
	}

	public ClientListener getListener() {
		return listener;
	}

	public void setListener(ClientListener listener) {
		this.listener = listener;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}	
	
}
