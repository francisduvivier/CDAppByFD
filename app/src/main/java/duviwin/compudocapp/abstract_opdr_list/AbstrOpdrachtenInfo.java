package duviwin.compudocapp.abstract_opdr_list;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duviwin.compudocapp.Connection.Connection;
import duviwin.compudocapp.OpdrachtDetails.Opdracht;
import duviwin.compudocapp.html_info.HtmlInfo;
import duviwin.compudocapp.open_opdrachten.OpdrListHtmlInfo;

public abstract class AbstrOpdrachtenInfo {
	public List<Opdracht> opdrachten;

	protected final HtmlInfo htmlInfo;
	public AbstrOpdrachtenInfo(HtmlInfo htmlInfo) {
		this.htmlInfo=htmlInfo;
		opdrachten = new ArrayList<Opdracht>();
	}

	public void downloadOpdrachten() {
		opdrachten.clear();
		Matcher m=getPreparedMatcher();

		while (m.find()) {
				addOpdrachtFromMatch(m);
		}
	}

	protected void addOpdrachtFromMatch(Matcher m) {
		String[] valList=new String[htmlInfo.getVals().length];
		for(int i=0;i<htmlInfo.getVals().length;i++){
            valList[i]=m.group(i+1);
        }
		opdrachten.add(new Opdracht(valList));
		Log.d("OpdrachtenInfo", "\n" + m.group(0));
	}


	public abstract String getUrl();

	public Matcher getPreparedMatcher() {
		String fullPage = Connection.getConnection().doGet(
				getUrl(), "");

		String line = fullPage;

		String pattern = OpdrListHtmlInfo.opdrListPattern;


		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(line);
		OpdrListHtmlInfo.Nms[] infoTypeNames = OpdrListHtmlInfo.Nms.values();
		return m;
	}
}
