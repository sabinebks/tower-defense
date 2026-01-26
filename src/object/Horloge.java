package object;

public class Horloge {
    private int timer;

   /*  constructeur de la classe horloge */
    public Horloge(){
        this.timer=0;
    }
   /*  fonction qui va retourner  le temps  */
    public int getTime(){
        return this.timer;    
    }
/*   fonction qui va servir a ajouter du temps , dans la boucle 
  principale du game a cahque fois on fait un tick d'horloge on incremente le temps  */
    public int addTime(){
        this.timer+=100;
        return this.timer;   
    }
}
