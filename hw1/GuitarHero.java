/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
import es.datastructur.synthesizer.GuitarString;
import java.lang.Math;

public class GuitarHero {
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    private static final double CONCERT_BASE = 440.0;

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        GuitarString concert[] = new GuitarString[keyboard.length()];
        for (int i = 0; i < concert.length; i++) {
            concert[i] = new GuitarString(CONCERT_BASE * Math.pow(2, (i - 24) / 12));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                int index = keyboard.indexOf(key);
                if (index != -1) {
                    concert[keyboard.indexOf(key)].pluck();
                }
            }

        /* compute the superposition of samples */
            double sample = 0;
            for (int i = 0; i < concert.length; i++) {
                sample += concert[i].sample();
            }

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
            for (int i = 0; i < concert.length; i++) {
                concert[i].tic();
            }
        }
    }
}

