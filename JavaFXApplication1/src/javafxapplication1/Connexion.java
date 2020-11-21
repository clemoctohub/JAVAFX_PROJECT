/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;
import java.sql.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author clemf
 */
public class Connexion {

    /**
     * Attributs prives : connexion JDBC, statement, ordre requete et resultat
     * requete
     */
    private final Connection conn;
    private final Statement stmt;
    private ResultSet rset;
    private ResultSetMetaData rsetMeta;
    /**
     * ArrayList public pour les tables
     */
    public ArrayList<String> tables = new ArrayList<>();
    /**
     * ArrayList public pour les requêtes de sélection
     */
    public ArrayList<String> requetes = new ArrayList<>();
    /**
     * ArrayList public pour les requêtes de MAJ
     */
    public ArrayList<String> requetesMaj = new ArrayList<>();
    private Object java;
    private Object pstmt;

    /**
     * Constructeur avec 3 paramètres : nom, login et password de la BDD locale
     *
     * @param nameDatabase
     * @param loginDatabase
     * @param passwordDatabase
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    public Connexion(String nameDatabase, String loginDatabase, String passwordDatabase) throws SQLException, ClassNotFoundException, ParseException{
        // chargement driver "com.mysql.jdbc.Driver"
        Class.forName("com.mysql.jdbc.Driver");
        
        String urlDatabase = "jdbc:mysql://localhost:3308/movie";
       // String urlDatabase = "jdbc:mysql://localhost:3308/jps?characterEncoding=latin1";

        //création d'une connexion JDBC à la base 
        conn = DriverManager.getConnection(urlDatabase, "root","");
        
        // création d'un ordre SQL (statement)
        stmt = conn.createStatement(); 
        
    }

    public java.sql.Date convertDate(String date) throws ParseException{
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = (Date) format.parse(date);
        java.sql.Date sql = new java.sql.Date(parsed.getTime());
        return sql;
    }
    
    //Ajout d'un nouveau membre dans la base de donnees
    public void insert_member(String login, String motDePasse, String firstName, String lastName, int age) throws SQLException {
        String sql = " INSERT INTO membre(login, mot_de_passe, first_name, last_name, age)"+" VALUES(?,?,?,?,?)";

        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, login);
        pstmt.setString(2, motDePasse);
        pstmt.setString(3, firstName);
        pstmt.setString(4, lastName);
        pstmt.setInt(5, age);
        pstmt.execute();
        
        conn.close();
    }
    //Ajout d'un nouveau film dans la base de donnees 
    public void insert_movie(String title, String author,java.sql.Date date, int rate, String type, int runningTime, int id, ArrayList<Session> sessions, String description) throws SQLException{
        String sql = " INSERT INTO movie(id, titre, auteur, genre, date, runningTime, note, description)"+" VALUES(?,?,?,?,?,?,?,?)";
        
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.setString(2, title);
        pstmt.setString(3, author);
        pstmt.setString(4, type);
        pstmt.setDate(5, date);
        pstmt.setInt(6, runningTime);
        pstmt.setInt(7, rate);
        pstmt.setString(8, description);
        pstmt.execute(); 
        
        insert_seance(sessions,id);
    }
    
    public void insert_seance(ArrayList<Session> sessions,int id) throws SQLException{
        String sql = " INSERT INTO session(id, movie_id, date, max_place)"+" VALUES(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for(int i=0;i<sessions.size();i++){
            pstmt.setInt(1, sessions.get(i).getId());
            pstmt.setInt(2, id);
            pstmt.setDate(3, sessions.get(i).getDate());
            pstmt.setInt(4, sessions.get(i).getNbr_places_max());
            pstmt.execute(); 
        }
    }
    
    public void insert_seance(Session session, int id) throws SQLException{
        String sql = " INSERT INTO session(id, movie_id, date, max_place)"+" VALUES(?,?,?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, session.getId());
        pstmt.setInt(2, id);
        pstmt.setDate(3, session.getDate());
        pstmt.setInt(4, session.getNbr_places_max());
        pstmt.execute();
    }
    
    public void insert_customer(int session_id,int id) throws SQLException{
        String sql = " INSERT INTO customer(id, id_session)"+" VALUES(?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.setInt(2, session_id);
        pstmt.execute();
        conn.close();
    }
    
    public void update_seance(Session session,String changes) throws SQLException{
        if(changes.equals("movie_id")){
            String sql = "update session set movie_id = ? where id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setInt(1,session.getMovie());
            preparedStmt.setInt(2, session.getId());
            preparedStmt.execute();
        }
        else if(changes.equals("number_places")){
            String sql = "update session set max_place = ? where id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setInt(1,session.getNbr_places_max());
            preparedStmt.setInt(2, session.getId());
            preparedStmt.execute();
        }
        else if(changes.equals("date")){
            String sql = "update session set date = ? where id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setDate(1,session.getDate());
            preparedStmt.setInt(2, session.getId());
            preparedStmt.execute();
        }
    }
    
    public void changeAll_seance(Session session) throws SQLException{
        String sql = "update session set movie_id = ?, date = ?, max_place = ? where id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setInt(1,session.getMovie());
        preparedStmt.setDate(2,session.getDate());
        preparedStmt.setInt(3,session.getNbr_places_max());
        preparedStmt.setInt(4, session.getId());
    }
    
    public void changeAll_user(Members member) throws SQLException{
        String sql = "update membre set password = ? where id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setString(1,member.getPassword());
        preparedStmt.setInt(2,member.getId());
    }
    
    public void changeAll_(Movies movie) throws SQLException{
        String sql = "update movie set titre = ?, auteur = ?, genre = ?, date = ?, runnningTime = ?, description = ? where id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setString(1,movie.getTitle());
        preparedStmt.setString(2,movie.getAuthor());
        preparedStmt.setString(3,movie.getType());
        preparedStmt.setDate(4,movie.getDate());
        preparedStmt.setInt(5,movie.getRunningTime());
        preparedStmt.setInt(6,movie.getId());
    }
    //Suppression d'un membre dans la base de donnees
    public void delete_member(String login) throws SQLException{
        ArrayList<Members> listeMem = recolterChampsMember();
        
        for(int i=0;i<listeMem.size();i++){
            if(listeMem.get(i).getLogin().equals(login)){
               String sql = " DELETE FROM membre WHERE `login` =?";
               PreparedStatement pstmt = conn.prepareStatement(sql);
               pstmt.setString(1, login);
               pstmt.executeUpdate();
            }     
        }  
    }
    //Suppression d'un movie de la base de donnees
    public void delete_movie(int id) throws SQLException{
        ArrayList<Movies> listeMov = recolterChampsMovies();
        
        for(int i=0;i<listeMov.size();i++){
            if(listeMov.get(i).getId() == id){
               String sql = " DELETE FROM movie WHERE `id` =?";
               PreparedStatement pstmt = conn.prepareStatement(sql);
               pstmt.setInt(1, id);
               pstmt.executeUpdate();
            }     
        }  
        
    }
    
    public void delete_session(int id) throws SQLException{
        ArrayList<Session> listeSess = recolterChampsSessions();
        
        for(int i=0;i<listeSess.size();i++){
            if(listeSess.get(i).getId() == id){
               String sql = " DELETE FROM session WHERE `id` =?";
               PreparedStatement pstmt = conn.prepareStatement(sql);
               pstmt.setInt(1, id);
               pstmt.executeUpdate();
            }     
        }  
    }
    
    public boolean delete_customer(int id) throws SQLException{
        ArrayList<Members> listeCust = recolterChampsCustomer();
        boolean condi = false;
        for(int i=0;i<listeCust.size();i++){
            if(listeCust.get(i).getId() == id){
               String sql = " DELETE FROM customer WHERE `id` =?";
               PreparedStatement pstmt = conn.prepareStatement(sql);
               pstmt.setInt(1, id);
               pstmt.executeUpdate();
               condi = true;
            }     
        }
        conn.close();
        return condi;
    }
    
    //Recolte des tables de donnees
    public ArrayList<Employees> recolterChampsEmployee() throws SQLException {
        // récupération de l'ordre de la requete
        rset = stmt.executeQuery("select * from employee");

        // creation d'une ArrayList d'Employees
        ArrayList<Employees> liste;
        liste = new ArrayList<>();
        Employees emp;
        // tant qu'il reste une ligne 
        while (rset.next()) {
            String champs[] = new String[4];
            
            for (int i = 0; i < 4; i++) {
                champs[i] = rset.getString(i+1);
            }
            emp = new Employees(champs[0], champs[1], champs[2], champs[3]);

            // ajouter l'Employees dans l'ArrayList
            liste.add(emp);
        }

        // Retourner l'ArrayList
        return liste;
    }
    public ArrayList<Members> recolterChampsMember() throws SQLException {
        // récupération de l'ordre de la requete
        rset = stmt.executeQuery("select * from membre");

        // creation d'une ArrayList d'Employees
        ArrayList<Members> liste;
        liste = new ArrayList<>();
        Members mem;
        
        // tant qu'il reste une ligne 
        while (rset.next()) {
            String log = rset.getString(1);
            String mdp = rset.getString(2);
            String f = rset.getString(3);
            String l = rset.getString(4);
            int a = rset.getInt(5);
            mem = new Members(f,l,mdp,a,log);
            liste.add(mem);
        }
        // Retourner l'ArrayList
        return liste;
    }
    public ArrayList<Movies> recolterChampsMovies() throws SQLException {
        // récupération de l'ordre de la requete
        rset = stmt.executeQuery("select * from movie");

        // récupération du résultat de l'ordre
        rsetMeta = rset.getMetaData();


        // creation d'une ArrayList d'Employees
        ArrayList<Movies> liste;
        liste = new ArrayList<>();
        Movies mov;
        
        // tant qu'il reste une ligne 
        while (rset.next()) {
            String title = rset.getString(2);
            String author = rset.getString(3);
            String type = rset.getString(4);    
            java.sql.Date date = rset.getDate(5);
            int id = rset.getInt(1);
            int runningTime = rset.getInt(6);
            int rate = rset.getInt(7);
            String description = rset.getString(8);
            mov = new Movies(title,author,date,rate,type,runningTime,id,description);
            liste.add(mov);
        }
        // Retourner l'ArrayList
        return liste;
    }
    public ArrayList<Session> recolterChampsSessions() throws SQLException{
        rset = stmt.executeQuery("select * from session");

        // récupération du résultat de l'ordre
        rsetMeta = rset.getMetaData();


        // creation d'une ArrayList d'Employees
        ArrayList<Session> liste = new ArrayList<>();
        
        // tant qu'il reste une ligne 
        while (rset.next()) {
            int id = rset.getInt(1);
            int id_movie = rset.getInt(2);
            java.sql.Date date = rset.getDate(3);    
            int max = rset.getInt(4);
            liste.add(new Session(id,id_movie,date,max));
        }
        conn.close();
        // Retourner l'ArrayList
        return liste;
    }
    
    public ArrayList<Session> recolterChampsSessionsMovie(int idmov) throws SQLException{
        ArrayList<Session> sess = new ArrayList<>();
        ArrayList<Session> rep = new ArrayList<>();
        sess = recolterChampsSessions();
        
        for(int i=0;i<sess.size();i++){
            if(sess.get(i).getMovie()==idmov)
                rep.add(sess.get(i));
        }
        // Retourner l'ArrayList
        
        return rep;
    }
    
    public ArrayList<Customers> recolterChampsCustomer(int id_movie) throws SQLException{
        rset = stmt.executeQuery("select * from customer");

        // récupération du résultat de l'ordre
        rsetMeta = rset.getMetaData();
        // creation d'une ArrayList d'Employees
        ArrayList<Customers> liste = new ArrayList<>();
        
        // tant qu'il reste une ligne 
        while (rset.next()) {
            int id = rset.getInt(1);
            int id2 = rset.getInt(2);
            if(id2==id_movie)
                liste.add(new Members(id));
        }
        // Retourner l'ArrayList
        return liste;
    }
    
    public ArrayList<Members> recolterChampsCustomer() throws SQLException{
        rset = stmt.executeQuery("select * from customer");

        // récupération du résultat de l'ordre
        rsetMeta = rset.getMetaData();
        // creation d'une ArrayList d'Employees
        ArrayList<Members> liste = new ArrayList<>();
        
        // tant qu'il reste une ligne 
        while (rset.next()) {
            int id = rset.getInt(1);
            int id2 = rset.getInt(2);
            liste.add(new Members(id));
        }
        // Retourner l'ArrayList
        return liste;
    }
    
    //Recherche du film parmi la liste de films disponibles
    public ArrayList<Movies> searchMovie(String name,String type,int time)throws SQLException{
        ArrayList<Movies> liste = new ArrayList<>();
        ArrayList<Movies> request = new ArrayList<Movies>();
        request = recolterChampsMovies();
        // tant qu'il reste une ligne
        boolean condi = false;
        
        for(int i=0;i<request.size();i++){
            if(request.get(i).getTitle().indexOf(name)!=-1){
                condi = true;
            }
            else if(request.get(i).getType().equals(type)){
                condi = true;
                System.out.println("il y a genre");
            }
            else if(request.get(i).getRunningTime() == time){
                condi = true;
            }
            if(condi==true){
                liste.add(request.get(i));
                condi = false;
            }
        }
        conn.close();
        return liste;
    }
    //Affichage console des Films et des Employés
    public void afficherMovies()throws SQLException{
        ArrayList<Movies> listeMov = recolterChampsMovies();
        for(int i=0;i<listeMov.size();i++){
            System.out.println("title "+listeMov.get(i).getTitle()+" date "+listeMov.get(i).getDate());
        }
    }
    public void afficherEmployees() throws SQLException{
        ArrayList<Employees> listeEmp = recolterChampsEmployee();
        for(int i=0;i<listeEmp.size();i++){
            System.out.println(listeEmp.get(i).getFirstName()+" "+listeEmp.get(i).getLastName()+" "+listeEmp.get(i).getLogin()+" "+listeEmp.get(i).getPassword());
        }
    }
    
    //Check des logins et mot de passe pour les membres et les employés
    public Members checkLoginMember(String login, String mdp) throws SQLException{
        ArrayList<Members> listeMem = recolterChampsMember();
        
        for(int i=0;i<listeMem.size();i++){
            if(listeMem.get(i).getLogin().equals(login) && listeMem.get(i).getPassword().equals(mdp))
                return listeMem.get(i);
        }
        conn.close();
        return null;        
    }
    public Employees checkLoginEmployee(String login, String mdp) throws SQLException{
        ArrayList<Employees> listeEmp = recolterChampsEmployee();
        
        for(int i=0;i<listeEmp.size();i++){
            if(listeEmp.get(i).getLogin().equals(login) && listeEmp.get(i).getPassword().equals(mdp))
                return listeEmp.get(i);
        }
        conn.close();
        return null;        
    }  
}
