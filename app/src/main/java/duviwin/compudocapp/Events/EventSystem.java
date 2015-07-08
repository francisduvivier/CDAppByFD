package duviwin.compudocapp.Events;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Duviwin on 7/1/2015.
 */
public class EventSystem {
    public static Context context;
    static Map<Integer, List<MyEventListener>> listenerMap=new HashMap<Integer, List<MyEventListener>>();
    static long prevTime=System.currentTimeMillis();
    public static void  publish(int publisherId, String msg){
//        List<MyEventListener> listenerList= listenerMap.shrtInfo(publisherId);
//        if(listenerList!=null){
//            for(MyEventListener l:listenerList){
//
//                l.handleMsg(System.currentTimeMillis()-prevTime+": "+msg);
//                prevTime=System.currentTimeMillis();
//
//            }
//        }
    }
    public static void subscribe(int publisherId, MyEventListener el){
        List<MyEventListener> listenerList= listenerMap.get(publisherId);
        if(listenerList==null){
            listenerList=new ArrayList<MyEventListener>();
        listenerMap.put(publisherId,listenerList);}
        listenerList.add(el);

    }
}
