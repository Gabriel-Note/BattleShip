package WebGames.GN.BattleShip.repository;

import WebGames.GN.BattleShip.entity.Ship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Integer> {
}
