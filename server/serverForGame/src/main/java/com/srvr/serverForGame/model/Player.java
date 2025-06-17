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
@Table(name = "players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nickname")  // Кавычки для регистрозависимости
    private String nickname;

    @Column(name = "is_ready")   // Указываем точное имя из БД
    private boolean isReady;

    @Column(name = "ad_budget")     // Без кавычек, т.к. в БД lowercase
    private int adBudget;

    @Column(name = "capital")       // Без кавычек
    private int capital;
}
