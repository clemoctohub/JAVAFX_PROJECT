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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public int addCustomerToSession(String num, String crypto, String mv, int id){
        int x=0;
        for(int i=0;i<num.length();i++){
            if(num.charAt(i) < '0' || num.charAt(i)> '9')
                return -1;
        }
        for(int i=0;i<crypto.length();i++){
            if(crypto.charAt(i) < '0' || crypto.charAt(i)> '9' || crypto.length()>3)
                return -1;
        }
        if(mv.length()>5 || mv.charAt(2)!='/')
            return -1;
        try {
            Connexion conn = new Connexion("movie", "root", "");
            ArrayList<Members> nvx = conn.recolterChampsCustomer();
            for(int i=0;i<nvx.size();i++){
                if(x==nvx.get(i).getId()){
                    x++;
                    i=-1;
                }
            }
            conn.insert_customer(id, x);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            System.out.println("errorroorr");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return x;
    }
    
    public ArrayList<Session> getSessionMovie(int id) throws SQLException, ClassNotFoundException, ParseException{
        ArrayList<Session> nvx = new ArrayList<>();
        Connexion conn = new Connexion("movie", "root", "");
        nvx = conn.recolterChampsSessionsMovie(id);
        return nvx;
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
    
    public int createMember(String firstName,String lastName, String age, String pwd) throws SQLException, ClassNotFoundException, ParseException{
        
        if(firstName==null || firstName.equals("") || lastName==null || lastName.equals(""))
            return -1;
        
        int ag = Integer.parseInt(age);
        if(ag<0)
            return -1;
        int condi = 0;
        Connexion nvx = new Connexion("movie", "root", "");
        ArrayList<Members> membre = nvx.recolterChampsMember();
        for(int i=0;i<membre.size();i++){
            System.out.println(membre.get(i).getLogin());
            System.out.println(firstName+Integer.toString(condi)+"."+lastName);
            
            if(condi==0){
                if(membre.get(i).getLogin().equals(firstName+"."+lastName)){
                    condi++;
                    i=-1;
                    
                }
            }
                
            else{
                if(membre.get(i).getLogin().equals(firstName+Integer.toString(condi)+"."+lastName)){
                    System.out.println("jentre");
                    condi++;
                    i=-1;
                }
            }
                
        }
        System.out.println("condi = "+condi);
        if(condi==0)
            nvx.insert_member(firstName+"."+lastName, pwd, firstName, lastName, ag);
        else
            nvx.insert_member(firstName+Integer.toString(condi)+"."+lastName, pwd, firstName, lastName, ag);
        
        return condi;
    }

}
