package com.example.ferran.testapi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSocket
// Add this annotation to an @Configuration class to configure processing WebSocket requests
public class WebSocketConfiguration implements WebSocketConfigurer {

    Set<String> paths = new HashSet<>();

    private static WebSocketConfiguration instance;
    private WebSocketHandlerRegistry registry;

    public static WebSocketConfiguration getInstance() {
        if (instance != null) {
            return instance;
        }
        return null;
    }

    public WebSocketHandlerRegistry getRegistry() {
        return registry;
    }

    public void addRegistryPath(String path) {
        if(!paths.contains(path)) {
            if(getRegistry() != null) {
                getRegistry().addHandler(new WebSocketHandler(), "/" + path);
                paths.add(path);
            } else {
                System.out.println("REGISTRY NULL");
            }
        }
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(), "/websocket");
        //registry.addHandler(new WebSocketHandler(), "/BHX123");
        this.registry = registry;
        instance = this;
    }
}
