/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    
    public ArrayList<Movies> searchMovies(String name,String type,String time) throws SQLException, ClassNotFoundException, ParseException{
        Connexion nvx = new Connexion("movie", "root", "");
        ArrayList<Movies> rep = new ArrayList<Movies>();
        if(name!=null)
            name = name.toLowerCase();
        if(type!=null)
            type = type.toLowerCase();
        System.out.println(name+" "+type+" "+time);
       
        int temps;
        if(time.equals(""))
            temps=0;
        else{
            temps = Integer.parseInt(time);
            if(temps<0)
                temps = 0;
        }
       
        rep = nvx.searchMovie(name,type,temps);
       
        System.out.println(rep.get(0).getTitle());
        return rep;
    }
    
    public Members getConnectedMember(String id, String pwd) throws SQLException, ClassNotFoundException, ParseException{
        Members test;
        Connexion nvx = new Connexion("movie", "root", "");
        test = nvx.checkLoginMember(id,pwd);
        
        return test;
    }
    
    public Employees getConnectedEmployee(String id, String pwd) throws SQLException, ClassNotFoundException, ParseException{
        Employees test;
        Connexion nvx = new Connexion("movie", "root", "");
        test = nvx.checkLoginEmployee(id, pwd);
        
        return test;
    }
    
    public boolean createMember(String firstName,String lastName, String age, String pwd) throws SQLException, ClassNotFoundException, ParseException{
        
        if(firstName==null || firstName.equals("") || lastName==null || lastName.equals(""))
            return false;
        
        int ag = Integer.parseInt(age);
        if(ag<0)
            return false;
        
        Connexion nvx = new Connexion("movie", "root", "");
        nvx.insert_member(firstName+"."+lastName, pwd, firstName, lastName, ag);
        
        return true;
    }

}
