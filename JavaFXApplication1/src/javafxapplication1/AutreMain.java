/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author clemf
 */
public class AutreMain implements Runnable{

    private final Employees actual;
    private final Stage second;
    private final Tab tab = new Tab();
    private Label connected = new Label("");
    private Controller controller;
    
    public AutreMain(Employees nvx){
        this.actual = nvx;
        second = new Stage();
        TabPane tot = new TabPane();
        tab.setContent(homePage());
        tot.getTabs().add(tab);
        connected = new Label("Hello "+actual.getFirstName().substring(0,1).toUpperCase()+actual.getFirstName().substring(1)+" "+actual.getLastName().substring(0,1).toUpperCase()+actual.getLastName().substring(1)+" !");
        connected.setStyle("-fx-font-size : 2em");
        
        VBox ad = new VBox(10);
        ad.setAlignment(Pos.CENTER);
        ad.getChildren().addAll(connected,tot);
        
        Scene scene = new Scene(ad, 1500, 800);
        scene.getStylesheets().add(getClass().getResource("StyleAutre.css").toExternalForm());
        second.setTitle("Employee");
        second.setScene(scene);
        second.setResizable(true);
        second.show();
    }
    
    public BorderPane homePage(){
        BorderPane tot = new BorderPane();
        VBox nvx = new VBox(50);
        nvx.setAlignment(Pos.CENTER);
        Label lab1 = new Label("MENU");
        lab1.setId("labMenu");
        Button but1 = new Button("Manage Cinema DataBase");
        Button but2 = new Button("Manage Cinema's Promotion");
        Button but3 = new Button("See cinema's statistics");
        Button but4 = new Button("Manage Employees/Members");
        Button but5 = new Button("Log Out");
        but1.setId("button-home");
        but2.setId("button-home");
        but3.setId("button-home");
        but4.setId("button-home");
        but5.setId("logout");
        Image img2;
        img2 = new Image(getClass().getResourceAsStream("/images/out.png"));
        ImageView view2 = new ImageView(img2);
        view2.setFitHeight(90);
        view2.setPreserveRatio(true);
        but5.setGraphic(view2);
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
        
        but5.setOnAction(new EventHandler<ActionEvent>() {     
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
                //second.close();
            }
        });
        but3.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                //second.close();
            }
        });
        but4.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                tab.setContent(accessMembEmploy());
            }
        });
        nvx.getChildren().addAll(lab1,but1,but2,but3,but4,but5);
        
        
        tot.setCenter(nvx);
        return tot;
    }
    
    public BorderPane accessMembEmploy(){
        BorderPane tot = new BorderPane();
        Button but = new Button();
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
        
        
        Label lab1 = new Label("Modify Employee/Member Data Bases");
        lab1.setId("labMenu");
        inter.getChildren().add(but);
        Button but1 = new Button("Access Member Data Base");
        Button but2 = new Button("Access Employee Data Base");
        but1.setId("button-home");
        but2.setId("button-home");
        nvx.getChildren().addAll(inter,lab1,but1,but2);
        but1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                //tab.setContent(homePage());
            }
        });
        but2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                //tab.setContent(homePage());
            }
        });
        tot.setCenter(nvx);
        return tot;
    }
    
    public BorderPane accessCinemaData(){
        BorderPane tot = new BorderPane();
        Button but = new Button();
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
                //tab.setContent(homePage());
            }
        });
        but3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                //tab.setContent(homePage());
            }
        });
        tot.setCenter(nvx);
        return tot;
    }
    
    public BorderPane accesMoviesData() throws SQLException, ClassNotFoundException, ParseException{
        BorderPane pane = new BorderPane();

        //Bouton back
        Button back = new Button();
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
        HBox inter = new HBox(50);
        inter.getChildren().add(back);
        
        //titre
        Label title = new Label("Modify Movie DataBase");
        title.setId("title-moviedata");
        
        try {
            controller = new Controller("movie","MoviesData");
            ArrayList<Movies> movies = controller.dispAllMovies();
            ArrayList<Button> movie = new ArrayList<>();
            for(int i=0;i<movies.size();i++){
                movie.add(new Button());
                movie.get(i).setText(movies.get(i).getTitle());
            }
            VBox box = new VBox(20);
            box.setAlignment(Pos.CENTER);
            box.getChildren().addAll(inter,title);
            for(int i=0;i<movie.size();i++){
                box.getChildren().add(movie.get(i));
            }
        
        pane.setCenter(box);
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //liste des films sous forme de boutons
        
        
        
        
        
       return pane; 
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(10) ;
        }catch (InterruptedException e) {
            System.out.println("error");
                    
        }
    }
    public void cancel() {
      
       // interruption du thread courant, c'est-à-dire le nôtre
        Thread.currentThread().interrupt() ;
   }
    
}
