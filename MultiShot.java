/**
 * Die MultiShot Klasse beinhaltet alle Variablen, die für MultiShot nötig sind,
 * sodass die PowerUps in einer ArrayList (Items) verwaltet werden können.
 */
public class MultiShot extends Item {

    /**
     * Konstruktor für die MultiShot Klasse.
     * @param x
     * @param y
     */
    public MultiShot(int x, int y) {
        super(x, y, 2, "MultiShotUp", "audio/ItemsPickUpSound.wav");
    }
}
