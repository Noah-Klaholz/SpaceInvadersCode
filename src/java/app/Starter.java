package src.java.app;

import src.java.ui.*;

import javax.swing.*;

/**
 * Die src.java.app.Starter Klasse startet das Menu und verwaltet alle Fenster.
 * Sie ermöglicht so auch das Wechseln zwischen beispielsweise dem Menü und dem Spiel (und zurück) oder das Einsehen in die Statistiken.
 */
public class Starter extends JFrame {

    float setMenuVolume = -15.0f;
    float setBackgroundVolume = -20.0f;
    float setSoundVolume =  -10.0f;
    public boolean tutorials = true;

    Menu menu;
    /**
     * Im Konstruktor von src.java.app.Starter werden die Parameter des JFrames gesetzt.
     */
    public Starter() {


        //Bestimmt die Eigenschaften des JFrames src.java.app.Starter()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        setResizable(false);
        setFocusable(false);

        openMenu();
    }

    /**
     * Wird beim Programmstart aufgerufen und erzeugt eine neue Instanz von src.java.app.Starter();.
     * Dabei wird der obige Konstruktor aufgerufen.
     * @param args
     */
    public static void main(String[] args) {new Starter();}

    /**
     * Öffnet das Menü und legt die Parameter für dieses fest.
     */
    public void openMenu() {
        setTitle("Menu");
        menu = new Menu(this, setMenuVolume);
        menu.setFocusable(false);
        add(menu);

        //Macht das JFrame bzw. das Fenster sichtbar
        setVisible(true);
    }

    /**
     * Startet das Spiel entweder normal oder im Sandbox Modus (Erzeugt eine neue Instanz von src.java.ui.Board)
     * @param sandBox
     */
    public void startGame(boolean sandBox) {
        setTitle("src.java.ui.Board");
        //Erzeugt eine neue Instanz von src.java.ui.Board und bestimmt ihre Eigenschaften
        Board board = new Board(this, sandBox, setBackgroundVolume, setSoundVolume, menu);

        //Fügt board ins Fenster ein und macht dieses sichtbar
        add(board);
        setVisible(true);

        //Erlaubt board Fokus zu erhalten und fordert diesen für board
        board.setFocusable(true);
        board.requestFocusInWindow();
    }

    /**
     * Öffnet eine neue Instanz von src.java.ui.RegistrationForm und übergibt ihr die aktuellen Rundenwerte (die dieser Methode als Parameter übergeben werden).
     * @param score
     * @param wave
     * @param kills
     * @param shotsFired
     * @param heartsLost
     */
    public void enterName(int score, int wave, int kills, int shotsFired, int heartsLost){
        RegistrationForm r = new RegistrationForm(new JFrame(), this, score, wave, kills, shotsFired, heartsLost);
    }

    /**
     * Öffnet eine neue Instanz von LoginForm und übergibt ihr die aktuellen Rundenwerte (die dieser Methode als Parameter übergeben werden).
     * @param roundScore
     * @param roundWave
     * @param roundKills
     * @param roundShotsFired
     * @param roundHeartsLost
     */
    public void login(int roundScore, int roundWave, int roundKills, int roundShotsFired, int roundHeartsLost) {
        LoginForm l = new LoginForm(new JFrame(), this, roundScore, roundWave, roundKills, roundShotsFired, roundHeartsLost);
    }

    /**
     * Öffnet eine neue Instanz von src.java.ui.DisplayTable
     */
    public void showTable(){
        DisplayTable d = new DisplayTable(new JFrame(), this, menu);
    }

    /**
     * Öffnet eine neue Instanz von src.java.ui.Einstellungen
     */
    public void settings(){
        Einstellungen s = new Einstellungen(new JFrame(), this, setMenuVolume, setBackgroundVolume, setSoundVolume);
    }

    /**
     * Setzt die Lautsärkewerte anhand der übergebenen Parameter.
     * @param setBackgroundVolume
     * @param setMenuVolume
     * @param setSoundVolume
     */
    public void getVolumes(float setBackgroundVolume, float setMenuVolume, float setSoundVolume){
        this.setBackgroundVolume = setBackgroundVolume;
        this.setMenuVolume = setMenuVolume;
        this.setSoundVolume = setSoundVolume;
    }

}
