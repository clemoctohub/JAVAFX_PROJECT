/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafxapplication1.model.Connexion;
import javafxapplication1.model.Employees;
import javafxapplication1.model.Members;
import javafxapplication1.model.Movies;
import javafxapplication1.model.Sessions;

/**
 *
 * @author clemf
 */
public class Controller {
    private final String request;
    private final String location;
    private final String password_ddb = "root";
    
    public Controller(String request,String location){
        this.request = request;
        this.location = location;
    }  
        
    public String getAMovie(int id){
        String nom="";
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            nom = conn.recolterSpecifikMovie(id);
            conn.closeConn();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nom;
    }
    
    public ArrayList<Sessions> getSessionConnected(String login){
        ArrayList<Integer> nvx;
        ArrayList<Sessions> other = new ArrayList<>();
        
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            nvx = conn.getSessionConnected(login);
            other = conn.recolterSessionMember(nvx);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return other;
    }
    
    public ArrayList<Integer> getIdCustomerSess(String login){
        ArrayList<Integer> nvx = new ArrayList<>();
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            nvx = conn.getSessionConnectedID(login);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nvx;
    }
    
    
    public void delete_movie(int id){
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.delete_movie(id);
            
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void update_movie(Movies movie){
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.changeAll_(movie);           
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public void addmovie(Movies movie){
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.add_movie(movie.getTitle(),movie.getAuthor(),movie.getDate(),movie.getRate(),movie.getType(),movie.getRunningTime(),movie.getId(),movie.getDescription());           
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean delete_session_customer(String id,String mail){
        int num;
        boolean condi=false;
        int num_sess;
        try{
            num = Integer.parseInt(id);
        }
        catch(NumberFormatException e){
            return false;
        }
        
        if(num<0)
            return false;
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            num_sess = conn.getMovieFromCust(num,mail);
            condi = conn.delete_customer(num,mail);
            if(condi==true){
                Sessions nvx = conn.recolterAmountSession(num_sess);
                double amount = nvx.getAmount()-4;
                int nbr = nvx.getActual()-1;
                conn.add_update_session(nbr, amount, num_sess);
                Members for_mail = new Members(0,mail);
                for_mail.sendMessage(-1,mail);
            }
            else{
                conn.closeConn();
            }
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return condi;
    }
    
    public void changePassword(String password, String id){
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.change_password(password,id);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void changePassword2(String password, String id){
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.change_password2(password,id);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public int addCustomerToSession(String num, String crypto, String mv, int id, String tot, String nbr,String date,String mail){
        int x=0;
        for(int i=0;i<num.length();i++){
            if(num.charAt(i) < '0' || num.charAt(i)> '9')
                return -1;
        }
        for(int i=0;i<crypto.length();i++){
            if(crypto.charAt(i) < '0' || crypto.charAt(i)> '9' || crypto.length()>3)
                return -1;
        }
        try{
            if(mv.length()!=5 || mv.charAt(2)!='/')
                return -1;
        }
        catch(StringIndexOutOfBoundsException e){
            return -1;
        }
        tot = tot.replaceAll("Total : ","");
        tot = tot.substring(0,tot.length()-2);
        double amount = Double.parseDouble(tot);
        int nombre = Integer.parseInt(nbr);
        
        if(mail==null || mail.equals(""))
            mail = "noMailForThisUser@error";
        date = date.replaceAll("/", "-");
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            ArrayList<Members> nvx = conn.recolterChampsCustomer();
            for(int i=0;i<nvx.size();i++){
                if(x==nvx.get(i).getId()){
                    x++;
                    i=-1;
                }
            }
            conn.insert_customer(id, x,date,mail);
            Sessions sess = conn.recolterAmountSession(id);
            amount += sess.getAmount();
            nombre += sess.getActual();
            conn.add_update_session(nombre, amount, id);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        for(int i=0;i<mail.length();i++){
            if(mail.charAt(i)=='@'){
                Members for_mail = new Members(0,mail);
                for_mail.sendMessage(x,mail);
            }
        }
        return x;
    }
    
    public ArrayList<Sessions> getSessionMovie(int id) throws SQLException, ClassNotFoundException, ParseException{
        ArrayList<Sessions> nvx;
        Connexion conn = new Connexion("movie", "root",password_ddb);
        nvx = conn.recolterChampsSessionsMovie(id);
        return nvx;
    }
    
    public ArrayList<Movies> searchMovies(String name,String type,String time,String day, String month) throws SQLException, ClassNotFoundException, ParseException{
        Connexion nvx = new Connexion("movie", "root", password_ddb);
        ArrayList<Movies> rep;
        int temps,jour,mois;
        try{
            if(time.equals(""))
                temps=0;
            else{
                temps = Integer.parseInt(time);
                if(temps<0)
                    temps = -temps;
            }
        }
        catch(NumberFormatException | NullPointerException e){
            return null;
        }
        
        try{
            type = type.toLowerCase();
        }
        catch(NullPointerException e){
            type = "";
        }
        try{
            name = name.toLowerCase();
        }
        catch(NullPointerException e){
            name = "";
        }
        try{
            jour = Integer.parseInt(day);
            mois = Integer.parseInt(month);
            
        }
        catch(NullPointerException | NumberFormatException e){
            day = "00";
            month = "00";
        }
        
        if(name.equals("")&&type.equals("")&&time.equals("")&&day.equals("00"))
            return null;
        
        
        rep = nvx.searchMovie(name,type,temps,month+"-"+day);
        return rep;
    }
    
    public Members getConnectedMember(String id, String pwd) throws SQLException, ClassNotFoundException, ParseException{
        Members test;
        Connexion nvx = new Connexion("movie", "root", password_ddb);
        test = nvx.checkLoginMember(id,pwd);
        nvx.closeConn();
        return test;
    }
    
    public Employees getConnectedEmployee(String id, String pwd) throws SQLException, ClassNotFoundException, ParseException{
        Employees test;
        Connexion nvx = new Connexion("movie", "root", password_ddb);
        test = nvx.checkLoginEmployee(id, pwd);
        nvx.closeConn();
        return test;
    }
    
    public int createMember(String firstName,String lastName, String age, String pwd) throws SQLException, ClassNotFoundException, ParseException{
        
        if(firstName==null || firstName.equals("") || lastName==null || lastName.equals(""))
            return -1;
        
        int ag = Integer.parseInt(age);
        if(ag<0)
            return -1;
        int condi = 0;
        Connexion nvx = new Connexion("movie", "root", password_ddb);
        ArrayList<Members> membre = nvx.recolterChampsMember();
        for(int i=0;i<membre.size();i++){
            
            if(condi==0){
                if(membre.get(i).getLogin().equals(firstName+"."+lastName)){
                    condi++;
                    i=-1;
                }
            }
            else{
                if(membre.get(i).getLogin().equals(firstName+Integer.toString(condi)+"."+lastName)){
                    condi++;
                    i=-1;
                }
            }   
        }
        if(condi==0)
            nvx.insert_member(firstName+"."+lastName, pwd, firstName, lastName, ag);
        else
            nvx.insert_member(firstName+Integer.toString(condi)+"."+lastName, pwd, firstName, lastName, ag);
        
        return condi;
    }
    
    public ArrayList<Movies> dispAllMovies()
    {
        ArrayList<Movies> movies=null;
        Connexion nvx;
        try {
            nvx = new Connexion("movie", "root", password_ddb);
            movies = nvx.recolterChampsMovies();
            nvx.closeConn();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return movies;
    }
    
    public int changePromotion(String in1, String in2, String in3){
        
        double i1=-1,i2=-1,i3=-1;
        try{
            if(!in1.equals(""))
                i1 = Double.parseDouble(in1);
        }
        catch(NumberFormatException e){
            return 0;
        }
        try{
            if(!in2.equals(""))
                i2 = Double.parseDouble(in2);
        }
        catch(NumberFormatException e){
            return 1;
        }
        try{
            if(!in3.equals(""))
                i3 = Double.parseDouble(in3);
        }
        catch(NumberFormatException e){
            return 2;
        }
        
        if(i1>1 && i1!=-1){
            i1/=100;
            if(i1>0.5)
                return 0;
        }
        else if(i1>0.5){
            return 0;
        }
        
        if(i2>1 && i2!=-1){
            i2/=100;
            if(i2>0.5)
                return 1;
        }
        else if(i2>0.5){
            return 1;
        }
        
        if(i3>1 && i3!=-1){
            i3/=100;
            if(i3>0.5)
                return 2;
        }
        else if(i3>0.5){
            return 2;
        }
        
        try {
            Connexion nvx = new Connexion("movie","root",password_ddb);
            nvx.changePromotions(i1,i2,i3);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public ArrayList<Members> recolterMembre(){
        ArrayList<Members> nvx = new ArrayList<>();
        try {
            Connexion conn = new Connexion("movie","root",password_ddb);
            nvx = conn.recolterChampsMember();
            conn.closeConn();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nvx;
    }
    public ArrayList<Sessions> recolterSessions(){
        ArrayList<Sessions> nvx = new ArrayList<>();
        try {
            Connexion conn = new Connexion("movie","root",password_ddb);
            nvx = conn.recolterChampsSessions();
            conn.closeConn();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nvx;
    }
    
    public ArrayList<Employees> getAllEmployee(){
        ArrayList<Employees> nvx = new ArrayList<>();
        Connexion conn;
        try {
            conn = new Connexion("movie","root",password_ddb);
            nvx = conn.recolterChampsEmployee();
            conn.closeConn();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nvx;
    }
    
    public ArrayList<Members> getAllMembers(){
        ArrayList<Members> nvx = new ArrayList<>();
        Connexion conn;
        try {
            conn = new Connexion("movie","root",password_ddb);
            nvx = conn.recolterChampsMember();
            conn.closeConn();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nvx;
    }
    
    public void deleteEmployee(String login){
        Connexion conn;
        try {
            conn = new Connexion("movie","root",password_ddb);
            conn.delete_employee(login);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void insert_employee(String i1,String i2,String i3){
        i1 = i1.replaceAll(" ","_");
        i2 = i2.replaceAll(" ","_");
        
        Connexion conn;
        try {
            conn = new Connexion("movie","root",password_ddb);
            int condi = 0;
            ArrayList<Employees> employee = conn.recolterChampsEmployee();
            for(int i=0;i<employee.size();i++){
                if(condi==0){
                    if(employee.get(i).getLogin().equals(i1+"."+i2)){
                        condi++;
                        i=-1;
                    }
                }
                else{
                    if(employee.get(i).getLogin().equals(i1+Integer.toString(condi)+"."+i2)){
                        condi++;
                        i=-1;
                    }
                }   
            }
            if(condi==0)
                conn.insert_employee(i1+"."+i2, "em1*", i1, i2, i3);
            else
                conn.insert_employee(i1+Integer.toString(condi)+"."+i2, "em1*", i1, i2, i3);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void deleteMember(String id){
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.delete_member(id);
            conn.closeConn();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean modifyMember(String age,String login){
        if(age.charAt(0)=='-' || age.charAt(0)==' ')
            return false;
        else if(age.equals(""))
            return false;
        int ag;
        try{
            ag = Integer.parseInt(age);
        }
        catch(NumberFormatException e){
            return false;
        }
        
        if(ag>120)
            return false;
        
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.update_member(ag,login);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public void update_Session(Sessions sess)
    {
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.changeAll_seance(sess);           
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Add_Session(Sessions sess, int id)
    {
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.insert_seance(sess,id);           
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void delete_Session(int id)
    {
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.delete_session(id);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public ArrayList<Members> AllCustomers() throws SQLException, ParseException, ClassNotFoundException{
        ArrayList<Members> customers;
        Connexion nvx = new Connexion("movie", "root", password_ddb);
        customers = nvx.recolterChampsCustomer();
        nvx.closeConn();
        return customers;
    }

    public void delete_customer(int id, String e){
        try {
            Connexion conn = new Connexion("movie", "root", password_ddb);
            conn.delete_customer(id,e);
            conn.closeConn();
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
