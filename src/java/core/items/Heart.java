package src.java.core.items;

/**
 * Die src.java.core.items.Heart Klasse beinhaltet alle Variablen, die für src.java.core.items.Heart nötig sind,
 * sodass die Herzen in einer ArrayList (Items) verwaltet werden können.
 */
public class Heart extends Item {

    /**
     * Konstruktor der src.java.core.items.Heart Klasse.
     * @param x
     * @param y
     */
    public Heart(int x, int y) {
        //Gibt die Parameter an src.java.core.entities.Character weiter
        super(x, y, 2, "src.java.core.items.Heart", "audioTracks/ItemsPickUpSound.wav");

    }
}
