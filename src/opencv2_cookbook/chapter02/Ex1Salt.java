/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv2_cookbook.chapter02;

import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import utils.MathUtils;

/**
 *
 * @author vabsoluter
 */
public class Ex1Salt {

    public static IplImage noize(IplImage image, int numOfPoints) {
        CvMat cvMat = image.asCvMat();
        for (int i = 0; i < numOfPoints; i++) {
            int x = (int) MathUtils.getRandomOnInterval(0, cvMat.rows());
            int y = (int) MathUtils.getRandomOnInterval(0, cvMat.cols());
            cvMat.put(x, y, 0, 255);
            cvMat.put(x, y, 1, 255);
            cvMat.put(x, y, 2, 255);
        }

        return cvMat.asIplImage();
    }
}
