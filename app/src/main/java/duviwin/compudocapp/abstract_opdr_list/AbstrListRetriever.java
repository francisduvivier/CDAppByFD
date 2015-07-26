package duviwin.compudocapp.abstract_opdr_list;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duviwin.compudocapp.Connection.BadCredentialsException;
import duviwin.compudocapp.Connection.Connection;
import duviwin.compudocapp.html_info.HtmlInfo;

public abstract class AbstrListRetriever {
	public List<GenericOpdracht> opdrachten;

	protected final HtmlInfo htmlInfo;
	public AbstrListRetriever(HtmlInfo htmlInfo) {
		this.htmlInfo=htmlInfo;
		opdrachten = new ArrayList<GenericOpdracht>();
	}

	public void downloadOpdrachten() throws BadCredentialsException{
		opdrachten.clear();
		Matcher m=getPreparedMatcher();
		Log.d("listRetrieverMatch", "Strarting trying to match");

		while (m.find()) {
				Log.d("listRetrieverMatch",""+m.group());
				addOpdrachtFromMatch(m);
		}
	}

	protected void addOpdrachtFromMatch(Matcher m) {
		String[] valList=new String[htmlInfo.getVals().length];
		for(int i=0;i<htmlInfo.getVals().length;i++){
            valList[i]=m.group(i+1);
        }
		opdrachten.add(new GenericOpdracht(htmlInfo, valList) {
		});
		Log.d("OpdrachtenInfo", "\n" + m.group(0));
	}


	public abstract String getUrl();

	public Matcher getPreparedMatcher() throws BadCredentialsException{
		String fullPage = Connection.getConnection().doGet(
				getUrl(), "");
		String line = fullPage;
		Log.d("listretriever","tried getting url: "+getUrl()+", resultString is of length: "+line.length());

		String pattern = htmlInfo.getPattern();


		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(line);
		return m;
	}
}
