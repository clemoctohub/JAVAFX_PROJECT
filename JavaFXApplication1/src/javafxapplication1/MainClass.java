/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.shape.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import static javafx.scene.paint.Color.*;

/**
 *
 * @author clemf
 */
public class MainClass extends Application {
    
    private Tab tab1 = new Tab("Home"),tab2 = new Tab("CinePass"),tab3 = new Tab("Search"),tab4 = new Tab("Connection");
    private TabPane tabPane = new TabPane();
    private Members actualMember;
    private Employees actualEmployee;
    
    private Controller controller;
    private Label connected;
    
    @Override
    public void start(Stage primaryStage) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //int width = gd.getDisplayMode().getWidth();
        //int height = gd.getDisplayMode().getHeight();
        Label bande = new Label("WELCOME IN OUR CINEMA");
        bande.setId("bande");
        connected = new Label("");
        connected.setId("bande");
        connected.setAlignment(Pos.CENTER_RIGHT);
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(bande,connected);
        tab2.setContent(getDiscount());
        tab3.setContent(searchVbox());
        tab4.setContent(getPane(0));
        
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(header);
        vbox.getChildren().add(tabPane);
        
        Scene scene = new Scene(vbox, 400, 600);
        //scene.getStylesheets().add("Style.css");
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setTitle("CHANGE");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
    
    public VBox searchVbox(){
        VBox nvx = new VBox();
        nvx.setAlignment(Pos.CENTER);
        String type="";
        
        TextField recherche = new TextField();
        HBox hbo1 = new HBox();
        hbo1.setAlignment(Pos.CENTER);
        hbo1.getChildren().add(recherche);
        
        ChoiceBox split = new ChoiceBox();
        split.getItems().addAll("Action","Science-Fiction","Horror","Fantastic","Comedia");
        
        TextField lab1 = new TextField();
        lab1.setPromptText("Month");
        Label lab2 = new Label("/");
        TextField lab3 = new TextField();
        lab3.setPromptText("Day");
        HBox cont = new HBox();
        cont.getChildren().addAll(lab1,lab2,lab3);
        
        TextField time = new TextField();
        time.setPromptText("time");
        
        HBox hbo2 = new HBox();
        hbo2.setAlignment(Pos.CENTER);
        hbo2.getChildren().addAll(split,cont,time);
        
        nvx.getChildren().addAll(hbo1,hbo2);
        
        
        return nvx;
        
    }
    
    
    public BorderPane getPane(int condi){
        BorderPane pane = new BorderPane();
        //BorderPane p = new BorderPane();
        //p.setCenter(getGridtab4());
        
        pane.setCenter(getGridtab4(condi));
        pane.setBottom(inscription());
        return pane;
    }
    
    public HBox inscription(){
        HBox nvx = new HBox();
        Label str = new Label("Subscribe if you don't have an account yet : ");
        Hyperlink hyperlink = new Hyperlink("Subscibe here");
        
        hyperlink.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                tab4.setContent(getSubscription());
            }
        });
        
        nvx.getChildren().addAll(str,hyperlink);
        nvx.setId("HBox-tab4");
        return nvx;
    }
    
    public GridPane getGridtab4(int condi){
        GridPane root=new GridPane(); 
        Label Email = new Label("Your ID number :");  
        Label Password = new Label("Password :");  
        Email.setId("label-tab4");
        Password.setId("label-tab4");
//Adding text-field to the form   
        final TextField tf1=new TextField();
        final PasswordField tf2=new PasswordField(); 
        
        tf1.setId("text-tab4");
        tf2.setId("text-tab4");
        
//creating submit button   
        Button submit=new Button("Submit");
        Button reset=new Button("Reset");
//setting ID for the submit button so that the particular style rules can be applied to it.   
        submit.setId("submit-tab4");
        reset.setId("submit-tab4");
//Creating reset button

//Creating title   
        Label title = new Label();  
        title.setText("Sign Up"); 
        title.setUnderline(true);  
        title.setId("title-tab4");  
        
        Label error = new Label();
        if(condi==1)
            error.setText("Password or user not found");
        else if(condi==2)
            error.setText("Enter some text in the input's fields");
        else
            error.setText("");
        error.setTextFill(RED);
//creating grid-pane  
        final CheckBox c1 = new CheckBox("Check if you are an employee"); 
//adding the the nodes to the GridPane's rows   
        
        root.addRow(0, title);
        root.addRow(3, Email);
        root.addRow(4, tf1);
        root.addRow(6, Password);
        root.addRow(7, tf2);
        root.addRow(8,error);
        root.addRow(10, c1);
        
        root.addRow(14, submit, reset);
        
        submit.setOnAction(new EventHandler<ActionEvent>() {  
              
            @Override  
            public void handle(ActionEvent arg0) {  
                // TODO Auto-generated method stub
                boolean action = false;
                controller = new Controller("login","tab4");
                
                try {
                    if(!c1.isSelected()){
                        actualMember=controller.getConnectedMember(tf1.getText(),tf2.getText());
                        action = false;
                    }
                    else{
                        actualEmployee=controller.getConnectedEmployee(tf1.getText(),tf2.getText());
                        action = true;
                    }
                    
                } catch (ClassNotFoundException | ParseException | SQLException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                if(tf1.getText().equals("") || tf2.getText().equals("")){
                    tab4.setContent(getPane(2));
                }
                else if((action==false && actualMember==null) || (action==true && actualEmployee == null)){
                    tab4.setContent(getPane(1));
                }
                else if(action==false){
                    connected.setText(" : "+actualMember.getFirstName()+" "+actualMember.getLastName());
                    tab4.setContent(membConnected());
                    System.out.println("MEMBER");
                }
                else if(action==true){
                    //nouvelle interface employee
                    System.out.println("EMPLOYEE");
                }
            }
        }); 
        
        reset.setOnAction(new EventHandler<ActionEvent>() {  
              
            @Override  
            public void handle(ActionEvent arg0) {  
                tf1.setText("");
                tf2.setText("");
            }
        });
  
//setting horizontal and vertical gaps between the rows   
        root.setHgap(10);  
        root.setVgap(10);
        root.setId("root-tab4");
        return root;
    }
    
    
    public VBox membConnected(){
        VBox nvx = new VBox(10);
        nvx.setAlignment(Pos.CENTER);
        Label lab1 = new Label("You are connected to your account"); 
        Label lab2 = new Label("First Name : "+actualMember.getFirstName());
        Label lab3 = new Label("Last Name : "+actualMember.getLastName());
        
        lab1.setId("label-co");
        lab2.setId("label-co");
        lab3.setId("label-co");
        
        nvx.getChildren().addAll(lab1,lab2,lab3);
        
        return nvx;
    }
    
    public BorderPane getSubscription(){
        
        BorderPane test = new BorderPane();
        VBox nvx = new VBox(10);
        
        nvx.setMaxWidth(400);
        nvx.setAlignment(Pos.CENTER);
        //layout.prefWidthProperty().bind(window.widthProperty().multiply(0.80));
        Label firstName = new Label("Enter your firstName :");
        Label lastName = new Label("Enter your lastName :");
        Label age = new Label("Enter your age :");
        Label password = new Label("Your Password :");
        Label confirmPassword = new Label("Confirm your password :");
//Adding text-field to the form
        TextField tf1=new TextField();
        TextField tf2=new TextField();
        TextField tf3=new TextField();
        PasswordField tf4=new PasswordField();
        PasswordField tf5=new PasswordField();
        tf1.setId("text-sub");
        tf2.setId("text-sub");
        tf3.setId("text-sub");
        tf4.setId("text-sub");
        tf5.setId("text-sub");
        
        firstName.setId("label-sub");
        lastName.setId("label-sub");
        age.setId("label-sub");
        password.setId("label-sub");
        confirmPassword.setId("label-sub");
        
        Button bout = new Button("Submit");
        bout.setId("submit-tab4");
        nvx.getChildren().addAll(firstName,tf1,lastName,tf2,age,tf3,password,tf4,confirmPassword,tf5,bout);
        
        nvx.setId("sub");
        
        test.setCenter(nvx);
        
        HBox sub = new HBox();
        Label str = new Label("Login if you already have an account : ");
        Hyperlink hyperlink = new Hyperlink("Login here");
        
        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
                tab4.setContent(getPane(0));
            }
        });
        
        
        sub.getChildren().addAll(str,hyperlink);
        sub.setId("HBox-tab4");
        
        test.setBottom(sub);
        
        return test;
    }
    
    public BorderPane getDiscount(){
        BorderPane pane = new BorderPane();
        pane.setCenter(getGridtab2());
        HBox test = new HBox();
        test.setAlignment(Pos.CENTER);
        Button button = new Button("Sign-in/Sign-up");
        button.setId("button-tab2");
        button.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        test.getChildren().add(button);
        pane.setBottom(test);
        BorderPane.setMargin(button,new Insets(10,0,0,550));
        
        button.setOnAction(new EventHandler<ActionEvent>() {  
              
            @Override  
            public void handle(ActionEvent arg0) {  
                // TODO Auto-generated method stub
                tab4.setContent(getSubscription());
                tabPane.getSelectionModel().select(tab4);
            }
        });  

        return pane;
    }
    
    public GridPane getGridtab2(){
        
        GridPane root=new GridPane();
        root.setId("root-tab2");
        root.setAlignment(Pos.CENTER);
        
        Rectangle info1 = new Rectangle();
        info1.setStrokeWidth(5);info1.setStroke(Color.BLACK);
        info1.setX(80);info1.setY(50);info1.setWidth(1120);info1.setHeight(200);info1.setArcHeight(50);info1.setArcWidth(50);info1.setFill(Color.WHITE);
        Rectangle rect1 = new Rectangle();
        rect1.setStrokeWidth(5);rect1.setStroke(Color.BLACK);
        rect1.setX(80);rect1.setY(50);rect1.setWidth(300);rect1.setHeight(200);rect1.setArcHeight(50);rect1.setArcWidth(50);rect1.setFill(Color.GRAY); 
        Rectangle rect2 = new Rectangle();
        rect2.setStrokeWidth(5);rect2.setStroke(Color.BLACK);
        rect2.setX(490);rect2.setY(50);rect2.setWidth(300);rect2.setHeight(200);rect2.setArcHeight(50);rect2.setArcWidth(50);rect2.setFill(Color.GRAY);
        Rectangle rect3 = new Rectangle();
        rect3.setStrokeWidth(5);rect3.setStroke(Color.BLACK);
        rect3.setX(900);rect3.setY(50);rect3.setWidth(300);rect3.setHeight(200);rect3.setArcHeight(50);rect3.setArcWidth(50);rect3.setFill(Color.GRAY);
        
        Label title = new Label("SIGN-UP & GET A DISCOUNT!");
        Label children = new Label("CHILDREN");
        Label regular = new Label("REGULAR");
        Label senior = new Label("SENIOR");
        Label d10 = new Label("-10%");
        Label d20 = new Label("-20%");
        Label d30 = new Label("-30%");
        Label title2 = new Label("Benefits all year round!!");
        Label text = new Label("Thanks to CinéPass, enjoy exclusive benefits and offers throughout the year.\n" +"To make sure you don't miss out on anything, receive our communications by newsletter .");
        
        title.setId("title-tab2");
        children.setId("children-tab2");
        regular.setId("regular-tab2");
        senior.setId("senior-tab2");
        title2.setId("title2-tab2");
        text.setId("text-tab2");
        d10.setId("d10");
        d20.setId("d20");
        d30.setId("d30");
            
        
        Group rects = new Group();
        rects.getChildren().add(rect1);
        rects.getChildren().add(rect2);
        rects.getChildren().add(rect3);
        rects.getChildren().add(children);
        rects.getChildren().add(regular);
        rects.getChildren().add(senior);
        rects.getChildren().add(d10);
        rects.getChildren().add(d20);
        rects.getChildren().add(d30);
        
        
        Group infos = new Group();
        infos.getChildren().add(info1);
        infos.getChildren().add(title2);
        infos.getChildren().add(text);
        
        root.addRow(0, title);
        root.addRow(2, rects);
        root.addRow(4, infos);
        root.setHgap(10);  
        root.setVgap(10);
        
        return root;
        
    }
    
}
