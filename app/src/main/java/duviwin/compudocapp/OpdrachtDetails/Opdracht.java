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

public class Opdracht implements Serializable {
	public static Opdracht getDummy(String text){
		return new Opdracht(text);
	};
	private Opdracht(String text){
		this.opdrachtNr="";
		this.plaats="";
		this.korteUitleg=text;
		this.SPOED=false;
		this.isDummy=true;
	}
	public final boolean isDummy;
	public final String opdrachtNr, plaats, korteUitleg;
	public String huidigBod="",  tijdVoorBod="";





	final boolean SPOED;
	public String numberClr="#c8d2d7";
	public String uitlegClr="#c8d2d7";
	boolean klantIsLid=false;
	public String[] allProperties=new String[propertyNames.length];
	public static String[] propertyNames={"gepost","OS","cat","omschrijving","afspraaktijd","internet","voorkeur","owner","straat","postcode","stad", "klantnr","feedbackscore","huidigbod","opdrstand"};
	public static int[] propertyIds={R.id.det_gepost,R.id.det_OS,R.id.det_cat,R.id.det_omschrijving,R.id.det_afspraaktijd,R.id.det_internet,R.id.det_voorkeur,R.id.det_owner,R.id.det_straat,R.id.det_postcode,R.id.det_stad,R.id.det_klantnr,R.id.det_feedbackscore,R.id.det_huidig_bod,R.id.det_opdr_stand};

	public Opdracht(String opdrachtNr, String plaats, String korteUitleg,
			String huidigBod, String tijdVoorBod, String opdrachtType,
			String uitlegKleur) {
		this.isDummy=false;
		this.opdrachtNr=opdrachtNr;
		this.plaats=plaats;
		this.korteUitleg=korteUitleg;
		this.huidigBod=huidigBod+"NC";
		this.tijdVoorBod=tijdVoorBod.replace("  sec","s").replace("  min  en ","m").replace(" uur","u");
		this.numberClr= CSSData.KLEUR_MAP.get(opdrachtType.replace("opdracht",""));
		System.out.println("opdrachtType: "+opdrachtType+", with replace: "+opdrachtType.replace("opdracht","")+", numberClr: "+numberClr);

		if(uitlegKleur.equals(" ")){
			SPOED=false;
			klantIsLid=false;
		}
		else if(uitlegKleur.equals(" style='background-color: #8EAFDD;color: white'")){
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
		String opdrachtUrl = "http://www.compudoc.be/index.php?page=opdrachten/detail&opdrachtnr="+ opdrachtNr;

		String fullPage=Connection.getConnection().doGet(opdrachtUrl, "");
		String pattern=
				"<th class='detail1.*?>Gepost op: </th><td class='detail2.*?>(.*?)"//1 gepost op
				+ "</td></tr><tr><th class='detail1.*?>Kenmerken: </th><td class='detail2.*?>(.*?)"//2 OS
				+ "</td><td class='detail2.*?>(.*?)"  //3 hoeveelste categorie
				+ "</td><tr><th class='detail1.*?>Omschrijving: </th><td class='detail2.*?><b>(.*?)"//4 omschrijving
				+ "</b></td></tr><tr><th class='detail1.*?>Afspraaktijd: </th><td class='detail2.*?>(.*?)"//5 afspraaktijd
				+ "</td></tr><tr><th class='detail1.*?>Internetverbinding: </th><td class='detail2.*?>(.*?)"//6 internetverbinding
				+ "</td></tr><tr><th class='detail1.*?>Voorkeur: </th><td class='detail2.*?><b>(.*?)"//7 Voorkeur
				+ "</b></td></tr><Th class='detail1.*?>Lead owner</Th><TD class='detail2.*?>(.*?)"//8 Lead owner
				+ "</TD></TR><tr><td class=\"detail1.*?>Klant: </td><td class=\"detail2.*?>(.*?)  in ([\\d+]*?) <b>(.*?)</b>, klantnr. (.*?)" //9 10 11 12 straat, postcode, stad, klantnr.
				+ "<br>Gemiddelde feedback Score: (.*?)" // 13 feedbackscore
				+ "/10<br>.*?Status opdracht"
				+"(.*?)"
 				+"<h2 align='center'>Je hebt <b>([^<]*?)"
 				+"</b> opdrachten.</h2>"
				;
//		<th class='detail1'>Gepost op: </th><td class='detail2' colspan='2'>Zaterdag 13-06-2015 om 10:17:39</td></tr><tr><th class='detail1'>Kenmerken: </th><td class='detail2'>Windows 8
//				</td><td class='detail2'>1e categorie
//				</td><tr><th class='detail1'>Omschrijving: </th><td class='detail2' colspan='2'><b>Krijgt steeds melding facebook subscribe en wenst dit te verwijderen</b>
//				</td></tr><tr><th class='detail1'>Afspraaktijd: </th><td class='detail2' colspan='2'>Blijft hetzelfde
//				</td></tr><tr><th class='detail1'>Internetverbinding: </th><td class='detail2' colspan='2'>Belgacom
//				</td></tr><tr><th class='detail1'>Voorkeur: </th><td class='detail2' colspan='2'><b>Niemand</b>
//				</td></tr><Th class='detail1'>Lead owner</Th><TD class='detail2' colspan=2>Compudoc
//				</td></TR><tr><td class="detail1">Klant:
//				</td><td class="detail2" colspan="2" >Rue de Louvain   in 1315 <b>Pietrebais Incourt</b>, klantnr. 19385
//				<br>Gemiddelde feedback Score: 0.00/10<br>
//				<h2 align='center'>Nog geen bod geplaatst.
// 				</h2><h2 align='center'>Je hebt <b>
// 				</b> opdrachten.</h2>
		Pattern r = Pattern.compile(pattern);
		
		// Now create matcher object.
		Matcher m = r.matcher(fullPage);
		int j=0;
		Log.d("","Started matching");
		while (m.find()) {
			j++;
			for(int i=1;i<=m.groupCount();i++){
			 allProperties[i-1]=m.group(i);
				if(i==4) {
					allProperties[i - 1] = allProperties[i - 1].replaceAll("(< ?[Bb][Rr] ?>|< ?[Bb][Rr] ?/>)", "\n"); //we vervangen alle <br> en </br> sequenties met een komma en spatie
				}else{
					allProperties[i - 1] = allProperties[i - 1].replaceAll("(< ?[Bb][Rr] ?>|< ?[Bb][Rr] ?/>)", ", "); //we vervangen alle <br> en </br> sequenties met een komma en spatie
				}
			System.out.println(i+": "+propertyNames[i-1]+": " +allProperties[i-1]);

			}
		}
		String huidigBod=getProperty("huidigbod");
		huidigBod=huidigBod.replaceAll("</p[^>]*>","\n");
		huidigBod=huidigBod.replaceAll("<[^>]*>","");
		setProperty("huidigbod",huidigBod);
		Log.d("", "Finished matching, found nb: " + j);


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
		return "opdracht nr: "+opdrachtNr+": "+plaats+": "+korteUitleg;
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
					String result=Connection.getConnection().doPost("http://www.compudoc.be/index.php?page=opdrachten/bieden", "bod=" + bod + "&opdrachtnr=" + opdrachtNr +
							"&bieder=" + AppSettings.userName + "&pagina=%2Findex.php%3Fpage%3Dopdrachten%2Fdetail%26opdrachtnr%3D" + opdrachtNr +
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

