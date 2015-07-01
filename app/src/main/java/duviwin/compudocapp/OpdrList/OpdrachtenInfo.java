package duviwin.compudocapp.OpdrList;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duviwin.compudocapp.Connection.Connection;
import duviwin.compudocapp.Events.EventSystem;
import duviwin.compudocapp.OpdrachtDetails.Opdracht;

public class OpdrachtenInfo {
	public List<Opdracht> opdrachten = new ArrayList<Opdracht>();


	public static List<Opdracht> getOpdrachtList(){
//		if(!Connection.getConnection().isLoggedIn){
			Connection.getConnection().Login();
//		}
		System.out.println("current cookies are: " + Connection.getConnection().getCurrCookies());
		System.out.println("Now we will GET open opdrachten page");
		OpdrachtenInfo opInfo=new OpdrachtenInfo();
//		List<String> opdrachten=new ArrayList<String>();
//
//		for(Opdracht opdr:opInfo.opdrachten){
//			opdr.getExtraInfo();
//		}
		return opInfo.opdrachten;
	}
	public OpdrachtenInfo() {
		opdrachten.clear();
		String fullPage = Connection.getConnection().doGet(
				"http://compudoc.be/index.php?page=opdrachten/open", "");
		// String[]
		// opdrachten=fullPage.split("<a href=\"index.php?page=opdrachten/detail&amp;opdrachtnr=");
		// for(String opdracht:opdrachten){
		// System.out.println(opdracht);
		// }
		String line = fullPage;
//		 href=\"index.php?page=opdrachten/detail&amp;

		String pattern = 
				"(<td class=\"(opdracht[^>]*?)\"><a.*?opdrachtnr=([\\d+]*?)\">[\\d+]*?</a></td>"
				+ "<td class=\"opdracht\".*?>(.*?)</td><td class=\"opdracht\" width=\"50%\"(.*?)>(.*?)</td>"
				+ "<td class=\"opdracht\" style=\"text-align:center;\">(.*?)NC.*?</td>"
				+ "<td class=\"opdracht\" >[<b>]*?([^<>]*)[</b>]*?</td>|hoofding\" style=\"text-align:left;padding:10px\" colspan=\"5\">(Opdrachten( van <b>(.*?)u en ouder)?))";
		// String pattern = ".*>";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(line);
		while (m.find()) {
			System.out.println("FOUND STRING with char 0: "+ m.group().charAt(0));
			System.out.println("full string: "+ m.group());

			if(m.group().charAt(0)=='h'){//dit betekent dat we en string gevonden hebben van de vorm:Opdrachten van <b>(.*?)u en ouder
				System.out.println("we found a life indication string:"+ m.group());
				String text=m.group()
						.replace("hoofding\" style=\"text-align:left;padding:10px\" colspan=\"5\">","" )
						.replace("<b>","");
				opdrachten.add(Opdracht.getDummy(text));
			}else {
				int i=1;
				String
						isZelfst = m.group(1+i),
						opdrachtNr = m.group(2+i),
//					opdrachtUrl = "http://www.compudoc.be/index.php?page=opdrachten/detail&opdrachtnr="+ opdrachtNr,
						plaats = m.group(3+i),
						uitlegKleur = m.group(4+i),
						korteUitleg = m.group(5+i).replaceAll("<br />","\n"),
						huidigBod = m.group(6+i),
						tijdVoorBod = m.group(7+i);
				opdrachten.add(new Opdracht(opdrachtNr, plaats, korteUitleg, huidigBod, tijdVoorBod, isZelfst, uitlegKleur));
				System.out.println("\n" + m.group(0));
				System.out.println("isZelfst: " + isZelfst);
				System.out.println("opdrachtNr: " + opdrachtNr);
				System.out.println("plaats: " + plaats);
				System.out.println("uitlegKleur: " + uitlegKleur);
				System.out.println("korteUitleg: " + korteUitleg);
				System.out.println("huidigBod: " + huidigBod);
				System.out.println("tijdVoorBod: " + tijdVoorBod);
			}
		}
	}
}
