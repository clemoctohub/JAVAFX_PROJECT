/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
    
    public void sendMessage(int x,String login){
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        
        Session session = Session.getDefaultInstance(properties,
            new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication("monCinemaIsMyCine@gmail.com","cinecine");
                }
            }
        );
        
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("monCinemaIsMyCine@gmail.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(login));
            message.setSubject("Places to the session");
            if(x!=-1)
                message.setText("Hello You !\n Thank you to buy a place in our Cinema !\n We hope you will enjoy the session."
                    + " If you want to delete your session, just go to our site and enter your email and place Id on the Home Page.\n"
                    + " You will receive an email to get 4$ back !\n "
                    + "Here is your ID session : "+x+"\n\n Thank you and enjoy your session.\n You can ask questions by this mail");
            else
                message.setText("Hello !\n Your place to the session has been deleted."
                    + " You will get your money back, just send me an email with your banks Id.\n"
                    + " We hope to see you really soon !\n "
                    + "Don't forget there are new movies each week !\n Enjoy your day !!");
            Transport.send(message);
        }
        catch(MessagingException e){
            System.out.println("Mail not send");
        }
        
    }
}
