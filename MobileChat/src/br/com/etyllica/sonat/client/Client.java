package br.com.etyllica.sonat.client;

public interface Client {

	public void init() throws Exception;
	
	public void finish();
	
	public void sendMessage(String message);
	
	public void setListener(ClientListener listener);
	
}
