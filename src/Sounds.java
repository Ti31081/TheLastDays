import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Sounds {
    private static Clip walkClip;
    private static Clip jumpClip;
    private static Clip landClip;
    
    // Initialisiere die Sound-Dateien
    static {
        try {
            // Lade die Sound-Dateien aus dem rsc Ordner
            walkClip = loadSound("rsc/walking.wav");
            jumpClip = loadSound("rsc/jumping.wav");
            landClip = loadSound("rsc/walking.wav");
        } catch (Exception e) {
            System.err.println("Fehler beim Laden der Sound-Dateien: " + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////-,
    // Hilfsmethode zum Laden der Sound-Dateien
    private static Clip loadSound(String path) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }
    
    // Methode für Gehgeräusch
    public static void playWalkSound() {
        playSound(walkClip);
    }
    
    // Methode für Sprunggeräusch
    public static void playJumpSound() {
        playSound(jumpClip);
    }
    
    // Methode für Landegeräusch
    public static void playLandSound() {
        playSound(landClip);
    }
    
    // Hilfsmethode zum Abspielen der Sounds
    private static void playSound(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0);
            clip.start();
        }
    }
    
    // Methode zum Stoppen aller Sounds
    public static void stopAllSounds() {
        if (walkClip != null) walkClip.stop();
        if (jumpClip != null) jumpClip.stop();
        if (landClip != null) landClip.stop();
    }
}
