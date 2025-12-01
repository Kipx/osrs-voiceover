package com.osrsvoiceover;

import lombok.Getter;

import javax.sound.sampled.*;
import java.io.*;

@Getter
public class AudioHandler implements LineListener {
    private final String path;
    public AudioHandler(String path) {
        this.path = path;
    }

    boolean isPlaybackCompleted;

    public void playAudioFile(Float volume) throws LineUnavailableException, IOException, UnsupportedAudioFileException {

        AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(path + ".wav"));
        Clip audioClip = AudioSystem.getClip();
        audioClip.addLineListener(this);
        audioClip.open(audioStream);
        FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(volume);
        System.out.println("Current level: " + audioClip.getLevel());
        audioClip.start();
    }

    @Override
    public void update(LineEvent event) {
        if (LineEvent.Type.START == event.getType()) {
            System.out.println("Playback started.");
        } else if (LineEvent.Type.STOP == event.getType()) {
            isPlaybackCompleted = true;
            System.out.println("Playback completed.");
        }
    }
}
