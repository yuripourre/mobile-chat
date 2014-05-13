package br.unirio.livechat;

import br.com.etyllica.sonat.client.Client;
import br.com.etyllica.sonat.client.ClientListener;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatActivity extends Activity implements ClientListener {

	private LinearLayout messages;

	private EditText lista;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.chat);

		lista = (EditText) findViewById(R.id.lista_pessoas);

		Bundle bundle = getIntent().getExtras();

		String serverAddress = bundle.getString(LoginActivity.SERVER_ADDRESS);

		Client client = null;
		
		//Start Client
		try {
			
			client = new Client(serverAddress, LoginActivity.DEFAULT_PORT);
			
			Button sendButton = (Button) findViewById(R.id.botao_enviar);
			
			configureSendButton(sendButton, client);
			
			client.init();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		messages = (LinearLayout)findViewById(R.id.mensagens);

		addFakeMessages(messages);
	}
	
	private void configureSendButton(Button sendButton, final Client client) {
		
		sendButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				EditText textField = (EditText) findViewById(R.id.texto_mensagem);   
				
				String message = textField.getText().toString();
				
				client.sendMessage(message);
				
			}
			
		});
		
	}

	private void addFakeMessages(LinearLayout layout) {

		addMessage(layout, "Frank Zappa", "Wazup!");
		
		addMessage(layout, "John Snow", "Sup bro...");
		
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
		String text = "Lista de Pessoas: ";

		for(String name: names) {
			text += name;
		}

		lista.setText(text);
	}

	@Override
	public void receiveMessage(String name, String message) {
		addMessage(messages,name, message);
	}

}
