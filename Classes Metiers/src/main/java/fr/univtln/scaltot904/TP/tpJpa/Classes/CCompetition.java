package fr.univtln.scaltot904.TP.tpJpa.Classes;

import com.owlike.genson.annotation.JsonIgnore;
import com.owlike.genson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Screetts on 07/03/2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "CCompetition.findCompetitionAll", query = "select competition from CCompetition competition"),
        @NamedQuery(name = CCompetition.COMPETITION_BY_NAME, query = "select competition from CCompetition competition where competition.name = :Pnom"),
        @NamedQuery(name = CCompetition.COMPETITION_DELETE, query = "delete from CCompetition competition where competition.name = :Pnom")
})
public class CCompetition {
    @TableGenerator(name = "yourTableGenerator",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="yourTableGenerator")
    private int id;

    @JsonIgnore
    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL)
    private List<CTeam> teamList = new ArrayList<CTeam>();

    public static final String COMPETITION_BY_NAME = "CCompetition.findCompetitionByName";
    public static final String COMPETITION_DELETE = "CCompetition.Delete";

    private String name;
    private String country;
    private ETypeCompetition typeCompet ;
    private int nbteam;

    public CCompetition() {
    }

    public CCompetition(String name) {
        this.name = name;
    }

    public CCompetition(String country, String name, int nbteam) {
        this.country = country;
        this.name = name;
        this.nbteam = nbteam;
    }

    public CCompetition(String name, int nbteam) {
        this.nbteam = nbteam;
        this.name = name;
    }

    public CCompetition(String name, List<CTeam> teamList) {
        this.name = name;
        this.teamList = teamList;
    }

    public CCompetition(String name, String country, ETypeCompetition type, List<CTeam> teamList, int nbteam) {
        this.name = name;
        this.country = country;
        this.typeCompet = type;
        this.teamList = teamList;
        this.nbteam = nbteam;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ETypeCompetition getType() {
        return typeCompet;
    }

    public void setType(ETypeCompetition type) {
        this.typeCompet = type;
    }

    public int getNbteam() {
        return nbteam;
    }

    public void setNbteam(int nbteam) {
        this.nbteam = nbteam;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<CTeam> getTeamList() {
        return teamList;
    }

    @JsonProperty
    public void setTeamList(List<CTeam> teamList) {
        this.teamList = teamList;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void adTeam(CTeam cTeam) {
        this.teamList.add(cTeam);
    }

    @Override
    public String toString() {
        return "CCompetition{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teamList=" + teamList +
                ", country='" + country + '\'' +
                '}';
    }
    public String affiche(){
        return "Id : " + id + "\nNom : " + name + "\nPays : " + country + "\nNbteam : " + nbteam;
    }
}
