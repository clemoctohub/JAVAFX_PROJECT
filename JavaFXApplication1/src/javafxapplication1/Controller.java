/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author clemf
 */
public class Controller {
    private String request;
    private String location;
    
    public Controller(String request,String location){
        this.request = request;
        this.location = location;
    }
    
    public void getAllMovies() throws SQLException, ClassNotFoundException, ParseException{
        Connexion nvx = new Connexion("movie", "root", "");
        //nvx.getAllMovies();
    }
    
    public void searchMovies(String name,String type,Date date,int time) throws SQLException, ClassNotFoundException, ParseException{
       Connexion nvx = new Connexion("movie", "root", "");
       //nvx.searchMovies(name,type,date,time)
    }
}
