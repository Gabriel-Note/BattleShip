package WebGames.GN.BattleShip.service;

import WebGames.GN.BattleShip.CellState;
import WebGames.GN.BattleShip.dto.PlayerTurnDto;
import WebGames.GN.BattleShip.dto.ShipPlacementDto;
import WebGames.GN.BattleShip.helper.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Scanner;

@Service
public class BoardService {
    private int gridSize = 10;
    private CellState[][] board = new CellState[gridSize][gridSize];
    private CellState[][] playerOneBoard = new CellState[gridSize][gridSize];
    private CellState[][] playerTwoBoard = new CellState[gridSize][gridSize];
    private int healthOfShips = 17;

    public BoardService() {
        clearAllBoards();
    }

    public CellState getCellStateByPlayer(PlayerTurnDto playerTurnDto){
        CellState[][] board = selectBoard(playerTurnDto.getPlayerNumber());
        return board[playerTurnDto.getRow()][playerTurnDto.getColumn()];
    }

    private CellState[][] selectBoard(int player){
        return switch (player){
            case 1 -> playerOneBoard;
            case 2 -> playerTwoBoard;
            default -> throw new ResponseStatusException(HttpStatus.NOT_FOUND, Message.playerNotFound());
        };
    }

    public void clearBoard(int player){
        CellState[][] board = selectBoard(player);

        for (int row = 0; row < gridSize; row++){
            for (int column = 0; column < gridSize; column++){
                board[row][column] = CellState.EMPTY;
            }
        }
    }

    public void clearAllBoards(){
        clearBoard(1);
        clearBoard(2);
    }

    public CellState[][] getBoard(){
        return board;
    }

    public CellState setCellStateByPlayer(PlayerTurnDto playerTurnDto){
        int row = playerTurnDto.getRow();
        int column = playerTurnDto.getColumn();
        CellState[][] board = selectBoard(playerTurnDto.getPlayerNumber());
        return setCellStateByLocation(row, column, board);
    }

    private CellState setCellStateByLocation(int row, int column, CellState[][] board){
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

    public String placementOfShips(ShipPlacementDto shipPlacementDto) {
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

        if (shipPlacementDto.isVertical()){
            for (int r = 0; r < shipSize; r++){
                try{
                    if (!(board[row+r][column] == CellState.EMPTY)){
                        return "Placement overlaps with another ship at: [" +
                                (row+r) + "]:[" + column + "]";
                    }
                }catch (IndexOutOfBoundsException e){
                    return Message.outOfBounds() + ": " + e.getMessage();
                }
            }
            for (int r = 0; r < shipSize; r++){
                board[row+r][column] = CellState.SHIP;
            }
        } else {
            for (int c = 0; c < shipSize; c++){
                try{
                    if (!(board[row][column+c] == CellState.EMPTY)){
                        return "Placement overlaps with another ship at: [" +
                                row + "]:[" + (column+c) + "]";
                    }
                }catch (IndexOutOfBoundsException e){
                    return Message.outOfBounds() + ": " + e.getMessage();
                }
            }
            for (int c = 0; c < shipSize; c++){
                board[row][column+c] = CellState.SHIP;
            }
        }
        return "Ship placed";
    }
}
