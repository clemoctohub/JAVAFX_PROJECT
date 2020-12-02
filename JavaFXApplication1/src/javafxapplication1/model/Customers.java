/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.model;

/**
 *
 * @author Utilisateur
 */
public abstract class Customers implements Person{
    private String firstName;
    private String lastName;
    protected int id;
    
    public Customers(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    public Customers(int id){
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    @Override
    public void display(){
        
    }
}
