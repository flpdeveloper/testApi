package com.example.ferran.testapi;

import com.google.gson.Gson;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebSocketHandler extends AbstractWebSocketHandler {

    private Map<Integer, WebSocketSession> sessions = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String msg = String.valueOf(message.getPayload());
        // Send back unique message depending on the id received from the client
        Message message1 = new Gson().fromJson(message.getPayload(), Message.class);
        System.out.println(message.getPayload());
        if (!sessions.containsKey(message1.getSenderToken())) {
            System.out.println("New session from " + message1.getSenderToken());
            Message registeredMessage = new Message("Registered", 2, message1.getSenderToken());
            session.sendMessage(new TextMessage(new Gson().toJson(registeredMessage)));
            sessions.put(message1.getSenderToken(), session);
        }

        if(message1.getAction() == 0) {
            for (Integer key: sessions.keySet()) {
                if (key != message1.getSenderToken()) { //No enviamos mensaje al remitente
                    Message replyMessage = new Message(message1.getMessage(), 3, message1.getSenderToken());
                    sessions.get(key).sendMessage(new TextMessage(new Gson().toJson(replyMessage)));
                }
            }
        } else if (message1.getAction() == 4){
            int token = message1.getSenderToken();
            sessions.remove(token);
            session.close();
            System.out.println("Session with token " + token + " closed by client request");
        }

        //System.out.println(msg);
        //session.sendMessage(new TextMessage("pringui"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.out.println("ERROR " + exception);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("Connection closed");

    }
}
