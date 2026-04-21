package entity.tower.projectil;

/**
 * exception when projectile does not match tower
 */
public class NotMatchException extends Exception {
    /**
     * constructor
     * @param message error message
     */
    public NotMatchException(String message) {
        super(message);
    }

    /**
     * get error message
     * @return message
     */
    public String getMessage() {
        return "the projectile does not match the tower";
    }
}