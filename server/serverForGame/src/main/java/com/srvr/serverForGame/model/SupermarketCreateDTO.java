package com.srvr.serverForGame.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupermarketCreateDTO {
    private int construction_time;
    private int monthly_payment;
    private int player_id;
    // Геттеры и сеттеры
}