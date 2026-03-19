package pcloginalert;

import java.util.Properties;

public class FetchPublicIP {

	static Properties credentials = Utils.getCredentials();

	public static String getPublicIP() {

		String ipCheckUrl = credentials.getProperty("ipcheckurl", "https://ipinfo.io/ip");

		if (ipCheckUrl == null || ipCheckUrl.isEmpty()) {
			Utils.writeToLog("IP Check URL is not configured properly..!");
			Utils.UpdatePCLoginAlertStatusINI("LastRunStatus", "IP Check URL is not configured properly");
			return "ipcheckurlnotconfigured";
		}

		int retryCount = Integer.parseInt(credentials.getProperty("retryCount", "10"));
		int retryDelay = Integer.parseInt(credentials.getProperty("retryDelay", "3000"));

		for (int attempt = 1; attempt <= retryCount; attempt++) {

			String publicIP = null;

			try (java.util.Scanner s = new java.util.Scanner(
					java.net.URI.create(ipCheckUrl).toURL().openStream(), "UTF-8")
					.useDelimiter("\\A")) {

				if (s.hasNext()) {
					publicIP = s.next().trim();
				}

			} catch (java.io.IOException e) {

				Utils.writeToLog("Attempt " + attempt + " : Error while checking public IP");

				if (attempt < retryCount) {
					try {
						Thread.sleep(retryDelay);
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
						Utils.writeToLog("Retry sleep interrupted.");
						return null;
					}
				}

				continue;
			}

			// Validate IP format
			if (publicIP != null && publicIP.chars().filter(ch -> ch == '.').count() == 3) {
				Utils.writeToLog("Public Ip Found from Web : " + publicIP);
				return publicIP;
			} else {
				Utils.writeToLog("Attempt " + attempt + " : Invalid Public IP format received: " + publicIP);
			}

			if (attempt < retryCount) {
				try {
					Thread.sleep(retryDelay);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
					Utils.writeToLog("Retry sleep interrupted.");
					return null;
				}
			}
		}

		Utils.writeToLog("Failed to fetch valid Public IP after retries.");
		return null;
	}
}