package veinthrough.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateFormat {
    private static final TimeZone SHANG_HAI = TimeZone.getTimeZone("Asia/Shanghai");
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS z";

    public static java.text.DateFormat getDateFormat(String pattern, TimeZone timeZone) {
        SimpleDateFormat format = new SimpleDateFormat(pattern );
        format.setTimeZone(timeZone);
        return format;
    }

    public static java.text.DateFormat getDateFormat() {
        return getDateFormat(DEFAULT_DATE_PATTERN, SHANG_HAI);
    }

    public static String formatDate(long date, String pattern, TimeZone timeZone) {
        return getDateFormat(pattern, timeZone).format(new Date(date));
    }

    public static String formatDate(long date) {
        return formatDate(date, DEFAULT_DATE_PATTERN, SHANG_HAI);
    }
}
