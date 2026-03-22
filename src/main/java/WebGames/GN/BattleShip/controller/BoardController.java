package WebGames.GN.BattleShip.controller;

import WebGames.GN.BattleShip.CellState;
import WebGames.GN.BattleShip.dto.PlayerTurnDto;
import WebGames.GN.BattleShip.dto.ShipPlacementDto;
import WebGames.GN.BattleShip.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PutMapping
    public CellState setCellStateByLocation(@RequestBody PlayerTurnDto playerTurnDto){
        return boardService.setCellStateByPlayer(playerTurnDto);
    }

    @GetMapping
    public CellState getCellStateByLocation(@RequestBody PlayerTurnDto playerTurnDt){
        return boardService.getCellStateByPlayer(playerTurnDt);
    }

    @GetMapping("/getBoard{player}")
    public ResponseEntity<CellState[][]> getBoard(@PathVariable int player){
        return ResponseEntity.ok(boardService.getBoard(player));
    }

    @PutMapping("/shipPlacement")
    public String placementOfShips(@RequestBody ShipPlacementDto shipPlacementDto){
        return boardService.placementOfShips(shipPlacementDto);
    }

    @PutMapping("/clearBoard{player}")
    public void clearBoard(@PathVariable int player){
        boardService.clearBoard(player);
    }

    @PutMapping("/newGame")
    public void clearAllBoards(){
        boardService.clearAllBoards();
    }
}
