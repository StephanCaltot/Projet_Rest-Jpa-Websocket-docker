package clientandroid.example.com.clientandroid;


import android.annotation.TargetApi;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.univtln.scaltot904.TP.tpJpa.Classes.CCompetition;
import fr.univtln.scaltot904.TP.tpJpa.Classes.CPlayer;
import fr.univtln.scaltot904.TP.tpJpa.Classes.CTeam;

/**
 * Created by Screetts on 25/03/2016.
 */
public class CJsonDecoder {

    public static Map JSONDecoder(URL url, String type) {
        Map map = new HashMap();
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            String result = InputStreamOperations.InputStreamToString(inputStream);
            JSONObject jsonObject = new JSONObject(result);

            if (type.equals("competition")) {
                String name = jsonObject.getString("name");
                String country = jsonObject.getString("country");
                int nbteam = jsonObject.getInt("nbteam");
                map.put("nom", name);
                map.put("nbteam", nbteam);
                map.put("country", country);
                map.put("id",jsonObject.getInt("id"));
            }
            if (type.equals("team")){
                JSONObject jsonObject2 = jsonObject.getJSONObject("competition");
                String name = jsonObject.getString("nom");
                map.put("nom", name);
                map.put("id",jsonObject.getInt("id"));
                map.put("competition",jsonObject2);
            }
            if (type.equals("player")){
                JSONObject jsonObject2 = jsonObject.getJSONObject("team");
                String name = jsonObject.getString("nom");
                map.put("nom", name);
                map.put("prenom", jsonObject.getString("prenom"));
                map.put("age",jsonObject.getInt("age"));
                map.put("id",jsonObject.getInt("id"));
                map.put("team",jsonObject2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static String JSONEncoder (String requestType, String type, Integer id, String name, String country, String nbteam, JSONObject competition, JSONObject team) throws JSONException {
        String body = "";

        JSONObject jsonObject = new JSONObject();

        if (requestType.equals("PUT"))
            {
                jsonObject.put("id", id);
            }

        if(type.equals("competition"))
            {
                jsonObject.put("name", name);
                jsonObject.put("country", country);
                jsonObject.put("nbteam", nbteam);
            }

        if (type.equals("team"))
            {
                jsonObject.put("competition", competition);
                jsonObject.put("nom", name);
            }

        if (type.equals("player"))
            {
                jsonObject.put("team", team);
                jsonObject.put("nom", name);
                jsonObject.put("age",nbteam);
                jsonObject.put("prenom",country);
            }

        body = jsonObject.toString();

        return body;
    }

    //@TargetApi(Build.VERSION_CODES.KITKAT)
    public static ArrayList JSONDecoderList(URL url, String type) {
        ArrayList entites = new ArrayList();
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();

            String result = InputStreamOperations.InputStreamToString(inputStream);

            JSONArray array = new JSONArray(result);

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = new JSONObject(array.getString(i));

                if (type.equals("competition")) {
                    CCompetition competition = new CCompetition();
                    competition.setId(obj.getInt("id"));
                    competition.setName(obj.getString("name"));
                    competition.setCountry(obj.getString("country"));
                    competition.setNbteam(obj.getInt("nbteam"));
                    entites.add(competition);
                }

                if (type.equals("team")) {
                    CTeam team = new CTeam();
                    team.setNom(obj.getString("nom"));
                    team.setId(obj.getInt("id"));
                    entites.add(team);
                }

                if (type.equals("player")) {
                    CPlayer player = new CPlayer();
                    player.setId(obj.getInt("id"));
                    player.setNom(obj.getString("nom"));
                    player.setPrenom(obj.getString("prenom"));
                    player.setAge(obj.getInt("age"));
                    entites.add(player);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entites;
    }
}