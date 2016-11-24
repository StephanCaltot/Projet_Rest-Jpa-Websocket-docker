package clientandroid.example.com.clientandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Map;

import fr.univtln.scaltot904.TP.tpJpa.Classes.CCompetition;

public class SpecialActivity extends AppCompatActivity {
    private JSONObject competition;
    private JSONObject team;
    private TextView textView;
    private TextView textViewint;
    private TextView textViewstring;
    private EditText editTextget;
    private EditText editname;
    private EditText editcountry;
    private EditText editnbteam;
    private String requestType = "";
    private String type = "";
    private String name = "";
    private String country = "";
    private String nbteam = "";
    private Spinner spinnerType2;
    private Integer id = 0;
    private Map map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.special_activity);

        textView = (TextView) findViewById(R.id.textView12);
        textViewint = (TextView) findViewById(R.id.textView6);
        textViewstring = (TextView) findViewById(R.id.textView7);
        editname = (EditText) findViewById(R.id.editText5);
        editcountry = (EditText) findViewById(R.id.editText4);
        editTextget = (EditText) findViewById(R.id.editText7);
        editnbteam = (EditText) findViewById(R.id.editText3);

        spinnerType2 = (Spinner) findViewById(R.id.spinner2);
        String[] types = {"competition","team","player"};
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,types);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType2.setAdapter(dataAdapterR);

        spinnerType2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String TYPE = String.valueOf(spinnerType2.getSelectedItem());
                Toast.makeText(SpecialActivity.this, TYPE + " selected",
                        Toast.LENGTH_SHORT).show();
                type = TYPE;
                if (type.equals("competition")){
                    textViewint.setText("Nb team    :");
                    textViewstring.setText("Country     :");
                }
                if (type.equals("team")){
                    textViewint.setText("                    ");
                    textViewstring.setText("                   ");
                }
                if (type.equals("player")){
                    textViewint.setText("Age           :");
                    textViewstring.setText("Prenom    :");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void maj_get()
        {
            if (type.equals("competition"))
                {
                    editnbteam.setEnabled(true);
                    editcountry.setEnabled(true);

                    editname.setText((String) map.get("nom"));
                    editcountry.setText((String) map.get("country"));
                    editnbteam.setText(String.valueOf(map.get("nbteam")));

                    name = editname.getText().toString();
                    country = editcountry.getText().toString();
                    nbteam = editnbteam.getText().toString();
                }

            if (type.equals("team"))
                {
                    editname.setText((String) map.get("nom"));
                    name = editname.getText().toString();

                    editcountry.setText("");
                    editnbteam.setText("");

                    editcountry.setEnabled(false);
                    editnbteam.setEnabled(false);
                }

            if (type.equals("player"))
                {
                    editname.setText((String) map.get("nom"));
                    name = editname.getText().toString();


                    editname.setText((String) map.get("nom"));
                    editcountry.setText((String) map.get("prenom"));
                    editnbteam.setText(String.valueOf(map.get("age")));

                    name = editname.getText().toString();
                    country = editcountry.getText().toString();
                    nbteam = editnbteam.getText().toString();

                    editcountry.setEnabled(true);
                    editnbteam.setEnabled(true);
                }
        }


    public void maj_put()
        {
            if (type.equals("competition")) {
                name = editname.getText().toString();
                country = editcountry.getText().toString();
                nbteam = editnbteam.getText().toString();
            }
            if (type.equals("team") | type.equals("player")){
                name = editname.getText().toString();
            }

            if (type.equals("player")) {
                name = editname.getText().toString();
                country = editcountry.getText().toString();
                nbteam = editnbteam.getText().toString();
            }
       }


    public void put(View view)
        {
            if (!editname.getText().toString().equals(""))
                {
                    requestType = "PUT";
                    type = String.valueOf(spinnerType2.getSelectedItem());
                    String urlString = MainActivity.url + type;
                    new CallAPI().execute(urlString);
                }
        }

    public void get2(View view)
        {
            if (!editTextget.getText().toString().equals(""))
                {
                    requestType = "GET";
                    String name_get = editTextget.getText().toString();
                    type = String.valueOf(spinnerType2.getSelectedItem());
                    String urlString = MainActivity.url + type + "/" + name_get;
                    new CallAPI().execute(urlString);
                }
        }

    public void post(View view)
        {
            if (!editname.getText().toString().equals(""))
                {
                    requestType = "POST";
                    type = String.valueOf(spinnerType2.getSelectedItem());
                    String urlString = MainActivity.url + type;
                    name = editname.getText().toString();
                    country = editcountry.getText().toString();
                    nbteam = editnbteam.getText().toString();
                    new CallAPI().execute(urlString);
                }
        }

    private class CallAPI extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            String json = "";
            URL url = null;
            try {
                url = new URL(urlString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            System.out.println(urlString);
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (requestType.equals("GET"))
                {
                    map = CJsonDecoder.JSONDecoder(url, type);
                    id = (Integer) map.get("id");
                    if (type.equals("team")){
                        competition = (JSONObject) map.get("competition");}

                    if (type.equals("player")){
                        team = (JSONObject) map.get("team");}

                    json = "Got";
                }

            if (requestType.equals("PUT") | requestType.equals("POST")) {

                try {
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoInput(true);
                    urlConnection.setDoOutput(true);

                    json = "Posted";
                    if (requestType.equals("PUT"))
                        {
                            json = "Puted";
                            urlConnection.setRequestMethod("PUT");
                            urlConnection.setDoInput(true);
                            urlConnection.setDoOutput(true);
                            maj_put();
                        }
                    String body = CJsonDecoder.JSONEncoder(requestType, type, id, name, country, nbteam, competition, team);
                    OutputStream output = new BufferedOutputStream(urlConnection.getOutputStream());
                    output.write(body.getBytes());
                    output.flush();
                    urlConnection.getResponseCode();

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e){
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    urlConnection.disconnect();
                }
            }
            return json;
        }

        @Override
        protected void onPostExecute(String result)
            {
                textView.setText(result);
                if (requestType.equals("GET"))
                    maj_get();
                if(requestType.equals("PUT"))
                    maj_put();
            }
    }
}