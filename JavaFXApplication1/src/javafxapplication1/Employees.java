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
public class Employees implements Person{
    private final String login;
    private String password;
    private final String firstName;
    private final String lastName;
    private final String access;
    
    public Employees(String login,String password,String firstName, String lastname,String access)
    {
        this.login =login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastname;
        this.access = access;
    }
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String getAccess(){
        return access;
    }
    
    public void setPassword(String mdp){
        password = mdp;
    }

    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
