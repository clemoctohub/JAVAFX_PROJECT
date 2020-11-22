/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

/**
 *
 * @author Utilisateur
 */
public class Movies {
    private final String title;
    private final String author;
    private final java.sql.Date date;
    private final int rate;
    private final int id;
    private final String type;
    private final int runningTime;
    private final String description;
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
