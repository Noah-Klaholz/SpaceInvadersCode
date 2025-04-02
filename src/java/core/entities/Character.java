package src.java.core.entities;

/**
 * Die src.java.core.entities.Character Klasse beinhaltet alle Variablen von src.java.core.entities.Character und dient als Elternklasse für src.java.core.entities.Alien und src.java.core.entities.Player
 */
public class Character {

    //Variablen
    public int x;
    public int y;
    public int speed;

    /**
     * Konstruktor von src.java.core.entities.Character.
     * @param x
     * @param y
     * @param speed
     */
    public Character(int x, int y, int speed) {
        //Setzt die Variablen den Parametern gleich
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
}
