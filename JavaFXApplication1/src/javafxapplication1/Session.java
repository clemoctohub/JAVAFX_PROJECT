/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.Date;

/**
 *
 * @author Utilisateur
 */
public class Session {
    private Date date;
    private Horaire horaire;
    private int id;
    private int movie;
    private int nbr_places_max;
    
    public Session(int id,int movie,Date date,int max)
    {
        this.movie=movie;
        this.id = id;
        this.date = date;
        this.nbr_places_max = max;
    }
    
    public int getMovie(){
        return movie;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public int getNbr_places_max() {
        return nbr_places_max;
    }
}
