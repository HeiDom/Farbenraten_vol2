package de.dome.farbenerraten.Util;

import java.util.Random;

/**
 * Created by Dominik on 04.09.2017.
 */

public class RandomUtil {

    private final static Random random = new Random();

    /**
     * Gibt eine zufällige Zahl zurück
     * @return gibt ein Int zurück
     */
    public static int getRandom(){
        return random.nextInt(4);
    }

    /**
     * Generiert eine zufällige Zahl
     * @param max Gibt die höchste Zahl an, die generiert werden kann
     * @return Gibt die generierte Zahl als int zurück
     */
    public static int getRandom(int max){
        return random.nextInt(max);
    }
}
