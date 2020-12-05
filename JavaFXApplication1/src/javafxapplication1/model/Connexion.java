/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.model;
import java.sql.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    /**
     * ArrayList public pour les tables
     */

    /**
     * Constructeur avec 3 paramètres : nom, login et password de la BDD locale
     *
     * @param nameDatabase
     * @param loginDatabase
     * @param passwordDatabase
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     * @throws java.text.ParseException
     */
    public Connexion(String nameDatabase, String loginDatabase, String passwordDatabase) throws SQLException, ClassNotFoundException, ParseException{
        // chargement driver "com.mysql.jdbc.Driver"
        Class.forName("com.mysql.jdbc.Driver");
        
        String urlDatabase = "jdbc:mysql://localhost:3308/movie";
       // String urlDatabase = "jdbc:mysql://localhost:3308/jps?characterEncoding=latin1";

        //création d'une connexion JDBC à la base 
        conn = DriverManager.getConnection(urlDatabase, "root","root");
        
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

        PreparedStatement var = conn.prepareStatement(sql);
        var.setString(1, login);
        var.setString(2, motDePasse);
        var.setString(3, firstName);
        var.setString(4, lastName);
        var.setInt(5, age);
        var.execute();
        
        conn.close();
    }
    
    public void add_movie(String title, String author,java.sql.Date date, int rate, String type, int runningTime, int id, String description)throws SQLException{
        String sql = " INSERT INTO movie(id, titre, auteur, genre, date, runningTime, note, description)"+" VALUES(?,?,?,?,?,?,?,?)";
        
        PreparedStatement nn = conn.prepareStatement(sql);
        nn.setInt(1, id);
        nn.setString(2, title);
        nn.setString(3, author);
        nn.setString(4, type);
        nn.setDate(5, date);
        nn.setInt(6, runningTime);
        nn.setInt(7, rate);
        nn.setString(8, description);
        nn.execute();
        conn.close();
    }
    
    public void insert_seance(ArrayList<Sessions> sessions,int id) throws SQLException{
        String sql = " INSERT INTO session(id, movie_id, date, max_place, heure, actual_place, amount)"+" VALUES(?,?,?,?,?,?,?)";
        PreparedStatement mt = conn.prepareStatement(sql);
        for (Sessions session : sessions) {
            mt.setInt(1, session.getId());
            mt.setInt(2, id);
            mt.setDate(3, session.getDate());
            mt.setInt(4, session.getNbr_places_max());
            mt.setString(5, session.getHoraire());
            mt.setInt(6, session.getActual());
            mt.setDouble(7, session.getAmount());
            mt.execute(); 
        }
    }
    
    public void insert_seance(Sessions session, int id) throws SQLException{
        String sql = " INSERT INTO session(id, movie_id, date, max_place, heure, actual_place, amount)"+" VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, session.getId());
        pstm.setInt(2, id);
        pstm.setDate(3, session.getDate());
        pstm.setInt(4, session.getNbr_places_max());
        pstm.setString(5, session.getHoraire());
        pstm.setInt(6, session.getActual());
        pstm.setDouble(7, session.getAmount());
        pstm.execute();
        conn.close();
    }
    
    public void insert_customer(int session_id, int id, String date,String mail) throws SQLException, ParseException{
        java.sql.Date dat = convertDate(date);
        String sql = " INSERT INTO customer(id, id_session, date, mail)"+" VALUES(?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);
        ps.setInt(2, session_id);
        ps.setDate(3, dat);
        ps.setString(4, mail);
        ps.execute();
    }
    
    public void insert_employee(String login,String mdp, String first, String last, String acc) throws SQLException{
        String sql = " INSERT INTO employee(login, motDePasse, firstName, lastName, cle_access)"+" VALUES(?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, login);
        ps.setString(2, mdp);
        ps.setString(3, first);
        ps.setString(4, last);
        ps.setString(5, acc);
        ps.execute();
        conn.close();
    }
    
    public void add_update_session(int tot,double amount,int id) throws SQLException{
        String sql = "update session set actual_place = ?, amount = ? where id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setInt(1,tot);
        preparedStmt.setDouble(2, amount);
        preparedStmt.setInt(3,id);
        preparedStmt.execute();
        conn.close();
    }
    
    public void changeAll_seance(Sessions session) throws SQLException{
        String sql = "UPDATE session SET movie_id = ?, date = ?, max_place = ?, heure = ?, actual_place = ?, amount = ? WHERE id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setInt(1,session.getMovie());
        preparedStmt.setDate(2,session.getDate());
        preparedStmt.setInt(3,session.getNbr_places_max());
        preparedStmt.setInt(7, session.getId());
        preparedStmt.setString(4, session.getHoraire());
        preparedStmt.setInt(5, session.getActual());
        preparedStmt.setDouble(6, session.getAmount());
        preparedStmt.execute();
        conn.close();
    }
    
    public void update_member(int tot,String id) throws SQLException{
        String sql = "update membre set age = ? where login = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setInt(1,tot);
        preparedStmt.setString(2,id);
        preparedStmt.execute();
        conn.close();
    }
    
    public void change_password(String password,String id) throws SQLException{
        String sql = "update membre set mot_de_passe = ? where login = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setString(1, password);
        preparedStmt.setString(2,id);
        preparedStmt.execute();
        conn.close();
    }
    
    public void change_password2(String password,String id) throws SQLException{
        String sql = "update employee set motDePasse = ? where login = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setString(1, password);
        preparedStmt.setString(2,id);
        preparedStmt.execute();
        conn.close();
    }
    public void changeAll_(Movies movie) throws SQLException{
        String sql = "UPDATE movie SET titre = ?, auteur = ?, genre = ?, date = ?, runningTime = ?, description = ?, note = ? WHERE id = ?";
        PreparedStatement preparedStmt = conn.prepareStatement(sql);
        preparedStmt.setString(1,movie.getTitle());
        preparedStmt.setString(2,movie.getAuthor());
        preparedStmt.setString(3,movie.getType());
        preparedStmt.setDate(4,movie.getDate());
        preparedStmt.setInt(5,movie.getRunningTime());
        preparedStmt.setString(6,movie.getDescription());
        preparedStmt.setInt(7,movie.getRate());
        preparedStmt.setInt(8,movie.getId());
        preparedStmt.executeUpdate();
        conn.close();
    }
    
    public void changePromotions(double i1,double i2, double i3) throws SQLException{
        if(i1!=-1){
            String sql = "update reduction set promo = ? where id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setDouble(1, i1);
            preparedStmt.setString(2, "senior");
            preparedStmt.execute();
        }
        if(i2!=-1){
            String sql = "update reduction set promo = ? where id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setDouble(1, i2);
            preparedStmt.setString(2, "children");
            preparedStmt.execute();
        }
        if(i3!=-1){
            String sql = "update reduction set promo = ? where id = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(sql);
            preparedStmt.setDouble(1, i3);
            preparedStmt.setString(2, "regular");
            preparedStmt.execute();
        }
        
        conn.close();
    }
    //Suppression d'un membre dans la base de donnees
    public void delete_member(String login) throws SQLException{
        ArrayList<Members> listeMem = recolterChampsMember();
        
        for (Members listeMem1 : listeMem) {
            if (listeMem1.getLogin().equals(login)) {
                String sql = " DELETE FROM membre WHERE `login` =?";
                PreparedStatement tmt = conn.prepareStatement(sql);
                tmt.setString(1, login);
                tmt.executeUpdate();
            }
        }  
    }
    //Suppression d'un movie de la base de donnees
    public void delete_movie(int id) throws SQLException{
        ArrayList<Movies> listeMov = recolterChampsMovies();
        
        for (Movies listeMov1 : listeMov) {
            if (listeMov1.getId() == id) {
                String sql = " DELETE FROM movie WHERE `id` =?";
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setInt(1, id);
                pst.executeUpdate();
            }
        }  
        conn.close();
    }
    
    public void delete_session(int id) throws SQLException{
        ArrayList<Sessions> listeSess = recolterChampsSessions();
        
        for (Sessions listeSes : listeSess) {
            if (listeSes.getId() == id) {
                String sql = " DELETE FROM session WHERE `id` =?";
                PreparedStatement psmt_ = conn.prepareStatement(sql);
                psmt_.setInt(1, id);
                psmt_.executeUpdate();
            }
        }
        conn.close();
    }
    
    public boolean delete_customer(int id,String e) throws SQLException{
        ArrayList<Members> listeCust = recolterChampsCustomer();
        boolean condi = false;
        for (Members listeCust1 : listeCust) {
            if (listeCust1.getId() == id && listeCust1.getLogin().equals(e)) {
                String sql = " DELETE FROM customer WHERE `id` =?";
                PreparedStatement pstmt1 = conn.prepareStatement(sql);
                pstmt1.setInt(1, id);
                pstmt1.executeUpdate();
                condi = true;
            }
        }
        return condi;
    }
    
    public void delete_employee(String login) throws SQLException{
        ArrayList<Employees> liste = recolterChampsEmployee();
        
        for (Employees listeEm : liste) {
            if (listeEm.getLogin().equals(login)) {
                String sql = " DELETE FROM employee WHERE `login` =?";
                PreparedStatement psmt_ = conn.prepareStatement(sql);
                psmt_.setString(1, login);
                psmt_.executeUpdate();
            }
        }
        conn.close();
    }
    
    public ArrayList<Integer> getSessionConnected(String login) throws SQLException{
        ArrayList<Integer> nvx = new ArrayList<>();
        rset = stmt.executeQuery("select * from customer");
        while (rset.next()) {
            String test = rset.getString(4);
            int id = rset.getInt(2);
            if(test.equals(login))
                nvx.add(id);
        }
        
        return nvx;
    }
    public ArrayList<Integer> getSessionConnectedID(String login) throws SQLException{
        ArrayList<Integer> nvx = new ArrayList<>();
        rset = stmt.executeQuery("select * from customer");
        while (rset.next()) {
            String test = rset.getString(4);
            int id = rset.getInt(1);
            if(test.equals(login))
                nvx.add(id);
        }
        conn.close();
        return nvx;
    }
    
    public ArrayList<Sessions> recolterSessionMember(ArrayList<Integer> nvx) throws SQLException{
        ArrayList<Sessions> other = new ArrayList<>();
        rset = stmt.executeQuery("select * from session");
        
        while (rset.next()) {
            int id_ = rset.getInt(1);
            int id_movie = rset.getInt(2);
            java.sql.Date date = rset.getDate(3);    
            int max = rset.getInt(4);
            String heure = rset.getString(5);
            int act = rset.getInt(6);
            double tot = rset.getDouble(7);
            for (Integer nvx1 : nvx) {
                if (nvx1 == id_) {
                    other.add(new Sessions(id_,id_movie,date,max,act,heure,tot));
                }
            }
        }
        conn.close();
        return other;
    }
    
    public Sessions recolterAmountSession(int id) throws SQLException{
        rset = stmt.executeQuery("select * from session");
        
        // tant qu'il reste une ligne 
        while (rset.next()) {
            int id_ = rset.getInt(1);
            int id_movie = rset.getInt(2);
            java.sql.Date date = rset.getDate(3);    
            int max = rset.getInt(4);
            String heure = rset.getString(5);
            int act = rset.getInt(6);
            double tot = rset.getDouble(7);
            if(id_==id)
                return new Sessions(id,id_movie,date,max,act,heure,tot);
        }
        // Retourner l'ArrayList
        return null;
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
            String champs[] = new String[5];
            
            for (int i = 0; i < 5; i++) {
                champs[i] = rset.getString(i+1);
            }
            emp = new Employees(champs[0], champs[1], champs[2], champs[3], champs[4]);

            // ajouter l'Employees dans l'ArrayList
            liste.add(emp);
        }
        // Retourner l'ArrayList
        return liste;
    }
    public ArrayList<Members> recolterChampsMember() throws SQLException {
        // récupération de l'ordre de la requete
        rset = stmt.executeQuery("select * from membre");

        // creation d'une ArrayList de Membre
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
    
    
    public String recolterSpecifikMovie(int id) throws SQLException{
        rset = stmt.executeQuery("select * from movie");
        while (rset.next()) {
            int title = rset.getInt(1);
            String titre = rset.getString(2);
            if(title==id){
                return titre;
            }
        }
        return "<Not found>";
    }
    
    
    public int recolterSpecifikSession(int id) throws SQLException{
        rset = stmt.executeQuery("select * from session");
        while (rset.next()) {
            int id_ = rset.getInt(1);
            int nbr = rset.getInt(4);
            if(id_==id){
                return nbr;
            }
        }
        return -1;
    }
    
    public int getMovieFromCust(int id,String e) throws SQLException{
        rset = stmt.executeQuery("select * from customer");

        // creation d'une ArrayList d'Employees
        
        // tant qu'il reste une ligne 
        while (rset.next()) {
            int id_ = rset.getInt(1);
            int id2 = rset.getInt(2);
            String id3 = rset.getString(4);
            if(id_==id && e.equals(id3))
                return id2;
        }
        return -1;
    }
    
    public ArrayList<Sessions> recolterChampsSessions() throws SQLException{
        rset = stmt.executeQuery("select * from session");

        // creation d'une ArrayList d'Employees
        ArrayList<Sessions> liste = new ArrayList<>();
        
        // tant qu'il reste une ligne 
        while (rset.next()) {
            int id = rset.getInt(1);
            int id_movie = rset.getInt(2);
            java.sql.Date date = rset.getDate(3);    
            int max = rset.getInt(4);
            String heure = rset.getString(5);
            int act = rset.getInt(6);
            double tot = rset.getDouble(7);
            liste.add(new Sessions(id,id_movie,date,max,act,heure,tot));
        }
        // Retourner l'ArrayList
        return liste;
    }
    
    public ArrayList<Sessions> recolterChampsSessionsMovie(int idmov) throws SQLException{
        ArrayList<Sessions> sess;
        ArrayList<Sessions> rep = new ArrayList<>();
        sess = recolterChampsSessions();
        
        for (Sessions ses : sess) {
            if (ses.getMovie() == idmov) {
                rep.add(ses);
            }
        }
        // Retourner l'ArrayList
        conn.close();
        return rep;
    }
    
    public ArrayList<Customers> recolterChampsCustomer(int id_movie) throws SQLException{
        rset = stmt.executeQuery("select * from customer");

        // creation d'une ArrayList d'Employees
        ArrayList<Customers> liste = new ArrayList<>();
        
        // tant qu'il reste une ligne 
        while (rset.next()){
            int id = rset.getInt(1);
            int id2 = rset.getInt(2);
            String id3 = rset.getString(4);
            if(id2==id_movie)
                liste.add(new Members(id,id3));
        }
        // Retourner l'ArrayList
        return liste;
    }
    
    public ArrayList<Members> recolterChampsCustomer() throws SQLException{
        rset = stmt.executeQuery("select * from customer");

        // creation d'une ArrayList d'Employees
        ArrayList<Members> liste = new ArrayList<>();
        
        // tant qu'il reste une ligne 
        while (rset.next()) {
            int id = rset.getInt(1);
            int id2 = rset.getInt(2);
            String id3 = rset.getString(4);
            liste.add(new Members(id,id3));
        }
        // Retourner l'ArrayList
        return liste;
    }
    
    //Recherche du film parmi la liste de films disponibles
    public ArrayList<Movies> searchMovie(String name,String type,int time,String date)throws SQLException{
        ArrayList<Movies> liste = new ArrayList<>();
        ArrayList<Movies> request;
        request = recolterChampsMovies();
        // tant qu'il reste une ligne
        boolean condi = false;
        for (Movies request1 : request) {
            String da = request1.getDate().toString();
            da = da.substring(5);
            if (request1.getTitle().contains(name) && !name.equals("")) {
                condi = true;
            }
            if(da.equals(date)){
                condi = true;
            }
            if (request1.getType().equals(type)) {
                condi = true;
            }
            if (request1.getRunningTime() == time) {
                condi = true;
            }
            if (condi==true) {
                liste.add(request1);
                condi = false;
            }
        }
        conn.close();
        return liste;
    }
    
    //Check des logins et mot de passe pour les membres et les employés
    public Members checkLoginMember(String login, String mdp) throws SQLException{
        ArrayList<Members> listeMem = recolterChampsMember();
        
        for (Members listeMem1 : listeMem) {
            if (listeMem1.getLogin().equals(login) && listeMem1.getPassword().equals(mdp)) {
                return listeMem1;
            }
        }
        return null;        
    }
    public Employees checkLoginEmployee(String login, String mdp) throws SQLException{
        ArrayList<Employees> listeEmp = recolterChampsEmployee();
        
        for (Employees listeEmp1 : listeEmp) {
            if (listeEmp1.getLogin().equals(login) && listeEmp1.getPassword().equals(mdp)) {
                return listeEmp1;
            }
        }
        return null;        
    }  
    
    public double[] getReduc() throws SQLException{
        double[] promo = new double[3];
        
        rset = stmt.executeQuery("select * from reduction");
        
        // tant qu'il reste une ligne 
        int i=0;
        while (rset.next()) {
            String id = rset.getString(1);
            double id2 = rset.getDouble(2);
            promo[i] = id2;
            i++;
        }
        
        return promo;
    }
    
    public void closeConn(){
        try {
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
