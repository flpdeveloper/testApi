package com.example.ferran.testapi;

import com.google.gson.Gson;
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

        if (!sessions.containsKey(message1.getSenderToken())) {
            System.out.println("New session from " + message1.getSenderToken());
            sessions.put(message1.getSenderToken(), session);
        }

        if(message1.getAction() == 0) {
            for (Integer key: sessions.keySet()) {
                if (key != message1.getSenderToken()) { //No enviamos mensaje al remitente
                    sessions.get(key).sendMessage(new TextMessage(message1.getMessage()));
                }
            }
        } else {

        }

        System.out.println(msg);
        //session.sendMessage(new TextMessage("pringui"));
    }
}
