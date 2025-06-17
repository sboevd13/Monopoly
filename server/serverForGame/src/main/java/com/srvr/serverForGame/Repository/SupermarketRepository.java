package com.srvr.serverForGame.Repository;

import com.srvr.serverForGame.model.House;
import com.srvr.serverForGame.model.Player;
import com.srvr.serverForGame.model.Supermarket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SupermarketRepository extends JpaRepository<Supermarket, Integer> {
    @Query("SELECT s FROM Supermarket s WHERE s.player_id = :player")
    List<Supermarket> findByPlayerId(@Param("player") Player player);
}
