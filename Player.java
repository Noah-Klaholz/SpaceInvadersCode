/**
 * Die Player Klasse beihnaltet alle Variablen, die für Player nötig sind.
 */
public class Player extends Character {

    //Variablen
    boolean moveRight;
    boolean moveLeft;
    boolean isVisible;

    /**
     * Konstruktor der Player Klasse.
     * @param x
     * @param y
     * @param s
     */
    public Player(int x, int y, int s) {
        //Gibt die Parameter an Character weiter
        super(x, y, s);

        //Setzt die Startwerte
        isVisible = true;
        moveRight = false;
        moveLeft = false;
    }
}
