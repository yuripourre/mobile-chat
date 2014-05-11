package br.unirio.livechat;

import br.com.etyllica.sonat.client.Client;
import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatActivity extends Activity {

	private LinearLayout messages;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		EditText lista = (EditText) findViewById(R.id.lista_pessoas);
		
		Bundle bundle = getIntent().getExtras();
		
		String userName = bundle.getString(LoginActivity.USER_NAME);

		//Start Client
		//new Client("127.0.0.1", 8000).init();
		
		lista.setText("Lista de Pessoas: "+userName);
				
		messages = (LinearLayout)findViewById(R.id.mensagens);
				
		addFakeMessages(messages);
		
		setContentView(R.layout.chat);
	}
	
	private void addFakeMessages(LinearLayout layout) {
		
		addMessage(layout, "Frank Zappa", "Wazup!");
		addMessage(layout, "John Snow", "Sup bro...");
		addMessage(layout, "Ned Stark", "Shut Up!");		
		
	}
	
	private void addMessage(LinearLayout layout, String usuario, String message) {
		
		TextView messageView = new TextView(this);
		
		messageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		
		messageView.setText(usuario+": "+message);
		
		layout.addView(messageView);
		
	}

}
