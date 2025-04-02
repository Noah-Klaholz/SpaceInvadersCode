package src.java.core.items;

/**
 * Die src.java.core.items.ShotSpeedUp Klasse beinhaltet alle Variablen, die für src.java.core.items.ShotSpeedUp nötig sind,
 * sodass die PowerUps in einer ArrayList (Items) verwaltet werden können.
 */
public class ShotSpeedUp extends Item {

    /**
     * Konstruktor der src.java.core.items.ShotSpeedUp Klasse.
     * @param x
     * @param y
     */
    public ShotSpeedUp(int x, int y) {
        super(x, y, 2, "src.java.core.items.ShotSpeedUp", "audioTracks/ItemsPickUpSound.wav");
    }
}
