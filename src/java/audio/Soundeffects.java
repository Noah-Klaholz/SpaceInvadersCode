package src.java.audio;

import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Diese Klasse verwaltet die src.java.audio.Soundeffects des Spiels.
 */
public class Soundeffects {

    /**
     * Diese Methode spielt den übergebenen Track ab.
     * @param track
     * @param setSoundVolume
     */
    public static synchronized void music(String track, float setSoundVolume) {

        final String trackname = track;

        //Erstellt einen neuen Thread und überschreibt seine run()-Methode
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(trackname));
                    clip.open(inputStream);
                    clip.start();

                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    if(trackname != "audioTracks/Shot.wav"){ //ShotSound soll nicht leiser sein
                        gainControl.setValue(setSoundVolume - 10.0f); //Verringert die Lautstärke der Soundeffekte
                    }
                    else gainControl.setValue(setSoundVolume);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start(); //Ruft die run()-Methode des Threads auf
    }

    /**
     * Die gameOver() Methode spielt zuerst den Explosions Soundeffect und danach den gameOver Soundeffect ab.
     * @param explosionTrack
     * @param gameOverTrack
     * @param setSoundVolume
     */
    public static synchronized void gameOver(String explosionTrack, String gameOverTrack, float setSoundVolume) {

        //Erstellt einen neuen Thread und überschreibt seine run()-Methode
        new Thread(new Runnable() {
            @Override
            public void run() {


                try {

                    //Spielt den Explosionssoundeffekt ab
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(explosionTrack));
                    clip.open(inputStream);
                    clip.start();

                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(setSoundVolume);

                    Thread.sleep(clip.getMicrosecondLength() / 3000); //Lässt den Thread für die Zeit, in der die audio-Datei abgespielt wird warten

                    //Spielt den zufälligen Game-Over-Soundeffekt ab
                    Clip clip2 = AudioSystem.getClip();
                    AudioInputStream inputStream2 = AudioSystem.getAudioInputStream(new File(gameOverTrack));
                    clip2.open(inputStream2);
                    clip2.start();

                    FloatControl gainControl2 = (FloatControl) clip2.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl2.setValue(setSoundVolume);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start(); //Ruft die run()-Methode des Threads auf
    }
}