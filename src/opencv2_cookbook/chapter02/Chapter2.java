/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv2_cookbook.chapter02;

import com.googlecode.javacpp.Pointer;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import com.googlecode.javacv.cpp.opencv_core.CvMat;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import java.util.List;
import utils.ImageUtils;
import utils.MathUtils;

/**
 *
 * @author vabsoluter
 */
public class Chapter2 {

    /**
     * @param image Воз
     *
     */
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

    public static IplImage reduceColor(IplImage image) {
        CvMat cvMatImage = image.asCvMat();
        long nbElements = cvMatImage.rows() * cvMatImage.cols() * image.nChannels();
        for (int i = 0; i < nbElements; i++) {
            // Get value as integer
            double v = cvMatImage.get(i);
            double newV = v / v * v + v / 2;
            cvMatImage.put(i, newV);
        }
        return cvMatImage.asIplImage();
    }

    public static IplImage lineFilter(IplImage image) {
        IplImage dest = cvCreateImage(cvSize(image.width(), image.height()), image.depth(), 3);
        // Construct sharpening kernel, all unassigned values are 0
        CvMat kernel = CvMat.create(3, 3, CV_32FC1);
        double rate = (double) 1 / (double) 16;
        kernel.put(0, 0, -1);
        kernel.put(0, 1, -1);
        kernel.put(0, 2, -1);
        kernel.put(1, 0, -1);
        kernel.put(1, 1, 9);
        kernel.put(1, 2, -1);
        kernel.put(2, 0, -1);
        kernel.put(2, 1, -1);
        kernel.put(2, 2, -1);
        System.out.println(kernel.get(0, 0));
        // Filter the image
        filter2D(image, dest, -1, kernel, new CvPoint(-1, -1), 0, BORDER_DEFAULT);
        return dest;
    }

    public static IplImage medianFilter(IplImage image) {
        CvMat mat = image.asCvMat();
        CvMat resultMat = mat.clone();
        for (int x = 0; x < mat.rows(); x++) {
            for (int y = 0; y < mat.cols(); y++) {
                //System.out.println("current time: " + System.currentTimeMillis());
                ImageUtils.Pixel pixel = ImageUtils.getMedian(mat, x, y, 3);
                resultMat.put(x, y, 0, pixel.getB());
                resultMat.put(x, y, 1, pixel.getG());
                resultMat.put(x, y, 2, pixel.getR());
            }
        }
        return resultMat.asIplImage();
    }
}
