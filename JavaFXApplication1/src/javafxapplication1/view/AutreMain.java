/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1.view;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data;
import javafx.geometry.Orientation;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import javafx.stage.Stage;
import java.util.Collections;
import javafx.application.Platform;
import javafx.stage.WindowEvent;
import javafxapplication1.controller.Controller;
import javafxapplication1.model.Employees;
import javafxapplication1.model.Members;
import javafxapplication1.model.Movies;
import javafxapplication1.model.Sessions;

/**
 *
 * @author clemf
 */
public class AutreMain{

    private final Employees actual;
    private final Stage second;
    private final Tab tab = new Tab();
    private Label connected = new Label("");
    private Controller controller;
    
    /**
     * 
     * Constructeur de la deuxième scène pour la partie employé.
     * 
     */
    public AutreMain(Employees nvx){
        this.actual = nvx;
        second = new Stage();
        TabPane tot = new TabPane();
        tab.setContent(homePage());
        tot.getTabs().add(tab);
        connected = new Label("Hello "+actual.getFirstName().substring(0,1).toUpperCase()+actual.getFirstName().substring(1)+" "+actual.getLastName().substring(0,1).toUpperCase()+actual.getLastName().substring(1)+" !");
        connected.setStyle("-fx-font-size : 2em; -fx-text-fill:#6C7A89; -fx-font-weight : bold");
        
        VBox ad = new VBox(10);
        ad.setStyle("-fx-background-color :#DADFE1");
        ad.setAlignment(Pos.CENTER);
        ad.getChildren().addAll(connected,tot);
        
        Scene scene = new Scene(ad, 1700, 800);
        scene.getStylesheets().add(getClass().getResource("StyleAutre.css").toExternalForm());
        second.setTitle("Employee");
        second.setScene(scene);
        second.setResizable(true);
        second.show();
        second.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
            }

        });
    }
    /**
     * Affichage de l'interface home partie employé
     * Chaque bouton permet d'accéder à certaine fonctionnalité
     * En fonction du grade de l'employé certains boutons sont désactivés.
     * 
     */
    public BorderPane homePage(){
        BorderPane tot = new BorderPane();
        VBox nvx = new VBox(50);
        nvx.setStyle("-fx-background-color:#DADFE1");
        nvx.setAlignment(Pos.CENTER);
        Label lab1 = new Label("MENU");
        lab1.setId("labMenu");
        Button but1 = new Button("Manage Cinema DataBase");
        Button but2 = new Button("Manage Cinema's Promotion");
        Button but3 = new Button("See cinema's statistics");
        Button but4 = new Button("Manage Employees/Members");
        Button but5 = new Button("Change Password");
        Button but6 = new Button("Log Out");
        but1.setId("button-home");
        but2.setId("button-home");
        but3.setId("button-home");
        but4.setId("button-home");
        but5.setId("button-home");
        but6.setId("logout");
        Image img2;
        img2 = new Image(getClass().getResourceAsStream("/images/out.png"));
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(90);
        view2.setPreserveRatio(true);
        but6.setGraphic(view2);
        switch (actual.getAccess()) {
            case "C":
                but2.setDisable(true);
                but3.setDisable(true);
                but4.setDisable(true);
                break;
            case "B":
                but4.setDisable(true);
                break;
        }
        
        but6.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                second.close();
            }
        });
        but1.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                tab.setContent(accessCinemaData());
            }
        });
        but2.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                tab.setContent(changePromotion());
            }
        });
        but3.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                tab.setContent(seeStatistics());
            }
        });
        but4.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                tab.setContent(accessMembEmploy());
            }
        });
        but5.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                tab.setContent(changePassword());
            }
        });
        nvx.getChildren().addAll(lab1,but1,but2,but3,but4,but5,but6);
        tot.setCenter(nvx);
        return tot;
    }
    /**
     * 
     * Permet à l'employé de modifier son mot de passe avec blindage 
     * pour ne pas se tromper de format
     * modification de la bdd pour le mdp
     */
    public HBox changePassword(){
        HBox tot = new HBox();
        tot.setId("setBack");
        tot.setAlignment(Pos.CENTER);
        VBox nvx = new VBox(20);
        nvx.setAlignment(Pos.CENTER);
        final PasswordField txt0 = new PasswordField();
        txt0.setPromptText("Old Password");
        final PasswordField txt1 = new PasswordField();
        txt1.setPromptText("New Password");
        final PasswordField txt2 = new PasswordField();
        txt2.setPromptText("Confirm Password");
        final Label txt3 = new Label();
        txt3.setText("");
        txt0.setId("labEmpl");
        txt1.setId("labEmpl");
        txt2.setId("labEmpl");
        txt3.setId("labEmpl");
        Button txt4 = new Button("Change password");
        Button txt5 = new Button("Go back");
        txt4.setId("butEmpl");
        txt5.setId("butEmpl");
        HBox inter = new HBox(20);
        inter.setAlignment(Pos.CENTER);
        inter.getChildren().addAll(txt4,txt5);
        txt4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                if(!txt1.getText().equals(txt2.getText())){
                    txt3.setText("Not same password");
                    txt3.setTextFill(RED);
                }
                else if(txt1.getText().equals("")){
                    txt3.setText("Enter a password");
                    txt3.setTextFill(RED);
                }
                else if(!txt0.getText().equals(actual.getPassword())){
                    txt3.setText("Old password wrong");
                    txt3.setTextFill(RED);
                }
                else{
                    controller = new Controller("changeMdp","tab4");
                    controller.changePassword2(txt1.getText(),actual.getLogin());
                    actual.setPassword(txt1.getText());
                    tab.setContent(homePage());
                }
            }
        });
        txt5.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                tab.setContent(homePage());
            }
        });
        nvx.getChildren().addAll(txt0,txt1,txt2,txt3,inter);
        tot.getChildren().add(nvx);
        return tot;
    }
    /**
     * 
     * Affichage des statistics du cinéma 
     * sous forme de graphics 
     */
    public ScrollPane seeStatistics(){

        ScrollPane scroll = new ScrollPane();
        scroll.setStyle("-fx-background-color:#DADFE1");
        GridPane nvx = new GridPane();
        nvx.setId("stat-grid");
        nvx.setVgap(50);
        nvx.setHgap(50);
        nvx.setAlignment(Pos.CENTER);
        ArrayList<Members> membres;
        ArrayList<Movies> movies;
        ArrayList<Integer> nbr = new ArrayList<>();
        ArrayList<Double> amount = new ArrayList<>();
        ArrayList<Integer> nbr_sess = new ArrayList<>();
        ArrayList<Sessions> sessions;
        PieChart piechart = new PieChart();
        
        int senior = 0, children = 0, regular = 0;
        controller = new Controller("statistics","member");
        membres = controller.recolterMembre();
        sessions = controller.recolterSessions();
        movies = controller.dispAllMovies();
        
        ///////////////////////////////////////////////////////////////////////
        for (Members membre : membres) {
            if (membre.getAge() < 18) {
                children++;
            } else if (membre.getAge() > 60) {
                senior++;
            } else {
                regular++;
            }
        }
        
        int total=senior+children+regular;
        
        ObservableList<Data> list = FXCollections.observableArrayList();  
        list.addAll(new PieChart.Data("Senior", senior*100/total),  
            new PieChart.Data("Children", children*100/total),new PieChart.Data("Regular",regular*100/total));
        piechart.setData(list);
        piechart.setTitle("Percentage of type of different pormotions");
        /////////////////////////////////////////////////////////////////////////////////////////
        
        for(int i=0;i<movies.size();i++){
            nbr.add(0);
            amount.add(0.0);
            nbr_sess.add(0);
            for (Sessions session : sessions) {
                if (movies.get(i).getId() == session.getMovie()) {
                    double tempo = amount.get(i);
                    amount.set(i, tempo + session.getAmount());
                    int temp = nbr.get(i);
                    nbr.set(i, temp + session.getActual());
                    int tempori = nbr_sess.get(i);
                    nbr_sess.set(i,tempori++);
                }
            }
        }
        
        CategoryAxis xaxis= new CategoryAxis();  
        NumberAxis yaxis = new NumberAxis(0,500,10);  
        xaxis.setLabel("Movies' name");  
        yaxis.setLabel("Number of places bought");  
      
    //Configuring BarChart   
        BarChart<String,Float> bar = new BarChart(xaxis,yaxis);
        bar.setLegendVisible(false);
        bar.setTitle("Number of places according to the movie");
        
        XYChart.Series<String,Float> series = new XYChart.Series<>();
        
        for(int i=0;i<movies.size();i++){
            series.getData().add(new XYChart.Data(movies.get(i).getTitle(),nbr.get(i)));
        }
        
        ArrayList<String> date = new ArrayList<>();
        boolean condi=false;
        for (Sessions session : sessions) {
            for (String date1 : date) {
                if (date1.equals(session.getDate().toString())) {
                    condi = true;
                }
            }
            if (condi==false) {
                date.add(session.getDate().toString());
            } else {
                condi = false;
            }
        }
        Collections.sort(date);
        
        ArrayList<Double> toto = new ArrayList<>();
        for(int i=0;i<date.size();i++){
            toto.add(0.0);
            for (Sessions session : sessions) {
                if (session.getDate().toString().equals(date.get(i))) {
                    double temp = toto.get(i);
                    toto.set(i, temp + session.getAmount());
                }
            }
        }
        
        NumberAxis xaxe = new NumberAxis(0,date.size(),1);  
        NumberAxis yaxe = new NumberAxis(0,1500,10);  
          
        //Defining Label for Axis   
        xaxis.setLabel("Movies");
        yaxis.setLabel("Amount"); 
        LineChart linechart = new LineChart(xaxe,yaxe);
        XYChart.Series serie = new XYChart.Series();
        serie.setName("Total amount per day"); 
        
        for(int i=0;i<date.size();i++){
            serie.getData().add(new XYChart.Data(i,toto.get(i)));
        }
        
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
                tab.setContent(homePage());
            }
        });
        
        linechart.setTitle("Revenue per day");
        linechart.getData().add(serie);
        bar.getData().add(series);
        Separator separator = new Separator(Orientation.HORIZONTAL);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        Separator separator4 = new Separator(Orientation.VERTICAL);
        Separator separator5 = new Separator(Orientation.VERTICAL);
        nvx.addRow(0,but);
        nvx.add(piechart,0,1);
        nvx.add(separator4,2,1);
        nvx.add(linechart,4,1);
        nvx.add(separator,0,3);
        nvx.add(separator2,4,3);
        nvx.add(totalTabStat(movies,sessions,nbr_sess,amount),0,4);
        nvx.add(separator5,2,4);
        nvx.add(bar,4,4);
        scroll.setContent(nvx);
        return scroll;
    }
    /**
     * 
     * @param movies
     * @param sessions
     * @param nbr_sess
     * @param amount
     * @return
     * Calcul de toutes les stats pour les graphiques 
     */
    public VBox totalTabStat(ArrayList<Movies> movies, ArrayList<Sessions> sessions,ArrayList<Integer> nbr_sess, ArrayList<Double> amount){
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        final PieChart piechart2 = new PieChart();
        piechart2.setLegendVisible(false);
        piechart2.setId("cchart");
        ObservableList<Data> list2 = FXCollections.observableArrayList();
        final PieChart piechart = new PieChart();
        ObservableList<Data> list = FXCollections.observableArrayList();
        final PieChart piechart3 = new PieChart();
        ObservableList<Data> list3 = FXCollections.observableArrayList();
        
        double recette=0.0;
        for (Double amount1 : amount) {
            recette += amount1;
        }
        
        for(int i=0;i<movies.size();i++){
            list2.add(new PieChart.Data(movies.get(i).getTitle()+" : "+Math.round(amount.get(i)*100/recette)+"%", amount.get(i)*100/recette));
        }
        piechart2.setData(list2);
        
        final Tab tab1 = new Tab();
        tab1.setContent(piechart2);
        
        double amount1=0,amount2=0,amount3=0,amount4=0,amount5=0;
        
        for(int i=0;i<movies.size();i++){
            switch (movies.get(i).getType()) {
            case "action":
                {
                    amount1+=amount.get(i);
                    break;
                }
            case "horror":
                {
                    amount2+=amount.get(i);
                    break;
                }
            case "comedia":
                {
                    amount3+=amount.get(i);
                    break;
                }
            case "fantastic":
                {
                    amount4+=amount.get(i);
                    break;
                }
            case "science-fiction" :
                {
                    amount5+=amount.get(i);
                    break;
                }
            }
        }
        list.addAll(new PieChart.Data("Action",amount1*100/recette),new PieChart.Data("Horror",amount2*100/recette),new PieChart.Data("Comedia",amount3*100/recette),new PieChart.Data("Fantastica",amount4*100/recette),new PieChart.Data("Science-Fiction",amount5*100/recette));
        piechart.setData(list);
        
        double before10=0,before14=0,before18=0;
        
        for (Sessions session : sessions) {
            if (session.getHoraire().length() < 5) {
                before10 += session.getAmount();
            } else if (session.getHoraire().compareTo("14h00") < 0){
                before10 += session.getAmount();
            } else if (session.getHoraire().compareTo("18h00") < 0){
                before14 += session.getAmount();
            } else {
                before18 += session.getAmount();
            }
        }
        list3.addAll(new PieChart.Data("Before 14pm",before10*100/recette),new PieChart.Data("14pm to 18pm",before14*100/recette),new PieChart.Data("After 18pm",before18*100/recette));
        piechart3.setData(list3);
        
        final Button button1 = new Button("Per Movies");
        button1.setStyle("-fx-background-color : royalblue;");
        final Button button2 = new Button("Per Types");
        final Button button3 = new Button("Per Hours");
        button1.setStyle("-fx-background-color : royalblue;");
        button2.setStyle("-fx-background-color : white;");
        button3.setStyle("-fx-background-color : white;");
        
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab1.setContent(piechart2);
                button1.setStyle("-fx-background-color : royalblue;");
                button2.setStyle("-fx-background-color : white;");
                button3.setStyle("-fx-background-color : white;");
            }
        });
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab1.setContent(piechart);
                button2.setStyle("-fx-background-color : royalblue;");
                button1.setStyle("-fx-background-color : white;");
                button3.setStyle("-fx-background-color : white;");
            }
        });
        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab1.setContent(piechart3);
                button3.setStyle("-fx-background-color : royalblue;");
                button1.setStyle("-fx-background-color : white;");
                button2.setStyle("-fx-background-color : white;");
            }
        });
        
        TabPane pane = new TabPane();
        pane.getTabs().add(tab1);
        HBox hbo = new HBox();
        hbo.setAlignment(Pos.CENTER);
        hbo.getChildren().addAll(button1,button2,button3);
        box.getChildren().addAll(new Label("Account of revenue"),pane,hbo);
        
        return box;
    }
    /**
     * methode permettant à l'employé de changer une promotion en fonction 
     * de senior, children et médium, possibilité de voir l'information changer en direct
     * @return 
     */
    public BorderPane changePromotion(){
        BorderPane toto=new BorderPane();
        toto.setStyle("-fx-background-color:#DADFE1");
        VBox tot = new VBox(10);
        tot.setAlignment(Pos.CENTER);
        Label lab = new Label("Change actual offers : ");
        lab.setStyle("-fx-font-size:1.2em; -fx-text-fill: #000000;");
        final ToggleGroup group = new ToggleGroup();
        RadioButton button1 = new RadioButton("Senior : ");
        RadioButton button2 = new RadioButton("Children : ");  
        RadioButton button3 = new RadioButton("Regular : "); 
        button1.setToggleGroup(group);  
        button2.setToggleGroup(group);
        button3.setToggleGroup(group);
        button1.setUserData("0");
        button2.setUserData("1");
        button3.setUserData("2");
        button1.setId("dsgn-promo-radio");
        button2.setId("dsgn-promo-radio");
        button3.setId("dsgn-promo-radio");
        final TextField txt1 = new TextField();
        final TextField txt2 = new TextField();
        final TextField txt3 = new TextField();
        txt1.setId("dsgn-promo-txt");
        txt2.setId("dsgn-promo-txt");
        txt3.setId("dsgn-promo-txt");
        txt1.setPromptText("Enter value of promotion");
        final Label lab1 = new Label();
        lab1.setTextFill(RED);
        final Label lab2 = new Label();
        lab2.setTextFill(RED);
        final Label lab3 = new Label();
        lab3.setTextFill(RED);
        txt2.setPromptText("Enter value of promotion");
        txt3.setPromptText("Enter value of promotion");
        txt1.setEditable(false);
        txt2.setEditable(false);
        txt3.setEditable(false);

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle toggle, Toggle new_toggle) {
                    if (new_toggle == null){
                        System.out.println("TOGGLE NULL");
                    }
                    else{
                        int temp = Integer.parseInt((String) group.getSelectedToggle().getUserData());
                        switch(temp){
                            case 0 :txt1.setEditable(true);
                                    txt2.setEditable(false);
                                    txt3.setEditable(false);
                                break;
                            case 1 :txt1.setEditable(false);
                                    txt2.setEditable(true);
                                    txt3.setEditable(false);
                                break;
                            case 2 :txt1.setEditable(false);
                                    txt2.setEditable(false);
                                    txt3.setEditable(true);
                                break;
                        }
                                
                    }
            }
        });
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
                tab.setContent(homePage());
            }
        });
        HBox box1 = new HBox(30);
        HBox box2 = new HBox(30);
        HBox box3 = new HBox(30);
        HBox box4 = new HBox();
        box1.setAlignment(Pos.CENTER);
        box2.setAlignment(Pos.CENTER);
        box3.setAlignment(Pos.CENTER);
        box4.setAlignment(Pos.CENTER);
        final PasswordField pwd = new PasswordField();
        pwd.setPromptText("Enter your password");
        pwd.setId("dsgn-promo-txt");
        final Label lab4 = new Label();
        
        box1.getChildren().addAll(button1,txt1);
        box2.getChildren().addAll(button2,txt2);
        box3.getChildren().addAll(button3,txt3);
        box4.getChildren().addAll(pwd);
        Button button = new Button("Change actual promotions");
        button.setId("button-home");
        tot.getChildren().addAll(lab,box1,lab1,box2,lab2,box3,lab3,box4,lab4,button);
        
        button.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                int condi;
                if(!pwd.getText().equals(actual.getPassword())){
                    lab4.setText("Wrong password");
                    lab4.setTextFill(RED);
                }
                else{
                    controller = new Controller("managePromotions","employeeTab");
                    condi = controller.changePromotion(txt1.getText(),txt2.getText(),txt3.getText());
                    if(condi==0){
                        lab1.setText("Wrong input here - Reduction can't be higher than 50% - please only enter the number");
                        lab2.setText("");
                        lab3.setText("");
                        lab4.setText("");
                    }
                    else if(condi==1){
                        lab2.setText("Wrong input here - Reduction can't be higher than 50% - please only enter the number");
                        lab1.setText("");
                        lab3.setText("");
                        lab4.setText("");
                    }
                    else if(condi==2){
                        lab3.setText("Wrong input here - Reduction can't be higher than 50% - please only enter the number");
                        lab2.setText("");
                        lab1.setText("");
                        lab4.setText("");
                    }
                    else if(condi==-1){
                        lab4.setText("Promotion changed !");
                        lab4.setTextFill(GREEN);
                        lab1.setText("");
                        lab2.setText("");
                        lab3.setText("");
                        lab4.setText("");
                        txt1.setText("");
                        txt2.setText("");
                        txt3.setText("");
                        pwd.setText("");
                    }
                }
            }
        });
        toto.setLeft(but);
        toto.setRight(emptyRight(but.getWidth()));
        toto.setCenter(tot);
        
        return toto;
    }
    /**
     * permet d'acceder à la bdd des employés ou des membres du cinéma
     * @return 
     */
    public BorderPane accessMembEmploy(){
        BorderPane tot = new BorderPane();
        tot.setStyle("-fx-background-color : #DADFE1");
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
                tab.setContent(homePage());
            }
        });
        
        VBox nvx = new VBox(20);
        nvx.setAlignment(Pos.CENTER);
        
        HBox inter = new HBox(50);
        
        Label lab1 = new Label("Modify Employee/Member Data Bases");
        lab1.setId("labMenu");
        inter.getChildren().add(but);
        Button but1 = new Button("Access Member Data Base");
        Button but2 = new Button("Access Employee Data Base");
        but1.setId("button-home");
        but2.setId("button-home");
        nvx.getChildren().addAll(lab1,but1,but2);
        but1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(dispMember());
            }
        });
        but2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(dispEmployee());
            }
        });
        tot.setLeft(inter);
        tot.setCenter(nvx);
        tot.setRight(emptyRight(but.getWidth()));
        return tot;
    }
    /**
     * permet d'accéder aux différentes fonctions du cinéma
     * les films, les sessions et les clients en fonction de la bdd
     * @return 
     */
    public BorderPane accessCinemaData(){
        BorderPane tot = new BorderPane();
        tot.setStyle("-fx-background-color : #DADFE1");
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
                tab.setContent(homePage());
            }
        });
        
        VBox nvx = new VBox(50);
        nvx.setAlignment(Pos.CENTER);
        
        HBox inter = new HBox(50);
        
        
        Label lab1 = new Label("Modify Cinema Data Base");
        lab1.setId("labMenu");
        inter.getChildren().add(but);
        Button but1 = new Button("Access Movie Data Base");
        Button but2 = new Button("Access Session Data Base");
        Button but3 = new Button("Access Customer Data Base");
        but1.setId("button-home");
        but2.setId("button-home");
        but3.setId("button-home");
        nvx.getChildren().addAll(inter,lab1,but1,but2,but3);
        
        but1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                try {
                    tab.setContent(accesMoviesData());
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                }   
            }
        });
        but2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                try 
                {
                    tab.setContent(acessSessionData());
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        but3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                try {
                    tab.setContent(accesCustomerData());
                } catch (SQLException | ParseException | ClassNotFoundException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tot.setCenter(nvx);
        return tot;
    }
    /**
     * permet de supprimer un client du fichier de la bdd
     * @return
     * @throws SQLException
     * @throws ParseException
     * @throws ClassNotFoundException 
     */
    public BorderPane accesCustomerData() throws SQLException, ParseException, ClassNotFoundException{
        BorderPane pane = new BorderPane();
        pane.setId("setBack");
        ScrollPane scroll = new ScrollPane();
        scroll.setId("setBack");
        //Bouton back
        Button back = new Button();
        back.setId("back-but");
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(90);
        view.setPreserveRatio(true);
        back.setGraphic(view);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(accessCinemaData());
            }
        });
        
        //titre
        Label title = new Label("Delete a Customer from the DataBase");
        title.setId("title-moviedata");
        
        HBox inter = new HBox(50);
        inter.getChildren().addAll(back,title);
        inter.setAlignment(Pos.CENTER);
        
        //Liste des Customer sous forme de boutons
        controller = new Controller("","");
        final ArrayList<Members> customers = controller.AllCustomers();
        ArrayList<Button> cust = new ArrayList<>();
        for(int i=0;i<customers.size();i++){
            cust.add(new Button());
            cust.get(i).setId("butEmpl");
            cust.get(i).setText("Delete customer : "+customers.get(i).getLogin()+ ", id : "+customers.get(i).getId());
        }
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        for (Button cust1 : cust) {
            box.getChildren().add(cust1);
        }
        
        //Action sur les boutons : Suppression du customer
            for(int i=0;i<customers.size();i++){
                final Button but = cust.get(i);
                final Members c = customers.get(i);
                
                but.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event){
                        controller = new Controller("","");
                        controller.delete_customer(c.getId(),c.getLogin());
                        tab.setContent(accessCinemaData());
                    }
                });
            }
        scroll.setContent(box);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
            
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
            
        pane.setTop(inter);
        pane.setCenter(scroll);
        
        return pane;
    }
    /**
     * donnée lié aux films de la bdd
     * interface pour modifier,supprimer ou ajouter un film 
     *
     */
    public BorderPane accesMoviesData() throws SQLException, ClassNotFoundException, ParseException{
        BorderPane pane = new BorderPane();
        ScrollPane scroll = new ScrollPane();
        pane.setId("setBack");
        //Bouton back
        Button back = new Button();
        back.setId("back-but");
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(90);
        view.setPreserveRatio(true);
        back.setGraphic(view);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(accessCinemaData());
            }
        });         
        
        
        //Boutton Ajouter movie
        Button addMovie = new Button("(+) Add a movie");
        addMovie.setId("butMovie");
        addMovie.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                try {
                    tab.setContent(AddMovie());
                } catch (ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
        
        //titre
        Label title = new Label("Modify Movie DataBase");
        title.setId("labEmpl");
        
        HBox inter = new HBox(50);
        inter.getChildren().addAll(back,title,addMovie);
        inter.setAlignment(Pos.CENTER);
        
       
        
        controller = new Controller("movie","MoviesData");
        final ArrayList<Movies> movies = controller.dispAllMovies();
        ArrayList<Button> movie = new ArrayList<>();
        for(int i=0;i<movies.size();i++){
            movie.add(new Button());
            movie.get(i).setId("butEmpl");
            movie.get(i).setText(movies.get(i).getTitle());
        }
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        for (Button movie1 : movie) {
            box.getChildren().add(movie1);
        }
        for(int i=0;i<movies.size();i++){
            final Button but = movie.get(i);
            final Movies mov = movies.get(i);
            but.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    tab.setContent(ModifMovie(mov));
                }
            });
        }
        scroll.setContent(box);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        pane.setTop(inter);
        pane.setCenter(scroll);
        
        //liste des films sous forme de boutons
               
       return pane; 
    }
    /**
     * donnée lié aux sessions de la bdd
     * interface pour modifier,supprimer ou ajouter une session 
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws ParseException 
     */
    public BorderPane acessSessionData() throws SQLException, ClassNotFoundException, ParseException
    {
        final BorderPane pane = new BorderPane();
        pane.setId("setBack");
        ScrollPane scroll = new ScrollPane();
        scroll.setId("setBack");
        //Bouton back
        Button back = new Button();
        back.setId("back-but");
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(90);
        view.setPreserveRatio(true);
        back.setGraphic(view);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(accessCinemaData());
            }
        }); 
        
        //titre
        Label title = new Label("Modify Session DataBase");
        title.setId("title-cine");
        HBox inter = new HBox(50);
        inter.getChildren().addAll(back,title);
        inter.setAlignment(Pos.CENTER);
        
        controller = new Controller("movie","MoviesData");
        final ArrayList<Movies> movies = controller.dispAllMovies();
        ArrayList<Button> movie = new ArrayList<>();
        for(int i=0;i<movies.size();i++){
            movie.add(new Button());
            movie.get(i).setId("butEmpl");
            movie.get(i).setText(movies.get(i).getTitle());
        }
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        for (Button movie1 : movie) {
            box.getChildren().add(movie1);
        }
        for(int i=0;i<movies.size();i++){
            final Button but = movie.get(i);
            final Movies mov = movies.get(i);
            //sess = controller.getSessionMovie(movies.get(i).getId());
            but.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    ArrayList<Sessions> sess;
                    try {
                        sess = controller.getSessionMovie(mov.getId());
                        tab.setContent(ModifSess(sess,mov.getId()));
                    } catch (SQLException | ClassNotFoundException | ParseException ex) {
                        Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
            });
        }
        scroll.setContent(box);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
        scroll.setFitToHeight(true);
        scroll.setFitToWidth(true);
        pane.setTop(inter);
        pane.setCenter(scroll);
        return pane; 
    }
    /**
     * permet de modifier les informations d'un film puis de les modifier dans la bdd
     * @param movie
     * @return 
     */
    public BorderPane ModifMovie(final Movies movie){
        BorderPane pane = new BorderPane();
        pane.setId("setBack");
        //Bouton back
        Button back = new Button();
        back.setId("back-but");
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(90);
        view.setPreserveRatio(true);
        back.setGraphic(view);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                try {
                    tab.setContent(accesMoviesData());
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
         
        
        Label aut = new Label("Author : ");
        final TextField auteur = new TextField(movie.getAuthor());
        Label tit = new Label("Title : ");
        final TextField titre = new TextField(movie.getTitle());
        Label desc = new Label("Summary : ");
        final TextArea description = new TextArea(movie.getDescription());
        description.setWrapText(true);
        Label type = new Label("Type : ");
        final TextField genre = new TextField(movie.getType());
        Label rate = new Label("Rate : ");
        final TextField note = new TextField(Integer.toString(movie.getRate()));
        aut.setId("labEmpl");
        auteur.setId("labEmpl");
        tit.setId("labEmpl");
        titre.setId("labEmpl");
        desc.setId("labEmpl");
        description.setId("labEmpl");
        type.setId("labEmpl");
        genre.setId("labEmpl");
        rate.setId("labEmpl");
        note.setId("labEmpl");
        
        final Label error = new Label();
        error.setAlignment(Pos.CENTER);
        error.setTextFill(RED);
        error.setText("");
        
        //Bouton Validate
        Button validate = new Button("Validate");
        validate.setId("butEmpl");
        validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                error.setText("");
                if(auteur.getText().equals("") || titre.getText().equals("") || description.getText().equals("") || genre.getText().equals("") || Integer.valueOf(note.getText())<0 || Integer.valueOf(note.getText())>10 || note.getText().equals(""))
                    error.setText("Please fill all the inputs or enter correct inputs");
                else{
                    controller = new Controller("movie","MoviesData");
                    Movies mov = new Movies(titre.getText(),auteur.getText(),movie.getDate(),Integer.valueOf(note.getText()),genre.getText(),movie.getRunningTime(),movie.getId(),description.getText());
                    controller.update_movie(mov);
                    try {                   
                        tab.setContent(accesMoviesData());
                    } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        //Bouton Delete
        Button delete = new Button("Delete");
        delete.setId("butEmpl");
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                controller = new Controller("movie","MoviesData");
                controller.delete_movie(movie.getId());
                try {
                    tab.setContent(accesMoviesData());
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 0, 0, 10));
        
        grid.add(aut,3,1);
        grid.add(auteur,4,1);
        grid.add(tit,3,2);
        grid.add(titre,4,2);
        grid.add(desc,3,3);
        grid.add(description,4,3);
        grid.add(type,3,4);
        grid.add(genre,4,4);
        grid.add(rate,3,5);
        grid.add(note,4,5);       
        grid.add(back,1,1);
        grid.add(validate,3,7);
        grid.add(error,4,6);
        grid.add(delete,4,7);
        
        grid.setAlignment(Pos.TOP_CENTER);          
        pane.setCenter(grid);       
        return pane;
    }
    /**
     * permet d'ajouter un film dans la bdd en remplissant toutes les informations
     * nécéssaires avec blindage de saisies
     * @return
     * @throws ParseException 
     */
    public BorderPane AddMovie() throws ParseException{
        BorderPane pane = new BorderPane();
        pane.setId("setBack");
        //Bouton back
        Button back = new Button();
        back.setId("back-but");
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(90);
        view.setPreserveRatio(true);
        back.setGraphic(view);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                try {
                    tab.setContent(accesMoviesData());
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Label aut = new Label("Author : ");
        final TextField auteur = new TextField();
        Label tit = new Label("Title : ");
        final TextField titre = new TextField();
        Label desc = new Label("Summary : ");
        final TextArea description = new TextArea();
        description.setWrapText(true);
        Label type = new Label("Type : ");
        //final TextField genre = new TextField();
        final ChoiceBox genre = new ChoiceBox();
        genre.getItems().addAll("action","science-fiction","horror","fantastic","comedia");
        Label rate = new Label("Rate : ");
        final TextField note = new TextField();
        final Label date = new Label("Date (yyyy-MM-dd) : ");
        final TextField dat = new TextField();
        Label runningTime = new Label("Running Time (minutes) : ");
        final TextField time = new TextField();
        Label id = new Label("Id : ");
        final TextField ID = new TextField();
        aut.setId("labEmpl");
        auteur.setId("labEmpl");
        tit.setId("labEmpl");
        titre.setId("labEmpl");
        desc.setId("labEmpl");
        description.setId("labEmpl");
        type.setId("labEmpl");
        genre.setId("butEmpl");
        rate.setId("labEmpl");
        note.setId("labEmpl");
        date.setId("labEmpl");
        dat.setId("labEmpl");
        runningTime.setId("labEmpl");
        time.setId("labEmpl");
        id.setId("labEmpl");
        ID.setId("labEmpl");
        
        final Label error = new Label();
        error.setAlignment(Pos.CENTER);
        error.setTextFill(RED);
        error.setText("");

        
        /**Bouton Ajout Image*/
        Button image = new Button("Import Image");
        image.setId("butEmpl");
        image.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser file = new FileChooser();  
                file.setTitle("Open File");  
                file.showOpenDialog(second);
            }
        });
        
        /**Bouton Ajout Film*/
        Button add = new Button("Add Movie");
        add.setId("butEmpl");
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller = new Controller("addmovie","");
                ArrayList<Movies> movies;
                try {
                    
                    movies = controller.dispAllMovies();
                    ArrayList IDs = new ArrayList();
                    for (Movies movie : movies) {
                        IDs.add(movie.getId());
                    }
                    if(ID.getText().equals("") || !IDs.contains(Integer.valueOf( ID.getText())) ) {
                        if(auteur.getText().equals("") || titre.getText().equals("") || description.getText().equals("") || genre.getValue()==null || Integer.valueOf(note.getText())<0 || Integer.valueOf(note.getText())>10 || checkDateFormat(dat.getText())==0)
                            error.setText("Please fill all the inputs or enter correct inputs");
                        else if(checkDateFormat(dat.getText())==1) {
                            //conversion date
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date parsed = (Date) format.parse(dat.getText());
                            final java.sql.Date sql = new java.sql.Date(parsed.getTime());
                            controller.addmovie(new Movies(titre.getText(),auteur.getText(),sql,Integer.valueOf( note.getText()),(String)genre.getValue(),Integer.valueOf( time.getText()),Integer.valueOf( ID.getText()),description.getText()));
                            tab.setContent(accesMoviesData());
                        }
                        else 
                           error.setText("Date format is incorrect"); 
                    }
                    else
                        error.setText("This Id is incorrect, please choose another one");
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 0, 0, 10));
        
        grid.add(aut,3,1);
        grid.add(auteur,4,1);
        grid.add(tit,3,2);
        grid.add(titre,4,2);
        grid.add(desc,3,3);
        grid.add(description,4,3);
        grid.add(type,3,4);
        grid.add(genre,4,4);
        grid.add(rate,3,5);
        grid.add(note,4,5);
        grid.add(date,3,6);
        grid.add(dat,4,6);
        grid.add(runningTime,3,7);
        grid.add(time,4,7);
        grid.add(id,3,8);
        grid.add(ID,4,8);
        grid.add(error,4,9);
        grid.add(image,3,10);
        grid.add(add,4,10);
        grid.add(back,1,1);
        
        grid.setAlignment(Pos.TOP_CENTER); 
        pane.setCenter(grid);
        
       return pane; 
    }
    /**
     * permet de modifier une session dans la bdd avec blindage de saisie
     * @param sess
     * @param id
     * @return 
     */
    public BorderPane ModifSess(final ArrayList<Sessions> sess, final int id)
    {
        BorderPane tot = new BorderPane();
        tot.setId("setBack");
        GridPane nvx = new GridPane();
        nvx.setHgap(50);
        
        Button back = new Button();
        back.setId("back-but");
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(90);
        view.setPreserveRatio(true);
        back.setGraphic(view);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                try {
                    tab.setContent(acessSessionData());
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Label date_txt = new Label("Date ");
        Label heure_txt = new Label("Heure ");
        Label nbr_txt = new Label("Number of places ");
        date_txt.setId("labEmpl");
        heure_txt.setId("labEmpl");
        nbr_txt.setId("labEmpl");
        VBox node1 = new VBox(20),node2 = new VBox(20),node3 = new VBox(20),node4 = new VBox(20), node5 = new VBox(20), node6= new VBox(20);
        node1.getChildren().add(date_txt);
        node2.getChildren().add(heure_txt);
        node3.getChildren().add(nbr_txt);
        Label emp1 = new Label("Click to validate");
        Label emp2 = new Label("Delete");
        node4.getChildren().add(emp1);
        emp1.setId("labEmpl");
        node5.getChildren().add(emp2);
        emp2.setId("labEmpl");
        Button add = new Button("Add Session");
        add.setId("butEmpl");
        add.setStyle("-fx-font-weight:bold");
        final Label error = new Label("");
        error.setTextFill(RED);
        for (Sessions ses : sess) {
            Button validate = new Button("Validate");
            validate.setId("butEmpl2");
            Button delete = new Button("Delete");
            delete.setId("butEmpl2");
            final Sessions session = ses;
            String temp = session.getDate().toString();
            final TextField date = new TextField(temp);
            final TextField heure = new TextField(session.getHoraire());
            final TextField np = new TextField(Integer.toString(session.getNbr_places_max()));
            date.setId("labEmpl");
            heure.setId("labEmpl");
            np.setId("labEmpl");
            node1.getChildren().add(date);
            node2.getChildren().add(heure);
            node3.getChildren().add(np);
            validate.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    try {
                        
                        if((np.getText().equals("") || date.getText().equals(""))||heure.getText().equals(""))
                        {    error.setText("");
                        error.setText("Please complete all data");}
                        else if(checkDateFormat(date.getText())==0)
                        {   error.setText("");
                        error.setText("Wrong date format");
                        }
                        else if(checkHeureFormat(heure.getText())==0)
                        {
                            error.setText("");
                            error.setText("Wrong hour format");
                        }
                        else if(EstUnNombre(np.getText())== 0)
                        {
                            error.setText("");
                            error.setText("Please enter a number");
                        }
                        else if(Integer.valueOf(np.getText()) < 0)
                        {
                            error.setText("");
                            error.setText("Please enter right number");
                        }
                        else
                        {
                            Up_Session(session,date,np,heure);
                            tab.setContent(acessSessionData());
                        }
                        
                    }
                    catch (ParseException | SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            /**Bouton Delete*/
            delete.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event){
                    controller.delete_Session(session.getId());
                    try {
                        tab.setContent(acessSessionData());
                    }
                    catch (SQLException | ClassNotFoundException | ParseException ex) {
                        Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            node4.getChildren().add(validate);
            node5.getChildren().add(delete);
        }
            add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(AddSession(id));
                }
            });
        node1.getChildren().add(error);
        node6.getChildren().add(add);
        nvx.addColumn(0,back);
        nvx.addColumn(1,node1);
        nvx.addColumn(2,node2);
        nvx.addColumn(3,node3);
        nvx.addColumn(4,node4);
        nvx.addColumn(5,node5);
        nvx.addColumn(6,node6);
        nvx.setAlignment(Pos.TOP_CENTER); 
        tot.setCenter(nvx);
        return tot;
    }
    /**
     * fonction permettant d'appeler le controller 
     * afin de modifier la bdd pour les sessions
     * @param session
     * @param date
     * @param np
     * @param heure
     * @throws ParseException 
     */
    public void Up_Session(Sessions session,TextField date,TextField np, TextField heure) throws ParseException
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = (Date) format.parse(date.getText());
        final java.sql.Date sql = new java.sql.Date(parsed.getTime());
        Sessions ses = new Sessions(session.getId(),session.getMovie(),sql,Integer.valueOf(np.getText()),session.getActual(),heure.getText(),session.getAmount());
        controller.update_Session(ses);
    }
    /**
     * permet de modifier une session en récupérant
     * l'id de son film pour pas de confusion
     * @param idmov
     * @return 
     */
    public BorderPane AddSession(final int idmov)
    {
        BorderPane pane = new BorderPane();
        pane.setId("setBack");
        GridPane g = new GridPane();
        g.setId("setBack");
        //Bouton back
        g.setHgap(30);
        Button back = new Button();
        back.setId("back-but");
        Image img;
        img = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(90);
        view.setPreserveRatio(true);
        back.setGraphic(view);
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                try {
                    tab.setContent(acessSessionData());
                } catch (SQLException | ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        Label date_txt = new Label("Date (yyyy-mm-dd)");
        Label heure_txt = new Label("Heure ");
        Label nbr_txt = new Label("Number of places ");
        date_txt.setId("labEmpl");
        heure_txt.setId("labEmpl");
        nbr_txt.setId("labEmpl");
        VBox node1 = new VBox(20),node2 = new VBox(20),node3 = new VBox(20),node4 = new VBox(20),node5 = new VBox(20), node0 = new VBox(20);
        node1.getChildren().add(date_txt);
        node2.getChildren().add(heure_txt);
        node3.getChildren().add(nbr_txt);
        final TextField date = new TextField();
        final TextField heure = new TextField();
        final TextField max = new TextField();
        date.setId("labEmpl");
        heure.setId("labEmpl");
        max.setId("labEmpl");
        Label id = new Label("Id ");
        id.setId("labEmpl");
        final TextField ID = new TextField();
        ID.setId("labEmpl");
        node4.getChildren().add(id);
        node1.getChildren().add(date);
        node2.getChildren().add(heure);
        node3.getChildren().add(max);
        node4.getChildren().add(ID);
        final Label error = new Label("");
        error.setTextFill(RED);
        //Bouton Ajout Session
        Button add = new Button("Add Session");
        add.setId("butEmpl");
        add.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            controller = new Controller("addsession","");
            ArrayList<Sessions> session;
            try {
                    //conversion date
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date parsed = (Date) format.parse(date.getText());
                final java.sql.Date sql = new java.sql.Date(parsed.getTime());
                    if((max.getText().equals("") || date.getText().equals(""))||heure.getText().equals(""))
                    {    error.setText("");
                        error.setText("Please complete all data");}
                    else if(checkDateFormat(date.getText())==0)
                    {   error.setText("");
                        error.setText("Wrong date format");
                    }
                    else if(checkHeureFormat(heure.getText())==0)
                    {
                        error.setText("");
                        error.setText("Wrong hour format");
                    }
                    else if(EstUnNombre(max.getText())== 0)
                    {
                        error.setText("");  
                        error.setText("Please enter a number");
                    }
                    else if(Integer.valueOf(max.getText()) < 0)
                    {
                        error.setText("");  
                        error.setText("Please enter right number");
                    }
                    
                    else    
                    {   
                        session = controller.recolterSessions();
                        ArrayList IDs = new ArrayList();
                    for (Sessions session1 : session) {
                        IDs.add(session1.getId());
                    }
                        if(!IDs.contains(Integer.valueOf( ID.getText())) ) {
                        Sessions S = new Sessions(Integer.valueOf(ID.getText()),idmov,sql,Integer.valueOf(max.getText()),0,heure.getText(),0.0);
                        controller.Add_Session(S,idmov);
                        tab.setContent(acessSessionData());
                        }
                        else
                        {
                            error.setText("ID taken");
                        }
                    } 
            }
            catch (SQLException | ClassNotFoundException | ParseException ex) {
                    Logger.getLogger(AutreMain.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
        });
        node1.getChildren().add(error);
        node5.getChildren().add(add);
        g.addColumn(0,back);
        g.addColumn(1,node1);
        g.addColumn(2,node2);
        g.addColumn(3,node3);
        g.addColumn(4,node4);
        g.addColumn(5,node5);
        g.setAlignment(Pos.TOP_CENTER);
        pane.setCenter(g);
        return pane;
    }
    
    public HBox emptyRight(double width){
        HBox nvx = new HBox();
        Button but = new Button();
        but.setPrefWidth(width);
        but.setId("back-but-hide");
        nvx.getChildren().add(but);
        return nvx;
    }
    /**
     * sert à gérer la liste d'employé 
     * que ce soit supprimer ou ajouter un employé
     * @return 
     */
    public VBox dispEmployee(){
        VBox nvx = new VBox(10);
        nvx.setId("setBack");
        Label uno = new Label("Click on one employee to delete");
        uno.setId("labEmpl");
        nvx.setAlignment(Pos.CENTER);
        GridPane tot = new GridPane();
        tot.setHgap(20);
        tot.setVgap(20);
        tot.setAlignment(Pos.CENTER);
        ArrayList<Employees> employees;
        controller = new Controller("&","&");
        employees = controller.getAllEmployee();
        Button butoo = new Button();
        butoo.setId("back-but");
        Image img2;
        img2 = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(90);
        view2.setPreserveRatio(true);
        butoo.setGraphic(view2);
        butoo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(accessMembEmploy());
            }
        });
        for(int i=0;i<employees.size();i++){
            final Button but = new Button("First Name : "+employees.get(i).getFirstName()+" - Last Name : "+employees.get(i).getLastName()+" - Login : "+employees.get(i).getLogin()+" - Access key : "+employees.get(i).getAccess());
            but.setId("butEmpl");
            final Employees temp = employees.get(i);
            but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                public void handle(ActionEvent event){
                    if(!temp.getLogin().equals(actual.getLogin()))
                        tab.setContent(deleteEmployee(temp));
                }
            });
            tot.addRow(i,but);
        }
        Button buton = new Button("+ Add Employee");
        buton.setId("butEmpl");
        buton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(addEmployee());
            }
        });
        nvx.getChildren().addAll(butoo,uno,tot,buton);
        return nvx;
    }
    /**
     * blindage pour la saisie de date
     * @param date
     * @return 
     */
    public int checkDateFormat(String date){
        int condi = 0;
        if(date.length()==10)
        {
            if(date.charAt(4)=='-' && date.charAt(7)=='-'){
                String split[] = date.split("-", 3);
                if(split[0].length()==4 && split[1].length()==2 && split[2].length()==2){
                    if(Integer.valueOf(split[1])>0 && Integer.valueOf(split[1])<13 && Integer.valueOf(split[2])>0 && Integer.valueOf(split[2])<32)
                        condi = 1;
                }
            }
            else
                condi = 0;
        }
        else{
            condi = 0;
        }
        return condi;
    }
    /**
     * blindage pour la saisie de nombre
     * @param nb
     * @return 
     */
    public int EstUnNombre(String nb)
    {
        int a = 1;
        if(Pattern.matches("[a-zA-Z]+",nb)== true)
            a = 0;
        
        return a;
    }
    /**
     * blidnage pour la saisie d'heure
     * @param heure
     * @return 
     */
    public int checkHeureFormat(String heure)
    {
        int condi = 0;
        if(heure.length()==5)
        {
            if(heure.charAt(2)=='h')
            {
                String split[] = heure.split("h", 2);
                if(split[0].length()==2&&split[1].length()==2)
                    if(Integer.valueOf(split[0])>=0 && Integer.valueOf(split[0])<=24 && Integer.valueOf(split[1])>=0 && Integer.valueOf(split[1])<=59)
                    {
                        condi = 1;
                    }
            }else condi = 0;
        }else condi =0;
        return condi;
    }
    /**
     * fonction pour supprimer un employé
     * @param employee
     * @return 
     */
    public VBox deleteEmployee(final Employees employee){
        VBox nvx = new VBox(40);
        nvx.setId("setBack");
        nvx.setAlignment(Pos.CENTER);
        Label lab1 = new Label("Are you sure you want to delete this employee :");
        Label lab2 = new Label(employee.getFirstName()+" "+employee.getLastName());
        Label lab3 = new Label("Your decision will be definitive");
        lab1.setId("labEmpl");
        lab2.setId("labEmpl");
        lab3.setId("labEmpl");
        Button but1 = new Button("Confirm delete employee");
        Button but2 = new Button("Cancel");
        but1.setId("butEmpl");
        but2.setId("butEmpl");
        but2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(dispEmployee());
            }
        });
        but1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                controller = new Controller("&","&");
                controller.deleteEmployee(employee.getLogin());
                tab.setContent(dispEmployee());
            }
        });
        HBox inte = new HBox(20);
        inte.setAlignment(Pos.CENTER);
        inte.getChildren().addAll(but1,but2);
        nvx.getChildren().addAll(lab1,lab2,lab3,inte);
        return nvx;
    }
    /**
     * fonction pour ajouter un employé
     * @return 
     */
    public VBox addEmployee(){
        VBox tot = new VBox(20);
        tot.setId("setBack");
        tot.setAlignment(Pos.CENTER);
        Label lab0 = new Label("Create new Employee");
        Label lab1 = new Label("First Name of the employee : ");
        Label lab2 = new Label("Last Name of the employee : ");
        Label lab3 = new Label("Access key of the employee : ");
        Label lab4 = new Label("Password is set by default, the employee will change it");
        lab0.setId("labEmpl");
        lab1.setId("labEmpl");
        lab2.setId("labEmpl");
        lab3.setId("labEmpl");
        lab4.setId("labEmpl");
        HBox box1 = new HBox(20);
        HBox box2 = new HBox(20);
        HBox box3 = new HBox(20);
        HBox box4 = new HBox(20);
        box1.setAlignment(Pos.CENTER);
        box2.setAlignment(Pos.CENTER);
        box3.setAlignment(Pos.CENTER);
        box4.setAlignment(Pos.CENTER);
        final TextField txt1 = new TextField();
        txt1.setPromptText("First Name");
        final TextField txt2 = new TextField();
        txt2.setPromptText("Last Name");
        txt1.setId("labEmpl");
        txt2.setId("labEmpl");
        final ChoiceBox txt3 = new ChoiceBox();
        txt3.getItems().addAll("A","B","C");
        txt3.setId("butEmpl");
        Button cfr = new Button("Confirm");
        Button can = new Button("Cancel");
        can.setId("butEmpl");
        cfr.setId("butEmpl");
        box1.getChildren().addAll(lab1,txt1);
        box2.getChildren().addAll(lab2,txt2);
        box3.getChildren().addAll(lab3,txt3);
        box4.getChildren().addAll(cfr,can);
        can.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(dispEmployee());
            }
        });
        cfr.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                controller = new Controller("&","&");
                controller.insert_employee(txt1.getText(),txt2.getText(),(String)txt3.getValue());
                tab.setContent(dispEmployee());
            }
        });
        
        tot.getChildren().addAll(lab0,box1,box2,box3,lab4,box4);
        return tot;
    }
    /**
     * affiche tous les clients membres
     * @return 
     */
    public VBox dispMember(){
        VBox nvx = new VBox(10);
        nvx.setId("setBack");
        Label uno = new Label("Click on one member to modify");
        uno.setId("labEmpl");
        nvx.setAlignment(Pos.CENTER);
        GridPane tot = new GridPane();
        tot.setHgap(20);
        tot.setVgap(20);
        tot.setAlignment(Pos.CENTER);
        ArrayList<Members> members;
        controller = new Controller("&","&");
        members = controller.getAllMembers();
        Button butoo = new Button();
        butoo.setId("back-but");
        Image img2;
        img2 = new Image(getClass().getResourceAsStream("/images/back.png"));
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(90);
        view2.setPreserveRatio(true);
        butoo.setGraphic(view2);
        butoo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(accessMembEmploy());
            }
        });
        int j=0,k=0;
        for(int i=0;i<members.size();i++){
            if(i%3==0){
                k=0;
                j++;
            }
            final Button but = new Button(members.get(i).getFirstName()+" "+members.get(i).getLastName());
            but.setId("butEmpl");
            final Members temp = members.get(i);
            but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
                public void handle(ActionEvent event){
                    tab.setContent(modifyMembers(temp));
                }
            });
            tot.add(but,k,j);
            k++;
        }
        nvx.getChildren().addAll(butoo,uno,tot);
        return nvx;
    }
    /**
     * modifier un client membre
     * @param memb
     * @return 
     */
    public HBox modifyMembers(final Members memb){
        VBox tot = new VBox(20);
        
        tot.setAlignment(Pos.CENTER);
        HBox toto = new HBox();
        toto.setId("setBack");
        toto.setAlignment(Pos.CENTER);
        Label lab1 = new Label("First Name : "+memb.getFirstName());
        Label lab2 = new Label("Last Name : "+memb.getLastName());
        Label lab3 = new Label("Login : "+memb.getLogin());
        Label lab4 = new Label("Age : ");
        lab1.setId("labEmpl");
        lab2.setId("labEmpl");
        lab3.setId("labEmpl");
        lab4.setId("labEmpl");
        final TextField txt = new TextField();
        txt.setText(Integer.toString(memb.getAge()));
        txt.setPromptText("Enter age");
        HBox inter1 = new HBox(20);
        inter1.getChildren().addAll(lab4,txt);
        final Label lab5 = new Label();
        lab5.setId("labEmpl");
        lab5.setTextFill(RED);
        Button but1 = new Button("Cancel");
        Button but2 = new Button("Modify");
        Button but3 = new Button("Delete");
        but1.setId("butEmpl");
        but2.setId("butEmpl");
        but3.setId("butEmpl");
        HBox inter2 = new HBox(20);
        but1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                tab.setContent(dispMember());
            }
        });
        but2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                controller = new Controller("&","&");
                boolean condi = controller.modifyMember(txt.getText(),memb.getLogin());
                if(condi==false){
                    lab5.setText("Enter good input please");
                    txt.setText("");
                } 
                else
                    tab.setContent(dispMember());
            }
        });
        but3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                controller = new Controller("&","&");
                controller.deleteMember(memb.getLogin());
                tab.setContent(dispMember());
            }
        });
        
        inter2.getChildren().addAll(but1,but2,but3);
        tot.getChildren().addAll(lab1,lab2,lab3,inter1,lab5,inter2);
        toto.getChildren().add(tot);
        return toto;
    }
}
