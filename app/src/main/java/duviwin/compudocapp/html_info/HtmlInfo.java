package duviwin.compudocapp.html_info;

import java.io.Serializable;
/**
 * Created by Duviwin on 7/8/2015.
 */
public interface HtmlInfo extends Serializable {
    String getPattern();
    HtmlInfoEnum[] getVals();
    int getOpdrNrIndex();
}

