/**
 * Diese Klasse beinhaltet alle Variablen, die für die Aliens nätig sind.
 * So können diese über eine ArrayList verwaltet werden.
 */
public class Alien extends Character{

    //Variablen
    boolean moveRight;
    boolean moveLeft;
    boolean isVisible;

    /**
     * Konstruktor der Alien Klasse.
     * @param x
     * @param y
     * @param s
     */
    public Alien(int x, int y, int s) {
        //Gibt die Parameter an Character weiter
        super(x, y, s);

        //Setzt Startwerte
        moveRight = true;
        moveLeft = false;
        isVisible = true;
    }
}
