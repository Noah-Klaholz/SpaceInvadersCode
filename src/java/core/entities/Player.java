package src.java.core.entities;

/**
 * Die src.java.core.entities.Player Klasse beihnaltet alle Variablen, die für src.java.core.entities.Player nötig sind.
 */
public class Player extends Character {

    //Variablen
    public boolean moveRight;
    public boolean moveLeft;
    public boolean isVisible;

    /**
     * Konstruktor der src.java.core.entities.Player Klasse.
     * @param x
     * @param y
     * @param s
     */
    public Player(int x, int y, int s) {
        //Gibt die Parameter an src.java.core.entities.Character weiter
        super(x, y, s);

        //Setzt die Startwerte
        isVisible = true;
        moveRight = false;
        moveLeft = false;
    }
}
