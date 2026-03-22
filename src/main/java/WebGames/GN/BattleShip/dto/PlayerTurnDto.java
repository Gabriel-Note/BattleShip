package WebGames.GN.BattleShip.dto;

public class PlayerTurnDto {
    int row;
    int column;
    int playerNumber;
    int boardToHitNumber;

    public PlayerTurnDto() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getBoardToHitNumber() {
        return boardToHitNumber;
    }

    public void setBoardToHitNumber(int boardToHitNumber) {
        this.boardToHitNumber = boardToHitNumber;
    }
}
