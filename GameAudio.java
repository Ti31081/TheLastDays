

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class GameAudio {
    // speichern die Pfade zu den Audiodateien.
    private static final String HINTERGRUND_AUDIO = "src/rsc/audio/Hintergrund.mp3";
    private static final String START_AUDIO = "src/rsc/audio/start.mp3";
    private static final String LAUFEN_AUDIO = "src/rsc/audio/laufen.wav";
    private static final String SPRINGEN_AUDIO = "src/rsc/audio/springen.mp3";

    private static MediaPlayer laufenMediaPlayer;
    private static MediaPlayer springenMediaPlayer;
    private static MediaPlayer mediaPlayer; //für hintergrund oder startmusik

    public static MediaPlayer playAudio(String path) {
        try {
            Media media = new Media(new File(path).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            return mediaPlayer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void playStartAudio() {
        MediaPlayer mediaPlayer = playAudio(START_AUDIO); //Spielt "rsc/audio/effect.mp3" am anfang ab
        if (mediaPlayer != null)  {      
            mediaPlayer.setVolume(0.5);
      // wenn start audio startet, fängt das hintergrundmusik an
            mediaPlayer.setOnEndOfMedia(GameAudio::playHintergrundAudio);
       }
    }

    public static void playlaufenAudio() {
        if (laufenMediaPlayer != null && laufenMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            return; // Falls das Laufgeräusch bereits abgespielt wird, passiert nichts.
        }
        if (laufenMediaPlayer == null) {
            Media media = new Media(new File(LAUFEN_AUDIO).toURI().toString()); //Falls laufenMediaPlayer noch nicht existiert, wird er erstellt
            laufenMediaPlayer = new MediaPlayer(media);
        }

        laufenMediaPlayer.setOnEndOfMedia(() -> {
            laufenMediaPlayer.seek(javafx.util.Duration.ZERO); //Setzt die Wiedergabezeit auf den Anfang der Audiodatei (0 Sekunden)
            // Neustart des Audios
            laufenMediaPlayer.play();
        });

        laufenMediaPlayer.play();
    }

    public static void stoplaufenAudio() {
        if (laufenMediaPlayer != null) {
            laufenMediaPlayer.stop();
       //Das Objekt wird auf null gesetzt
            laufenMediaPlayer = null; 
        }
    }

    public static void playspringenAudio() {
        if (springenMediaPlayer == null) {
            Media media = new Media(new File(SPRINGEN_AUDIO).toURI().toString());
            springenMediaPlayer = new MediaPlayer(media);
        }

        // Startet von neu aus 
        springenMediaPlayer.seek(javafx.util.Duration.ZERO);
        springenMediaPlayer.play();
    }

    public static void playHintergrundAudio() {
        new Thread(() -> {
            try {
                Media media = new Media(new File(HINTERGRUND_AUDIO).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
//      der loop wird hier erzeugt  
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            // startet die Wiedergabe automatisch. 
                mediaPlayer.setAutoPlay(true);
                mediaPlayer.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
                  mediaPlayer.setVolume(0.2);

        }).start();
    }
}
