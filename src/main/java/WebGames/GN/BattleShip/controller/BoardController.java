package WebGames.GN.BattleShip.controller;

import WebGames.GN.BattleShip.CellState;
import WebGames.GN.BattleShip.dto.ShipPlacementDto;
import WebGames.GN.BattleShip.service.BoardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PutMapping("/{row}-{column}")
    public CellState setCellStateByLocation(@PathVariable int row,
                                            @PathVariable int column){
        return boardService.setCellStateByLocation(row, column);
    }

    @GetMapping("/{row}-{column}")
    public CellState getCellStateByLocation(@PathVariable int row,
                                            @PathVariable int column){
        return boardService.getCellStateByLocation(row, column);
    }

    @PutMapping("/shipPlacement")
    public String placementOfShips(@RequestBody ShipPlacementDto shipPlacementDto){
        return boardService.placementOfShips(shipPlacementDto);
    }

    @PutMapping("/clearBoard")
    public void clearBoard(){
        boardService.clearBoard();
    }
}
