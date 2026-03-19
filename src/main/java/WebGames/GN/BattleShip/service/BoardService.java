package WebGames.GN.BattleShip.service;

import WebGames.GN.BattleShip.CellState;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private int gridSize = 10;
    private CellState[][] board = new CellState[gridSize][gridSize];
    private int healthOfShips = 17;
    private final ShipService shipService;

    public BoardService(ShipService shipService) {
        this.shipService = shipService;

        for (int row = 0; row < gridSize; row++){
            for (int column = 0; column < gridSize; column++){
                board[row][column] = CellState.EMPTY;
            }
        }
    }

    public CellState getCellStateByLocation(int row, int column){
        return board[row][column];
    }

    public CellState setCellStateByLocation(int row, int column){
        Enum<CellState> currentCell = board[row][column];
        if (currentCell == CellState.EMPTY){
            board[row][column] = CellState.MISS;
            return CellState.MISS;
        }
        else if (currentCell == CellState.SHIP) {
            healthOfShips -= 1;
            if (healthOfShips == 0){
                return CellState.ALL_SHIPS_HIT;
            }
            board[row][column] = CellState.HIT;
            return CellState.HIT;
        }
        else{
            return CellState.ALREADY_USED;
        }
    }
}
