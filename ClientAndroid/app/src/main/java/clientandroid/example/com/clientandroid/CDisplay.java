package clientandroid.example.com.clientandroid;

import android.widget.EditText;

import java.util.ArrayList;
import java.util.Map;

import fr.univtln.scaltot904.TP.tpJpa.Classes.CCompetition;
import fr.univtln.scaltot904.TP.tpJpa.Classes.CPlayer;
import fr.univtln.scaltot904.TP.tpJpa.Classes.CTeam;

/**
 * Created by Screetts on 31/03/2016.
 */
public class CDisplay {
    public static String Display(String requestType, String type, Map map, ArrayList entites) {
        String json = "";
        if (!requestType.equals("DEL")) {
            if (requestType.equals("ALL") | requestType.equals("GET-BY")) {
                if (type.equals("competition")) {
                    for (int i = 0; i < entites.size(); i++) {
                        CCompetition competition = (CCompetition) entites.get(i);
                        json = json.concat(competition.affiche() + "\n\n");
                    }
                }
                if (type.equals("team")) {
                    for (int i = 0; i < entites.size(); i++) {
                        CTeam team = (CTeam) entites.get(i);
                        json = json.concat(team.affiche() + "\n\n");
                    }
                }
                if (type.equals("player")) {
                    for (int i = 0; i < entites.size(); i++) {

                        CPlayer player = (CPlayer) entites.get(i);
                        json = json.concat(player.affiche() + "\n\n");
                    }
                }
            } else {
                if (type.equals("team")) {
                    json = json.concat("Nom       : " + map.get("nom") + "\n");
                    json = json.concat("id       : " + map.get("id") + "\n");
                }

                if (type.equals("competition")) {
                    json = json.concat("id       : " + map.get("id") + "\n");
                    json = json.concat("Nom       : " + map.get("nom") + "\n");
                    json = json.concat("Country  : " + map.get("country") + "\n");
                    json = json.concat("Nb team : " + map.get("nbteam") + "\n");
                }

                if (type.equals("player")) {
                    json = json.concat("id       : " + map.get("id") + "\n");
                    json = json.concat("Nom       : " + map.get("nom") + "\n");
                    json = json.concat("Prenom      : " + map.get("prenom") + "\n");
                    json = json.concat("Age      : " + map.get("age") + "\n");
                }
            }
        }
        return json;
    }

}
