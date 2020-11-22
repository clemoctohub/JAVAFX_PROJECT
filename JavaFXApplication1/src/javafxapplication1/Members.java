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
public class Members extends Customers{
    private String password;
    private int age;
    private final String login;
    
    public Members(String firstName, String lastName,String password,int age, String login)
    {
        super(firstName,lastName);
        this.password = password;
        this.age = age;
        this.login = login;
    }
    
    public Members(int id,String login){
        super(id);
        this.login = login;
    }
    
    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getLogin() {
        return login;
    }
    
    public int getId(){
        return id;
    }
}
