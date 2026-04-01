package WebGames.GN.BattleShip.controller;

import WebGames.GN.BattleShip.CellState;
import WebGames.GN.BattleShip.dto.GameStateDto;
import WebGames.GN.BattleShip.dto.PlayerTurnDto;
import WebGames.GN.BattleShip.dto.ShipPlacementDto;
import WebGames.GN.BattleShip.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PutMapping
    public CellState fireAtSpecificPlayer(@RequestBody PlayerTurnDto playerTurnDto){
        return boardService.setCellStateByPlayer(playerTurnDto);
    }

    @GetMapping
    public CellState getCellStateByLocation(@RequestBody PlayerTurnDto playerTurnDt){
        return boardService.getCellStateByPlayer(playerTurnDt);
    }

    @GetMapping("/getBoard{player}")
    public ResponseEntity<CellState[][]> getBoardForSpecificPlayer(@PathVariable int player){
        return ResponseEntity.ok(boardService.getBoard(player));
    }

    @PutMapping("/shipPlacement")
    public ResponseEntity<String> placeShipsForSpecificPlayer(@RequestBody ShipPlacementDto shipPlacementDto){

        return ResponseEntity.ok(boardService.placementOfShips(shipPlacementDto));
    }

    @PutMapping("/clearBoard{player}")
    public void resetBoardForSpecificPlayer(@PathVariable int player){
        boardService.clearBoard(player);
    }

    @PutMapping("/newGame")
    public void newGame(){
        boardService.resetGameStateAndBoard();
    }

    @GetMapping("/state")
    public ResponseEntity<GameStateDto> getGameState(){
        return ResponseEntity.ok(boardService.getGameState());
    }
}
