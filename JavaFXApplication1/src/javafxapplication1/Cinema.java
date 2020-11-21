/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.util.ArrayList;

/**
 *
 * @author Utilisateur
 */
public class Cinema {
    private ArrayList<Movies> movies;
    final double PRIX = 8;
    private double regular,senior,children;

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
        this.senior = 0.1;
        this.children = 0.2;
        this.regular = 0.05;
    }
    
    public Cinema (ArrayList<Movies> movies)
    {
        this.senior = 0.2;
        this.children = 0.3;
        this.regular = 0.1;
        this.movies = movies;
    }
    
    public Movies getCorrectMovie(int id){
        for(int i=0;i<movies.size();i++){
            if(movies.get(i).getId()==id){
                return movies.get(i);
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
}
