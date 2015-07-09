package duviwin.compudocapp.mijn_gegevens;

import duviwin.compudocapp.R;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.html_info.HtmlInfoEnum;

/**
 * Created by Duviwin on 7/8/2015.
 */
public class OpdrListHtmlInfo implements HtmlInfo
{
    public static final String opdrListPattern=
//            "(.*)";
            "<tr>" +
            "<td class=\"lid2\"><a href=[^>]*>(\\d*)</a></td>" +
            "<td class=\"lid2\">(.*?)</td>" +
            "<td class=\"lid2\">(\\d*)</td>" +
            "<td class=\"lid2\">([^<]*)</td>" +
            "<td class=\"lid2\"[^>]*>([^<]*)</td>" +
            "</tr>";

    /**<table [^>]*><..><.. [^>]*>Opdrachtnr</..><.. [^>]*>Omschrijving</..><.. [^>]*>Bod </..><.. [^>]*>Status </..><.. [^>]*>Resterende tijd</..></..>
     * <tr>
     *     <td class="lid2"><a href="index.php?page=opdrachten/detail&amp;opdrachtnr=52724">52724</a></td>
     * <td class="lid2">Desktop start normaal op, geeft bureaublad netjes weer en na een\tijdje geeft die de melding No S</td>
     * <td class="lid2">10</td>
     * <td class="lid2">Lopend</td>
     * <td class="lid2" style="text-align:center;">9 dagen, 7 u , 18  min , en 46  sec</td></tr></table>
    **/
     Nms num= Nms.opdrachtNr;
    int i=num.index;
    Nms[] tst= Nms.values();


    @Override
    public String getPattern() {
        return opdrListPattern;
    }

    @Override
    public int getOpdrNrIndex() {
        return Nms.opdrachtNr.index;
    }

    @Override
    public HtmlInfoEnum[] getVals(){
        return Nms.values();
    }
    public enum Nms implements HtmlInfoEnum {
        //Opdrachtnr</..><.. [^>]*>Omschrijving</..><.. [^>]*>Bod </..><.. [^>]*>Status </..><.. [^>]*>Resterende tijd
        opdrachtNr(0, R.id.opdracht_item_opdrNr),
        korteUitleg(1,R.id.opdracht_item_korteUitleg, new String[]{"<br />"}, new String[]{"\n"}),
        huidigBod(2,R.id.opdracht_item_hBod,new String[]{"(\\d++)[^\\d]*+"},new String[]{"$1NC"}),
        status(3, R.id.opdracht_item_plaats),
        tijdVoorBod(4,R.id.opdracht_item_tijdVoorBod, new String[]{" dagen, ","  sec","  min , en "," u , "}, new String[]{"d","s","m","u"});
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

