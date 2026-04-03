package WebGames.GN.BattleShip.service;

import WebGames.GN.BattleShip.CellState;
import WebGames.GN.BattleShip.Mode;
import WebGames.GN.BattleShip.dto.GameStateDto;
import WebGames.GN.BattleShip.dto.PlayerTurnDto;
import WebGames.GN.BattleShip.dto.ShipPlacementDto;
import WebGames.GN.BattleShip.helper.Message;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Scanner;

@Service
public class BoardService {
    private int gridSize = 10;
    private CellState[][] playerOneBoard = new CellState[gridSize][gridSize];
    private CellState[][] playerTwoBoard = new CellState[gridSize][gridSize];
    private int playerTurn;
    public int currentShip;
    public Mode currentMode;
    private int healthOfPlayerOneShips;
    private int healthOfPlayerTwoShips;

    public BoardService() {
        resetGameStateAndBoard();
    }

    public void resetGameStateAndBoard(){
        clearBoard(1);
        clearBoard(2);
        playerTurn = 1;
        currentShip = 5;
        currentMode = Mode.PLACEMENT;
        healthOfPlayerOneShips = 17;
        healthOfPlayerTwoShips = 17;
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



    public CellState[][] getBoard(int player){
        return selectBoard(player);
    }

    public ResponseEntity<?> setCellStateByPlayer(PlayerTurnDto playerTurnDto){
        if (playerTurn == playerTurnDto.getPlayerNumber()){
            return ResponseEntity.badRequest().body("Don't shoot at your own ships");
        }
        int row = playerTurnDto.getRow();
        int column = playerTurnDto.getColumn();
        CellState[][] board = selectBoard(playerTurnDto.getPlayerNumber());
        CellState cellState = setCellStateByLocation(row, column, board);
        if (cellState != CellState.ALREADY_USED){
            switchPlayer(playerTurn);
        } else {
            return ResponseEntity.badRequest().body("You have already fired here");
        }
        return ResponseEntity.ok().body(cellState);
    }

    private void switchPlayer(int player){
        switch (player) {
            case 1 -> playerTurn = 2;
            case 2 -> playerTurn = 1;
        }
    }

    private CellState setCellStateByLocation(int row, int column, CellState[][] board){
        Enum<CellState> currentCell = board[row][column];
        if (currentCell == CellState.EMPTY){
            board[row][column] = CellState.MISS;
            return CellState.MISS;
        }
        else if (currentCell == CellState.SHIP) {
            board[row][column] = CellState.HIT;
            switch (playerTurn){
                case 1:
                    healthOfPlayerTwoShips -= 1;
                    if (healthOfPlayerTwoShips == 0){
                        return CellState.ALL_P2_SHIPS_HIT;
                    }
                    break;
                case 2:
                    healthOfPlayerOneShips -= 1;
                    if (healthOfPlayerOneShips == 0){
                        return CellState.ALL_P1_SHIPS_HIT;
                    }
                    break;
            }
            return CellState.HIT;
        }
        else{
            return CellState.ALREADY_USED;
        }
    }

    public String placementOfShips(ShipPlacementDto shipPlacementDto) {
        if (shipPlacementDto.getPlayer() != playerTurn){
            return "not your turn";
        }
        int row = shipPlacementDto.getRow();
        int column = shipPlacementDto.getColumn();
        int activeShip = shipPlacementDto.getShipNumber();
        CellState[][] board = selectBoard(shipPlacementDto.getPlayer());
        int shipSize = switch (activeShip) {
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

        currentShip -= 1;
        if (activeShip == 1){
            switchPlayer(playerTurn);
            currentShip = 5;
            System.out.println("player turn: " + playerTurn);
            if (playerTurn == 1){
                System.out.println("Mode before: "+ currentMode);
                currentMode = Mode.SHOOTING;
                System.out.println("Mode after: "+ currentMode);

            }
        }
        return "Ship placed";
    }

    public GameStateDto getGameState(){
        GameStateDto gameStateDto = new GameStateDto();
        gameStateDto.setCurrentShip(currentShip);
        gameStateDto.setPlayerTurn(playerTurn);
        gameStateDto.setCurrentMode(currentMode);
        return gameStateDto;
    }
}
