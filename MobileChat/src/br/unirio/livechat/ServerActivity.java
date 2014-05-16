package br.unirio.livechat;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.http.conn.util.InetAddressUtils;

import br.com.etyllica.sonat.adapter.mina.server.MinaServer;
import br.com.etyllica.sonat.server.Server;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ServerActivity extends Activity {

	private Server server;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.server);

		TextView text = (TextView) findViewById(R.id.mensagem_servidor);

		final String localIp = getLocalIpAddress();
		
		if(localIp!=null) {
			
			server = new MinaServer(LoginActivity.DEFAULT_PORT);
			
			try {
				
				text.setText("Servidor Criado: "+localIp);
				
				server.init();
				
			} catch (Exception e) {
				
				text.setText("Servidor não pode ser Criado!");
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {

			text.setText("Servidor não pode ser Criado!");

		}

	}

	public static String getLocalIpAddress() {

		String ipv4 = "";
		
		try {

			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {

				NetworkInterface intf = en.nextElement();

				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {

					InetAddress inetAddress = enumIpAddr.nextElement();

					// for getting IPV4 format
					if (!inetAddress.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4 = inetAddress.getHostAddress())) {

						return ipv4;
					}

					//Defautl Behavior
					/*if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}*/
				}

			}

		} catch (SocketException ex) {

			Log.e(ServerActivity.class.getSimpleName(), ex.toString());
		}

		return null;

	}

}
