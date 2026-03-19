package WebGames.GN.BattleShip.controller;

import WebGames.GN.BattleShip.dto.ShipDto;
import WebGames.GN.BattleShip.service.BoardService;
import WebGames.GN.BattleShip.service.ShipService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/battleShips")
public class ShipController {

    private final ShipService shipService;
    private final BoardService boardService;

    public ShipController(ShipService shipService, BoardService boardService) {
        this.shipService = shipService;
        this.boardService = boardService;
    }

    HashMap<int [][], Boolean> map = new HashMap<>();

    @GetMapping
    public List<ShipDto> getAllShips(ShipDto shipDto){
        List<ShipDto> shipDtoList = new ArrayList<>();
        shipDtoList = shipService.getAllShips();
        return shipDtoList;
    }
}
