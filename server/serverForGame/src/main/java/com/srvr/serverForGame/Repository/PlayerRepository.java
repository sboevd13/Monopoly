package com.srvr.serverForGame.Repository;

import com.srvr.serverForGame.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Integer> {

    @Query("SELECT p.id FROM Player p WHERE p.nickname = :nickname")
    Long findIdByNickname(String nickname);

}
