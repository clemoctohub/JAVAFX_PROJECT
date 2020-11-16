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
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    
    public Employees(String login,String password,String firstName, String lastname)
    {
        this.login =login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastname;
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

    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
