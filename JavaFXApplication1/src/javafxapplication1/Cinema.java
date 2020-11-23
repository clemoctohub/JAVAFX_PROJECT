/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Utilisateur
 */
public class Cinema {
    private ArrayList<Movies> movies;
    final double PRIX = 8;
    private double regular,senior,children;
    private String description;

    public void setRegular(double regular) {
        this.regular = regular;
    }

    public void setSenior(double senior) {
        this.senior = senior;
    }

    public void setChildren(double children) {
        this.children = children;
    }
    public Cinema(){
        double[] promo = new double[3];
        try {
            Connexion nvx = new Connexion("movie", "root", "");
            promo = nvx.getReduc();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Cinema.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.senior = promo[1];
        this.children = promo[2];
        this.regular = promo[0];
        System.out.println(promo[0]+" "+senior);
    }
    
    public Cinema (ArrayList<Movies> movies,double senior,double children,double regular)
    {
        this.senior = senior;
        this.children = children;
        this.regular = regular;
    }
    
    public Movies getCorrectMovie(int id){
        for (Movies movie : movies) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return null;
    }
    
    public double prixTicket(Members mem,int i){
        double prix;
        prix = i*PRIX;
        if(mem.getAge()<18)
            prix = prix-prix*children;
        else if(mem.getAge()>60)
            prix = prix-prix*senior;
        else prix = prix-prix*regular;
       
        return prix;
    } 
    
    public double getPrix(){
        return PRIX;
    }
    
    public String getDescription(){
        return description = "  Benefits all year round!!\n"
                                   +"  Thanks to CinéPass, enjoy exclusive benefits\n"
                                   +"  and offers throughout the year.\n"
                                   +"  To make sure you don't miss out on anything,\n"
                                   +"  receive our communications by newsletter.\n"
                                   +"  Children : -"+(children*100)+"%    Regular : -"+(regular*100)+"%    Senior : -"+senior*100+"%";
    }
}
