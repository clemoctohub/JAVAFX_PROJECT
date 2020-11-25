/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.scene.image.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.shape.*;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import static javafx.scene.paint.Color.*;
import java.net.MalformedURLException;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author clemf
 */
public class MainClass extends Application {
    
    private final Tab tab1 = new Tab("Home"),tab2 = new Tab("CinePass"),tab3 = new Tab("Search"),tab4 = new Tab("Connection");
    private final TabPane tabPane = new TabPane();
    private Members actualMember;
    private Employees actualEmployee;
    private Controller controller;
    private Label connected;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    
    @Override
    public void start(Stage primaryStage) throws MalformedURLException, FileNotFoundException {
        
        Label bande = new Label("WELCOME TO OUR CINEMA");
        bande.setId("bande");
        connected = new Label("");
        connected.setId("bande");
        connected.setAlignment(Pos.CENTER_RIGHT);
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.getChildren().addAll(bande,connected);
        tab2.setContent(getDiscount());
        tab3.setContent(searchTab(false,null));
        tab4.setContent(getPane(0));
        tab1.setContent(getSPane());
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(header);
        vbox.getChildren().add(tabPane);
        
        Scene scene = new Scene(vbox, 1900, 1000);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setTitle("CINEMA");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }
    
    private double calculatePrice(int i){
        Cinema cine = new Cinema();
        double j;
        if(!connected.getText().equals("")){
            j = cine.prixTicket(actualMember,i);
            return j;
        }
        else
            return i*cine.getPrix();
    }
    
    public VBox buyItemsPage(int id){
        VBox tot = new VBox(50);
        tot.setId("paid-page");
        tot.setAlignment(Pos.CENTER);
        Label ok = new Label("Your command is on board, you can go to your session !");
        Label dac = new Label("Here is your ID number for the session :");
        Label idi = new Label(Integer.toString(id));
        idi.setTextFill(RED);
        Label war = new Label("Please keep it if you want to modify your place");
        
        Button but = new Button("OK");
        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab3.setContent(searchTab(false,null));
                tab1.setContent(getSPane());
                if(!connected.getText().equals(""))
                    tab4.setContent(connectedOK());
                tabPane.getSelectionModel().select(tab1);
            }
        });
        tot.getChildren().addAll(ok,dac,idi,war,but);
        return tot;
    }
    
    public VBox dispMovieToBuy(final Movies movie,Session sess,final ArrayList<Movies> movies,final int tab){
        VBox tot = new VBox(50);
        
        tot.setAlignment(Pos.CENTER);
        VBox node = new VBox(20);
        HBox detail = new HBox(20);
        detail.setAlignment(Pos.CENTER);
        node.setAlignment(Pos.CENTER);
        String maString = movie.getTitle().substring(0,1).toUpperCase()+movie.getTitle().substring(1);
        Label lab1 = new Label("Title : "+maString);
        Label lab2 = new Label("Author : "+movie.getAuthor());
        Label lab3 = new Label("Date : "+movie.getDate());
        Label lab4 = new Label("Type : "+movie.getType());
        Label lab5 = new Label("Runing time : "+movie.getRunningTime());
        
        node.getChildren().addAll(lab1,lab2,lab3,lab4,lab5);
        
        Label bis1 = new Label("Date : 2000-12-12");
        Label bis2 = new Label("Heure : 12h12");
        
        detail.getChildren().addAll(bis1,bis2);
        String nom = movie.getTitle();
        nom = nom.toLowerCase();
        nom = nom.replaceAll(" ", "_");
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/"+nom+".jpg"));
        ImageView view = new ImageView(img);
        view.setFitHeight(300);
        view.setPreserveRatio(true);
        
        
        Button but = new Button();
        but.setId("back-but");
        Image img2;
        img2 = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(90);
        view2.setPreserveRatio(true);
        but.setGraphic(view2);
        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                if(tab==3)
                    tab3.setContent(dispSeance(movie,movies,3));
                else if(tab==1)
                    tab1.setContent(dispSeance(movie,movies,1));
            }
        });
        tot.getChildren().addAll(but,view,detail,node);
        
        return tot;
    }
    
    public VBox dispPaiCustomer(final Session sess,final int tab){
        HBox nvx = new HBox(20);
        nvx.setAlignment(Pos.CENTER);
        Image[] img = new Image[4];
        ImageView[] view = new ImageView[4];
        for(int i=0;i<4;i++){
            img[i] = new Image(getClass().getResourceAsStream("/images/paie"+i+".JPG"));
            view[i] = new ImageView(img[i]);
            view[i].setFitHeight(90);
            view[i].setPreserveRatio(true);
            nvx.getChildren().add(view[i]);
        }
        
        VBox input = new VBox(20);
        input.setAlignment(Pos.CENTER);
        final TextField num = new TextField();
        num.setPromptText("Enter your number card");
        num.setAlignment(Pos.CENTER);
        HBox both = new HBox();
        both.setAlignment(Pos.CENTER);
        final TextField crypto = new TextField();
        crypto.setPromptText("Enter your crypto");
        final TextField mv = new TextField();
        mv.setPromptText("Ex : 01/21");
        both.getChildren().addAll(crypto,mv);
        final Label error = new Label();
        error.setAlignment(Pos.CENTER);
        error.setTextFill(RED);
        error.setText("");
        input.getChildren().addAll(num,both,error);
        num.setId("box-pay");
        both.setId("box-pay");
        HBox ad = new HBox(10);
        ad.setAlignment(Pos.CENTER);
        final Button plus = new Button("+");
        Button moins = new Button("-");
        HBox email = new HBox(20);
        email.setAlignment(Pos.CENTER);
        Label ver = new Label("Please enter your mail (login if you are a member) before to click : ");
        ver.setStyle("-fx-font-weigth: bold");
        final TextField mail = new TextField();
        mail.setId("box-pay");
        mail.setPromptText("Enter your mail/login");
        email.getChildren().addAll(ver,mail);
        final Label nbr = new Label();
        nbr.setText("0");
        final Label disc = new Label();
        disc.setId("button-pay");
        disc.setText("Total : 0 $");
        
        plus.setOnAction(new EventHandler<ActionEvent>() {
            @Override  
            public void handle(ActionEvent arg0) {
                int i = Integer.parseInt(nbr.getText());
                i++;
                if(sess.getActual()+i>= sess.getNbr_places_max()){
                   plus.setDisable(true); 
                }
                double j = calculatePrice(i);
                disc.setText("Total : "+Double.toString(j)+" $");
                nbr.setText(Integer.toString(i));
            }
        });
        moins.setOnAction(new EventHandler<ActionEvent>() {
            @Override  
            public void handle(ActionEvent arg0) {
                int i = Integer.parseInt(nbr.getText());
                double j=0;
                i--;
                if(sess.getActual()+i<sess.getNbr_places_max()){
                   plus.setDisable(false); 
                }
                if(i<0)
                    i=0;
                else{
                    j = calculatePrice(i);
                }
                disc.setText("Total : "+Double.toString(j)+" $"); 
                nbr.setText(Integer.toString(i));
            }
        });
        
        Button but = new Button("Ready to pay !");
        but.setId("button-pay");
        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override  
            public void handle(ActionEvent arg0) {
                int condi;
                if(nbr.getText().equals("0") || num.getText().equals("") || crypto.getText().equals("") || mv.getText().equals("")){
                    error.setText("Please fill all the inputs");
                }
                else{
                    controller = new Controller("pay","paypage");
                    Date date = new Date();
                    condi = controller.addCustomerToSession(num.getText(),crypto.getText(),mv.getText(),sess.getId(),disc.getText(),nbr.getText(),sdf.format(date),mail.getText());
                    if(condi==-1){
                        error.setText("Please enter correct inputs");
                    }
                    else{
                        if(tab==3)
                            tab3.setContent(buyItemsPage(condi));
                        else if(tab==1)
                            tab1.setContent(buyItemsPage(condi));
                    }
                }
                
            }
        });
        ad.getChildren().addAll(moins,nbr,plus);
        VBox intermediaire = new VBox(20);
        intermediaire.setAlignment(Pos.CENTER);
        intermediaire.setId("button-pay");
        Label nbr_tick = new Label("Number of tickets : ");
        intermediaire.getChildren().addAll(nbr_tick,ad);
        VBox container = new VBox(80);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(nvx,input,intermediaire,email,disc,but);
        
        return container;
    }
    
    public VBox dispTicketCustomer(){
        VBox tot = new VBox(20);
        tot.setId("co-pay");
        Label co = new Label("You are connected as : ");
        co.setAlignment(Pos.CENTER);
        final Label nom = new Label();
        nom.setText("");
        if(!connected.getText().equals("")){
            nom.setText(actualMember.getFirstName()+" "+actualMember.getLastName());
            nom.setAlignment(Pos.CENTER);
        }
        else{
            nom.setText("A Customer");
            nom.setAlignment(Pos.CENTER);
        }
        Button but = new Button();
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/refresh.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        but.setPrefSize(50, 50);
        but.setGraphic(view);
        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                if(!connected.getText().equals("")){
                    nom.setText(actualMember.getFirstName()+" "+actualMember.getLastName());
                    nom.setAlignment(Pos.CENTER);
                }
                else if(connected.getText().equals("")){
                    nom.setText("A Customer");
                }
            }
        });
        tot.getChildren().addAll(co,nom,but);
        return tot;
    }
    
    public BorderPane paymentPage(Movies movie,Session sess,final ArrayList<Movies> movies,int tab){
        BorderPane tot = new BorderPane();
        tot.setLeft(dispMovieToBuy(movie,sess,movies,tab));
        tot.setCenter(dispPaiCustomer(sess,tab));
        tot.setRight(dispTicketCustomer());
        //tot.setAlignment(Pos.CENTER);
        return tot;
    }
    
    public BorderPane dispMovie(Movies movie,final ArrayList<Movies> movies, final int tab){
        GridPane tot = new GridPane();
        BorderPane border = new BorderPane();
        
        tot.setHgap(100);
        tot.setAlignment(Pos.CENTER);
        VBox node = new VBox(20);
        node.setAlignment(Pos.CENTER);
        String maString = movie.getTitle().substring(0,1).toUpperCase()+movie.getTitle().substring(1);
        Label lab1 = new Label("Title : "+maString);
        Label lab2 = new Label("Author : "+movie.getAuthor());
        Label lab3 = new Label("Date : "+movie.getDate());
        Label lab4 = new Label("Type : "+movie.getType());
        Label lab5 = new Label("Runing time : "+movie.getRunningTime());
        
        
        String desc = movie.getDescription();
        for(int i=0;i<desc.length();i++){
            if(i%40==0 && i!=0){
                if(desc.charAt(i+1)==' ' || desc.charAt(i+1)=='.' || desc.charAt(i)==' ' || desc.charAt(i)=='.'){
                    desc = desc.substring(0,i) + "\n"+ desc.substring(i);
                }
                else{
                    desc = desc.substring(0,i) + "-\n"+ desc.substring(i);
                }
            } 
        }
        Label description = new Label(desc);
        description.setId("desc-sess");
        node.setId("desc-sess");
        String nom = movie.getTitle();
        nom = nom.toLowerCase();
        nom = nom.replaceAll(" ", "_");
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/"+nom+".jpg"));
        ImageView view = new ImageView(img);
        view.setFitHeight(300);
        view.setPreserveRatio(true);
        
        int temp = movie.getRate()/2;
        Image img3;
        img3 = new Image(getClass().getResourceAsStream("/images/"+temp+"star.jpg"));
        ImageView view3 = new ImageView(img3);
        view3.setPreserveRatio(true);
        node.getChildren().addAll(lab1,lab2,lab3,lab4,lab5,view3);
        
        tot.addColumn(0, view);
        tot.addColumn(1, description);
        tot.addColumn(2, node);
        
        
        Button but = new Button();
        but.setId("back-but");
        Image img2;
        img2 = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(90);
        view2.setPreserveRatio(true);
        but.setGraphic(view2);
        but.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                if(tab==3)
                    tab3.setContent(searchTab(true,movies));
                else if(tab==1)
                    tab1.setContent(getSPane());
            }
        });
        border.setCenter(tot);
        border.setLeft(but);
        return border;
    }
    
    public ScrollPane dispAllSess(final Movies movie,final ArrayList<Movies> movies, final int tab){
        ScrollPane tot = new ScrollPane();
        GridPane nvx = new GridPane();
        nvx.setHgap(50);
        ArrayList<Session> sess = new ArrayList<>();
        controller = new Controller("session","tab3");
        try{
            sess = controller.getSessionMovie(movie.getId());
        }
        catch(SQLException | ClassNotFoundException | ParseException e){
            System.out.println("ERROR");
        }
        
        Label date_txt = new Label("Date ");
        Label heure_txt = new Label("Heure ");
        Label nbr_txt = new Label("Number of places ");
        Label reserver = new Label("Click to reserve a place");
        
        ArrayList<Button> button = new ArrayList<>();
        VBox node1 = new VBox(20),node2 = new VBox(20),node3 = new VBox(20),node4 = new VBox(20),node5 = new VBox(20);
        node1.setId("box-session");
        node2.setId("box-session");
        node3.setId("box-session");
        node4.setId("button-session");
        node5.setId("box-session");
        reserver.setId("button-session");
        node1.getChildren().add(date_txt);
        node2.getChildren().add(heure_txt);
        node3.getChildren().add(nbr_txt);
        node4.getChildren().add(reserver);
        node5.getChildren().add(new Label(""));
        for(int i=0;i<sess.size();i++){
            String temp = sess.get(i).getDate().toString();
            Label date = new Label(temp);
            Label heure = new Label(sess.get(i).getHoraire());
            String nbrr = Integer.toString(sess.get(i).getNbr_places_max());
            Label nbr = new Label("Number of places "+nbrr);
            Label max = new Label("");
            
            button.add(new Button("Reserve"));
            node1.getChildren().add(date);
            node2.getChildren().add(heure);
            node3.getChildren().add(nbr);
            if(sess.get(i).getActual()>=sess.get(i).getNbr_places_max()){
                button.get(i).setDisable(true);
                max.setText("Full session");
                max.setTextFill(RED);
            }
            else{
                max.setText(sess.get(i).getActual()+" / "+sess.get(i).getNbr_places_max()+" places left");
                max.setTextFill(GREEN);
            }
            node4.getChildren().add(button.get(i));
            node5.getChildren().add(max);
            final Button mybut = button.get(i);
            final Session session = sess.get(i);
            mybut.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    if(tab==1)
                        tab1.setContent(paymentPage(movie,session,movies,1));
                    else if(tab==3)
                        tab3.setContent(paymentPage(movie,session,movies,3));
                }
            });
        }
        nvx.addColumn(0,node1);
        nvx.addColumn(1,node2);
        nvx.addColumn(2,node3);
        nvx.addColumn(3,node4);
        nvx.addColumn(4,node5);
        tot.setContent(nvx);
        return tot;
    }
    
    public BorderPane dispSeance(Movies movie,ArrayList<Movies> movies, int tab){
        BorderPane tot = new BorderPane();
        tot.setTop(dispMovie(movie,movies,tab));
        tot.setCenter(dispAllSess(movie,movies,tab));
        return tot;
    }
    
    public BorderPane searchTab(boolean condi,ArrayList<Movies> movies){
        BorderPane nvx = new BorderPane();
        nvx.setTop(searchVbox());
        
        if(condi==true){
            nvx.setCenter(resultMovies(movies));
        }
        
        return nvx;
    }
    
    public ScrollPane resultMovies(final ArrayList<Movies> movies){
        ScrollPane bar = new ScrollPane();
        
        GridPane tot = new GridPane();
        
        tot.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(30);
        ArrayList<Button> tabButton = new ArrayList<>(); 
        for(int i=0;i<movies.size();i++){
            Image image;
            ImageView view;
            String nomfilm = movies.get(i).getTitle();
            nomfilm = nomfilm.toLowerCase();
            nomfilm = nomfilm.replaceAll(" ","_");
            image = new Image(getClass().getResourceAsStream("/images/"+nomfilm+".jpg"));
            view = new ImageView(image);
            view.setFitWidth(70);
            view.setPreserveRatio(true);
            String maString = movies.get(i).getTitle().substring(0,1).toUpperCase()+movies.get(i).getTitle().substring(1);
            Label nom = new Label("Title : "+maString+" ");
            Label auteur = new Label("Author : "+movies.get(i).getAuthor()+" ");
            Label date = new Label("Date : "+movies.get(i).getDate()+" ");
            tabButton.add(new Button("Reserve"));
            HBox hbo = new HBox(20);
            hbo.setAlignment(Pos.CENTER_LEFT);
            hbo.setId("disp-movie");
            tabButton.add(new Button("Reserve"));
            tabButton.get(i).setId("disp-button");
            hbo.getChildren().addAll(view,nom,auteur,date,tabButton.get(i));
            vbox.getChildren().add(hbo);
            
            
            final Button mybut = tabButton.get(i);
            final Movies movie = movies.get(i);
            mybut.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    tab3.setContent(dispSeance(movie,movies,3));
                }
            });
        }
        tot.addColumn(0, vbox);
        tot.setId("search");
        bar.setContent(tot);
        bar.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        return bar;
    }
    
    public VBox searchVbox(){
        VBox nvx = new VBox(50);
        //nvx.setAlignement(Pos.CENTER);
        final TextField recherche;
        recherche = new TextField();
        recherche.setText("");
        recherche.setPromptText("Name of the movie");
        VBox hbo1 = new VBox();
        hbo1.setAlignment(Pos.CENTER);
        hbo1.getChildren().add(recherche);
        
        final ChoiceBox split = new ChoiceBox();
        split.getItems().addAll("Action","Science-Fiction","Horror","Fantastic","Comedia");
        
        final TextField lab1 = new TextField();
        lab1.setPromptText("Month");
        Label lab2 = new Label("/");
        lab1.setText("");
        lab2.setText("");
        final TextField lab3 = new TextField();
        lab3.setPromptText("Day");
        HBox cont = new HBox(10);
        cont.getChildren().addAll(lab1,lab2,lab3);
        
        recherche.setId("bar-search");
        nvx.setId("vbox-search");
        lab1.setId("lab-search");
        lab2.setId("lab-search");
        lab3.setId("lab-search");
        split.setId("lab-search");
        
        
        final TextField time = new TextField();
        time.setText("");
        time.setPromptText("Running time");
        time.setId("lab-search");
        HBox hbo2 = new HBox(50);
        hbo2.setAlignment(Pos.CENTER);
        hbo2.getChildren().addAll(split,cont,time);
        
        HBox HButton = new HBox(100);
        HButton.setAlignment(Pos.CENTER);
        Button button = new Button("Search");
        button.setId("but-search");
        Button reset = new Button("Reset");
        reset.setId("but-search");
        HButton.getChildren().addAll(button,reset);
        
        nvx.getChildren().addAll(hbo1,hbo2,HButton);
        
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override  
            public void handle(ActionEvent arg0) {
                ArrayList<Movies> movies = new ArrayList<>();
                controller = new Controller("search","search");
                try {
                    movies = controller.searchMovies(recherche.getText(),(String)split.getValue(),time.getText(),lab3.getText(),lab1.getText());
                } catch (ClassNotFoundException | ParseException | SQLException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(movies!=null && movies.size()>0){
                    tab3.setContent(searchTab(true,movies));
                } 
                else{
                    recherche.setText("");
                    lab1.setText("");
                    lab3.setText("");
                    time.setText("");
                }
            }
        });
        
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override  
            public void handle(ActionEvent arg0) {
                tab3.setContent(searchTab(false,null));
            }
        });
        
        return nvx;
        
    }
    
    public BorderPane getPane(int condi){
        BorderPane pane = new BorderPane();
        
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
                tab4.setContent(getSubscription(""));
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
        tf1.setPromptText("Your username");
        tf2.setPromptText("Your password");
//creating submit button
        Button submit=new Button("Sign in !");
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
        root.addRow(1, Email);
        root.addRow(2, tf1);
        root.addRow(4, Password);
        root.addRow(5, tf2);
        root.addRow(6,error);
        root.addRow(8, c1);
        
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
                    String nom,prenom;
                    prenom = actualMember.getFirstName().substring(0, 1).toUpperCase()+actualMember.getFirstName().substring(1);
                    nom = actualMember.getLastName().substring(0, 1).toUpperCase()+actualMember.getLastName().substring(1);
                    connected.setText(" : "+prenom+" "+nom);
                    tab4.setContent(connectedOK());
                }
                else if(action==true){
                    AutreMain autre = new AutreMain(actualEmployee);
                    tf1.setText("");
                    tf2.setText("");
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
    
    public BorderPane connectedOK(){
        BorderPane pane = new BorderPane();
        pane.setRight(changeMdp());
        pane.setCenter(membConnecte());
        pane.setLeft(dispSessOfMemb());
        return pane;
    }
    
    public VBox dispSessOfMemb(){
        VBox tot = new VBox(50);
        tot.setAlignment(Pos.CENTER);
        tot.setId("changepass");
        controller = new Controller("session_member_connected","tab4");
        ArrayList<Session> sess;
        ArrayList<Integer> tt;
        sess = controller.getSessionConnected(actualMember.getLogin());
        tt = controller.getIdCustomerSess(actualMember.getLogin());
        
        Label title = new Label("Your reservations : ");
        tot.getChildren().add(title);
        if(sess.isEmpty()){
            tot.getChildren().add(new Label("You don't have any reservation"));
        }
        for(int i=0;i<sess.size();i++){
            HBox tst = new HBox(20);
            tst.setAlignment(Pos.CENTER);
            Label lab1 = new Label((i+1)+")");
            Label lab2 = new Label(controller.getAMovie(sess.get(i).getMovie()));
            Label lab3 = new Label(sess.get(i).getDate().toString());
            Label lab4 = new Label(sess.get(i).getHoraire());
            Label lab5 = new Label("Id : "+Integer.toString(tt.get(i)));
            tst.getChildren().addAll(lab1,lab2,lab3,lab4,lab5);
            tot.getChildren().add(tst);
        }
        
        return tot;
    }
    
    public VBox changeMdp(){
        VBox tot = new VBox(50);
        tot.setAlignment(Pos.CENTER);
        final PasswordField txt1 = new PasswordField();
        txt1.setPromptText("New Password");
        final PasswordField txt2 = new PasswordField();
        txt2.setPromptText("Confirm Password");
        final Label txt3 = new Label();
        txt3.setTextFill(RED);
        txt3.setText("");
        txt1.setId("changepass");
        txt2.setId("changepass");
        txt3.setId("changepass");
        Button txt4 = new Button("Change password");
        txt4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if(!txt1.getText().equals(txt2.getText())){
                    txt3.setText("Not same password");
                }
                else if(txt1.getText().equals("")){
                    txt3.setText("Enter a password");
                }
                else{
                    controller = new Controller("changeMdp","tab4");
                    controller.changePassword(txt1.getText(),actualMember.getLogin());
                    txt3.setText("Changed !");
                }
            }
        });
        tot.getChildren().addAll(txt1,txt2,txt3,txt4);
        return tot;
    }
    
    public VBox membConnecte(){
        VBox nvx = new VBox(10);
        nvx.setAlignment(Pos.CENTER);
        Label lab1 = new Label("You are connected to your account !"); 
        Label lab2 = new Label("First Name : "+actualMember.getFirstName());
        Label lab3 = new Label("Last Name : "+actualMember.getLastName());
        Label lab4 = new Label("Login : "+actualMember.getLogin());
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/deco.jpg"));
        ImageView view = new ImageView(img);
        view.setFitHeight(80);
        view.setPreserveRatio(true);
        Button deco = new Button();
        deco.setPrefSize(80, 75);
        deco.setGraphic(view);
        lab1.setId("label-co");
        lab2.setId("label-co");
        lab3.setId("label-co");
        lab4.setId("label-co");
        HBox deconect = new HBox(50);
        deconect.setAlignment(Pos.CENTER);
        Label deconection = new Label("Click here to disconect : ");
        deconection.setId("label-co");
        deconect.getChildren().addAll(deconection,deco);
        nvx.getChildren().addAll(deconect,lab1,lab2,lab3,lab4);
        
        deco.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                connected.setText("");
                actualMember = null;
                tab4.setContent(getPane(0));
            }
        });
        
        return nvx;
    }

    public BorderPane getSPane()
    {   
        BorderPane pane = new BorderPane();
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(getPanetab1());
        scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        pane.setCenter(scroll);
        pane.setRight(deletePlace());
        pane.setTop(dispPriceReduc());
        return pane;
    }
    
    public HBox dispPriceReduc(){
        Cinema cine = new Cinema();
        HBox nvx = new HBox(10);
        nvx.setAlignment(Pos.CENTER);
        nvx.setId("boxtoptab1");
        Label first = new Label("Ticket's price : "+cine.getPrix()+" $\t\t");
        Label second = new Label("Click here to see the promotion : ");
        first.setId("toptab1");
        second.setId("toptab1");
        Button third = new Button("See discount offers");
        Button but = new Button("Refresh");
        
        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab1.setContent(getSPane());
            }
        });
        
        third.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tabPane.getSelectionModel().select(tab2);
            }
        });
        
        nvx.getChildren().addAll(but,first,second,third);
        return nvx;
    }
    
    public VBox deletePlace(){
        VBox nvx = new VBox(50);
        nvx.setAlignment(Pos.CENTER);
        nvx.setId("deletePlace");
        VBox inter = new VBox(20);
        Label txt = new Label("If you want to remove your place,");
        Label txt2 = new Label("enter your id session and email/login here : ");
        inter.getChildren().addAll(txt,txt2);
        
        final TextField id = new TextField();
        final TextField mail = new TextField();
        id.setPromptText("Place's ID");
        mail.setPromptText("Enter your mail");
        final Label error = new Label();
        
        error.setText("");
        Button button = new Button("Remove my place");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                controller = new Controller("delete","tab1");
                boolean condi = controller.delete_session_customer(id.getText(),mail.getText());
                if(condi==false){
                    error.setText("Not found or wrong input");
                    error.setTextFill(RED);
                }
                else{
                    error.setText("Remove !");
                    error.setTextFill(GREEN);
                    if(!connected.getText().equals(""))
                        tab4.setContent(connectedOK());
                }
                id.setText("");
                mail.setText("");
            }
        });
        nvx.getChildren().addAll(inter,id,mail,error,button);
        return nvx;
    }
    
    public HBox getGridtab1(Movies movies)
    {
        HBox tot = new HBox(50);
        tot.setAlignment(Pos.CENTER);
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        String maString = movies.getTitle().substring(0,1).toUpperCase()+movies.getTitle().substring(1);
        Label Titre = new Label("Title : "+maString);  
        Label Author = new Label("Author : "+movies.getAuthor());
        Label RD = new Label("Release Date : "+movies.getDate());
        Label GT = new Label("Gender type : "+movies.getType());
        Label RT = new Label("Running Time : "+movies.getRunningTime());
        
        int temp = movies.getRate()/2;
        Image img3;
        img3 = new Image(getClass().getResourceAsStream("/images/"+temp+"star.jpg"));
        ImageView view3 = new ImageView(img3);
        view3.setPreserveRatio(true);
        
        gp.addRow(0 ,Titre);
        gp.addRow(1, Author);
        gp.addRow(2, RD);
        gp.addRow(3, GT);
        gp.addRow(4, RT);
        gp.addRow(5, view3);
        gp.setVgap(10);
        String desc = movies.getDescription();
        for(int i=0;i<desc.length();i++){
            if(i%40==0 && i!=0){
                if(desc.charAt(i+1)==' ' || desc.charAt(i+1)=='.' || desc.charAt(i)==' ' || desc.charAt(i)=='.'){
                    desc = desc.substring(0,i) + "\n"+ desc.substring(i);
                }
                else{
                    desc = desc.substring(0,i) + "-\n"+ desc.substring(i);
                }
            }
        }
        Label tempo = new Label(desc);
        tot.getChildren().addAll(gp,tempo);
        
        return tot;
    }
    public FlowPane getPanetab1()
    {
        ArrayList<Movies> movies;
        controller = new Controller("movie","tab1");
        movies = controller.dispAllMovies();
        FlowPane pane = new FlowPane();
        GridPane gp = new GridPane();
        ArrayList<Button> tabButton = new ArrayList<>();
        ArrayList<ImageView> view = new ArrayList<>();
        ArrayList<Image> image = new ArrayList<>();
        for(int i=0; i<movies.size() ;i++)
        {
            tabButton.add(new Button());
            HBox box = new HBox(20);
            String nomfilm = movies.get(i).getTitle();
            nomfilm = nomfilm.toLowerCase();
            nomfilm = nomfilm.replaceAll(" ","_");
            image.add(new Image(getClass().getResourceAsStream("/images/"+nomfilm+".jpg")));
            view.add(new ImageView(image.get(i)));
            view.get(i).setFitWidth(170);
            view.get(i).setPreserveRatio(true);
            tabButton.get(i).setPrefSize(170,view.get(i).getY());
            tabButton.get(i).setGraphic(view.get(i));
            box.getChildren().addAll(tabButton.get(i),getGridtab1(movies.get(i)));
            gp.addRow(i , box);
            final Button mybut = tabButton.get(i);
            final Movies movie = movies.get(i);
            final ArrayList<Movies> M = movies;
            mybut.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    tab1.setContent(dispSeance(movie, M,1));
                    String right = connected.getText();
                    if(!right.equals("")) {
                        tab4.setContent(connectedOK());
                    } else {
                    }
                }
            });
        }
        gp.setVgap(50);
        pane.setPrefSize(300,1000);
        pane.getChildren().add(gp);
        //pane.getChildren().add(getGridtab1());
        return pane;
    }
    public VBox giveId(String nom){
        VBox tot = new VBox(50);
        tot.setId("paid-page");
        tot.setAlignment(Pos.CENTER);
        Label ok = new Label("Your are part of the team now !");
        Label dac = new Label("Here is your ID to login / keep it preciously :");
        Label idi = new Label(nom);
        idi.setTextFill(RED);
        
        Button but = new Button("OK");
        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab4.setContent(connectedOK());
            }
        });
        tot.getChildren().addAll(ok,dac,idi,but);
        return tot;
    }
    
    public BorderPane getSubscription(String message){
        
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
        final Label error = new Label(message);
        error.setTextFill(RED);
//Adding text-field to the form
        final TextField tf1=new TextField();
        final TextField tf2=new TextField();
        final TextField tf3=new TextField();
        final PasswordField tf4=new PasswordField();
        final PasswordField tf5=new PasswordField();
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
        
        Button bout = new Button("Sign up !");
        bout.setId("submit-tab4");
        nvx.getChildren().addAll(firstName,tf1,lastName,tf2,age,tf3,password,tf4,confirmPassword,tf5,error,bout);
        
        nvx.setId("sub");
        
        bout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                int condi=-1;
                controller = new Controller("subscription","tab4");
                if(!tf5.getText().equals(tf4.getText()) || tf4.getText().equals("")){
                    tab4.setContent(getSubscription("Please enter same password"));
                }
                else{
                    try {
                        condi = controller.createMember(tf1.getText(),tf2.getText(),tf3.getText(),tf4.getText());
                    } catch (SQLException | ClassNotFoundException | ParseException ex) {
                        Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(condi>0){
                        String login = tf1.getText()+Integer.toString(condi)+"."+tf2.getText();
                        int age = Integer.parseInt(tf3.getText());
                        actualMember = new Members(tf1.getText(),tf2.getText(),tf3.getText(),age,login);
                        tab4.setContent(giveId(login));
                        connected.setText(" : "+actualMember.getFirstName()+" "+actualMember.getLastName());
                    }
                    else if(condi==0){
                        String login = tf1.getText()+"."+tf2.getText();
                        int age = Integer.parseInt(tf3.getText());
                        actualMember = new Members(tf1.getText(),tf2.getText(),tf3.getText(),age,login);
                        tab4.setContent(giveId(login));
                        connected.setText(" : "+actualMember.getFirstName()+" "+actualMember.getLastName());
                    }
                    else{
                        tab4.setContent(getSubscription("Wrong input try again please"));
                    }
                }
            }
        });
        
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
        button.setStyle("-fx-font: 22 arial; -fx-background-color : linear-gradient(from 0% 0% to 150% 200%, repeat, yellow 0%, red 50%);");
        test.getChildren().add(button);
        HBox bouton = new HBox(100);
        bouton.setAlignment(Pos.CENTER);
        BorderPane.setMargin(button,new Insets(10,0,0,550));
        Button but = new Button("Refresh Page");
        bouton.getChildren().addAll(but,button);
        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override  
            public void handle(ActionEvent arg0) {
                tab2.setContent(getDiscount());
            }
        });  
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override  
            public void handle(ActionEvent arg0) {
                if(connected.getText().equals(""))
                    tab4.setContent(getSubscription(""));
                tabPane.getSelectionModel().select(tab4);
            }
        });  
        pane.setBottom(bouton);
        return pane;
    }
    
    public GridPane getGridtab2(){
        Cinema cine = new Cinema();
        GridPane root=new GridPane();
        root.setId("root-tab2");
        root.setAlignment(Pos.CENTER);
        
        Image carte = new Image(getClass().getResourceAsStream("/images/carteCinePass.png"));
        ImageView view;
        view = new ImageView(carte);
        view.setFitHeight(300);
        view.setPreserveRatio(true);
        view.setX(-80);
        view.setY(-10);
        
        Text titre = new Text("The CinéPass");
        titre.setId("titre-tab2");
        DropShadow drop = new DropShadow();  
        drop.setBlurType(BlurType.GAUSSIAN);  
        drop.setColor(Color.BLACK);  
        drop.setHeight(100);  
        drop.setWidth(150);  
        drop.setOffsetX(10);  
        drop.setOffsetY(10);  
        drop.setSpread(0.2);  
        drop.setRadius(10);
        titre.setEffect(drop);
        Text discounts = new Text(cine.getDescription());
        discounts.setId("discounts-tab2");
        discounts.setY(50);
        
        Rectangle infos = new Rectangle();
        infos.setStrokeWidth(2);infos.setStroke(Color.BLACK);
        infos.setX(90);infos.setY(50);infos.setWidth(630);infos.setHeight(200);infos.setArcHeight(50);infos.setArcWidth(50);infos.setFill(Color.WHITE);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(infos,discounts);
        stack.setLayoutX(70);
        stack.setLayoutY(60);
        
        Group rects = new Group();
        rects.getChildren().addAll(stack,view);        
        
        root.addRow(0, titre);
        root.addRow(1, rects);
        //root.addRow(2, discounts);

        root.setHgap(10);  
        root.setVgap(7);
        
        return root; 
    }   
}
