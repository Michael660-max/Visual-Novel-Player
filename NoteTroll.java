package Trolls;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Class NoteTroll.
 * Course code tailored by the CSC207 instructional
 * team at UTM, with special thanks to:
 *
 * @author anshag01
 * @author mustafassami
 * @author guninkakr03
 *  */
public class NoteTroll implements Troll {

    int currTone; //current tone
    Scanner scanner; //use to read input from user
    double [] hzs = new double[]{261.63, 329.63, 392}; //C note, E or G?
    String [] answers = new String[]{"C", "E", "G"}; //Note options
    String answer; //what note?


    /* Function: NoteTroll Constructor
     * ----------------------
     * Make a NoteTroll
     */
    public NoteTroll() {
        Random r = new Random();
        this.scanner = new Scanner(System.in);
        this.currTone = r.nextInt(3);
        this.answer = answers[currTone];
    }

    /* Function: playTone
     * ----------------------
     * Play an individual tone at a given frequency
     *
     * @param hz the input tone
     */
    public void playTone() {
        try{
            generateTone(hzs[currTone],
                    1000,
                    (int)(25*1.28),
                    true);
        }catch(LineUnavailableException lue){
            System.out.println(lue);
        }
    }

    /** Generates a tone.
     * From https://forums.oracle.com/ords/apexds/post/example-code-to-generate-audio-tone-8529
     @param hz Base frequency (neglecting harmonic) of the tone in cycles per second
     @param msecs The number of milliseconds to play the tone.
     @param volume Volume, form 0 (mute) to 100 (max).
     @param addHarmonic Whether to add a harmonic one octave up. */
    public static void generateTone(double hz, int msecs, int volume, boolean addHarmonic) throws LineUnavailableException {

        float frequency = 44100;
        byte[] buf;
        AudioFormat af;
        if (addHarmonic) {
            buf = new byte[2];
            af = new AudioFormat(frequency,8,2,true,false);
        } else {
            buf = new byte[1];
            af = new AudioFormat(frequency,8,1,true,false);
        }
        SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
        sdl = AudioSystem.getSourceDataLine(af);
        sdl.open(af);
        sdl.start();
        for(int i=0; i<msecs*frequency/1000; i++){
            double angle = i/(frequency/hz)*2.0*Math.PI;
            buf[0]=(byte)(Math.sin(angle)*volume);

            if(addHarmonic) {
                double angle2 = (i)/(frequency/hz)*2.0*Math.PI;
                buf[1]=(byte)(Math.sin(2*angle2)*volume*0.6);
                sdl.write(buf,0,2);
            } else {
                sdl.write(buf,0,1);
            }
        }
        sdl.drain();
        sdl.stop();
        sdl.close();
    }

    /**
     * Print NoteTroll instructions for the user
     */
    public void giveInstructions()
    {
        System.out.println("I am a PERFECT PITCH TROLL. You must beat me at my game to pass.");
        System.out.println("Listen to the following tone and tell me:");
        System.out.println("IS the note you are hearing a C, a E or G?");
    }

    /**
     * Play the NoteTroll game
     *
     * @return true if player wins the game, else false
     */
    public boolean playGame() {

        giveInstructions();
        playTone();

        String tone = scanner.next();
        if (tone.equals(this.answer)) {
            System.out.println("Curses, you have have good pitch. You MAY PASS.\n");
            return true; }
        else {
            System.out.println("WRONG!");
            System.out.println("The correct tone was a: " + this.answer);
            System.out.println("Better luck next time ... this time you MAY NOT PASS!!\n");
            return false;
        }
    }

    /**
     * Main method, use for debugging
     *
     * @param args: Input arguments
     */
    public static void main(String [] args) throws InterruptedException {
        NoteTroll s = new NoteTroll();
        boolean a = s.playGame();
    }
}

