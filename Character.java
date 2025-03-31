/**
 * Die Character Klasse beinhaltet alle Variablen von Character und dient als Elternklasse für Alien und Player
 */
public class Character {

    //Variablen
    int x;
    int y;
    int speed;

    /**
     * Konstruktor von Character.
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
