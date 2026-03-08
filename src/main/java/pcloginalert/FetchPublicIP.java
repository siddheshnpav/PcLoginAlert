package pcloginalert;

public class FetchPublicIP {

	public static String getPublicIP() {

		String publicIP = null;
		String ipCheckUrl = Utils.getCredentials("ipcheckurl");

		if (ipCheckUrl == null || ipCheckUrl.isEmpty()) {
			Utils.writeToLog("IP Check URL is not configured properly..!");
			Utils.UpdatePCLoginAlertStatusINI("LastRunStatus", "IP Check URL is not configured properly");
			return "ipcheckurlnotconfigured";
		}

		try (java.util.Scanner s = new java.util.Scanner(
				java.net.URI.create(ipCheckUrl).toURL().openStream(), "UTF-8")
				.useDelimiter("\\A")) {
			if (s.hasNext()) {
				publicIP = s.next().trim();
			}

		} catch (java.io.IOException e) {
			Utils.writeToLog("Error while checking public IP");
			return null;
		}

		if (publicIP != null && publicIP.chars().filter(ch -> ch == '.').count() == 3) {
			Utils.writeToLog("Public Ip Found from Web : " + publicIP);
			Utils.UpdatePCLoginAlertStatusINI("LastPublicIP", publicIP);
			return publicIP;
		} else {
			Utils.writeToLog("Invalid Public IP format received: " + publicIP);
			return "#InvalidIP#";
		}

	}

}
