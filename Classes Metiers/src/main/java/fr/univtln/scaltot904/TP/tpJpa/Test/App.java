package fr.univtln.scaltot904.TP.tpJpa.Test;

import fr.univtln.scaltot904.TP.tpJpa.Classes.*;
import fr.univtln.scaltot904.TP.tpJpa.DAO.CCrudServiceBean;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scaltot904 on 22/02/16.
 */
public class App {
    public static void main(String[] args) {

        CCrudServiceBean<CTeam> crudTeam = new CCrudServiceBean<CTeam>();
        CCrudServiceBean<CCompetition> crudCompet = new CCrudServiceBean<CCompetition>();
        CCrudServiceBean<CPlayer> crudPlayer = new CCrudServiceBean<CPlayer>();

        CCompetition competition3 = new CCompetition("Italie","Serie A",20);
        CCompetition competition2 = new CCompetition("Allemagne","Bundesliga",20);
        CCompetition competition = new CCompetition("Espagne","Liga",20);

        CTeam team = new CTeam(competition, "FC Barcelone");
        CTeam team2 = new CTeam(competition, "Real Madrid");
        CTeam team3 = new CTeam(competition3, "Juventus");

        CPlayer player = new CPlayer("Messi",team);
        team.adPlayer(player);
        competition.adTeam(team);
        competition.adTeam(team2);
        competition3.adTeam(team3);

        //crudTeam.create(team2);
        //crudCompet.create(competition3);
        //crudPlayer.create(player);
    }

}
