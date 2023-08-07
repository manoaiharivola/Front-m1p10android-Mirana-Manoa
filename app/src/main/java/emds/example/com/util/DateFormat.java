package emds.example.com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Locale;

public class DateFormat {

    public static String formatVueDate(String dateToFormat) {
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        inputDateFormat.setTimeZone(TimeZone.getTimeZone("GMT")); // Assurez-vous que la date d'entrée est interprétée comme étant en GMT

        Date date;
        try {
            date = inputDateFormat.parse(dateToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateToFormat;
        }

        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm", new Locale("fr", "FR"));
        outputDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+03:00")); // Définissez le fuseau horaire souhaité (GMT+3)
        String outputDate = outputDateFormat.format(date);
        return outputDate;
    }
}
