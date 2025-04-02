package src.java.core.items;

import src.java.core.entities.Character;

/**
 * Die src.java.core.items.Item Klasse beinhaltet alle Variablen, die für Items nötig sind,
 * sodass verschiedene PowerUps in einer ArrayList vom Typ src.java.core.items.Item verwaltet werden können.
 * Sie dient dabei als Elternklasse für die verschiedenen PowerUps (z.B. Herzen).
 */
public class Item extends Character {
    public String type;
    public String audioTrack;

    /**
     * Konstruktor der src.java.core.items.Item Klasse.
     * @param x
     * @param y
     * @param s
     * @param type
     * @param audioTrack
     */
    public Item(int x, int y, int s, String type, String audioTrack) {
        //Gibt die Parameter an src.java.core.entities.Character weiter
        super(x, y, s);
        this.type = type;
        this.audioTrack = audioTrack;
    }
}
