package com.srvr.serverForGame.Repository;

import com.srvr.serverForGame.model.House;
import com.srvr.serverForGame.model.Player;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface HouseRepository extends JpaRepository<House, Integer> {
    @Query("SELECT h FROM House h WHERE h.player_id = :player")
    List<House> findByPlayerId(@Param("player") Player player);
}
