
import pcloginalert.TelegramAlert;
import pcloginalert.Utils;
import pcloginalert.WebcamCapture;

public class CaptureAndSendTelegramAlert {

    public static void main(String[] args) {
        captureAndSendAlert();
    }

    public static void captureAndSendAlert() {

        Utils.writeToLog("Starting PC Login Alert...");

        String capturedImagePath = WebcamCapture.captureAndSaveImage();

        if (capturedImagePath == null) {
            Utils.writeToLog("Failed to capture image. Telegram alert will not be sent.");
            return;
        }
        TelegramAlert.sendTelegramAlert(capturedImagePath);

        Utils.writeToLog("PC Login Alert process completed.");
    }

}
