package com.minras.android.hotsapp.manager;

import java.util.ArrayList;

public class MessageManager {
    public static final String STATUS_ERROR = "error";
    public static final String STATUS_WARNING = "warning";
    public static final String STATUS_SUCCESS = "success";
    public static final String STATUS_NEUTRAL = "neutral";

    protected MessageManager() {}
    private static MessageManager instance = null;
    public static MessageManager getInstance() {
        if (instance == null) {
            instance = new MessageManager();
        }
        return instance;
    }

    private ArrayList<MessageListener> messageListeners = new ArrayList<>();

    public interface MessageListener
    {
        void onMessageReceived(String status, String msg);
    }

    public void subscribeMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    public void unsubscribeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
    }

    public void sendMessage(String status, String msg) {
        for (MessageListener listener : messageListeners) {
            listener.onMessageReceived(status, msg);
        }
    }
}
