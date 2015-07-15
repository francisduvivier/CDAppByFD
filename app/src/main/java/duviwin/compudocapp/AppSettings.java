package duviwin.compudocapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Duviwin on 6/27/2015.
 */
public class AppSettings {
    public static String userName="";
    public static String password="";
    public static String defaultLoc="";
    public static void refreshPrefs(Context cntxt){
        SharedPreferences prefMgr= PreferenceManager
                .getDefaultSharedPreferences(cntxt);
        userName=prefMgr.getString("userNameKey", "");
        password=prefMgr.getString("passwordKey", "");
        defaultLoc=prefMgr.getString("defaultLocationKey", "");
//		EventSystem.subscribe(Connection.getConnection().getPublisherId(), this );

    }
}
