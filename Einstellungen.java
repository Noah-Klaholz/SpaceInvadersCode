import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Diese Klasse verwaltet das Einstellungsmenü.
 */
public class Einstellungen extends JDialog {
    private JSlider menuMusicSlider;
    private JSlider gameMusicSlider;
    private JSlider soundSlider;
    private JButton btnCancel;
    private JPanel settingsPanel;
    private JButton btnOK;
    private JCheckBox tutorialCheckBox;
    float setMenuVolume;
    float setBackgroundVolume;
    float setSoundVolume;

    /**
     * Hier wird der JFrame erzeugt und die actionListener werden hinzugefügt.
     *
     * @param parent
     * @param starter
     * @param menuVolume
     * @param backgroundVolume
     * @param soundVolume
     */
    public Einstellungen(JFrame parent, Starter starter, float menuVolume, float backgroundVolume, float soundVolume) {
        super(parent);
        setTitle("Settings");      //legt alle Angaben für das Fenster fest
        setContentPane(settingsPanel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        menuMusicSlider.setValue((int) menuVolume);
        gameMusicSlider.setValue((int) backgroundVolume);
        soundSlider.setValue((int) soundVolume);
        if (starter.tutorials == true) tutorialCheckBox.setSelected(true);
        else tutorialCheckBox.setSelected(false);


        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {// Diese Methode stellt die Lautstärke über die Regler ein
                int menuValue = menuMusicSlider.getValue();
                if (menuValue == -35) setMenuVolume = -80f;
                else setMenuVolume = (float) (menuValue);

                int gameValue = gameMusicSlider.getValue();
                if (gameValue == -40) setBackgroundVolume = -80f;
                else setBackgroundVolume = (float) (gameValue);

                int soundValue = soundSlider.getValue();
                if (soundValue == -26) setSoundVolume = -70f;
                else setSoundVolume = (float) (soundValue);

                dispose();
                MenuMusik.musicStop();
                MenuMusik.musicStart(setMenuVolume);
                starter.getVolumes(setBackgroundVolume, setMenuVolume, setSoundVolume);
            }
        });
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        tutorialCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (tutorialCheckBox.isSelected()) {
                    starter.tutorials = true;
                } else {
                    starter.tutorials = false;

                }
            }
        });

        setVisible(true);
    }
}
