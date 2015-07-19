package duviwin.compudocapp.mijn_afspraken;

import duviwin.compudocapp.R;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.html_info.HtmlInfoEnum;

/**
 * Created by Duviwin on 7/8/2015.
 */
public class MijnAfsprHtmlInfo implements HtmlInfo
{

    @Override
    public String getPattern() {
        return opdrListPattern;
    }

    @Override
    public int getOpdrNrIndex() {
        return Nms.opdrachtNr.index;
    }

    @Override
    public int getLoadingIndex() {
        return Nms.datumEnTijd.index;
    }

    /**
     <TR>
     <TD class='agenda'>Mon Jul 20, 2015</TD><TD class='agenda'>16:00:00<BR>17:00:00<BR><BR></TD>
     <TD class='agenda'><a href='#52807' class='nyroModal'>Danielle Van der Weeen, Naamsesteenweg 413 bus 001
     <BR>3001 Heverlee</a><div id='52807' style='font-size: 11px; display: none; width: 600px;'>
     <a href='index.php?page=opdrachten/detail&opdrachtnr=52807'>Opdracht 52807</a>
     <BR><BR><BR>
     <Table class='admin'>
     <TR><Th align='left'>Technieker</Th><TD align='left'>284, Francis</TD></TR>
     <TR><Th align='left'>Klant</Th><TD align='left'>Danielle Van der Weeen</TD></TR>
     <TR><Th align='left'>GSM</Th><TD align='left'>016 40 38 22 / 0471 02 35 32</TD></TR>
     <TR><Th align='left'>Adres</Th>
     <TD align='left'>Naamsesteenweg 413 bus 001<BR>3001 Heverlee</TD></TR>
     <TR><Th align='left'>Datum/tijd</Th><TD align='left'>2015-07-20 16:00:00</TD></TR>
     <TR><Th align='left'>Opmerking: </Th><TD align='left'>Tegen maandag zal ik een afspraak kunnen maken omdat de laptop er dan zeker zal zijn</TD></TR>
     </TABLE>
     </div></TD><TD class='agenda'><a href='index.php?page=opdrachten/detail&opdrachtnr=52807'>opdracht 52807</a></TD>
     <FORM action='' method='POST'><input type='hidden' name='id' value=10976><input type='hidden' name='mission' value=52807><TD class='agenda'><input type='submit' name='detail' class='admin' value='wijzigen'/></TD></FORM></TR><TR><TD colspan=5><hr></TD></TR>
     **/
    public static final String opdrListPattern="<TR>"+
    "<..[^>]*>[^<]*</..><..[^>]*>[\\d:]*<..[^>]*>([\\d:]*)" +/*eind_uur*/ "<..[^>]*><..[^>]*><..[^>]*>"+
    "<..[^>]*><..[^>]*>[^<]*, [^<]*"+
    "<..[^>]*>[\\d]{4} [^>]*<..[^>]*><..[^>]*>"+
    "<..[^>]*>Opdracht ([\\d]*)<..[^>]*>"+/*opdrachtNr*/
    "<..[^>]*><..[^>]*><..[^>]*>"+
    "<..[^>]*>"+
    "<..[^>]*><..[^>]*>Technieker<..[^>]*><..[^>]*>[^>]*<..[^>]*><..[^>]*>"+
    "<..[^>]*><..[^>]*>Klant<..[^>]*><..[^>]*>([^>]*)" +/*klant_naam*/"<..[^>]*><..[^>]*>"+
    "<..[^>]*><..[^>]*>GSM<..[^>]*><..[^>]*>([^>]*)" +/*tel_nrs*/"<..[^>]*><..[^>]*>"+
    "<..[^>]*><..[^>]*>Adres<..[^>]*>"+
    "<..[^>]*>([^>]*)" +/*straat_en_nr*/"<..[^>]*>([\\d]{4} [^>]*)"+/*postcode_en_stad*/"<..[^>]*><..[^>]*>"+
    "<..[^>]*><..[^>]*>Datum/tijd<..[^>]*><..[^>]*>([^>]*)"+/*datum_en_tijd*/"<..[^>]*><..[^>]*>"+
    "<..[^>]*><..[^>]*>Opmerking: <..[^>]*><..[^>]*>([^>]*)"+/*opmerking*/"<..[^>]*>";

    @Override
    public HtmlInfoEnum[] getVals(){
        return Nms.values();
    }
    public enum Nms implements HtmlInfoEnum {
        //Opdrachtnr</..><.. [^>]*>Omschrijving</..><.. [^>]*>Bod </..><.. [^>]*>Status </..><.. [^>]*>Resterende tijd
        eindUur(0, R.id.afspr_eind_uur),
        opdrachtNr(1, R.id.afspr_opdracht_nr),
        klantNaam(2, R.id.afspr_klant_naam),
        telNrs(3, R.id.afspr_tel_nrs),
        straatEnNr(4, R.id.afspr_straat_en_nr),
        postcodeEnStad(5, R.id.afspr_postcode_en_stad),
        datumEnTijd(6, R.id.afspr_datum_en_tijd),
        opmerking(7, R.id.afspr_opmerking);
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

