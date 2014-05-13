package br.unirio.livechat;

import br.com.etyllica.sonat.client.Client;
import br.com.etyllica.sonat.client.ClientListener;
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

		//Start Client
		startClient(serverAddress);

		messages = (LinearLayout)findViewById(R.id.mensagens);

		addFakeMessages(messages);
	}
	
	private void startClient(String serverAddress) {

		try {

			client = new Client(serverAddress, LoginActivity.DEFAULT_PORT);
			
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
					
					textField.setText("");

				}

			}

		});

	}

	private void addFakeMessages(LinearLayout layout) {

		addMessage(layout, "Frank Zappa", "Wazup!");

		addMessage(layout, "John Snow", "Sup bro...");

		addMessage(layout, "Ned Stark", "Shut Up!");
		
		addMessage(layout, "Ned Stark", "Shut Up!");
		
		addMessage(layout, "Ned Stark", "Shut Up!");
		
		addMessage(layout, "Ned Stark", "Shut Up!");
		
		addMessage(layout, "Ned Stark", "Shut Up!");

		addMessage(layout, "João das neves", "Sabe de nada inocente");

	}

	private void addMessage(LinearLayout layout, String usuario, String message) {

		TextView messageView = new TextView(this);

		messageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

		messageView.setText(usuario+": "+message);

		layout.addView(messageView);

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
		addMessage(messages, name, message);
	}
	
	private void backToLoginScreen() {
		
		Toast.makeText(this, "Nenhum Servidor foi encontrado", Toast.LENGTH_LONG).show();
		
		Intent intent = new Intent(ChatActivity.this, LoginActivity.class);

	    startActivity(intent);
	    
	    ChatActivity.this.finish();
		
	}

}
