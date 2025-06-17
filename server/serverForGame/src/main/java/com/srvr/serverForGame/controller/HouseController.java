package com.srvr.serverForGame.controller;

import com.srvr.serverForGame.Repository.HouseRepository;
import com.srvr.serverForGame.Repository.PlayerRepository;
import com.srvr.serverForGame.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/houses")
public class HouseController {

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @PostMapping
    public ResponseEntity<?> createHouse(@RequestBody HouseCreateDTO dto) {

        System.out.println("Получен запрос: " + dto.getPlayer_id() + " !!! " + dto.getKolichestvo_kvartir() + " !!! ");
        Player player = playerRepository.findById( dto.getPlayer_id())
                .orElseThrow(() -> new RuntimeException("Player not found " + dto.getPlayer_id()));

        House house = new House();
        house.setStoimost_kvartyry(dto.getStoimost_kvartyry());
        house.setKolichestvo_kvartir(dto.getKolichestvo_kvartir());
        house.set_completed(false);
        house.setConstruction_time(dto.getConstruction_time());
        house.setEzemes_platez(dto.getEzemes_platez());
        house.setDola(0.0f);
        house.setPlayer_id(player);

        houseRepository.save(house);
        player.setCapital(player.getCapital() - house.getEzemes_platez());
        playerRepository.save(player);

        return ResponseEntity.ok(player.getCapital());
    }

    @GetMapping("/player/{playerId}")
    public List<HouseListDTO> getPlayerHouses(@PathVariable Integer playerId) {
        List<House> houses = houseRepository.findByPlayerId(
                playerRepository.findById(playerId)
                        .orElseThrow(() -> new RuntimeException("Player not found"))
        );

        return houses.stream()
                .map(HouseController::convertToDto)
                .collect(Collectors.toList());

    }

    public static HouseListDTO convertToDto(House house) {
        HouseListDTO dto = new HouseListDTO();
        dto.setStoimost_kvartyry(house.getStoimost_kvartyry());
        dto.setKolichestvo_kvartir(house.getKolichestvo_kvartir());
        dto.setConstruction_time(house.getConstruction_time());
        dto.setEzemes_platez(house.getEzemes_platez());

        return dto;
    }
}
