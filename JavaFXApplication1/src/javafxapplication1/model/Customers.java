/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.model;

/**
 *
 * @author Utilisateur
 * classe abstraite représentant les clients non membre
 */
public abstract class Customers implements Person{
    private String firstName;
    private String lastName;
    protected int id;
    /**
     * constructeur
     * @param firstName
     * @param lastName 
     */
    public Customers(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    /**
     * return l'id du customer
     * @param id 
     */
    public Customers(int id){
        this.id = id;
    }
    /**
     * return nom et prénom du customer
     * @return 
     */
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
