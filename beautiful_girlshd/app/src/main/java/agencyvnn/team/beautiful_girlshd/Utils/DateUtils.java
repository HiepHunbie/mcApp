package agencyvnn.team.beautiful_girlshd.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Tomorow on 11/2/2015.
 */
public class DateUtils {
    public static Date getToday() {
        return new Date();
    }

    public static String getTommorrowText() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return dateFormat.format(cal.getTime());
    }

    public static String getTodayText() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    public static String convertDateFormat(String text){
        if(text != null && !text .isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date testDate = null;
            try {
                testDate = sdf.parse(text);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            String newFormat = formatter.format(testDate);
            return newFormat;
        }else {
            return "";
        }
    }

//    public static String convertDateFormat(String time) {
//        String inputPattern = "yyyy-MM-dd";
//        String outputPattern = "dd-MM-yyyy";
//        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
//        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
//
//        Date date = null;
//        String str = null;
//
//        try {
//            date = inputFormat.parse(time);
//            str = outputFormat.format(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }

    public static boolean isFuture(String year, String month, String dayOfMonth){
        Calendar c = Calendar.getInstance();

// set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

// and get that as a Date
        Date today = c.getTime();

// or as a timestamp in milliseconds
        long todayInMillis = c.getTimeInMillis();

// user-specified date which you are testing
// let's say the components come from a form or something

// reuse the calendar to set user specified date
        c.set(Calendar.YEAR, Integer.parseInt(year));
        c.set(Calendar.MONTH, Integer.parseInt(month));
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayOfMonth));

// and get that as a Date
        Date dateSpecified = c.getTime();

// test your condition
        if (dateSpecified.before(today)) {
            return false;
        } else {
            return true;
        }
    }
}
