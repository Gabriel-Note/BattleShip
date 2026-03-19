package WebGames.GN.BattleShip.dto;


public class ShipDto {

    private Integer id;
    private int health;

    public ShipDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
