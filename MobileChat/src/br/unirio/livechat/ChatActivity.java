package br.unirio.livechat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.etyllica.mobile.gui.HorizontalRule;
import br.com.etyllica.sonat.adapter.mina.client.MinaClient;
import br.com.etyllica.sonat.client.Client;
import br.com.etyllica.sonat.client.ClientListener;

public class ChatActivity extends Activity implements ClientListener {

	private LinearLayout messages;
	
	private EditText textField;

	private TextView lista;

	private Client client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		setContentView(R.layout.chat);

		lista = (TextView) findViewById(R.id.lista_pessoas);

		Bundle bundle = getIntent().getExtras();

		String serverAddress = bundle.getString(LoginActivity.SERVER_ADDRESS);

		messages = (LinearLayout)findViewById(R.id.mensagens);
		
		addMessage(messages, "Servidor", "Hello!");
		
		//Start Client
		startClient(serverAddress);

	}
	
	private void startClient(String serverAddress) {

		try {

			client = new MinaClient(serverAddress, LoginActivity.DEFAULT_PORT);
			
			client.setListener(ChatActivity.this);

			Button sendButton = (Button) findViewById(R.id.botao_enviar);

			configureSendButton(sendButton);

			client.init();

		} catch (Exception e) {
			
			backToLoginScreen();
			
		}
		
	}

	private void configureSendButton(Button sendButton) {

		sendButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				textField = (EditText) findViewById(R.id.texto_mensagem);   

				String message = textField.getText().toString();

				if(!message.isEmpty()) {

					client.sendMessage(message);
					
					addMessage(messages, "Eu: ", message);
					
					textField.setText("");
					
				}

			}

		});

	}

	private void addMessage(LinearLayout layout, String usuario, String message) {

		TextView messageView = new TextView(this);

		//messageView.setText(usuario+": "+message);
		messageView.setText(message);

		layout.addView(messageView, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layout.addView(new HorizontalRule(this));

	}

	@Override
	public void updateNames(String[] names) {
		String text = "Pessoas: ";
		
		text+= names[0];
		
		for(int i=1;i<names.length;i++) {
			text += " ";
			text += names[i];
		}
		
		lista.setText(text);
	}

	@Override
	public void receiveMessage(String name, String message) {
		Toast.makeText(this, name+": "+message, Toast.LENGTH_LONG).show();
		
		addMessage(messages, name, message);
	}
	
	private void backToLoginScreen() {
		
		Toast.makeText(this, "Nenhum Servidor foi encontrado", Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(ChatActivity.this, LoginActivity.class);

	    startActivity(intent);
	    
	    ChatActivity.this.finish();
		
	}

}
