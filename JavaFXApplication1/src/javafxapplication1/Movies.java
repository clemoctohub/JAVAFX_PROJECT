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
public class Movies {
    private String title;
    private String author;
    private java.sql.Date date;
    private int rate;
    private int id;
    private String type;
    private int runningTime;
    private String description;
    //private ArrayList<Session> sessions;
    
    public Movies(String title, String author,java.sql.Date date, int rate, String type, int runningTime, int id, String description)
    {
        this.title = title;
        this.author = author;
        this.date = date;
        this.id = id;
        this.rate = rate;
        this.type = type;
        this.runningTime = runningTime;
        this.description = description;
    }
    
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public java.sql.Date getDate() {
        return date;
    }

    public int getRate() {
        return rate;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public int getRunningTime() {
        return runningTime;
    }
    
    public String getDescription(){
        return description;
    }
}
