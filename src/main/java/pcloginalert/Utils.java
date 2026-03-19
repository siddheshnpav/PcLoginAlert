package pcloginalert;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Utils {

    private static String DirectoryPath = System.getenv("ProgramData") + "\\PCLoginAlert";
    private static String logDirectoryPath = System.getenv("ProgramData") + "\\PCLoginAlert\\Logs";
    private static String PCLoginAlertConfigINIFilePath = "C:\\Sidh\\PCLoginAlert\\PCLoginAlertConfig.ini";
    private static String PCLoginAlertStatusFilePath = DirectoryPath + "\\PCLoginAlertStatus.ini";
    private static String CaptureDirectoryPath = DirectoryPath + "\\WebcamCaptures";

    public static String getCaptureDirectoryPath() {
        return CaptureDirectoryPath;
    }

    public static String getDirectoryPath() {
        return DirectoryPath;
    }

    public static String getLogDirectoryPath() {
        return logDirectoryPath;
    }

    public static String getPCLoginAlertConfigINIFilePath() {
        return PCLoginAlertConfigINIFilePath;
    }

    public static String getPCLoginAlertStatusFilePath() {
        return PCLoginAlertStatusFilePath;
    }

    private static void CreateDirectoryIfNotExists(String directoryPath) {
        Path path = Paths.get(directoryPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.out.println("An error occurred while creating directory: " + directoryPath);
                e.printStackTrace();
            }
        }
    }

    public static void writeToLog(String a) {
        CreateDirectoryIfNotExists(logDirectoryPath);
        CreateDirectoryIfNotExists(CaptureDirectoryPath);

        String logpath = String.format("%s\\%s.log", logDirectoryPath, getLocalDateTime.date());

        try {
            FileWriter myWriter = new FileWriter(logpath, true);
            BufferedWriter br = new BufferedWriter(myWriter);
            if (a.startsWith("Starting PC Login Alert") || a.startsWith("PC Login Alert is already running")) {
                br.write("\n" + getLocalDateTime.dateTime() + " 		" + a + "\n");
            } else {
                br.write(getLocalDateTime.dateTime() + " 		" + a + "\n");
            }
            br.close();
            myWriter.close();
            System.out.println(getLocalDateTime.dateTime() + " 		" + a + "\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    public static synchronized void UpdatePCLoginAlertStatusINI(String key, String value) {

        Properties prop = new Properties();
        File file = new File(PCLoginAlertStatusFilePath);

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                prop.load(fis);
            } catch (IOException e) {
                System.out.println("Error loading PCLoginAlertStatus.ini");
                e.printStackTrace();
                return;
            }
        }

        prop.setProperty(key, value);

        try (FileOutputStream fos = new FileOutputStream(file)) {
            prop.store(fos, "PC Login Alert Status");
        } catch (IOException e) {
            System.out.println("Error writing to PCLoginAlertStatus.ini");
            e.printStackTrace();
        }
    }

    	public static String getPcAlertStatusINI(String key) {

		Properties prop = new Properties();
		FileInputStream fis = null;

		File file = new File(PCLoginAlertStatusFilePath);

		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				prop.load(fis);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return prop.getProperty(key);

		} else {

			Utils.writeToLog("PCLoginAlertStatus.ini File not found. " + PCLoginAlertStatusFilePath);
			return null;
		}
	}

    public static Properties getCredentials() {

		Properties prop = new Properties();
		FileInputStream fis = null;

		File file = new File(PCLoginAlertConfigINIFilePath);

		if (file.exists()) {
			try {
				fis = new FileInputStream(file);
				prop.load(fis);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return prop;

		} else {

			Utils.writeToLog("PCLoginAlertConfig.ini File not found. " + PCLoginAlertConfigINIFilePath);
			return null;

		}
	}

}
