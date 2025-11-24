package com.osrsvoiceover;

import javazoom.jl.converter.Converter;
import javazoom.jl.decoder.JavaLayerException;
import lombok.Getter;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Getter
public class AudioHandler implements LineListener {
    private final String path;
    public AudioHandler(String path) {
        this.path = path;
    }

    boolean isPlaybackCompleted;

    public void createAudioFile(InputStream inputStream) throws IOException, JavaLayerException {
        File outputFile =  new File(path + ".mp3");
        if (outputFile.getParentFile().isDirectory() || outputFile.mkdirs()) {
            Converter conv = new Converter();
            Files.copy(inputStream, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
            conv.convert(path + ".mp3", path + ".wav");
        }
        else {
            System.out.println("Unable to create directory for " + path);
        }
    }

    public void playAudioFile(Float volume) throws LineUnavailableException, IOException, UnsupportedAudioFileException, JavaLayerException {

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
