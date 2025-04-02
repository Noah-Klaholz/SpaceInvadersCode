package src.java.ui;

/**
 * Die src.java.ui.Explosion Klasse beinhaltet alle Variablen, die für Explosionen nötig sind,
 * sodass diese über eine ArrayList verwaltet werden können.
 */
public class Explosion{

    //Variablen
    int x;
    int y;
    int frame;
    long lastFrameTime;

    /**
     * Konstruktor der Explosionsklasse.
     * @param x
     * @param y
     * @param f
     */
    public Explosion(int x, int y, int f) {
        //Setzt die Variablen den Parametern gleich
        this.x = x;
        this.y = y;
        this.frame = f;
    }
}
