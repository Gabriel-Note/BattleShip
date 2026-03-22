/*
package WebGames.GN.BattleShip.service;

import WebGames.GN.BattleShip.dto.ShipDto;
import WebGames.GN.BattleShip.entity.Ship;
import WebGames.GN.BattleShip.repository.ShipRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShipService {

    private final ShipRepository shipRepository;

    public ShipService(ShipRepository shipRepository){
        this.shipRepository = shipRepository;
    }

    public List<ShipDto> getAllShips(){
        List<Ship> shipList = shipRepository.findAll();
        List<ShipDto> shipDtoList = new ArrayList<>();
        for (Ship ship : shipList){
            ShipDto shipDto = new ShipDto();
            shipDto.setId(ship.getId());
            shipDto.setHealth(ship.getHealth());
            shipDtoList.add(shipDto);
        }

        return shipDtoList;
    }
}
*/
