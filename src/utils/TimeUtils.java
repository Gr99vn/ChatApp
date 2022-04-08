package utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimeUtils {
    public static String convertDateTime(Timestamp timestamp) {
        String pattern = "HH:mm:ss dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String datetime = simpleDateFormat.format(new Date(timestamp.getTime()));
        return datetime;
    }
}
