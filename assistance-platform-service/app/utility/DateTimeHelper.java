package utility;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class DateTimeHelper {
	public static long localDateTimeToTimestamp(LocalDateTime ldt) {
		return ldt.toEpochSecond(ZoneOffset.UTC);
	}
}