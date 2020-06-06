package com.example.ferran.testapi;

import com.google.gson.Gson;

public class Message {

    private String senderToken;
    private String message;
    private ACTION action;
    private String gameID;

    public Message(String message, ACTION action, String senderToken) {
        this.message = message;
        this.action = action;
        this.senderToken = senderToken;
    }

    public static Message makeMessageFromJSOn(String json) {
        try {
            return new Gson().fromJson(json, Message.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public String getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(String senderToken) {
        this.senderToken = senderToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ACTION getAction() {
        return action;
    }

    public void setAction(ACTION action) {
        this.action = action;
    }

    public enum ACTION {
        SEND_MESSAGE_INCLUSIVE,
        SEND_MESSAGE_EXCLUSIVE,
        RECEIVE_MESSAGE,
        UPDATE,
        END_GAME,
        REGISTER,
        REGISTER_SUCCESSFUL,
        CLOSE_SESSION
    }
}
