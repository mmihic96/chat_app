package com.bcoolit.chat.client.socket;

import java.io.Serializable;

public class SocketMessage implements Serializable {
	
	public SocketMessage() {
		
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isServer() {
		return server;
	}

	public void setServer(boolean server) {
		this.server = server;
	}

	private String msg;
	
	private boolean server;
	
	
}
