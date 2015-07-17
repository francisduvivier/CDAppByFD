package duviwin.compudocapp.abstract_opdr_list;

import android.graphics.Color;
import android.util.Log;

import java.io.Serializable;

import duviwin.compudocapp.CSSData;
import duviwin.compudocapp.OpdrachtDetails.DetailedOpdracht;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.html_info.HtmlInfoEnum;
import duviwin.compudocapp.open_opdrachten.OpdrListHtmlInfo;
import duviwin.compudocapp.open_opdrachten.OpdrListHtmlInfo.Nms;

public class ShortOpdracht implements Serializable {


    public static ShortOpdracht getDummy(String text) {
        return new ShortOpdracht(text);
    }

    final public String[] shrtInfo;
    final HtmlInfo htmlInfo;

    public boolean isDummy;

    private ShortOpdracht(String text) {
        htmlInfo = new OpdrListHtmlInfo();
        shrtInfo = new String[Nms.values().length];
        this.shrtInfo[htmlInfo.getOpdrNrIndex()] = "";
        this.shrtInfo[Nms.plaats.index] = "";
        this.shrtInfo[Nms.korteUitleg.index] = text;
        this.SPOED = false;
        this.klantIsLid=false;
        this.isDummy = true;
    }

    final boolean SPOED;
    final boolean klantIsLid;

    public ShortOpdracht(HtmlInfo hi, String[] vals) {
        for(String val:vals){
        Log.d("ShortOpdracht",val);}
        this.htmlInfo = hi;
        this.isDummy = false;
        shrtInfo = new String[hi.getVals().length];
        for (HtmlInfoEnum enumVal : hi.getVals()) {

            this.shrtInfo[enumVal.getIndex()] = vals[enumVal.getIndex()];
            //After setting the value we edit it with regex
            for (int i = 0; i < enumVal.getToFind().length; i++) {
                shrtInfo[enumVal.getIndex()] = shrtInfo[enumVal.getIndex()].replaceAll(enumVal.getToFind()[i], enumVal.getToPut()[i]);
            }
        }

        if (hi.getClass().equals(OpdrListHtmlInfo.class)) {
            Log.d("ShortOpdracht", "shrtInfo[Nms.isZelfst.index]: " + shrtInfo[Nms.isZelfst.index]);


            if (shrtInfo[Nms.uitlegKleur.index].equals(" ")) {
                SPOED = false;
                klantIsLid = false;
            } else if (shrtInfo[Nms.uitlegKleur.index].contains("#8EAFDD")) {
                SPOED = false;
                klantIsLid = true;
            } else {
                SPOED = true;
                klantIsLid = false;//this is actually unknown then
            }
        } else {
            SPOED = false;
            klantIsLid = false;
        }
    }


    public int getNumberClr() {
        if (SPOED) {
            return CSSData.getKleur("_spoed");
        }
        else if(CSSData.KLEUR_MAP.get(shrtInfo[Nms.isZelfst.index])!=null){  return CSSData.getKleur(shrtInfo[Nms.isZelfst.index]);}//TODO
        else return CSSData.getKleur("");

    }

    public int getUitlegClr() {
        if (klantIsLid) {
            return CSSData.getKleur("lid");
        }
        return Color.parseColor("#ffffff");
    }

    @Override
    public String toString() {
        return "opdracht nr: " + shrtInfo[htmlInfo.getOpdrNrIndex()] + ": " + shrtInfo[Nms.plaats.index] + ": " + shrtInfo[Nms.korteUitleg.index];
    }


    public DetailedOpdracht makeDetailedOpdr() {
        return new DetailedOpdracht(Integer.parseInt(shrtInfo[htmlInfo.getOpdrNrIndex()]));
    }
}

