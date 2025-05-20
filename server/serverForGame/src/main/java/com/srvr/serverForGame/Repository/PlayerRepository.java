package com.srvr.serverForGame.Repository;

import com.srvr.serverForGame.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByConnectedTrue();

    int countByConnectedTrue();
}
