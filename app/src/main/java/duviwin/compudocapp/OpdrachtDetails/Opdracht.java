package duviwin.compudocapp.OpdrachtDetails;

import android.os.AsyncTask;
import android.util.Log;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import duviwin.compudocapp.AppSettings;
import duviwin.compudocapp.CSSData;
import duviwin.compudocapp.Connection.Connection;
import duviwin.compudocapp.R;
import duviwin.compudocapp.open_opdrachten.OpdrListHtmlInfo.Nms;

public class Opdracht implements Serializable {
	public static Opdracht getDummy(String text){
		return new Opdracht(text);
	}
	public String[] shrtInfo =new String[Nms.values().length];
	public static int[] shrtIds={R.id.det_gepost,R.id.det_OS,R.id.det_cat,R.id.det_omschrijving,R.id.det_afspraaktijd,R.id.det_internet,R.id.det_voorkeur,R.id.det_owner,R.id.det_straat,R.id.det_postcode,R.id.det_stad,R.id.det_klantnr,R.id.det_feedbackscore,R.id.det_huidig_bod,R.id.det_opdr_stand};

	public  boolean isDummy;
	private Opdracht(String text){
		this.shrtInfo[Nms.opdrachtNr.index]="";
		this.shrtInfo[Nms.plaats.index]="";
		this.shrtInfo[Nms.korteUitleg.index]=text;
		this.SPOED=false;
		this.isDummy=true;
	}

//	public String opdrachtNr, huidigBod = "", tijdVoorBod = "", plaats, korteUitleg;
	final boolean SPOED;
	public String numberClr="#c8d2d7";
	public String uitlegClr="#c8d2d7";
	boolean klantIsLid=false;
	public String[] allProperties=new String[propertyNames.length];
	public static String[] propertyNames={"gepost","OS","cat","omschrijving","afspraaktijd","internet","voorkeur","owner","straat","postcode","stad", "klantnr","feedbackscore","huidigbod","opdrstand"};
	public static int[] propertyIds={R.id.det_gepost,R.id.det_OS,R.id.det_cat,R.id.det_omschrijving,R.id.det_afspraaktijd,R.id.det_internet,R.id.det_voorkeur,R.id.det_owner,R.id.det_straat,R.id.det_postcode,R.id.det_stad,R.id.det_klantnr,R.id.det_feedbackscore,R.id.det_huidig_bod,R.id.det_opdr_stand};



	public Opdracht(String[] vals) {
		this.isDummy=false;

		for(Nms enumVal:Nms.values()){
			this.shrtInfo[enumVal.index]=vals[enumVal.index];
			for(int i=0;i<enumVal.toFind.length;i++){
				shrtInfo[enumVal.index]= shrtInfo[enumVal.index].replaceAll(enumVal.toFind[i], enumVal.toPut[i]);
			}
		}
		Log.d("Opdracht","shrtInfo[Nms.isZelfst.index]: "+shrtInfo[Nms.isZelfst.index]);
		this.numberClr= CSSData.KLEUR_MAP.get(shrtInfo[Nms.isZelfst.index]);

		if(shrtInfo[Nms.uitlegKleur.index].equals(" ")){
			SPOED=false;
			klantIsLid=false;
		}
		else if(shrtInfo[Nms.uitlegKleur.index].equals(" style='background-color: #8EAFDD;color: white'")){
			SPOED=false;
			klantIsLid=true;
			this.uitlegClr="#8EAFDD";
		}
		else{
			SPOED=true;
			klantIsLid=false;//this is actually unknown then
			this.numberClr=CSSData.KLEUR_MAP.get("_spoed");
		}

		}
	
	//Deze methode zorgt ervoor dat extra info over de opdracht opgehaald wordt via de opdrachtlink en dat die info dan hier in het object gezet wordt.
	public void getExtraInfo(){
		String opdrachtUrl = "http://www.compudoc.be/index.php?page=opdrachten/detail&opdrachtnr="
				+ shrtInfo[Nms.opdrachtNr.index]
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
				+", klantnr. ([\\d]*(?:<..>Lidkaart serienummer [\\d]*)?)(?:<..>Btw nr.: [^<]*)?" //9 10 11 12 straat, postcode, stad, klantnr.
				+ "(?:.*<..>(?:<strong>)?Gemiddelde feedback Score: (.*)" // 13 feedbackscore
				+ "/10.*?Status opdracht"
				+"(.*?)"
 				+"<h2 align='center'>Je hebt <.>([^<]*?)"
 				+"</.> opdrachten.</..>)?"
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
			int count=m.groupCount();
			Log.d("","MATCHED:: " + m.group());
			while(i<count){
				System.out.println();

			 allProperties[i]=m.group(i+1);
				if(allProperties[i]==null){
					allProperties[i]="(Not found)";
				}
				if(i==4) {
					allProperties[i] = allProperties[i].replaceAll("(< ?[Bb][Rr] ?>|< ?[Bb][Rr] ?/>)", "\n"); //we vervangen alle <br> en </br> sequenties met een komma en spatie
				}else{
					allProperties[i] = allProperties[i].replaceAll("(< ?[Bb][Rr] ?>|< ?[Bb][Rr] ?/>)", ", "); //we vervangen alle <br> en </br> sequenties met een komma en spatie
				}
				Log.d("","MATCHED: " + i + ": " + propertyNames[i] + ": " + allProperties[i]);
			i++;
			}
		}
		Log.d("", "Finished detail matching, found nb: " + i);

		while(i<allProperties.length){
			allProperties[i]="(Not found)";
			i++;
		}

		String huidigBod=getProperty("huidigbod");
		huidigBod=huidigBod.replaceAll("</p[^>]*>","\n");
		huidigBod=huidigBod.replaceAll("<[^>]*>","");
		setProperty("huidigbod",huidigBod);



	}
	public String getProperty(String name){
		List<String> propertyNameList=Arrays.asList(propertyNames);
		return allProperties[propertyNameList.indexOf(name)];
	}
	public void setProperty(String name,String newVal){
		List<String> propertyNameList=Arrays.asList(propertyNames);
		allProperties[propertyNameList.indexOf(name)]=newVal;
	}
	public int getPropertyId(String name){
		List<String> propertyNameList=Arrays.asList(propertyNames);
		return propertyIds[propertyNameList.indexOf(name)];
	}
	public String bodResult="";
	@Override
	public String toString(){
		return "opdracht nr: "+shrtInfo[Nms.opdrachtNr.index]+": "+shrtInfo[Nms.plaats.index]+": "+shrtInfo[Nms.korteUitleg.index];
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
					String result=Connection.getConnection().doPost("http://www.compudoc.be/index.php?page=opdrachten/bieden", "bod=" + bod + "&opdrachtnr=" + shrtInfo[Nms.opdrachtNr.index] +
							"&bieder=" + AppSettings.userName + "&pagina=%2Findex.php%3Fpage%3Dopdrachten%2Fdetail%26opdrachtnr%3D" + shrtInfo[Nms.opdrachtNr.index] +
							"&max_bod=" + maxBod + "&submit_bod=Bieden%21");
					result="result: "+result.replaceAll(".*<div class=\"notification[^>]*\">([^<]*)<.*","`$1");
					getExtraInfo();
					return result;

				}
				@Override
				protected void onPostExecute(String result) {
					Opdracht.this.bodResult=result;
					activity.update(Opdracht.this);
				}
			}
		BiedenTask task=new BiedenTask();
		task.execute();

	}
	}

