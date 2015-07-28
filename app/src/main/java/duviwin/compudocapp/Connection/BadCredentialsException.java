package duviwin.compudocapp.Connection;

import android.app.AlertDialog;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Duviwin on 7/27/2015.
 */
public class BadCredentialsException extends Exception{
    private static long latestAlertTime=0;

    public static void showException(FragmentActivity activity) {
       if(System.currentTimeMillis()-latestAlertTime> 10*1000){        new AlertDialog.Builder(activity)
                .setTitle("Username of wachtwoord fout, pas aan in instellingen AUB!")
                .setIcon(android.R.drawable.ic_dialog_alert).setNeutralButton("OK", null)
                .show();
//           Intent showSettings=new Intent(activity,SettingsActivity.class);
//           startActivityForResult(showSettings,REQ_CODE_SETTINGS_MAIN);
           latestAlertTime=System.currentTimeMillis();}
    }
}
