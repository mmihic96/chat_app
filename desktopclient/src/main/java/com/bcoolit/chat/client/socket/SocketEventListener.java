package com.bcoolit.chat.client.socket;

public interface SocketEventListener {
	
	void onChatMessageReceived(SocketMessage msg);
}
