/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.model;

/**
 *
 * @author Utilisateur
 * représenteles employés du cinéma
 */
public class Employees implements Person{
    private final String login;
    private String password;
    private final String firstName;
    private final String lastName;
    private final String access;
    /**
     * constructeur
     * @param login
     * @param password
     * @param firstName
     * @param lastname
     * @param access 
     */
    public Employees(String login,String password,String firstName, String lastname,String access)
    {
        this.login =login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastname;
        this.access = access;
    }
    /**
     * getter login
     * @return 
     */
    public String getLogin() {
        return login;
    }
/**
 * getter password
 * @return 
 */
    public String getPassword() {
        return password;
    }
    /**
     * getter nom et prénom de l'employé
     * @return 
     */
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    /**
     * niveau d'agréditation de l'employé
     * @return 
     */
    public String getAccess(){
        return access;
    }
    /**
     * setterdu mdp de l'employé
     * @param mdp 
     */
    public void setPassword(String mdp){
        password = mdp;
    }

    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
