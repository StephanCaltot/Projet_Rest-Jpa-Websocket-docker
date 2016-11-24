package fr.univtln.scaltot904.TP.tpJpa.Classes;

import com.owlike.genson.annotation.JsonIgnore;
import com.owlike.genson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * Created by Screetts on 07/03/2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "CPlayer.findPlayerAll", query = "select player from CPlayer player"),
        @NamedQuery(name = CPlayer.PLAYER_BY_NOM, query = "select player from CPlayer player where player.nom = :Pnom"),
        @NamedQuery(name = CPlayer.PLAYER_BY_TEAM, query = "select player from CPlayer player where player.team.nom = :Pnom")
            })
public class CPlayer extends CPerson{

    private boolean starter ;
    private EPoste position ;
    private int skill ;
    private int numero ;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "Team")
    private CTeam team ;

    public static final String PLAYER_BY_NOM = "CPlayer.findPlayerByNom";
    public static final String PLAYER_BY_TEAM = "CPlayer.findPlayerByTeam";


    public CPlayer (){
        super();
    }

    public CPlayer(CTeam team) {
        this.team = team;
    }

    public CPlayer(String nom, CTeam team) {
        super(nom);
        this.team = team;
    }

    public CPlayer(boolean starter, EPoste position, int skill, int numero, CTeam team) {
        this.starter = starter;
        this.position = position;
        this.skill = skill;
        this.numero = numero;
        this.team = team;
    }




    public boolean isStarter() {
        return starter;
    }

    public void setStarter(boolean starter) {
        this.starter = starter;
    }

    public EPoste getPosition() {
        return position;
    }

    public void setPosition(EPoste position) {
        this.position = position;
    }

    public int getSkill() {
        return skill;
    }

    public void setSkill(int skill) {
        this.skill = skill;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public CTeam getTeam() {
        return team;
    }

    public void setTeam(CTeam team) {
        this.team = team;
    }

    public String affiche(){
        return "Nom : " + getNom() + "   Prenom : " + getPrenom() + "  Age : " + getAge();

    }

}
