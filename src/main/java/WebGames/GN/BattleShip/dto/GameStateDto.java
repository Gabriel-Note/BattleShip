package WebGames.GN.BattleShip.dto;

import WebGames.GN.BattleShip.Mode;

public class GameStateDto {
    private int playerTurn;
    private int currentShip;
    private Mode currentMode;

    public GameStateDto() {
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public int getCurrentShip() {
        return currentShip;
    }

    public void setCurrentShip(int currentShip) {
        this.currentShip = currentShip;
    }

    public Mode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(Mode currentMode) {
        this.currentMode = currentMode;
    }
}
