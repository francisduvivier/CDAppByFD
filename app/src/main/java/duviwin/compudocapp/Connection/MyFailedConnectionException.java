package duviwin.compudocapp.Connection;

import android.app.Activity;
import android.app.AlertDialog;

import duviwin.compudocapp.R;

/**
 * Created by Duviwin on 7/29/2015.
 */
public class MyFailedConnectionException extends Exception {
    private static long latestAlertTime=0;
    public static void showException(Activity activity) {
        if(System.currentTimeMillis()-latestAlertTime> 2*1000){

            new AlertDialog.Builder(activity)
                .setTitle("There seems to be no connectivity!")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.ok,null)
                .show();
            latestAlertTime=System.currentTimeMillis();}
    }

    }
