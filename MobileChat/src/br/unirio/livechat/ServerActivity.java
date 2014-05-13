package br.unirio.livechat;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ServerActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView text = (TextView) findViewById(R.id.mensagem_servidor);
		
		final String localIp = getLocalIpAddress();
		
		if(localIp!=null) {
			
			text.setText("Servidor Criado: "+localIp);
			
		} else {
			
			text.setText("Servidor Criado");
			
		}
		
		setContentView(R.layout.server);
		
	}
	
	public String getLocalIpAddress() {
		
	    try {
	    	
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
	            
	        	NetworkInterface intf = en.nextElement();
	        	
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
	                
	            	InetAddress inetAddress = enumIpAddr.nextElement();
	                
	                if (!inetAddress.isLoopbackAddress()) {
	                    return inetAddress.getHostAddress().toString();
	                }
	                
	             }
	            
	         }
	        
	     } catch (SocketException ex) {
	    	 
	         Log.e(ServerActivity.class.getSimpleName(), ex.toString());
	     }

	     return null;
	     
	}

}
