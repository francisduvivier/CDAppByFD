package duviwin.compudocapp.OpdrachtDetails;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import duviwin.compudocapp.AppSettings;
import duviwin.compudocapp.R;
import duviwin.compudocapp.SettingsActivity;


public class ShowDetailsActivity extends ActionBarActivity {
    DetailedOpdracht opdracht = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSettings.refreshPrefs(getBaseContext());
        setContentView(R.layout.activity_show_details);
        Intent intent = getIntent();
        opdracht = (DetailedOpdracht) intent.getSerializableExtra("opdracht");
        if (opdracht == null) {
            //In this case this is a call from an URI
            String url = intent.getData().toString();
            opdracht = new DetailedOpdracht(Integer.parseInt(url.replaceAll(".*opdrachtnr=([\\d]*).*", "$1")));
        }

        class GetExtraInfoTask extends AsyncTask<Object, Void, DetailedOpdracht> {
            @Override
            protected DetailedOpdracht doInBackground(Object... params) {
                opdracht.getExtraInfo();
                return opdracht;
            }

            @Override
            protected void onPostExecute(DetailedOpdracht result) {
                ShowDetailsActivity.this.update(result);
            }
        }
        GetExtraInfoTask task = new GetExtraInfoTask();
        task.execute();
    }

    public void update(DetailedOpdracht opdr) {
        int i = 0;
        for (String prop : opdr.allProperties) {
            try {
                TextView tv = ((TextView) findViewById(DetailedOpdracht.getPropertyId(i++)));
                tv.setPadding(0, 5, 0, 5);
                tv.setText(prop);
//                ((ViewGroup.MarginLayoutParams) tv.getLayoutParams()).setMargins(0, 5, 0, 5);
            } catch (HasNoPropIdException e) {
                //do nothing
            }
        }

        try {
            ((TextView) findViewById(DetailedOpdracht.getPropertyId("huidigbod"))).setText(opdr.getProperty("huidigbod").replace("&euro;", "€").replace(". ,", ".").replace("\n ", "\n"));
            findViewById(DetailedOpdracht.getPropertyId("klantnr")).setBackgroundColor(opdr.getKlantNrKleur());
            findViewById(DetailedOpdracht.getPropertyId("gepost")).setBackgroundColor(opdr.getTijdKleur());
            ((LinearLayout) findViewById(DetailedOpdracht.getPropertyId("straat")).getParent().getParent()).setBackgroundColor(opdr.getAdresKleur());
            findViewById(DetailedOpdracht.getPropertyId("omschrijving")).setBackgroundColor(opdr.getOmschrijvingKleur());
            ((TextView) findViewById(R.id.det_bod_result)).setText(opdr.bodResult);
            if (opdr.biedenIsAfgelopen()) {
                ((LinearLayout) findViewById(R.id.enkel_voor_open)).removeAllViews();

                addOptionalInfo();



                if (opdr.isGewonnenDoorGebruiker()) {
                    List<String> telNrs = opdr.getTelNrs();
                    for (String telNr : telNrs) {
                        LayoutInflater li = getLayoutInflater();
                        View callerFragmentView = li.inflate(R.layout.fragment_caller, null);
                        ((LinearLayout) findViewById(R.id.enkel_voor_open)).addView(callerFragmentView);
                        TextView callText = (TextView) callerFragmentView.findViewById(R.id.telnr);
                        callText.setText(telNr);

//                    We set the tag of the parent to the telnr so that it can be used by the onclick in the Parent, see showDetailsActivity.bellen(...)
                        ((LinearLayout) callText.getParent()).setTag(telNr);
                        Log.d("CallClick", "set tel: " + telNr);


                    }
                }
            }
        } catch (HasNoPropIdException e) {
            throw new RuntimeException(e.getMessage() + " ,getPropertyId(\"gepost\") or getPropertyId(\"huidigbod\") seems to have thrown a HasNoPropIdException, this is very weird.");
        }


    }

    private void addOptionalInfo() {
        for(int i=0;i<opdracht.opInfoItem.length;i++){
            LayoutInflater li = getLayoutInflater();
            View opInfoView = li.inflate(R.layout.frament_optional_details, null);
            ((LinearLayout) findViewById(R.id.enkel_voor_open)).addView(opInfoView);
            TextView callText = (TextView) opInfoView.findViewById(R.id.optional_info_item);
            callText.setText(opdracht.opInfoItem[i]);
            TextView announcer = (TextView) opInfoView.findViewById(R.id.optional_info_text);
            announcer.setText(DetailedOpdracht.optInfoNames[i]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:
                Intent showSettings = new Intent(this, SettingsActivity.class);
                startActivity(showSettings);
                return true;
            case R.id.action_refresh:
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void bieden(View view) {
        EditText minBodView = (EditText) findViewById(R.id.min_bod);
        EditText maxBodView = (EditText) findViewById(R.id.max_bod);
        int minBod = Integer.parseInt(minBodView.getText().toString());
        int maxBod = Integer.parseInt(maxBodView.getText().toString());

        opdracht.bied(minBod, maxBod, this);
    }

    public void openMaps(View view) {
        String straat = opdracht.getProperty("straat").replaceAll(".*Adres: ", "").replaceAll(" bus \\d*+", "");
        String destinationString = straat + ", "
                + opdracht.getProperty("stad") + " " + opdracht.getProperty("postcode");
        String startString = AppSettings.defaultLoc;

        Uri geoLocation = Uri.parse("http://maps.google.com/maps?daddr=" + destinationString + "&saddr=" + startString);

        launchActivity(geoLocation);
    }

    public void bellen(View view) {
        String telnr = ((String) view.getTag());
        Uri uri = Uri.parse("tel:" + telnr.replaceAll("[/ ]", ""));
        Log.d("CallClick", "tel: " + telnr);
        Log.d("CallClick", "uri: " + uri.toString());
        launchActivity(uri);

    }

    private void launchActivity(Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
