package com.srvr.serverForGame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
public class ServerForGameApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ServerForGameApplication.class, args);
    }

}
