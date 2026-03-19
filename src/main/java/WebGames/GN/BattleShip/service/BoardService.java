package WebGames.GN.BattleShip.service;

import WebGames.GN.BattleShip.CellState;
import WebGames.GN.BattleShip.dto.ShipPlacementDto;
import WebGames.GN.BattleShip.helper.Helper;
import org.springframework.stereotype.Service;

import java.util.Scanner;

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

    public void placementOfShips(ShipPlacementDto shipPlacementDto) {
        int row = shipPlacementDto.getRow();
        int column = shipPlacementDto.getColumn();
        int currentShip = shipPlacementDto.getShipNumber();
        int shipSize = switch (currentShip) {
            case 5 -> 5;
            case 4 -> 4;
            case 2, 3 -> 3;
            case 1 -> 2;
            default -> -1;
        };

        if (shipPlacementDto.isHorizontal()){
            for (int r = 0; r < shipSize; r++){
                if (!(board[row+r][column] == CellState.EMPTY)){
                    System.out.println("Placement overlaps with other ships or is out of bounds at: [" +
                            (row+r) + "]:[" + column + "]");
                    return;
                }
            }
        } else {
            for (int c = 0; c < shipSize; c++){
                if (!(board[row][column+c] == CellState.EMPTY)){
                    System.out.println("Placement overlaps with other ships or is out of bounds at: [" +
                            row + "]:[" + (column+c) + "]");
                    return;
                }
            }
        }

    }
}
