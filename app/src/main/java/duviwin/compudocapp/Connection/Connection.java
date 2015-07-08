package duviwin.compudocapp.Connection;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import duviwin.compudocapp.AppSettings;
import duviwin.compudocapp.events.EventSystem;
import duviwin.compudocapp.events.MyPublisher;
import duviwin.compudocapp.opdrList.OpdrListFragment;

public class Connection implements Serializable,MyPublisher {
	final int publisherId=0;
	@Override
	public int getPublisherId(){
		return publisherId;
	}
	public static final String USER_AGENT = "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2";
	public Map<String, String> currentCookies = new HashMap<String, String>();
	public boolean isLoggedIn=false;
	public OpdrListFragment opdrListFrgmt;
	private static Connection currConnection;
	public static Connection getConnection(){
		if(currConnection==null){
			currConnection=new Connection();
		}
		return currConnection;
	}
	public String Login() {
		String result=doPost("http://www.compudoc.be/index.php",
				"status=login&login_name=" + AppSettings.userName + "&login_password="
						+ AppSettings.password + "&submit=Login");
		isLoggedIn=result.contains("U bent nu ingelogd");
		return result;
	}

	public String doPost(String urlToRead, String params) {
		String response = "";
		try {
			response = doHttpStuff("POST", urlToRead, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public String doHttpStuff(String method, String urlToRead, String params)
			throws IOException {
		URL urlObj = new URL(urlToRead);
		EventSystem.publish(getPublisherId(),"Opening Connection ");

		HttpURLConnection httpCon = (HttpURLConnection) urlObj.openConnection();
		EventSystem.publish(getPublisherId(),"DONE Opening Connection ");

		httpCon.setRequestMethod(method);

		httpCon.setRequestProperty("User-Agent", USER_AGENT);
		httpCon.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		httpCon.setRequestProperty("Cookie", getCurrCookies());
		String urlParameters = params;

		// Send post request
		httpCon.setDoOutput(true);
		EventSystem.publish(getPublisherId(),"DONE setDoOutput(true) ");

		DataOutputStream wr = new DataOutputStream(httpCon.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();
		EventSystem.publish(getPublisherId(),"DONE writing data out) ");

//		int responseCode = httpCon.getResponseCode();
//		System.out.println("\nSending '" + method + "' request to URL : "
//				+ urlToRead);
//		System.out.println(method + " parameters : " + urlParameters);
//		System.out.println("Response Code : " + responseCode);
//
//		EventSystem.publish(getPublisherId(),"DONE getting response code ");
//
//		BufferedReader in = new BufferedReader(new InputStreamReader(
//				httpCon.getInputStream()));
//		httpCon.getResponseMessage();
//		String inputLine;
		StringBuilder response = new StringBuilder();
//		EventSystem.publish(getPublisherId(),"DONE setting bufferedReader");
//
//		while ((inputLine = in.readLine()) != null) {
//			response.append(inputLine);
//		}

//		in.close();

		Scanner scanner = new Scanner(httpCon.getInputStream(), "utf-8");
		try {
			while (scanner.hasNextLine()){
				response.append(scanner.nextLine());
			}
		}
		finally{
			scanner.close();
		}
		EventSystem.publish(getPublisherId(),"DONE reading lines");

		handleCookies(httpCon);
		EventSystem.publish(getPublisherId(),"----------------DONE handling cookies-------------------");


		return response.toString();
	}

	public void handleCookies(HttpURLConnection authCon) {
		// find the cookies in the response header from the first request
		List<String> cookies = authCon.getHeaderFields().get("Set-Cookie");
		if (cookies != null) {
			for (String cookie : cookies) {

				System.out.println("the whole cookie is: " + cookie);

				// only want the first part of the cookie header that has the
				// value
				String pair = cookie.split(";")[0];
				System.out.println("putting pair in cookies: " + pair);
				currentCookies.put(pair.split("=")[0], pair.split("=")[1]);
			}
		}
	}


	public String getCurrCookies() {
		String str = "";
		for (String key : currentCookies.keySet()) {
			if (!str.isEmpty()) {
				str += "; ";
			}
			str += key + "=" + currentCookies.get(key);
		}
		return str;
	}

	public String doGet(String urlToRead, String params) {
		String response = "";
		try {
			response = doHttpStuff("GET", urlToRead, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.d("","GOT RESPONSE HTML: "+response);
		return response;
	}

}
