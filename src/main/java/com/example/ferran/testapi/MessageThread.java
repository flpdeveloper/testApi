package com.example.ferran.testapi;

import com.google.gson.Gson;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

public class MessageThread implements Runnable{

    private final Message message;
    private final WebSocketSession session;
    MessageListener listener;

    public MessageThread(WebSocketSession session, String messagePayload, MessageListener messageListener) {
        this.session = session;
        this.message = Message.makeMessageFromJSOn(messagePayload);
        this.listener = messageListener;
    }

    private void handleRequest(Message message) {
        Message.ACTION action = message.getAction();
        switch (action) {
            case SEND_MESSAGE_EXCLUSIVE:
                sendMessageExclusive(message.getMessage(), message.getSenderToken());
                break;
        }
    }

    private void sendMessageInclusive(String message, String senderToken) {
        try {
            Message m = new Message(message, Message.ACTION.RECEIVE_MESSAGE, senderToken);
            String msgJSOn = new Gson().toJson(m);
            synchronized (session) {
                for(String tokenKey: WebSocketHandler.getSessions().keySet()) {
                    WebSocketHandler.getSessions().get(tokenKey).sendMessage(new TextMessage(msgJSOn));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private void sendMessageExclusive(String message, String senderToken) {
        try {
            Message m = new Message(message, Message.ACTION.RECEIVE_MESSAGE, senderToken);
            String msgJSOn = new Gson().toJson(m);
            synchronized (session) {
                for(String tokenKey: WebSocketHandler.getSessions().keySet()) {
                    if (!senderToken.equals(tokenKey)) {
                        WebSocketHandler.getSessions().get(tokenKey).sendMessage(new TextMessage(msgJSOn));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public interface MessageListener{
        void onMessageSent();
    }
}
