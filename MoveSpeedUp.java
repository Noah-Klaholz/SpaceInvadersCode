/**
 * Die MoveSpeedUp Klasse beinhaltet alle Variablen, die für MoveSpeedUp nötig sind,
 * sodass die PowerUps in einer ArrayList (Items) verwaltet werden können.
 */
public class MoveSpeedUp extends Item {

    /**
     * Konstruktor für die MoveSpeedUp Klasse.
     * @param x
     * @param y
     */
    public MoveSpeedUp(int x, int y) {
        super(x, y, 2, "MoveSpeedUp", "audio/ItemsPickUpSound.wav");
    }
}
