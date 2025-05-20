package com.srvr.serverForGame.controller;

import com.srvr.serverForGame.Repository.PlayerRepository;
import com.srvr.serverForGame.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/session")
public class GameSessionController {
    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping("/connect")
    public ResponseEntity<?> connectPlayer(@RequestBody String nickname) {
        if (playerRepository.countByConnectedTrue() >= 5) {
            return ResponseEntity.badRequest().body("Game session is full");
        }

        Player player = new Player();
        player.setNickname(nickname);
        player.setConnected(true);
        playerRepository.save(player);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/players")
    public List<Player> getConnectedPlayers() {
        return playerRepository.findByConnectedTrue();
    }
}
