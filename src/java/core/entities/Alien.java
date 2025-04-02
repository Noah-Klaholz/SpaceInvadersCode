package src.java.core.entities;

/**
 * Diese Klasse beinhaltet alle Variablen, die für die Aliens nätig sind.
 * So können diese über eine ArrayList verwaltet werden.
 */
public class Alien extends Character {

    //Variablen
    public boolean moveRight;
    public boolean moveLeft;
    boolean isVisible;

    /**
     * Konstruktor der src.java.core.entities.Alien Klasse.
     * @param x
     * @param y
     * @param s
     */
    public Alien(int x, int y, int s) {
        //Gibt die Parameter an src.java.core.entities.Character weiter
        super(x, y, s);

        //Setzt Startwerte
        moveRight = true;
        moveLeft = false;
        isVisible = true;
    }
}
