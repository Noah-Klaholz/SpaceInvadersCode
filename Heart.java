/**
 * Die Heart Klasse beinhaltet alle Variablen, die für Heart nötig sind,
 * sodass die Herzen in einer ArrayList (Items) verwaltet werden können.
 */
public class Heart extends Item{

    /**
     * Konstruktor der Heart Klasse.
     * @param x
     * @param y
     */
    public Heart(int x, int y) {
        //Gibt die Parameter an Character weiter
        super(x, y, 2, "Heart", "audio/ItemsPickUpSound.wav");

    }
}
