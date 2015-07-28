package duviwin.compudocapp.Connection;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import duviwin.compudocapp.MainActivity;
import duviwin.compudocapp.SettingsActivity;

/**
 * Created by Duviwin on 7/27/2015.
 */
public class BadCredentialsException extends Exception{
    public static final String NOTIFY_STRING = "Bad Credentials";
    private static long latestAlertTime=0;

    public static void handleException(FragmentActivity activity) {
       if(System.currentTimeMillis()-latestAlertTime> 2*1000){
           Intent showSettings=new Intent(activity,SettingsActivity.class);
           showSettings.putExtra(null,NOTIFY_STRING);
           activity.startActivityForResult(showSettings, MainActivity.REQ_CODE_SETTINGS_MAIN);
           latestAlertTime=System.currentTimeMillis();}
    }
}
