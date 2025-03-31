/**
 * Die Item Klasse beinhaltet alle Variablen, die für Items nötig sind,
 * sodass verschiedene PowerUps in einer ArrayList vom Typ Item verwaltet werden können.
 * Sie dient dabei als Elternklasse für die verschiedenen PowerUps (z.B. Herzen).
 */
public class Item extends Character{
    String type;
    String audioTrack;

    /**
     * Konstruktor der Item Klasse.
     * @param x
     * @param y
     * @param s
     * @param type
     * @param audioTrack
     */
    public Item(int x, int y, int s, String type, String audioTrack) {
        //Gibt die Parameter an Character weiter
        super(x, y, s);
        this.type = type;
        this.audioTrack = audioTrack;
    }
}
