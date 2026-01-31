package object;

public class Game {
    
    private int lives;      // vies du joueur

    private int money;      // argent total

    private int currentRoundNumber;

    private Round currentRound;

    /* constructeur de la classe Game */
    public Game(int lives) {
        this.lives = lives;
        this.money = 0;
        this.currentRoundNumber = 0;
        this.currentRound = null;
    }

    /* fonction qui retourne les vies du joueur */
    public int getLives() {
        return lives;
    }
    /* procedure pour decrementer les vies du joueur */
    public void decrementLives(int lives) {
        this.lives -= lives;
    }
    /* fonction qui retourne l'argent du joueur */
    public int getMoney() {
        return money;
    }
    /* procedure pour mettre a jour l'argent du joueur */
    public void addMoney(int somme) {
          this.money += somme;
    }

    /* fonction qui retourne le numero de la manche courante */
    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }   
    /* procedure pour incrementer le numero de la manche courante */
    public void incrementCurrentRoundNumber() {
        this.currentRoundNumber++;
    }   
    /* fonction qui retourne la manche courante */
    public Round getCurrentRound() {
        return currentRound;
    }   
    /* procedure pour definir la manche courante */
    public void setCurrentRound(Round currentRound) {
        this.currentRound = currentRound;
    }       
    /* procedure pour faire perdre une vie au joueur */
    public void loseLife() {
        this.lives--;
    }


}
