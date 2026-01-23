package board;

/**
 * Représente la position dune cellule dans le plateau
 * Chaque position a une ligne (row) et une colonne (col)
 */
public class Position {

    /**  la ligne */
    private int row;

    /**  la colonne */
    private int col;

    /**
     * Constructeur de Position
     *
     * @param row Ligne de la cellule 
     * @param col Colonne de la cellule
     */
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Retourne l'indice de la ligne
     *
     * @return Ligne de la cellule
     */
    public int getRow() {
        return row;
    }

    /**
     * Retourne l'indice de la colonne
     *
     * @return Colonne de la cellule
     */
    public int getCol() {
        return col;
    }

    /**
     * methode equals pour comparer deux positions 
     *
     * @param obj Objet à comparer
     * @return true si positions identiques, false sinon
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
          return true;
        } 
        if (obj == null || getClass() != obj.getClass()) 
            {return false;}
        Position other = (Position) obj;
        return this.row == other.row && this.col == other.col;
    }

   
    /**
     * ecrit la position
     *
     * @return String de la forme "(row,col)"
     */
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
