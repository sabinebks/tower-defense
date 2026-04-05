package game;

import board.*;
import board.typeOfBoard.*;
import object.balloon.*;
import object.tower.*;
import object.tower.evolution.TypeEvolution;
import object.tower.projectil.*;
import player.Player;

import java.util.*;

public class Game {
    /**
     * les attributs :
     * ballons : les ballons qui existent dans la grille
     * towers : les towers qui existe dans la grille
     * activeProjectils : les projectil qui seront lancer par les towers
     * board : la grille de jeux
     * idCounter : pour les Id des ballons
     * player : le joueur
     */
    private List<Balloon> balloons;
    private List<Tower> towers;
    private List<Projectil> activeProjectils;
    private Board board;
    private int idCounter = 0;
    private Player player;

    /**
     * Le constructeur de la classe Game
     */
    public Game() {
        this.balloons = new ArrayList<>();
        this.towers = new ArrayList<>();
        this.activeProjectils = new ArrayList<>();
        this.board = null;
        this.player = new Player(new HumanListChooser());
    }

}