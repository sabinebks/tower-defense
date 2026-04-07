package entity.tower.projectil;

public class NotMatchException extends Exception {
    public NotMatchException(String message) {
        super(message);
    }

    public String getMessage() {
         return "The projectile does not match the tower";
    }
}
