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
        
        Scene scene = new Scene(vbox, 400, 600);
        //scene.getStylesheets().add("Style.css");
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setTitle("CHANGE");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
    
    public BorderPane searchTab(boolean condi,ArrayList<Movies> movies){
        BorderPane nvx = new BorderPane();
        nvx.setTop(searchVbox());
        
        if(condi==true){
            nvx.setCenter(resultMovies(movies));
        }
        
        return nvx;
    }
    
    public ScrollPane resultMovies(ArrayList<Movies> movies){
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
            final int j = i;
            mybut.setOnAction(new EventHandler<ActionEvent>() {
                
                public void handle(ActionEvent event) {
                    System.out.println("c'est le bouton : "+j);
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
        final TextField recherche;
        recherche = new TextField();
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

    public FlowPane getSPane()
    {   
        FlowPane pane = new FlowPane();
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(getPanetab1());
        scroll.setVbarPolicy(ScrollBarPolicy.ALWAYS);
        scroll.setPrefSize(1250,300);
        pane.getChildren().add(scroll);
        return pane;
    }
    public GridPane getGridtab1()
    {
        GridPane gp = new GridPane();
        Label Titre = new Label("Title :");  
        Label Author = new Label("Author :");
        Label RD = new Label("Release Date :");
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
        GridPane gp = new GridPane();
        ArrayList<Button> tabButton = new ArrayList<>();
        ImageView[] view = new ImageView[3];
        Image[] image = new Image[3];
        for(int i=0; i<3 ;i++)
        {
            tabButton.add(new Button());
            HBox box = new HBox(20);
            image[i] = new Image(getClass().getResourceAsStream("/images/Image"+(i+1)+".jpg"));
            view[i] = new ImageView(image[i]);
            view[i].setFitWidth(170);
            view[i].setPreserveRatio(true);
            tabButton.get(i).setPrefSize(170,view[i].getY());
            tabButton.get(i).setGraphic(view[i]);
            box.getChildren().addAll(tabButton.get(i),getGridtab1());
            gp.addRow(i , box);
        }
        gp.setVgap(10);
        pane.setPrefSize(300,1000);
        pane.getChildren().add(gp);
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
        
        Image carte = new Image(getClass().getResourceAsStream("/images/carteCinePass.png"));
        ImageView view;
        view = new ImageView(carte);
        view.setFitHeight(300);
        view.setPreserveRatio(true);
        view.setX(-80);
        view.setY(-10);
        
        Text titre = new Text("The CinéPass");
        titre.setId("titre-tab2");
        
        Text discounts = new Text(  "  Benefits all year round!!\n"
                                   +"  Thanks to CinéPass, enjoy exclusive benefits\n"
                                   +"  and offers throughout the year.\n"
                                   +"  To make sure you don't miss out on anything,\n"
                                   +"  receive our communications by newsletter.\n"
                                   +"  Children : -30%    Regular : -20%    Senior : -15%");
        discounts.setId("discounts-tab2");
        discounts.setY(50);
        
        Rectangle infos = new Rectangle();
        infos.setStrokeWidth(2);infos.setStroke(Color.BLACK);
        infos.setX(90);infos.setY(50);infos.setWidth(630);infos.setHeight(200);infos.setArcHeight(50);infos.setArcWidth(50);infos.setFill(Color.WHITE);
        
        StackPane stack = new StackPane();
        stack.getChildren().addAll(infos,discounts);
        stack.setLayoutX(50);
        stack.setLayoutY(50);
        
        
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
