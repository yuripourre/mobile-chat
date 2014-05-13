package br.unirio.livechat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	public static final String SERVER_ADDRESS = "host";
	
	public static final int DEFAULT_PORT = 8080;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		setContentView(R.layout.login);
				
		TextView textView = (TextView) findViewById(R.id.servidor);
		
		textView.setText(ServerActivity.getLocalIpAddress()+":"+DEFAULT_PORT);		
		
		final EditText textField = (EditText) findViewById(R.id.endereco);
		
		final Button button = (Button) findViewById(R.id.botao_entrar);
				
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
				
				String address = textField.getText().toString();
				
				if(address.isEmpty()) {
					address = "127.0.0.1";
				}
				
				Toast.makeText(LoginActivity.this, address, Toast.LENGTH_LONG).show();
				
			    intent.putExtra(SERVER_ADDRESS, address);
			    
			    startActivity(intent);
			    
			    LoginActivity.this.finish();
				
			}
			
		});
		
		final Button serverButton = (Button) findViewById(R.id.botao_criar_sala);
		
		serverButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				Intent intent = new Intent(LoginActivity.this, ServerActivity.class);

			    startActivity(intent);
			    
			    LoginActivity.this.finish();
				
			}
			
		});
		
	}

}
