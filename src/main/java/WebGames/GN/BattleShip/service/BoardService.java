package WebGames.GN.BattleShip.service;

import WebGames.GN.BattleShip.CellState;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private int gridSize = 10;
    private CellState[][] board = new CellState[gridSize][gridSize];

    public BoardService() {
        for (int row = 0; row < gridSize; row++){
            for (int col = 0; col < gridSize; col++){
                board[row][col] = CellState.EMPTY;
            }
        }
    }



}
