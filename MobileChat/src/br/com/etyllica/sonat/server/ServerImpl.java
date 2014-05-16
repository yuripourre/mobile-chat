package br.com.etyllica.sonat.server;

public abstract class ServerImpl implements Server {

	protected int port;
	
	public ServerImpl(int port) {
		super();
		this.port = port;
	}

	public int getPort() {
		return port;
	}
	
}
