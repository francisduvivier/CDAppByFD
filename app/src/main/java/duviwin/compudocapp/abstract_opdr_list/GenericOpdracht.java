package duviwin.compudocapp.abstract_opdr_list;

import android.util.Log;

import java.io.Serializable;

import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.html_info.HtmlInfoEnum;
import duviwin.compudocapp.opdracht_details.DetailedOpdracht;

/**
 * Created by Francis Duvivier on 19.07.15.
 */
public abstract class GenericOpdracht implements Serializable {
    public final String[] shrtInfo;
    public final HtmlInfo htmlInfo;
    public boolean isDummy;

    public static GenericOpdracht getDummy(HtmlInfo hi,String text) {
        return new GenericOpdracht(hi,text) {
        };
    }

    public GenericOpdracht(HtmlInfo hi,String text){
        this.isDummy = true;
        this.htmlInfo = hi;
        this.shrtInfo = new String[hi.getVals().length];
        this.shrtInfo[hi.getLoadingIndex()] = text;
    }
    public GenericOpdracht(HtmlInfo hi, String[] vals) {
        for(String val:vals){
            Log.d("GenericOpdracht", val);}
        this.htmlInfo = hi;
        this.shrtInfo = new String[hi.getVals().length];
        this.isDummy=false;
        for (HtmlInfoEnum enumVal : hi.getVals()) {

            this.shrtInfo[enumVal.getIndex()] = vals[enumVal.getIndex()];
            //After setting the value we edit it with regex
            for (int i = 0; i < enumVal.getToFind().length; i++) {
                shrtInfo[enumVal.getIndex()] = shrtInfo[enumVal.getIndex()].replaceAll(enumVal.getToFind()[i], enumVal.getToPut()[i]);
            }
        }

    }
    public String getOpdrNr(){
        return shrtInfo[htmlInfo.getOpdrNrIndex()];
    }
    public DetailedOpdracht makeDetailedOpdr() {
        return new DetailedOpdracht(Integer.parseInt(shrtInfo[htmlInfo.getOpdrNrIndex()]));
    }
}
