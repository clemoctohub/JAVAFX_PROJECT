/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

//import com.sun.javafx.scene.layout.region.BackgroundFill;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javafx.scene.image.*;
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
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

/**
 *
 * @author clemf
 */
public class MainClass extends Application {
    
    private final Tab tab1 = new Tab("Home"),tab2 = new Tab("CinePass"),tab3 = new Tab("Search"),tab4 = new Tab("Connection");
    private final TabPane tabPane = new TabPane();
    private Members actualMember;
    private Employees actualEmployee;
    private Cinema cine = new Cinema();
    private Controller controller;
    private Label connected;
    
    @Override
    public void start(Stage primaryStage) throws MalformedURLException, FileNotFoundException {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
        Label bande = new Label("WELCOME IN OUR CINEMA");
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
        
        tab1.setContent(getPanetab1());
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
    
    public double calculatePrice(int i){
        double j;
        if(!connected.getText().equals("")){
            j = cine.prixTicket(actualMember,i);
            return j;
        }
        else
            return i*cine.getPrix();
    }
    
    public VBox dispMovieToBuy(final Movies movie,Session sess,final ArrayList<Movies> movies){
        VBox tot = new VBox(50);
        
        tot.setAlignment(Pos.CENTER);
        VBox node = new VBox(20);
        HBox detail = new HBox();
        detail.setAlignment(Pos.CENTER);
        node.setAlignment(Pos.CENTER);
        Label lab1 = new Label("Title : "+movie.getTitle());
        Label lab2 = new Label("Author : "+movie.getAuthor());
        Label lab3 = new Label("Date : "+movie.getDate());
        Label lab4 = new Label("Type : "+movie.getType());
        Label lab5 = new Label("Runing time : "+movie.getRunningTime());
        
        node.getChildren().addAll(lab1,lab2,lab3,lab4,lab5);
        
        Label bis1 = new Label("Date : 2000-12-12");
        Label bis2 = new Label("Heure : 12h12");
        
        detail.getChildren().addAll(bis1,bis2);
        
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/spider_man.jpg"));
        ImageView view = new ImageView(img);
        view.setFitHeight(300);
        view.setPreserveRatio(true);
        
        
        Button but = new Button();
        Image img2;
        img2 = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(90);
        view2.setPreserveRatio(true);
        but.setGraphic(view2);
        but.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                tab3.setContent(dispSeance(movie,movies));
            }
        });
        tot.getChildren().addAll(but,view,detail,node);
        
        return tot;
    }
    
    public VBox dispPaiCustomer(){
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
        TextField num = new TextField();
        num.setPromptText("Enter your number card");
        num.setAlignment(Pos.CENTER);
        HBox both = new HBox();
        both.setAlignment(Pos.CENTER);
        TextField crypto = new TextField();
        crypto.setPromptText("Enter your crypto");
        TextField mv = new TextField();
        mv.setPromptText("Ex : 01/21");
        both.getChildren().addAll(crypto,mv);
        input.getChildren().addAll(num,both);
        num.setId("box-pay");
        both.setId("box-pay");
        HBox ad = new HBox(10);
        ad.setAlignment(Pos.CENTER);
        Button plus = new Button("+");
        Button moins = new Button("-");
        final Label nbr = new Label();
        nbr.setText("0");
        final Label disc = new Label();
        disc.setId("button-pay");
        disc.setText("0");
        
        plus.setOnAction(new EventHandler<ActionEvent>() {
            @Override  
            public void handle(ActionEvent arg0) {
                int i = Integer.parseInt(nbr.getText());
                i++;
                double j = calculatePrice(i);
                disc.setText(Double.toString(j));
                nbr.setText(Integer.toString(i));
            }
        });
        moins.setOnAction(new EventHandler<ActionEvent>() {
            @Override  
            public void handle(ActionEvent arg0) {
                int i = Integer.parseInt(nbr.getText());
                double j=0;
                i--;
                if(i<0)
                    i=0;
                else{
                    j = calculatePrice(i);
                }
                disc.setText(Double.toString(j)); 
                nbr.setText(Integer.toString(i));
                
            }
        });
        
        Button but = new Button("Ready to pay !");
        but.setId("button-pay");
        ad.getChildren().addAll(moins,nbr,plus);
        ad.setId("button-pay");
        VBox container = new VBox(80);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(nvx,input,ad,disc,but);
        
        return container;
    }
    
    public VBox dispTicketCustomer(){
        VBox tot = new VBox(20);
        tot.setId("co-pay");
        Label co = new Label("You are connected as : ");
        co.setAlignment(Pos.CENTER);
        Label nom = new Label("");
        if(!connected.getText().equals("")){
            nom.setText(actualMember.getFirstName()+" "+actualMember.getLastName());
            nom.setAlignment(Pos.CENTER);
        }
        else{
            nom.setText("A Customer");
            nom.setAlignment(Pos.CENTER);
        }
        tot.getChildren().addAll(co,nom);
        return tot;
    }
    
    public BorderPane paymentPage(Movies movie,Session sess,final ArrayList<Movies> movies){
        BorderPane tot = new BorderPane();
        tot.setLeft(dispMovieToBuy(movie,sess,movies));
        tot.setCenter(dispPaiCustomer());
        tot.setRight(dispTicketCustomer());
        //tot.setAlignment(Pos.CENTER);
        return tot;
    }
    
    public BorderPane dispMovie(Movies movie,final ArrayList<Movies> movies){
        GridPane tot = new GridPane();
        BorderPane border = new BorderPane();
        
        tot.setHgap(100);
        tot.setAlignment(Pos.CENTER);
        VBox node = new VBox(20);
        node.setAlignment(Pos.CENTER);
        Label lab1 = new Label("Title : "+movie.getTitle());
        Label lab2 = new Label("Author : "+movie.getAuthor());
        Label lab3 = new Label("Date : "+movie.getDate());
        Label lab4 = new Label("Type : "+movie.getType());
        Label lab5 = new Label("Runing time : "+movie.getRunningTime());
        
        node.getChildren().addAll(lab1,lab2,lab3,lab4,lab5);
        String desc = "iahfuhzefiuhazeofuhezfuazhfouazhfu. eazrgzaeg ezrhufiazep eruhfazuaezfuh efuahf. aeofiuhazofho azefuiohazouh aui.AZefAZEf fezf. fezffzefz!";
        for(int i=0;i<desc.length();i++){
            if(i%30==0 && i!=0){
                if(desc.charAt(i+1)==' ' || desc.charAt(i+1)=='.'){
                    desc = desc.substring(0,i) + "\n"+ desc.substring(i);
                }
                else{
                    desc = desc.substring(0,i) + "-\n"+ desc.substring(i);
                }
            } 
        }
        Label description = new Label(desc);
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/spider_man.jpg"));
        ImageView view = new ImageView(img);
        view.setFitHeight(300);
        view.setPreserveRatio(true);
        
        tot.addColumn(0, view);
        tot.addColumn(1, description);
        tot.addColumn(2, node);
        
        Button but = new Button();
        Image img2;
        img2 = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(90);
        view2.setPreserveRatio(true);
        but.setGraphic(view2);
        but.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event){
                tab3.setContent(searchTab(true,movies));
            }
        });
        border.setCenter(tot);
        border.setLeft(but);
        
        return border;
    }
    
    public ScrollPane dispAllSess(final Movies movie,final ArrayList<Movies> movies){
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
        VBox node1 = new VBox(20),node2 = new VBox(20),node3 = new VBox(20),node4 = new VBox(20);
        node1.setId("box-session");
        node2.setId("box-session");
        node3.setId("box-session");
        node4.setId("button-session");
        reserver.setId("box-session");
        node1.getChildren().add(date_txt);
        node2.getChildren().add(heure_txt);
        node3.getChildren().add(nbr_txt);
        node4.getChildren().add(reserver);
        for(int i=0;i<sess.size();i++){
            Label date = new Label("2000-12-12");
            Label heure = new Label("17h10");
            String nbrr = Integer.toString(sess.get(i).getNbr_places_max());
            Label nbr = new Label("Number of places "+nbrr);
            button.add(new Button("Reserve"));
            node1.getChildren().add(date);
            node2.getChildren().add(heure);
            node3.getChildren().add(nbr);
            node4.getChildren().add(button.get(i));
            final Button mybut = button.get(i);
            final Session session = sess.get(i);
            mybut.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event){
                    tab3.setContent(paymentPage(movie,session,movies));
                }
            });
        }
        nvx.addColumn(0,node1);
        nvx.addColumn(1,node2);
        nvx.addColumn(2,node3);
        nvx.addColumn(3,node4);
        
        tot.setContent(nvx);
        return tot;
    }
    
    public BorderPane dispSeance(Movies movie,ArrayList<Movies> movies){
        BorderPane tot = new BorderPane();
        //tot.setAlignment(Pos.CENTER);
        tot.setTop(dispMovie(movie,movies));
        tot.setCenter(dispAllSess(movie,movies));
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
        VBox other = new VBox(30);
        ArrayList<Button> tabButton = new ArrayList<>(); 
        for(int i=0;i<movies.size();i++){
            Label nom = new Label("Title : "+movies.get(i).getTitle()+" ");
            Label auteur = new Label("Author : "+movies.get(i).getAuthor()+" ");
            Label date = new Label("Date : "+movies.get(i).getDate()+" ");
            tabButton.add(new Button("Reserve"));
            HBox hbo = new HBox(20);
            hbo.setAlignment(Pos.CENTER_LEFT);
            hbo.setId("disp-movie");
            hbo.getChildren().addAll(nom,auteur,date,tabButton.get(i));
            vbox.getChildren().add(hbo);
            
            tabButton.add(new Button("Reserve"));
            tabButton.get(i).setId("disp-button");
            other.getChildren().add(tabButton.get(i));
            final Button mybut = tabButton.get(i);
            final Movies movie = movies.get(i);
            mybut.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event){
                    tab3.setContent(dispSeance(movie,movies));
                }
            });
        }
        tot.addColumn(0, vbox);
        tot.addColumn(1, other);
        tot.setId("search");
        bar.setContent(tot);
        bar.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        return bar;
    }
    
    public VBox searchVbox(){
        VBox nvx = new VBox(50);
        //nvx.setAlignement(Pos.CENTER);
        final TextField recherche = new TextField();
        recherche.setPromptText("Name of the movie");
        VBox hbo1 = new VBox();
        hbo1.setAlignment(Pos.CENTER);
        hbo1.getChildren().add(recherche);
        
        final ChoiceBox split = new ChoiceBox();
        split.getItems().addAll("Action","Science-Fiction","Horror","Fantastic","Comedia");
        
        final TextField lab1 = new TextField();
        lab1.setPromptText("Month");
        final Label lab2 = new Label("/");
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
        time.setPromptText("time");
        
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
                    movies = controller.searchMovies(recherche.getText(),(String)split.getValue(),time.getText());
                } catch (ClassNotFoundException | ParseException | SQLException ex) {
                    Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                }
                if(movies!=null && movies.size()>0)
                    tab3.setContent(searchTab(true,movies));
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
        
        nvx.getChildren().addAll(deco,lab1,lab2,lab3);
        
        deco.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                connected.setText("");
                actualMember = null;
                tab4.setContent(getPane(0));
            }
        });
        
        return nvx;
    }

    public ScrollPane getSPane()
    {
        VBox box = new VBox();      
        ScrollPane scroll = new ScrollPane();
        ImageView[] view = new ImageView[3];
        Image[] image = new Image[3];
        String[] imageNames = new String[3];
        imageNames[0]= "https://cdn.radiofrance.fr/s3/cruiser-production/2019/10/796598e0-2d78-492d-9b08-ad2ec8188c2c/1200x680_shrek-et-le-chat-potte-reviennent-bientot-au-cinema-big.jpg";
        imageNames[1]= "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTZ1GwpGofIbWesz2O_2eFxiAX4kDsfCTgaYA&usqp=CAU.jpg";
        imageNames[2]= "https://cdn.radiofrance.fr/s3/cruiser-production/2019/10/796598e0-2d78-492d-9b08-ad2ec8188c2c/1200x680_shrek-et-le-chat-potte-reviennent-bientot-au-cinema-big.jpg";
        for(int i=0; i<3 ;i++)
        {
            image[i] = new Image(imageNames[i]);
            view[i] = new ImageView(image[i]);
            view[i].setFitWidth(300);
            view[i].setPreserveRatio(true);           
            box.getChildren().add(view[i]);
            
        }
        scroll.setContent(box);
        scroll.setPrefSize(320,250);
        scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        return scroll;
    }
    public GridPane getGridtab1()
    {
        GridPane gp = new GridPane();
        Label Titre = new Label("Title :");  
        Label Author = new Label("Author :");
        Label RD = new Label("Realase Date :");
        Label GT = new Label("Gender type :");
        Label RT = new Label("Running Time :");
        Label Rate = new Label("Rating: ");
        
        gp.addRow(0 ,Titre);
        gp.addRow(1, Author);
        gp.addRow(2, RD);
        gp.addRow(3, GT);
        gp.addRow(4, RT);
        gp.addRow(5, Rate);
        gp.setVgap(10);
        return gp;
    }
    public FlowPane getPanetab1()
    {
        FlowPane pane = new FlowPane();
        pane.setHgap(10);
        pane.getChildren().addAll(getSPane(),getGridtab1());
        //pane.getChildren().add(getGridtab1());
        return pane;
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
        
        Button bout = new Button("Submit");
        bout.setId("submit-tab4");
        nvx.getChildren().addAll(firstName,tf1,lastName,tf2,age,tf3,password,tf4,confirmPassword,tf5,error,bout);
        
        nvx.setId("sub");
        
        bout.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                boolean condi=false;
                controller = new Controller("subscription","tab4");
                if(!tf5.getText().equals(tf4.getText())){
                    tab4.setContent(getSubscription("Please enter same password"));
                }
                else{
                    try {
                        condi = controller.createMember(tf1.getText(),tf2.getText(),tf3.getText(),tf4.getText());
                    } catch (SQLException | ClassNotFoundException | ParseException ex) {
                        Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    System.out.println(condi);
                    if(condi==true){
                        String login = tf1.getText()+tf2.getText();
                        int age = Integer.parseInt(tf3.getText());
                        actualMember = new Members(tf1.getText(),tf2.getText(),tf3.getText(),age,login);
                        tab4.setContent(membConnected());
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
        button.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        test.getChildren().add(button);
        pane.setBottom(test);
        BorderPane.setMargin(button,new Insets(10,0,0,550));
        
        button.setOnAction(new EventHandler<ActionEvent>() {  
              
            @Override  
            public void handle(ActionEvent arg0) {  
                // TODO Auto-generated method stub
                if(connected.getText().equals(""))
                    tab4.setContent(getSubscription(""));
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
