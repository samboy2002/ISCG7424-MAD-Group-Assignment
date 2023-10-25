package unitec.iscg7424.groupassignment.utlities;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import unitec.iscg7424.groupassignment.models.User;

public class Constants {
    public static User loginUser;
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat simpleDate =  new SimpleDateFormat("d/M/yyyy");
    public static String CurrentDate() {
        return simpleDate.format(new Date());
    }
}
