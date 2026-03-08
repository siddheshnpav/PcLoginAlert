package pcloginalert;

import java.io.File;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class TelegramAlert {

    public static void sendTelegramAlert(String FilePath) {
        try {
            String botToken = CryptoUtil.EncyptDecryptString(Utils.getCredentials("botToken"));
            String chatId = CryptoUtil.EncyptDecryptString(Utils.getCredentials("chatId"));

            String CaptionText = TelegramAlertTemplate();

            File photo = new File(FilePath);

            Response response = given()
                    .multiPart("photo", photo)
                    .formParam("chat_id", chatId)
                    .formParam("caption", CaptionText)
                     .formParam("parse_mode", "HTML")
                    .formParam("protect_content", true)
                    .post("https://api.telegram.org/bot" + botToken + "/sendPhoto");

            String isSuccess = response.jsonPath().getString("ok");

            int statusCode = response.getStatusCode();

            if(statusCode != 200) {
                Utils.writeToLog("Telegram API responded with status code: " + statusCode);
                Utils.UpdatePCLoginAlertStatusINI("LastAlertFailed", getLocalDateTime.dateTime());
                return;
            }

            if ("true".equals(isSuccess)) {
                Utils.writeToLog("Telegram alert sent successfully.");
                Utils.UpdatePCLoginAlertStatusINI("LastAlertSent", getLocalDateTime.dateTime());
            } else {
                Utils.writeToLog("Failed to send Telegram alert.");
                Utils.UpdatePCLoginAlertStatusINI("LastAlertFailed", getLocalDateTime.dateTime());
            }

        } catch (Exception e) {
            Utils.writeToLog("Error sending Telegram alert");
            Utils.writeToLog(e.getMessage());
            Utils.UpdatePCLoginAlertStatusINI("LastAlertFailed", getLocalDateTime.dateTime());
        }
    }

    	public static String TelegramAlertTemplate() {

        String publicIP = FetchPublicIP.getPublicIP();
		String machine = SystemInfo.getMachineName();
		String user = SystemInfo.getUsername();
		String osName = SystemInfo.getOsName();
		String timestamp = getLocalDateTime.dateTime();

		return String.format(
				"<b>PC Login Alert</b>%n%n" +
                "<b>Public IP</b>: <code>%s</code>%n" +
						"<b>PC Name</b>: %s%n" +
						"<b>User</b>: %s%n" +
						"<b>OS</b>: %s%n" +
						"<b>Timestamp</b>: %s",
				publicIP,
				machine,
				user,
				osName,
				timestamp

		);
	}
}
