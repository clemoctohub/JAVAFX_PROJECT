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
    private String horaire;
    private int id;
    private int movie;
    private int nbr_places_max;
    private int actual_place;
    private double amount;
    
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
