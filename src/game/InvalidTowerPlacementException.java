package game;

public class InvalidTowerPlacementException extends Exception {

    public InvalidTowerPlacementException(String message) {
        super(message);
    }

    public InvalidTowerPlacementException() {
        super();
    }
}
