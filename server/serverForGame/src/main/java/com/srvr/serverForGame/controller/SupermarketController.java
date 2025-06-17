package com.srvr.serverForGame.controller;

import com.srvr.serverForGame.Repository.HouseRepository;
import com.srvr.serverForGame.Repository.PlayerRepository;
import com.srvr.serverForGame.Repository.SupermarketRepository;
import com.srvr.serverForGame.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/supermarkets")
public class SupermarketController {

    @Autowired
    private SupermarketRepository supermarketRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping
    public ResponseEntity<?> createSupermarket(@RequestBody SupermarketCreateDTO dto) {

        Player player = playerRepository.findById( dto.getPlayer_id())
                .orElseThrow(() -> new RuntimeException("Player not found " + dto.getPlayer_id()));

        Supermarket supermarket = new Supermarket();
        supermarket.set_completed(false);
        supermarket.setConstruction_time(dto.getConstruction_time());
        supermarket.setMonthly_payment(dto.getMonthly_payment());
        supermarket.setPlayer_id(player);

        supermarketRepository.save(supermarket);
        player.setCapital(player.getCapital() - supermarket.getMonthly_payment());
        playerRepository.save(player);

        return ResponseEntity.ok(player.getCapital());
    }

    @GetMapping("/player/{playerId}")
    public List<SupermarketListDTO> getPlayerSupermarkets(@PathVariable Integer playerId) {
        List<Supermarket> supermarkets = supermarketRepository.findByPlayerId(
                playerRepository.findById(playerId)
                        .orElseThrow(() -> new RuntimeException("Player not found"))
        );
        return supermarkets.stream()
                .map(SupermarketController::convertToDto)
                .collect(Collectors.toList());

    }

    public static SupermarketListDTO convertToDto(Supermarket supermarket) {
        SupermarketListDTO dto = new SupermarketListDTO();
        dto.setConstruction_time(supermarket.getConstruction_time());
        dto.setMonthly_payment(supermarket.getMonthly_payment());

        return dto;
    }

}
