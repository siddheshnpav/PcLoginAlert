package pcloginalert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class getLocalDateTime {

	private static String getLocalDate() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

	private static String getLocalTime() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh-mm-ss a", Locale.ENGLISH);
        return LocalDateTime.now().format(formatter);
	}

	public static String dateTime() {

		String dateTime = getLocalDate() + "_" + getLocalTime();

		return dateTime;

	}
	
	public static String date() {
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd");
		LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
	}

}
