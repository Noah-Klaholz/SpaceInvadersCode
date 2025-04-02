package src.java.core.items;

/**
 * Die src.java.core.items.MoveSpeedUp Klasse beinhaltet alle Variablen, die für src.java.core.items.MoveSpeedUp nötig sind,
 * sodass die PowerUps in einer ArrayList (Items) verwaltet werden können.
 */
public class MoveSpeedUp extends Item {

    /**
     * Konstruktor für die src.java.core.items.MoveSpeedUp Klasse.
     * @param x
     * @param y
     */
    public MoveSpeedUp(int x, int y) {
        super(x, y, 2, "src.java.core.items.MoveSpeedUp", "audioTracks/ItemsPickUpSound.wav");
    }
}
