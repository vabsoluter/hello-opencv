/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author vabsoluter
 */
public class MathUtils {

    public static double getRandomOnInterval(long a, long b) {
        double u = Math.random();
        return u * (b - a) + a;
    }
}
