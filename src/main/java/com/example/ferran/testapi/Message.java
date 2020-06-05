package com.example.ferran.testapi;

public class Message {
    private int senderToken;
    private String message;
    private int action;

    public Message(String message, int action, int senderToken) {
        this.message = message;
        this.action = action;
        this.senderToken = senderToken;
    }

    public int getSenderToken() {
        return senderToken;
    }

    public void setSenderToken(int senderToken) {
        this.senderToken = senderToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }
}
