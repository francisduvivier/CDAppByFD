package duviwin.compudocapp.OpdrList;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duviwin.compudocapp.Connection.Connection;
import duviwin.compudocapp.OpdrachtDetails.Opdracht;
import duviwin.compudocapp.html_info.OpdrListHtmlInfo;

public class OpdrachtenInfo {
	public List<Opdracht> opdrachten;


	public static List<Opdracht> getOpdrachtList(){

		Log.d("OpdrachtenInfo","Now we will GET open opdrachten page");
		OpdrachtenInfo opInfo=new OpdrachtenInfo();
		opInfo.downloadOpdrachten();
		return opInfo.opdrachten;
	}

	public OpdrachtenInfo() {
		opdrachten = new ArrayList<Opdracht>();
	}

	private void downloadOpdrachten() {
		opdrachten.clear();
		String fullPage = Connection.getConnection().doGet(
				getUrl(), "");

		String line = fullPage;

		String pattern = OpdrListHtmlInfo.opdrListPattern;


		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(line);
		OpdrListHtmlInfo.Nms[] infoTypeNames= OpdrListHtmlInfo.Nms.values();

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
				String[] valList=new String[infoTypeNames.length];
				for(int i=0;i<infoTypeNames.length;i++){
					valList[i]=m.group(i + 1);
				}
				opdrachten.add(new Opdracht(valList));
				Log.d("OpdrachtenInfo","\n" + m.group(0));
			}
		}
	}


	public String getUrl() {
		return "http://compudoc.be/index.php?page=opdrachten/open";
	}
}
