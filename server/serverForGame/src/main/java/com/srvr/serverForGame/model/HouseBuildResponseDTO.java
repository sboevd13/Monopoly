package com.srvr.serverForGame.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HouseBuildResponseDTO {
    private int stoimost_kvartyry;

    private int kolichestvo_kvartir;

    private int construction_time;

    private int ezemes_platez;

    private int player_capital;

}
