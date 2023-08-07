package emds.example.com.util;

import java.text.DecimalFormat;

public class NumberFormat {

    public static String formatVueFloat(float nombre) {
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String formattedNumber = (nombre % 1 == 0) ? "0" : decimalFormat.format(nombre);
        return formattedNumber;
    }
}
