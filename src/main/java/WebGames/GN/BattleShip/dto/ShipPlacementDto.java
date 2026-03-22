package WebGames.GN.BattleShip.dto;

public class ShipPlacementDto {
    private int shipNumber;
    private int row;
    private int column;
    private boolean isVertical;

    public ShipPlacementDto() {
    }

    public int getShipNumber() {
        return shipNumber;
    }

    public void setShipNumber(int shipNumber) {
        this.shipNumber = shipNumber;
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

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        this.isVertical = vertical;
    }
}
