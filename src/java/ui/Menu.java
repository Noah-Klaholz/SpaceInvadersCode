package src.java.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import src.java.audio.*;
import src.java.app.Starter;

/**
 * Die Menu Klasse verwaltet das gesamte Menü inklusive der Buttons, der Musik und dem Hintergrund.
 */
public class Menu extends JPanel implements MouseListener{
    Starter starter;
    Toolkit t = Toolkit.getDefaultToolkit();

    Image BackgroundImg = t.getImage("PNG/background.jpg");
    Image TitleImg = t.getImage("PNG/Title.png");
    Image SettingsButton = t.getImage("PNG/settingsButton.png");
    Image ExitButton = t.getImage("PNG/ExitButton.png");

    int mouseX;
    int mouseY;
    int startX = 600;
    int startY = 600;
    int startWidth = 300;
    int startHeight = 60;
    int statsX = 600;
    int statsY = 700;
    int statsWidth = 300;
    int statsHeight = 60;
    int sandBoxX = 600;
    int sandBoxY = 800;
    int sandBoxWidth = 300;
    int sandBoxHeight = 60;
    int settingsButtonX = 1420;
    int settingsButtonY = 900;
    int settingsButtonWidth = 50;
    int settingsButtonHeight = 50;
    int exitButtonX = 20;
    int exitButtonY = 20;
    int exitButtonWidth = 36;
    int exitButtonHeight = 36;
    float setMenuVolume;


    Font middle = new Font("Bitstream Vera Sans", Font.BOLD, 35);
    Font veryBig = new Font("Bitstream Vera Sans", Font.BOLD, 110);

    /**
     * Im Konstruktor von Menu wird das Menu gezeichnet, der mouseListener hinzugefügt und die Lautstärke geregelt.
     * @param starter
     * @param setMenuVolume
     */
    public Menu(Starter starter, float setMenuVolume) {
        this.starter = starter;
        this.setMenuVolume = setMenuVolume;
        MenuMusik.musicStart(setMenuVolume);
        //Fügt einen Mouse Listener hinzu, um im Spiel inputs vom Spieler zu erkennen
        addMouseListener(this);
        repaint(); //Ruft paint() auf

    }

    /**
     * Zeichnet alle zu zeichnenden Elemente des Menüs.
     * @param g  the <code>Graphics</code> context in which to paint
     */
    public void paint(Graphics g) {
        g.drawImage(BackgroundImg, 0, 0, this);
        g.drawImage(TitleImg, 120, -90, this);
        g.drawImage(SettingsButton, settingsButtonX, settingsButtonY, this);
        g.drawImage(ExitButton, exitButtonX, exitButtonY, this);
        g.setColor(Color.darkGray);
        g.fillRect(startX, startY, startWidth, startHeight);
        g.fillRect(statsX, statsY, statsWidth, statsHeight);
        g.fillRect(sandBoxX, sandBoxY, sandBoxWidth, sandBoxHeight);
        g.setColor(Color.lightGray);
        g.setFont(middle);
        g.drawString("Start", startX + 110, startY + 43);
        g.drawString("src.java.core.entities.Statistics", statsX + 75, statsY + 43);
        g.drawString("Sandbox-Mode", sandBoxX + 28, sandBoxY + 43);
        g.setFont(veryBig);
        g.setColor(Color.red);
    }


    /**
     * Implementiert einen mouseListener und ermöglicht so die Überprüfung, ob Buttons gedrückt wurden
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        //Speichert die Position der Maus
        mouseX = e.getX();
        mouseY = e.getY();
        //Überprüft, ob der Klick auf einem der Buttons war
        if (mouseX >= startX && mouseX <= startX + startWidth
                && mouseY >= startY && mouseY <= startY + startHeight) {
            MenuMusik.musicStop();
            //Startet das Spiel und entfernt das Menu
            starter.remove(this);
            starter.startGame(false);
        }
        if (mouseX >= sandBoxX && mouseX <= sandBoxX + sandBoxWidth
                && mouseY >= sandBoxY && mouseY <= sandBoxY + sandBoxHeight){
            MenuMusik.musicStop();
            //Startet das Spiel im SandBox-Modus und entfernt das Menu
            starter.remove(this);
            starter.startGame(true);
        }
        if (mouseX >= statsX && mouseX <= statsX + statsWidth
                && mouseY >= statsY && mouseY <= statsY + statsHeight) {
            starter.remove(this);
            starter.showTable();
        }
        if (mouseX >= settingsButtonX && mouseX <= settingsButtonX + settingsButtonWidth
                && mouseY >= settingsButtonY && mouseY <= settingsButtonY + settingsButtonHeight){
            starter.settings();
        }
        if (mouseX >= exitButtonX && mouseX <= exitButtonX + exitButtonWidth
                && mouseY >= exitButtonY && mouseY <= exitButtonY + exitButtonHeight){
            if (JOptionPane.showConfirmDialog(null, "Willst du das Spiel wirklich beenden?", "WARNUNG",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                System.exit(0);}

        }
    }

    //Die restlichen Overrides werden nicht verwendet, müssen aber implementiert werden
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
