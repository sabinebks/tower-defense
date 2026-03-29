package game;

import board.Board;
import board.Position;
import object.balloon.Balloon;
import object.balloon.BalloonType;
import object.tower.Tower;
import object.tower.projectil.Projectil;

import java.util.ArrayList;
import java.util.List;

public class Round {

    public int numRound;
    private ArrayList<Balloon> balloons;
    private int idBalloons = 0;
    private List<Projectil> activeProjectils;
    private Board board;
    public Round(int numRound) {
        this.numRound = numRound;
        this.board = null;
        this.balloons = new ArrayList<>();
        this.activeProjectils = new ArrayList<>();
    }

    /**
     * Cree et ajoute un ballon au jeu en fonction du type de plateau
     *
     * @param type le type de ballon
     */

    public void spawnBalloon(BalloonType type) {
        idBalloons++;

        List<Position> cheminASuivre = this.board.getPathPositions();

        if (cheminASuivre != null && !cheminASuivre.isEmpty()) {
            Position depart = cheminASuivre.get(0);
            Balloon b = new Balloon(idBalloons, depart.getRow(), depart.getCol(), type);
            b.setPath(cheminASuivre);

            balloons.add(b);
        }
    }

    //getters

    public int getNumRound() {
        return numRound;
    }

    public ArrayList<Balloon> getBalloons() {
        return balloons;
    }

    public List<Projectil> getActiveProjectils(){
        return activeProjectils;
    }

    public Board getBoard(){
        return this.board;
    }

    //methodes

    public void addBalloon(Balloon balloon) {
        this.balloons.add(balloon);
    }

    public void removeBalloon(Balloon balloon) {
        this.balloons.remove(balloon);
    }


    public boolean estFinie(){
        if(balloons.isEmpty()){
            return true;
        }
        return false;
    }
}
