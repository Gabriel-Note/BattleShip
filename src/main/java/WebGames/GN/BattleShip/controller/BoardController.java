package WebGames.GN.BattleShip.controller;

import WebGames.GN.BattleShip.CellState;
import WebGames.GN.BattleShip.service.BoardService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PutMapping("/{row}{column}")
    public CellState setCellStateByLocation(@PathVariable int row,
                                            @PathVariable int column){
        return boardService.setCellStateByLocation(row, column);
    }
}
