package org.cabro.opencv;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;

public class Test {
    public static void main(String[] args) {

        // Don't count on LD_LIBRARY_PATH
        File openCvLib = new File("/opt/local/share/OpenCV/java/libopencv_java331.dylib");
        if (!openCvLib.exists())
            throw new RuntimeException("Unable to locate OpenCV lib");

        System.load(openCvLib.getAbsolutePath());
        System.out.println("Finding faces...");
        CascadeClassifier faceDetector = new CascadeClassifier();
        faceDetector.load("./src/main/java/org/cabro/opencv/haarcascade_frontalface_alt.xml");

        // Input image
        Mat image = Imgcodecs.imread("/tmp/target.png");

        // Detecting faces
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        // Creating a rectangular box showing faces detected
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0));
        }

        // Saving the output image
        String filename = "/tmp/Output.jpg";
        Imgcodecs.imwrite(filename, image);
    }

}
