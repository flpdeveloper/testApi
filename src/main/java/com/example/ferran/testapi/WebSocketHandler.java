package com.example.ferran.testapi;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class WebSocketHandler extends AbstractWebSocketHandler implements MessageThread.MessageListener {

    private static Map<String, WebSocketSession> sessions = Collections.synchronizedMap(new HashMap<>());

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        handleMessage(message.getPayload(), session);
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

    public void handleMessage(String messagePayload, WebSocketSession session) {
        new MessageThread(session, messagePayload, this).run();
    }

    @Override
    public void onMessageSent() {

    }

    public static Map<String, WebSocketSession> getSessions() {
        return sessions;
    }
}
