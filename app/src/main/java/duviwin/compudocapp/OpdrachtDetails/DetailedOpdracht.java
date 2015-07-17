package duviwin.compudocapp.OpdrachtDetails;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duviwin.compudocapp.AppSettings;
import duviwin.compudocapp.CSSData;
import duviwin.compudocapp.Connection.Connection;
import duviwin.compudocapp.R;

public class DetailedOpdracht implements Serializable {






	public final int opdrNr;
	public String[] allProperties=new String[propertyNames.length];
	public static final String[] propertyNames={"gepost","OS","cat","omschrijving","afspraaktijd","internet","voorkeur","owner"
			,"straat","postcode","stad", "klantnr","feedbackscore","optionalInfo","huidigbod","opdrstand"};
	private static final int[] propertyIds={R.id.det_gepost,R.id.det_OS,R.id.det_cat,R.id.det_omschrijving,R.id.det_afspraaktijd,R.id.det_internet,R.id.det_voorkeur,R.id.det_owner,R.id.det_straat,R.id.det_postcode,R.id.det_stad,R.id.det_klantnr,R.id.det_feedbackscore,-1,R.id.det_huidig_bod,R.id.det_opdr_stand};
	private boolean isSpoed=false;
	private boolean foundZelfstTag=false;

	public String getProperty(String name){
		List<String> propertyNameList=Arrays.asList(propertyNames);
		return allProperties[propertyNameList.indexOf(name)];
	}

	public DetailedOpdracht(int nr){
		this.opdrNr=nr;
	}


	public static final String[] optInfoNames = {"Gestart Op:", "Bod:"
			,""
	};
	public String[] opInfoItem =new String[optInfoNames.length];

	private void processOptionalInfo(){
		String regex="<.. .[^>]*>Gestart op: </..><.. .[^>]*>([^<]*)</..></..>" +
				"<..><.. .[^>]*>Bod: </..><.. .[^>]*><[Bb]>([^<]*)</[Bb]></..></..>"
				+
				"<..><.. .[^>]*>[^<]*</..><.. .[^>]*>([^<]*)</..>";
		/*
<tr><th class="detail1">Gestart op: </th><td class="detail2" colspan="2">Dinsdag 2015-07-14 om 10:48:02</td></tr>
<tr><th class="detail1">Bod: </th><td class="detail2" colspan="2"><b>10 NC door David, nr. 193</b></td></tr>
<tr><td class="detail1">Resterende tijd: </td><td class="detail2" colspan="2" style="text-align:center;">21 dagen, 6 u , 45  min , en 27  sec</td>
		 */


		Pattern p = Pattern.compile(regex);

		// Now create matcher object.
		Matcher m = p.matcher(getProperty("optionalInfo"));
		Log.d("DetailedOpdracht","Started optional info matching");

		if (m.find()){

			for(int i=0;i<optInfoNames.length;i++){
				opInfoItem[i]=m.group(i+1);
				Log.d("DetailedOpdracht","OpInfo "+i+": "+ opInfoItem[i]);
			}
		}else{
			Log.e("DetailedOpdracht","we did not find the optional info in the right format, this is not good");
		}
	}

	//Deze methode zorgt ervoor dat extra info over de opdracht opgehaald wordt via de opdrachtlink en dat die info dan hier in het object gezet wordt.
	public void getExtraInfo(){
		String opdrachtUrl = "http://www.compudoc.be/index.php?page=opdrachten/detail&opdrachtnr="
				+ opdrNr
				;

		String fullPage=Connection.getConnection().doGet(opdrachtUrl, "");
		String pattern=
				"<.. .[^>]*>Gepost op: </..><.. [^>]*>(.*?)"//1 gepost op
				+ "</..></..><..><.. [^>]*>Kenmerken: </..><.. [^>]*>(.*?)"//2 OS
				+ "</..><.. [^>]*>(.*?)"  //3 hoeveelste categorie
				+ "</..><..><.. [^>]*>Omschrijving: </..><td.*?><b>(.*?)"//4 omschrijving
				+ "</b></..></..><..><.. [^>]*>Afspraaktijd: </..><.. [^>]*>(.*?)"//5 afspraaktijd
				+ "</..></..><..><.. [^>]*>Internetverbinding: </..><.. [^>]*>(.*?)"//6 internetverbinding
				+ "</..></..><..><.. [^>]*>Voorkeur: </..><.. [^>]*><b>(.*?)"//7 Voorkeur
				+ "</b></..></..><.. [^>]*>Lead owner</..><.. [^>]*>(.*?)"//8 Lead owner
				+ "</..></..><..><.. [^>]*>Klant: </..><.. [^>]*>(.*?) in ([\\d+]*?) <b>(.*?)</b>"
				+", klantnr. ([\\d]*(?:<..>Lidkaart serienummer [\\d]*)?(?:<..>Btw nr.: [^<]*)?+)" //9 10 11 12 straat, postcode, stad, klantnr.
				+ "(?:.*Gemiddelde feedback Score:[ ]?+(.*)" // 13 feedbackscore
				+ "/10(.*)?Status opdracht"
				+"(?:(.*?)"
 				+"<h2 align='center'>Je hebt <.>([^<]*?)"
 				+"</.> opdrachten.</..>)?+)?"
				;
		/**

		/**
		 <.. [^>]*>Gepost op: </..><.. [^>]*>Zaterdag 13-06-2015 om 10:17:39</..></..>
		 <..><.. [^>]*>Kenmerken: </..><.. [^>]*>Windows 8</..><.. [^>]*>1e categorie
		 </..><..><.. [^>]*>Omschrijving: </..><.. [^>]*><b>Krijgt steeds melding facebook subscribe en wenst dit te verwijderen</b>
		 </..></..><..><.. [^>]*>Afspraaktijd: </..><.. [^>]*>Blijft hetzelfde
		 </..></..><..><.. [^>]*>Internetverbinding: </..><.. [^>]*>Belgacom
		 </..></..><..><.. [^>]*>Voorkeur: </..><.. [^>]*><b>Niemand</b>
		 </..></..><.. [^>]*>Lead owner</..><.. [^>]*>Compudoc
		 </..></..><..><.. [^>]*>Klant:
		 </..><.. [^>]*>Rue de Louvain   in 1315 <b>Pietrebais Incourt</b>, klantnr. 19385
		 <..>Gemiddelde feedback Score: 0.00/10<..>
		 <.. [^>]*>Nog geen bod geplaatst.
		 </..><h2 align='center'>Je hebt <b>
		 </b> opdrachten.</..>
		 **/



		//Opdracht waarbij bieden afgelopen is:
		/**
		 <.. [^>]*>Gepost op: </..><.. [^>]*>Maandag 29-06-2015 om 09:45:21</..></..>
		 <..><.. [^>]*>Kenmerken: </..><.. [^>]*>Onbekend</..><.. [^>]*>1e categorie</..>
		 <..><.. [^>]*>Omschrijving: </..><.. [^>]*><b>Het systeem...<.. [^>]*>blabla</b></..></..>
		 <..><.. [^>]*>Afspraaktijd: </..><.. [^>]*>Liefst tijdens kantooruren</..></..>
		 <..><.. [^>]*>Internetverbinding: </..><.. [^>]*>Belgacom</..></..>
		 <..><.. [^>]*>Voorkeur: </..><.. [^>]*><b>Niemand</b></..></..>
		 <.. [^>]*>Lead owner</..><.. [^>]*>Compudoc</..></..><..>
		 <.. [^>]*>Klant: </..><.. [^>]*>Heidelaan  in 3001 <b>Heverlee</b>, klantnr. 369<..>Gemiddelde feedback Score: 10.00/10
		 <..><strong><a href='javascr.....'>Commentaren</a></strong>
		 <..><span id="comments" class="hiding"><span class='comment'><..><..><..><small>Op 2013-01-04 door lid nr 245 mbt opdracht 43706</small></..></span></span></..></..>
		 <..><.. [^>]*>Gestart op: </..><.. [^>]*>Maandag 2015-06-29 om 16:01:02</..></..>
		 <..><.. [^>]*>Bod: </..><.. [^>]*><b>10 NC door Bert, nr. 187</b></..></..>
		 <..><.. [^>]*>Resterende tijd: </..><.. [^>]*>5 dagen, 23 u , 58  min , en 35  sec</..></table>
		 <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="margin-bottom:15px;">
		 <..>
		 </..>
		 <.. [^>]*> <..>Status opdracht</..></..>
		 <td width="11" style="padding-right:25px">
		 </..>
		 </..></table><div id='page-title' class='hideLine'><div id='bolded-line'></div></div><h2 align='center'>Je hebt <b>2 / 18</b> opdrachten.</..><h2 align='center'>Bieden afgelopen ! Opdracht gaat naar Bert, nr. 187</..>
		 **/


		Pattern r = Pattern.compile(pattern);
		
		// Now create matcher object.
		Matcher m = r.matcher(fullPage);
		int i=0;
		Log.d("","Started matching");

		if (m.find()){
			if(m.group().contains("detail2_spoed")){
				this.isSpoed=true;
			}
			if(m.group().contains("detail1_zelfst")){
				this.foundZelfstTag=true;
			}


			int count=m.groupCount();
			Log.d("","MATCHED:: " + m.group());
			while(i<count){
				System.out.println();

				allProperties[i] = m.group(i + 1);
				if (allProperties[i] == null) {
					allProperties[i] = "(Not found)";
				}
				else if (propertyNames[i]=="omschrijving") {
					allProperties[i] = allProperties[i].replaceAll("(< ?[Bb][Rr] ?>|< ?[Bb][Rr] ?/>)", "\n"); //we vervangen alle <br> en </br> sequenties met een komma en spatie
				} else {
					allProperties[i] = allProperties[i].replaceAll("(< ?[Bb][Rr] ?>|< ?[Bb][Rr] ?/>)", ", "); //we vervangen alle <br> en </br> sequenties met een komma en spatie
				}
				Log.d("", "MATCHED: " + i + ": " + propertyNames[i] + ": " + allProperties[i]);
				i++;
			}
		}
		Log.d("", "Finished detail matching, found nb: " + i);

		while(i<allProperties.length){
			allProperties[i]="(Not found)";
			i++;
		}
		processOptionalInfo();

		String huidigBod=getProperty("huidigbod");
		huidigBod=huidigBod.replaceAll("</p[^>]*>","\n");
		huidigBod=huidigBod.replaceAll("<[^>]*>","");
		setProperty("huidigbod",huidigBod);



	}

	public void setProperty(String name,String newVal){
		List<String> propertyNameList=Arrays.asList(propertyNames);
		allProperties[propertyNameList.indexOf(name)]=newVal;
	}
	public static int getPropertyId(String name) throws HasNoPropIdException {
		List<String> propertyNameList=Arrays.asList(propertyNames);
		int propId=propertyIds[propertyNameList.indexOf(name)];
		if(propId==-1){
			throw new HasNoPropIdException(name+"has no propertyId coupled to it");
		}
		return propId ;
	}
	public static int getPropertyId(int i) throws HasNoPropIdException {
		int propId=propertyIds[i];
		if(propId==-1){
			throw new HasNoPropIdException(propertyNames[i]+ ", index="+i+" has no propertyId coupled to it");
		}
		return propId ;
	}
	public String bodResult="";
	@Override
	public String toString(){
		return "opdracht nr: "+this.opdrNr+": "+getProperty("stad")+": "+getProperty("omschrijving");
	}
	public boolean biedenIsAfgelopen(){
		return opInfoItem[0]!=null||getProperty("huidigbod").equals("(Not found)");
	}
	public boolean isGewonnenDoorGebruiker(){
		return getProperty("straat").contains("Telefoon:");
	}
	public void bied(final int bod, final int maxBod, final ShowDetailsActivity activity){
		//todo work with the following data
		//todo for open webpage
//		Uri uri = Uri.parse("http://localhost:8080/test/error.jsp;jsessionid=C4E6732EBB4C17F409AB41143735C096");
//		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//		startActivity(intent);
		//todo for bieden
		class BiedenTask extends AsyncTask<Void, Void, String> {
				@Override
				protected String doInBackground(Void... params) {
					String result=Connection.getConnection().doPost("http://www.compudoc.be/index.php?page=opdrachten/bieden", "bod=" + bod + "&opdrachtnr=" + DetailedOpdracht.this.opdrNr +
							"&bieder=" + AppSettings.userName + "&pagina=%2Findex.php%3Fpage%3Dopdrachten%2Fdetail%26opdrachtnr%3D" + DetailedOpdracht.this.opdrNr +
							"&max_bod=" + maxBod + "&submit_bod=Bieden%21");
					result="result: "+result.replaceAll(".*<div class=\"notification[^>]*\">([^<]*)<.*","$1");
					getExtraInfo();
					return result;

				}
				@Override
				protected void onPostExecute(String result) {
					DetailedOpdracht.this.bodResult=result;
					activity.update(DetailedOpdracht.this);
				}
			}
		BiedenTask task=new BiedenTask();
		task.execute();

	}

	public List<String> getTelNrs() {
		List<String> telNrs=new ArrayList<String>();
		String pattern="\\d{3,7}[ /]*\\d\\d[ ]?\\d\\d[ ]?\\d\\d";
		Pattern p=Pattern.compile(pattern);
		//Het telefoonnummer staat momenteel in de tekst van de straat.
		Matcher m=p.matcher(getProperty("straat"));
		while(m.find()){
			telNrs.add(m.group());
		}
		return telNrs;
	}

	public int getKlantNrKleur() {
		if(isZelfst()){
			return CSSData.getKleur("_zelfst");
		}
		else if(klantIsLid()){
			return CSSData.getKleur("lid");
		}

		return Color.parseColor("white");
	}
	public int getOmschrijvingKleur(){
		if(isSpoedOpdr()){
			return CSSData.getKleur("_spoed");
		}else if(opInfoItem[2]!=null&&opInfoItem[2].contains("geannuleerd")){
			return CSSData.getKleur("geannuleerd");
		}
		return Color.parseColor("white");
	}
	public int getAdresKleur() {
		if(klantIsLid()){
			return CSSData.getKleur("lid");
		}
		else if(isZelfst()){
			return CSSData.getKleur("_zelfst");
		}
		return Color.parseColor("white");
	}

	private boolean klantIsLid() {
		return getProperty("klantnr").contains("Lidkaart");
	}

	public boolean isSpoedOpdr() {
		return isSpoed;
	}

	public boolean isZelfst(){
		//normally we can recognise business customers by the html code detail1_zelfst, in that case foundZelfstTag would be true.
		//But if it is also a spoed-opdr, then we can still try to recognize it by checking whether the klantnr contains a Btw nr.(The last one only works when the user of the app has won the opdracht)
		return getProperty("klantnr").contains("Btw nr.:")||foundZelfstTag;
	}
	public int getTijdKleur() {
		int tijdKleur=CSSData.getKleur("1");
		int verstreken=getAantalVerstekenUren();
		for(int i=1;i<5;i++){
			if(verstreken>=i*4){
				tijdKleur=CSSData.getKleur((i+1)+"");
			}
		}
		return tijdKleur;
	}

	public int getAantalVerstekenUren() {
		String gepost=getProperty("gepost");
		String regex="\\d{2,4}+";
		Pattern p= Pattern.compile(regex);
		Matcher m=p.matcher(gepost);
		int[] date=new int[6];
		int i=0;
		while(m.find()){
			date[i++]=Integer.parseInt(m.group());
		}
		//format of gepost: Vrijdag 10-07-2015 om 08:00:02
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("Europe/Brussels"));
		//date[1]-1 because January is 0 and not 1
		cal.set(date[2], date[1]-1, date[0], date[3],date[4],date[5]);
		Log.d("Uren1",cal.toString());

		long yourmilliseconds = System.currentTimeMillis();
		long diff=yourmilliseconds-cal.getTimeInMillis();
//		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
//		Date d2 = new Date(yourmilliseconds);
//		Date d=new Date(cal.getTimeInMillis());
//		Log.d("Uren1", sdf.format(d));
//		Log.d("Uren2", sdf.format(d2));


		Log.d("Uren","int :"+(int) diff/(1000*3600)+"");

		return (int) diff/(1000*3600);
	}
}

