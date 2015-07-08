package duviwin.compudocapp.html_info;

import duviwin.compudocapp.R;

/**
 * Created by Duviwin on 7/8/2015.
 */
public class OpdrListHtmlInfo
{
    public static final String opdrListPattern=
            "(?:<.. class=\"opdracht([^\"]*)" +
            "[^>]*?\"><a.*?opdrachtnr=([\\d+]*)" +
            "[^>]*?\">[\\d+]*?</a></td><td class=\"opdracht\"[^>]*?>(.*?)" +
                    "</..><.. class=\"opdracht\"[^>]*?width=\"50%\"([^>]*?)>(.*?)" +
                    "</..><.. class=\"opdracht\"[^>]*?\">(.*?)" +
                    "NC.*?</..><.. class=\"opdracht\"[^>]*?>[<.>]*?([^<>]*)" +
                    "[</.>]*?</..>|hoofding\" style=\"text-align:left;padding:10px\" colspan=\"5\">(Opdrachten( van <b>(.*?)" +
                    "u en ouder)?))";
    Nms num= Nms.opdrachtNr;
    int i=num.i;
    Nms[] tst= Nms.values();

    public enum Nms {

        isZelfst(1,null), opdrachtNr(2, R.id.opdracht_item_opdrNr), plaats(3,R.id.opdracht_item_plaats), uitlegKleur(4,null),
        korteUitleg(5,R.id.opdracht_item_korteUitleg, new String[]{"<br />"}, new String[]{"\n"}),
        huidigBod(6,R.id.opdracht_item_hBod,new String[]{"(.*)"},new String[]{"$1\"NC\""}),
        tijdVoorBod(7,R.id.opdracht_item_tijdVoorBod, new String[]{"  sec","  min  en "," uur"}, new String[]{"s","m","u"});
        public final int i;
        public final String[] toFind;
        public final String[] toPut;
        public final Integer resId;
        Nms(int index, Integer resId){
            this.i=index;
            toFind =new String[]{""};
            toPut =new String[]{""};
            this.resId=resId;
        }
        Nms(int index, Integer resId, String[] afterWorkMatch, String[] afterWorkReplace){
            this.i=index;
            this.toFind =afterWorkMatch;
            this.toPut =afterWorkReplace;
            this.resId=resId;
        }
    }
}

