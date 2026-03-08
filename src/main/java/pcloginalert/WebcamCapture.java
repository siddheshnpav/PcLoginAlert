package pcloginalert;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import java.io.File;

public class WebcamCapture {

    public static String captureAndSaveImage() {

        String CaptureDirectoryPath = Utils.getCaptureDirectoryPath();

        String Fillename = String.format("%s\\%s.png", CaptureDirectoryPath, getLocalDateTime.dateTime());

        Webcam webcam = Webcam.getDefault();
        webcam.setViewSize(new Dimension(640, 480));

        try {
            // Open the webcam
            webcam.open();
            // Capture image
            ImageIO.write(webcam.getImage(), "PNG", new File(Fillename));
            Utils.writeToLog("Webcam image saved successfully at: " + Fillename);
            return Fillename;
        } catch (Exception e) {
            Utils.writeToLog("Error capturing webcam image: " + e.getMessage());
            return null;
        } finally {
            // Close the webcam
            if (webcam.isOpen()) {
                webcam.close();
            }
        }
    }
}