/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.googlecode.javacpp.Pointer;
import static com.googlecode.javacv.cpp.opencv_core.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 *
 * @author vabsoluter
 */
public class ImageUtils {

    public static enum CHANEL {

        R, G, B
    };

    public static class Pixel {

        private double R;
        private double G;
        private double B;

        public Pixel(double R, double G, double B) {
            this.R = R;
            this.G = G;
            this.B = B;
        }

        public double getB() {
            return B;
        }

        public double getG() {
            return G;
        }

        public double getR() {
            return R;
        }
        
    }

    public static Map<CHANEL, List<Double>> getNeightbours(CvMat image, int x, int y, int size) {
        size = size % 2 == 0 ? size + 1 : size;
        int delta = size / 2;
        Map<CHANEL, List<Double>> neightboursMap = new HashMap<CHANEL, List<Double>>();
        List<Double> neightboursR = new ArrayList<Double>();
        List<Double> neightboursG = new ArrayList<Double>();
        List<Double> neightboursB = new ArrayList<Double>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (isInBounds(x - delta + i, y - delta + j, image.rows(), image.cols())) {
                    try {
                        neightboursB.add(image.get(x - delta + i, y - delta + j, 0));
                        neightboursG.add(image.get(x - delta + i, y - delta + j, 1));
                        neightboursR.add(image.get(x - delta + i, y - delta + j, 2));
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println(ex.getMessage() + " x - delta + i=" + (x - delta + i) + ", y - delta + j=" + (y - delta + j));
                    }
                }
            }
        }
        neightboursMap.put(CHANEL.R, neightboursR);
        neightboursMap.put(CHANEL.G, neightboursG);
        neightboursMap.put(CHANEL.B, neightboursB);
        return neightboursMap;
    }

    private static boolean isInBounds(int i, int j, int rows, int cols) {
        return i >= 0 && i < rows && j >= 0 && j < cols;
    }

    private double getMedian(List<Double> values) {
        Collections.sort(values);
        return values.get(values.size() / 2);
    }

    public static Pixel getMedian(CvMat image, int x, int y, int size) {
        Map<CHANEL, List<Double>> neightboursMap = getNeightbours(image, x, y, size);
        List<Double> neightboursR = neightboursMap.get(CHANEL.R);
        List<Double> neightboursG = neightboursMap.get(CHANEL.G);
        List<Double> neightboursB = neightboursMap.get(CHANEL.B);
        Collections.sort(neightboursR);
        Collections.sort(neightboursG);
        Collections.sort(neightboursB);
        return new Pixel(neightboursR.get(neightboursR.size() / 2), neightboursG.get(neightboursG.size() / 2), neightboursB.get(neightboursB.size() / 2));
    }
}
