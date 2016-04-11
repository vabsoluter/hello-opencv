package utils;
/* 
 * I developed some code for recognize motion detections with JavaCV.
 * Actually, it works with an array of Rect, performing, every cicle, an
 * intersection test with area of difference with the rect of interests
 * (this array is callad "sa", stands for SizedArea). I hope could this
 * helps someone.
 * 
 * Feel free to ask about any question regarding the code above, cheers!
 *
 * Angelo Marchesin <marchesin.angelo@gmail.com>
 */

import com.googlecode.javacpp.Loader;
import com.googlecode.javacv.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_calib3d.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;

public class MotionDetector {
    public static void main(String[] args) throws Exception {
        FFmpegFrameGrabber grabber = new  FFmpegFrameGrabber("/home/vabsoluter/D/01.MOV");
        grabber.start();

        IplImage frame = grabber.grab();
        IplImage image = null;
        IplImage prevImage = null;
        IplImage diff = null;

        CanvasFrame canvasFrame = new CanvasFrame("Some Title");
        canvasFrame.setCanvasSize(frame.width(), frame.height());


        while (canvasFrame.isVisible() && (frame = grabber.grab()) != null) {




                canvasFrame.showImage(frame);

        }
        grabber.stop();
        canvasFrame.dispose();
    }
}
