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
@Table(name = "supermarket")
public class Supermarket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "is_completed")
    private boolean is_completed;

    @Column(name = "construction_time")
    private int construction_time;

    @Column(name = "monthly_payment")
    private int monthly_payment;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player_id;

    // Геттеры и сеттеры
}
