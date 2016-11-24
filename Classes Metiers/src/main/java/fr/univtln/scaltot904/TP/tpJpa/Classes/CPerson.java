package fr.univtln.scaltot904.TP.tpJpa.Classes;

import javax.annotation.Generated;
import javax.persistence.*;

/**
 * Created by scaltot904 on 22/02/16.
 */


@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQueries(@NamedQuery(name = "CPerson.findPersonAll", query = "select person from CPerson person"))
public class CPerson {
    @TableGenerator(name = "yourTableGenerator",allocationSize = 1, initialValue = 1)
    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="yourTableGenerator")
    private int id;

    private String nom ;
    private String prenom;
    private int age;

    public CPerson() {
    }

    public CPerson(String nom) {
        this.nom = nom;
    }

    public CPerson(String nom, String prenom, int age) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
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

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(int id) {
        this.id = id;
    }
}

