/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.model;

import java.sql.Date;

/**
 *
 * @author Utilisateur
 */
public class Session {
    private final Date date;
    private final String horaire;
    private final int id;
    private final int movie;
    private final int nbr_places_max;
    private final int actual_place;
    private final double amount;
    
    public Session(int id,int movie,Date date,int max,int actual,String horaire,double amount)
    {
        this.movie=movie;
        this.id = id;
        this.date = date;
        this.nbr_places_max = max;
        this.actual_place = actual;
        this.horaire = horaire;
        this.amount = amount;
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
    
    public double getAmount(){
        return amount;
    }
    
    public String getHoraire(){
        return horaire;
    }
    
    public int getActual(){
        return actual_place;
    }
}
