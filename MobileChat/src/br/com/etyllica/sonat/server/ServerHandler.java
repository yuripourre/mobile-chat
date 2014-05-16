package br.com.etyllica.sonat.server;

public interface ServerHandler {

	public String getKey(Object channel);
	
	public void tellAll(String message);
	
}
