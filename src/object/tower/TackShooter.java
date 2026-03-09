package object.tower;

import board.Position;

import object.balloon.*;
import object.tower.projectil.Projectil;
import object.tower.projectil.ProjectilType;


public class TackShooter extends Tower {
  /**
     * Crée une TackShooter à la position spécifiée.
     *
     * @param pos la position initiale de la tour sur le plateau
     */
    public TackShooter(Position pos) {
        super(pos, 350, 80, 2, true, EffectType.IMMEDIATE, ProjectilType.AIGUILLE);
    }
}
