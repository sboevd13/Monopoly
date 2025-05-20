package com.srvr.serverForGame.service;

import com.srvr.serverForGame.Repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class PlayerUpdateService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private PlayerRepository playerRepository;

    public void notifyPlayersUpdated() {
        messagingTemplate.convertAndSend("/topic/players", playerRepository.findByConnectedTrue());
    }
}
