package com.srvr.serverForGame.controller;

import com.srvr.serverForGame.Repository.PlayerRepository;
import com.srvr.serverForGame.model.*;
import com.srvr.serverForGame.service.GameEconomic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import static com.srvr.serverForGame.controller.GameWebSocketHandler.notifyAllPlayersEnd;
import static com.srvr.serverForGame.controller.GameWebSocketHandler.notifyAllPlayersReady;

@RestController
@RequestMapping("/api/player")
public class GameSessionController {
    private final PlayerRepository playerRepository;
    private final GameEconomic gameEconomic;

    @Autowired
    public GameSessionController(
            PlayerRepository playerRepository,
            GameEconomic gameEconomic
    ) {
        this.playerRepository = playerRepository;
        this.gameEconomic = gameEconomic;
    }


    @PostMapping("/connect")
    public ResponseEntity<?> connectPlayer(@RequestBody String nickname) {
        if (playerRepository.count() >= 5) {
            return ResponseEntity.badRequest().body("Game session is full");
        }

        Player player = new Player();
        player.setNickname(nickname);
        player.setReady(false);
        player.setCapital(15000000);
        player.setAdBudget(0);
        Player savedPlayer = playerRepository.save(player);

        if (playerRepository.count() >= 2) {
            GameWebSocketHandler.notifyAllPlayers();
        }

        return ResponseEntity.ok(playerRepository.findIdByNickname(savedPlayer.getNickname()));
    }

    @PostMapping("/adBudget")
    public ResponseEntity<?> changeAdBudget(@RequestBody AdBudgetDTO dto) {

        Player player = playerRepository.findById(dto.getPlayer_id())
                .orElseThrow(() -> new RuntimeException("Player not found " + dto.getPlayer_id()));

        player.setAdBudget(dto.getAdBudget());
        player.setCapital(player.getCapital() - dto.getAdBudget());
        playerRepository.save(player);

        return ResponseEntity.ok(player.getCapital());
    }

    @PostMapping("/setReady")
    public ResponseEntity<?> setPlayerReady(@RequestBody PlayerReadyDTO dto, OutputStream outputStream) {

        Player player = playerRepository.findById(dto.getPlayer_id())
                .orElseThrow(() -> new RuntimeException("Player not found " + dto.getPlayer_id()));

        player.setReady(true);
        playerRepository.save(player);

        List<Player> players = playerRepository.findAll();
        boolean allReady = players.stream().allMatch(Player::isReady);

        if (allReady) {
            gameEconomic.nextMonth();
            if(gameEconomic.getMonth() == 12 && gameEconomic.getYear() == 2028){
                notifyAllPlayersEnd();
            }else{
                gameEconomic.setMonth(1);
                gameEconomic.setYear(2025);
                notifyAllPlayersReady(); // Отправляем уведомление через WebSocket
            }
            // Сбрасываем флаги готовности (если нужно)
            players.forEach(p -> {
                p.setReady(false);
                playerRepository.save(p);
            });
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/getMonth")
    public ResponseEntity<?> getCurrentMonth() {
        return ResponseEntity.ok(new TimeDTO(gameEconomic.getMonth(), gameEconomic.getYear()));
    }

    @GetMapping("/getCapital/{playerId}")
    public Integer getCapital(@PathVariable Integer playerId) {
        return playerRepository.findById(playerId)
                .map(Player::getCapital)
                .orElseThrow(() -> new RuntimeException("Player not found"));
    }

    @GetMapping("/getWinner")
    public WinnerDTO getWinner() {
        List<Player> players = playerRepository.findAll();
        Player winner = players.get(0);
        for(Player player : players){
            if (player.getCapital() > winner.getCapital()){
                winner = player;
            }
        }
        return new WinnerDTO(winner.getNickname(), winner.getCapital());
    }

    @GetMapping("/getPlayers/{playerId}")
    public List<PlayerListDTO> getPlayers(@PathVariable Integer playerId) {
        List<Player> players = playerRepository.findAll();
        return players.stream()
                .filter(player -> player.getId() != playerId)
                .map(GameSessionController::convertToDto)
                .collect(Collectors.toList());
    }

    public static PlayerListDTO convertToDto(Player player) {
        PlayerListDTO dto = new PlayerListDTO();
        dto.setNickname(player.getNickname());
        dto.setCapital(player.getCapital());
        return dto;
    }

}
