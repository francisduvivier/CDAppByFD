package duviwin.compudocapp.open_opdrachten;

import android.util.Log;

import java.util.regex.Matcher;

import duviwin.compudocapp.OpdrachtDetails.Opdracht;
import duviwin.compudocapp.abstract_opdr_list.AbstrListRetriever;

public class ListRetriever extends AbstrListRetriever {

	public ListRetriever(){
		super(new OpdrListHtmlInfo());
	}
	@Override
	public void downloadOpdrachten() {
		opdrachten.clear();
		Matcher m=getPreparedMatcher();

		while (m.find()) {
			Log.d("OpdrachtenInfo","FOUND STRING with char 0: " + m.group().charAt(0));
			Log.d("OpdrachtenInfo","full string: " + m.group());

			if(m.group().charAt(0)=='h'){//dit betekent dat we en string gevonden hebben van de vorm:Opdrachten van <b>(.*?)u en ouder
				Log.d("OpdrachtenInfo","we found a life indication string:" + m.group());
				String text=m.group()
						.replace("hoofding\" style=\"text-align:left;padding:10px\" colspan=\"5\">","" )
						.replace("<b>","");
				opdrachten.add(Opdracht.getDummy(text));
			}else {
				addOpdrachtFromMatch(m);
			}
		}
	}

	@Override
	public String getUrl() {
			return "http://compudoc.be/index.php?page=opdrachten/open";
	}
}
