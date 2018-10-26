package com.bcoolit.chat.client.socket;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.IO.Options;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketIOClient {
	
	private static final String NODEJS_SOCKET_IO_URL = "http://localhost:3000";
	
	private static SocketIOClient instance = null;
	
	private List<SocketEventListener> listeners = new ArrayList<SocketEventListener>();
	private ObjectMapper mapper = new ObjectMapper();
	
	private SocketIOClient() {
		try {
			initializeSocketIOClient();
		} catch(Exception e) {
			throw new IllegalStateException("Unable to connect to the server , application cannot continue!!!");
		}
	}
	
	public static SocketIOClient getInstance() {
		if(instance == null) {
			instance = new SocketIOClient();
		}
		return instance;
	}
	
	
	private Socket socket;
	
	public interface ChatServerTopics {
		String NEW_MSG = "clientChat";
		String ADMIN_MSG = "serverMessages";
	}
	
	public void sendMessage(SocketMessage msg) {
		try {
			socket.emit(ChatServerTopics.NEW_MSG, mapper.writeValueAsString(msg));
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void initializeSocketIOClient() throws URISyntaxException {
		Options opts = new IO.Options();
		opts.forceNew = true;
		opts.reconnection = true;
		socket = IO.socket(NODEJS_SOCKET_IO_URL, opts);

		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				System.out.println("WebSocket connected socket io");
			}

		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

			@Override
			public void call(Object... args) {

			}

		}).on(ChatServerTopics.NEW_MSG, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				 JSONObject obj = (JSONObject)args[0];

				 try {
					SocketMessage msg = mapper.readValue(obj.toString(), SocketMessage.class);
					notifyEventHandlers(msg);
					
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}).on(ChatServerTopics.ADMIN_MSG, new Emitter.Listener() {

			@Override
			public void call(Object... args) {
				 JSONObject obj = (JSONObject)args[0];

				 try {
					SocketMessage msg = mapper.readValue(obj.toString(), SocketMessage.class);
					msg.setServer(true);
					notifyEventHandlers(msg);
					
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		socket.connect();
	}
	
	
	private void notifyEventHandlers(SocketMessage msg) {
		for(SocketEventListener listener: listeners) {
			listener.onChatMessageReceived(msg);
		}
		
	}
	
	
	public void addEventListener(SocketEventListener listener) {
		this.listeners.add(listener);
	}
	
	public void removEventListener(SocketEventListener listener) {
		this.listeners.remove(listener);
	}
}
