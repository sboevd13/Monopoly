package com.srvr.serverForGame.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "house")
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "stoimost_kvartyry")
    private int stoimost_kvartyry;

    @Column(name = "kolichestvo_kvartir")
    private int kolichestvo_kvartir;

    @Column(name = "is_completed")
    private boolean is_completed;

    @Column(name = "construction_time")
    private int construction_time;

    @Column(name = "ezemes_platez")
    private int ezemes_platez;

    @Column(name = "dola")
    private float dola;


    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player_id;

    // Геттеры и сеттеры
}
