package src.java.core.items;

/**
 * Die src.java.core.items.MultiShot Klasse beinhaltet alle Variablen, die für src.java.core.items.MultiShot nötig sind,
 * sodass die PowerUps in einer ArrayList (Items) verwaltet werden können.
 */
public class MultiShot extends Item {

    /**
     * Konstruktor für die src.java.core.items.MultiShot Klasse.
     * @param x
     * @param y
     */
    public MultiShot(int x, int y) {
        super(x, y, 2, "MultiShotUp", "audioTracks/ItemsPickUpSound.wav");
    }
}
