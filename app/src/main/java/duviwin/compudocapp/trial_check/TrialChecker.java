package duviwin.compudocapp.trial_check;

import android.app.Activity;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by Duviwin on 7/25/2015.
 */
public class TrialChecker {
    public static void checkTrial(long currMillis, Activity act) {
       Calendar calExpiration = new GregorianCalendar(TimeZone.getTimeZone("Europe/Brussels"));
        //date[1]-1 because January is 0 and not 1

        calExpiration.set(2015,11,1); //1 december 2015
        if(currMillis>calExpiration.getTimeInMillis()){
            stopApp(act);
        }
    }

    private static void stopApp(Activity act) {
        act.finish();
    }
}
