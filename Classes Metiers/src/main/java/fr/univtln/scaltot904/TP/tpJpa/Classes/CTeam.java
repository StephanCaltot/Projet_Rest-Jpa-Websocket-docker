package fr.univtln.scaltot904.TP.tpJpa.Classes;

import com.owlike.genson.annotation.JsonIgnore;
import com.owlike.genson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scaltot904 on 22/02/16.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "CTeam.findTeamAll", query = "select team from CTeam team"),
        @NamedQuery(name = CTeam.TEAM_BY_COMP, query = "select team from CTeam team where team.competition.name = :Pnom_competition"),
        @NamedQuery(name = CTeam.TEAM_BY_NAME, query = "select team from CTeam team where team.nom = :Pnom")
              })

public class CTeam {
    @TableGenerator(name = "yourTableGenerator",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="yourTableGenerator")
    @Column(name="team_id")
    private int id;
    private String nom;

    @JsonIgnore
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<CPlayer> playersList = new ArrayList<CPlayer>();

    //@JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "competition_id")
    private CCompetition competition;

    public static final String TEAM_BY_COMP = "CTeam.findTeamByComp";
    public static final String TEAM_BY_NAME = "CTeam.findTeamByName";

    public CTeam() {
    }

    public CTeam(CCompetition competition, String nom) {
        this.competition = competition;
        this.nom = nom;
    }

    public CTeam(String nom, List<CPlayer> playersList, CCompetition competition) {
        this.nom = nom;
        this.playersList = playersList;
        this.competition = competition;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @JsonIgnore
    public List<CPlayer> getPlayersList() {
        return playersList;
    }

    @JsonProperty
    public void setPlayersList(List<CPlayer> playersList) {
        this.playersList = playersList;
    }

    public void adPlayer(CPlayer cPlayer) {
        playersList.add(cPlayer);
    }

    //@JsonIgnore
    public CCompetition getCompetition() {
        return competition;
    }

    //@JsonProperty
    public void setCompetition(CCompetition competition) {
        this.competition = competition;
    }

    @Override
    public String toString() {
        return "CTeam{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", playersList=" + playersList +
                '}';
    }

    public String affiche(){
        return "Nom : " + nom + "  Id : " + id ;
    }

}
