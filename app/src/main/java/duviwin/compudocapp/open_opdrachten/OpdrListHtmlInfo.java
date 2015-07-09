package duviwin.compudocapp.open_opdrachten;

import duviwin.compudocapp.R;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.html_info.HtmlInfoEnum;

/**
 * Created by Duviwin on 7/8/2015.
 */
public class OpdrListHtmlInfo implements HtmlInfo
{
    public static final String opdrListPattern=
            "(?:<.. class=\"opdracht([^\"]*)[^>]*?\"><a.*?opdrachtnr=([\\d+]*)[^>]*?\">[\\d+]*?</a></td>"
                    + "<.. class=\"opdracht\"[^>]*?>(.*?)</td><td class=\"opdracht\"[^>]*?width=\"50%\"([^>]*?)>(.*?)</td>"
                    + "<.. class=\"opdracht\"[^>]*?\">(.*?)NC.*?</td>"
                    + "<.. class=\"opdracht\"[^>]*?>(?:<b>)?([^<>]*)(?:</b>)?</..>" +
                    "|hoofding\" style=\"text-align:left;padding:10px\" colspan=\"5\">(Opdrachten( van <b>(.*?)u en ouder)?))";

    Nms num= Nms.opdrachtNr;
    int i=num.index;
    Nms[] tst= Nms.values();


    @Override
    public String getPattern() {
        return opdrListPattern;
    }

    @Override
    public HtmlInfoEnum[] getVals(){
        return Nms.values();
    }
    public enum Nms implements HtmlInfoEnum {

        isZelfst(0,null),
        opdrachtNr(1, R.id.opdracht_item_opdrNr),
        plaats(2,R.id.opdracht_item_plaats),
        uitlegKleur(3,null),
        korteUitleg(4,R.id.opdracht_item_korteUitleg, new String[]{"<br />"}, new String[]{"\n"}),
        huidigBod(5,R.id.opdracht_item_hBod,new String[]{"(\\d++)[^\\d]*+"},new String[]{"$1NC"}),
        tijdVoorBod(6,R.id.opdracht_item_tijdVoorBod, new String[]{"  sec","  min  en "," uur"}, new String[]{"s","m","u"});
        public final int index;
        public final String[] toFind;
        public final String[] toPut;
        public final Integer resId;
        @Override
        public Integer getResId(){
            return resId;
        }

        @Override
        public int getIndex() {
            return index;
        }

        @Override
        public String[] getToFind() {
            return toFind;
        }

        @Override
        public String[] getToPut() {
            return toPut;
            }

        Nms(int index, Integer resId){
            this.index =index;
            toFind =new String[]{""};
            toPut =new String[]{""};
            this.resId=resId;
        }
        Nms(int index, Integer resId, String[] afterWorkMatch, String[] afterWorkReplace){
            this.index =index;
            this.toFind =afterWorkMatch;
            this.toPut =afterWorkReplace;
            this.resId=resId;
        }
    }
}

