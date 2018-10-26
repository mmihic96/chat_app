package com.bcoolit.chat.client;

import com.bcoolit.chat.client.socket.SocketEventListener;
import com.bcoolit.chat.client.socket.SocketIOClient;
import com.bcoolit.chat.client.socket.SocketMessage;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ChatController implements SocketEventListener {

	@FXML
	private TextArea chatMessages;
	@FXML
	private TextField inputMessage;
	@FXML
	private Label messageLabel;

	public ChatController() {
		SocketIOClient socketClient = SocketIOClient.getInstance();
		socketClient.addEventListener(this);
	}

	@FXML
	void sendChatMessage(MouseEvent event) {

		String message = "JavaFX: " + inputMessage.getText();
		SocketMessage msg = new SocketMessage();
		msg.setMsg(message);
		SocketIOClient socketClient = SocketIOClient.getInstance();
		socketClient.sendMessage(msg);
		event.consume();
	}

	@Override
	public void onChatMessageReceived(SocketMessage msgObj) {
		String message = msgObj.getMsg();

		if (msgObj.isServer()) {
			message = "Admin: Please take care about profanity.";
		}
		chatMessages.appendText(message + "\n");
	}

}
