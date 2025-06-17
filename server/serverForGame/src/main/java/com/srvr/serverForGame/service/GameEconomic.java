package com.srvr.serverForGame.service;


import com.srvr.serverForGame.Repository.HouseRepository;
import com.srvr.serverForGame.Repository.PlayerRepository;
import com.srvr.serverForGame.Repository.SupermarketRepository;
import com.srvr.serverForGame.model.House;
import com.srvr.serverForGame.model.Player;
import com.srvr.serverForGame.model.Supermarket;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class GameEconomic {
    private int month = 1;
    private int year = 2025;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private SupermarketRepository supermarketRepository;

    enum Season{
        WINTER(0.5f),
        SPRING(1.0f),
        SUMMER(1.5f),
        AUTUMN(0.7f);

        private final float multiplier;

        // Конструктор enum
        Season(float multiplier) {
            this.multiplier = multiplier;
        }
        public float getMultiplier() {
            return multiplier;
        }
    }

    @Transactional
    public void nextMonth() {
        if(month == 12 && year == 2029){
            return;
        }else if(month == 12){
            year++;
            month = 1;
        }else{
            month++;
        }

        float seasonQ = 1.0f;
        if (month == 1 || month == 2 || month == 12) {
            seasonQ = Season.WINTER.multiplier;
        } else if (month >= 3 && month <= 5) {
            seasonQ = Season.SPRING.multiplier;
        } else if (month >= 6 && month <= 8) {
            seasonQ = Season.SUMMER.multiplier;
        } else if (month >= 9 && month <= 11) {
            seasonQ = Season.AUTUMN.multiplier;
        }

        List<Player> players = playerRepository.findAll();
        for (Player player : players) {
            List<House> houses = houseRepository.findByPlayerId(player);
            List<Supermarket> supermarkets = supermarketRepository.findByPlayerId(player);
            for (Supermarket supermarket : supermarkets) {
                if (supermarket.getConstruction_time() != 0) {
                    player.setCapital(player.getCapital() - supermarket.getMonthly_payment());
                    supermarket.setConstruction_time(supermarket.getConstruction_time() - 1);
                    supermarketRepository.save(supermarket);
                }
            }
            for (House house : houses) {
                if (house.getConstruction_time() != 0) {
                    player.setCapital(player.getCapital() - house.getEzemes_platez());
                    house.setConstruction_time(house.getConstruction_time() - 1);
                    houseRepository.save(house);
                }
            }
        }
        playerRepository.saveAll(players);

        Random random = new Random();
        float quantityOfFlat = (float) random.nextInt(21) + 50;
        quantityOfFlat = quantityOfFlat * seasonQ;

        int sum = 0;
        int quantityHouse = 0;
        List<House> houses = houseRepository.findAll();
        for ( House house : houses){
            if(house.getConstruction_time() == 0 && house.getKolichestvo_kvartir() > 0) {
                sum += house.getStoimost_kvartyry();
                quantityHouse++;
            }
        }
        int sredn = 0;
        if(quantityHouse != 0){
            sredn = sum / quantityHouse;
        }

        float totalShare = 0.0f;
        for(House house : houses){
            if(house.getConstruction_time() == 0 && house.getKolichestvo_kvartir() > 0) {
                Player player = house.getPlayer_id();
                long count = supermarketRepository.findAll().stream()
                        .filter(s -> s.getPlayer_id().getId() == player.getId() && s.is_completed()) // фильтруем по ID игрока
                        .count(); // считаем количество
                float ad = ((float) player.getAdBudget() / 10000) * 0.5f;
                float sup = count * 3;
                float ratio = (float) 1 / house.getStoimost_kvartyry() * sredn;
                float dola = ad + sup + ratio;
                house.setDola(dola);
                totalShare += dola;
            }
        }
        houseRepository.saveAll(houses);

        int quantityOfFlatinHouse = 0;
        for(House house : houses){
            if(house.getConstruction_time() == 0 && house.getKolichestvo_kvartir() > 0) {
                Player player = house.getPlayer_id();
                quantityOfFlatinHouse = (int) (house.getDola() / totalShare * quantityOfFlat);
                if (house.getKolichestvo_kvartir() < quantityOfFlatinHouse) {
                    quantityOfFlatinHouse = house.getKolichestvo_kvartir();
                    house.setKolichestvo_kvartir(0);
                }else{
                    house.setKolichestvo_kvartir(house.getKolichestvo_kvartir() - quantityOfFlatinHouse);
                }
                player.setCapital(player.getCapital() + (quantityOfFlatinHouse * house.getStoimost_kvartyry()));
                player.setAdBudget(0);
                playerRepository.save(player);
            }
        }
        houseRepository.saveAll(houses);

        List<Supermarket> supermarkets = supermarketRepository.findAll();
        for(Supermarket supermarket : supermarkets){
            if(supermarket.getConstruction_time() == 0) {
                Player player = supermarket.getPlayer_id();
                player.setCapital(player.getCapital() + 200000);
                playerRepository.save(player);
            }
        }
    }
}
