package duviwin.compudocapp.open_opdrachten;

import android.graphics.Color;
import android.util.Log;

import java.io.Serializable;

import duviwin.compudocapp.CSSData;
import duviwin.compudocapp.abstract_opdr_list.GenericOpdracht;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.open_opdrachten.OpenOpdrHtmlInfo.Nms;

public class ShortOpdracht extends GenericOpdracht {
    final boolean SPOED;
    final boolean klantIsLid;

    public static ShortOpdracht getDummy(String text) {
        return new ShortOpdracht(text);
    }


    private ShortOpdracht(String text) {
        super(text);
        this.SPOED = false;
        this.klantIsLid=false;
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
    public ShortOpdracht(HtmlInfo hi, String[] vals) {
        super(hi,vals);
        if (hi.getClass().equals(OpenOpdrHtmlInfo.class)) {
            Log.d("ShortOpdracht", "shrtInfo[Nms.isZelfst.index]: " + shrtInfo[OpenOpdrHtmlInfo.Nms.isZelfst.index]);


            if (shrtInfo[OpenOpdrHtmlInfo.Nms.uitlegKleur.index].equals(" ")) {
                SPOED = false;
                klantIsLid = false;
            } else if (shrtInfo[OpenOpdrHtmlInfo.Nms.uitlegKleur.index].contains("#8EAFDD")) {
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
}

