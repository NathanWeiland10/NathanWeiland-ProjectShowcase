package com.company;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class PlaySound {

    private static boolean playSounds = true;

    public static void play(URL resource) {

        if (playSounds) {
            // Begin citation:
            // https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator

            AudioInputStream audioInputStream = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(resource);
            } catch (UnsupportedAudioFileException | IOException e) {
                e.printStackTrace();
            }
            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            try {
                assert clip != null;
                clip.open(audioInputStream);
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
            clip.start();

            // End citation
        }

    }

    public static void setPlaySounds(boolean s) {
        playSounds = s;
    }

}
