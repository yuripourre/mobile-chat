package br.com.etyllica.sonat.client;

public interface ClientListener {

	public void updateNames(String[] names);
	
	public void receiveMessage(String name, String message);
	
}
