package pcloginalert;
import java.net.InetAddress;
public class SystemInfo {

    
	public static String getUsername() {
        return System.getProperty("user.name");
    }

    public static String getMachineName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            return "Unknown-Host";
        }
    }

	public static String getOsName() {
        return System.getProperty("os.name");
    }
}
