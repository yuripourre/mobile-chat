package br.unirio.livechat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	public static final String USER_NAME = "username";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.login);
				
		final EditText textField = (EditText) findViewById(R.id.apelido);
		
		final Button button = (Button) findViewById(R.id.botao_login);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				Intent intent = new Intent(LoginActivity.this, ChatActivity.class);
												
			    intent.putExtra(USER_NAME, textField.getText().toString());
			    
			    startActivity(intent);
				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
