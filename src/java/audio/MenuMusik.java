package src.java.audio;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Diese Klasse verwaltet die Menümusik.
 */
public class MenuMusik {
    static boolean running = true;
    static Clip clip;
    //Für das ursprüngliche Starten der Musik

    /**
     * Diese Methode spielt den übergebenen Track ab.
     * @param track
     * @param setVolume
     */
    public static synchronized void music(String track, float setVolume) {

        //Erstellt einen neuen Thread und überschreibt seine run()-Methode
        new Thread(new Runnable() {
            @Override
            public void run() {
                float volume = setVolume;
                while (running) { //Infinite loop; spielt die übergebene .wav-Datei so lange ab, wie Musik.running = true ist
                    try {
                        clip = AudioSystem.getClip();
                        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(track));
                        clip.open(inputStream);

                        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                        gainControl.setValue(volume); //Verringert die Lautstärke der Musik

                        clip.loop(clip.LOOP_CONTINUOUSLY); //Spielt die audio-Datei immer weiter ab
                        Thread.sleep(clip.getMicrosecondLength() / 1000); //Lässt den Thread für die Zeit, in der die audio-Datei abgespielt wird warten
                        clip.stop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start(); //Ruft die run()-Methode des Threads auf
    }

    /**
     * Stoppt den aktuellen clip und sorgt dafür, dass die while(running) Schleife nicht mehr aufgerufen wird.
     * Somit wird die Hintergrundmusik des Spiels unterbrochen.
     */
    public static void musicStop() {
        running = false;
        clip.stop();
    }

    /**
     * Startet den aktuellen clip und sorgt dafür, dass die while(running) Schleife aufgerufen wird.
     * Somit wird die Hintergrundmusik des Spiels abgespielt.
     * @param setVolume
     */
    public static void musicStart(float setVolume) {
        running = true;
        music("audioTracks/MenuMusic.wav", setVolume);
    }

}
