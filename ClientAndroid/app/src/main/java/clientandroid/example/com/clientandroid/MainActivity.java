package clientandroid.example.com.clientandroid;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editget;
    private EditText edit8;
    private EditText edit_get_by;
    private TextView textView;
    private String requestType;
    private String type;
    private Map map;
    private Spinner spinnerType;
    private ArrayList entites;
    private String messageToSend;
    private WebSocketClient mWebSocketClient = null;
    // ip tel : 192.168.137.135    ip fac : 10.42.6.235      maison : 192.168.1.12
    public final static String ip = "192.168.137.135";
    public final static String port = "9998";
    public final static String url = "http://" + ip + ":" + port + "/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connectWebSocket();

        Button special_button;
        textView = (TextView) findViewById(R.id.textView);
        textView.setMovementMethod(new ScrollingMovementMethod());
        editget = (EditText) findViewById(R.id.edit_text_Get);
        edit_get_by = (EditText) findViewById(R.id.editText6);
        special_button = (Button) findViewById(R.id.special_button);
        edit8 = (EditText) findViewById(R.id.editText8);
        special_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SpecialActivity.class);
                startActivity(intent);
            }
        });

        spinnerType = (Spinner) findViewById(R.id.spinnerRegion);
        final String[] types = {"competition","team","player"};
        ArrayAdapter<String> dataAdapterR = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,types);
        dataAdapterR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(dataAdapterR);


        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String TYPE = String.valueOf(spinnerType.getSelectedItem());
                Toast.makeText(MainActivity.this,TYPE + " selected",
                        Toast.LENGTH_SHORT).show();
                type = TYPE;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void affiche()
    {
        String json = CDisplay.Display(requestType, type, map, entites);
        textView.setText(json);
    }


    public void get(View view) {
        String nom = editget.getText().toString();
        if (!nom.equals("") && !nom.isEmpty()) {
            requestType = "GET";
            type = String.valueOf(spinnerType.getSelectedItem());
            String urlString = url + type + "/" + nom;
            new CallAPI().execute(urlString);
        }
        else
            textView.setText("\n\nLa case NAME est vide");
    }

    public void getBy(View view)
    {
        String getby = edit_get_by.getText().toString() ;

        if (!getby.equals(""))
        {
            requestType = "GET-BY";
            type = String.valueOf(spinnerType.getSelectedItem());
            String urlString = url + type + "/" + type + "s" + "/" + getby;
            new CallAPI().execute(urlString);
        }
        else
            textView.setText("\n\nLa case GET-BY est vide");
    }

    public void all(View view){
        type = String.valueOf(spinnerType.getSelectedItem());
        entites = new ArrayList();

        String urlString = url + type + "/" + type + "s";
        requestType = "ALL";
        new CallAPI().execute(urlString);
        //affiche();
    }

    public void del(View view){
        String id = edit8.getText().toString();
        type = String.valueOf(spinnerType.getSelectedItem());
        if (!id.equals("") && !id.isEmpty()){
            requestType = "DEL";
            String urlString = url + type + "/" + id;
            new CallAPI().execute(urlString);
        }
        else
            textView.setText("\n\nLa case ID DEL est vide");
    }

    private class CallAPI extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            String urlString = params[0];
            String result = "";
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

            if (requestType.equals("ALL") | requestType.equals("GET-BY")){

                entites = CJsonDecoder.JSONDecoderList(url, type);
                //Log.i("Websocket", " get all selected and done");
                //messageToSend = "I've got some "+ type ;
            }

            if (requestType.equals("GET")) {
                try {

                    map = CJsonDecoder.JSONDecoder(url, type);

                } catch (Exception e) {
                }
            }

            if (requestType.equals("DEL")) {
                try {
                    urlConnection.setRequestMethod("DELETE");
                    urlConnection.getInputStream();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                result = "\n\n\n         Deleted";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            affiche();
            if (requestType.equals("DEL"))
                textView.setText(result);
            //sendMessage();
        }
    }



    private void connectWebSocket() {
        URI uri;
        try {
            uri = new URI("ws://192.168.1.15:8025" + "/echo");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Opened");
                mWebSocketClient.send("Hello from here" + Build.MANUFACTURER + " " + Build.MODEL);
            }

            @Override
            public void onMessage(String s) {
                final String message = s;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(textView.getText() + "\n" + message);
                    }
                });
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };
        mWebSocketClient.connect();
    }


    public void sendMessage() {
        System.out.println("Message envoyé depuis le telephone -> " + messageToSend);
        mWebSocketClient.send(messageToSend);
    }


}