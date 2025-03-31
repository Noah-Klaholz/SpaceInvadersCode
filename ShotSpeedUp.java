/**
 * Die ShotSpeedUp Klasse beinhaltet alle Variablen, die für ShotSpeedUp nötig sind,
 * sodass die PowerUps in einer ArrayList (Items) verwaltet werden können.
 */
public class ShotSpeedUp extends Item {

    /**
     * Konstruktor der ShotSpeedUp Klasse.
     * @param x
     * @param y
     */
    public ShotSpeedUp(int x, int y) {
        super(x, y, 2, "ShotSpeedUp", "audio/ItemsPickUpSound.wav");
    }
}
