/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static javafx.scene.paint.Color.RED;
import javafx.stage.FileChooser;
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
    
    public BorderPane accesMoviesData() throws SQLException, ClassNotFoundException, ParseException{
        BorderPane pane = new BorderPane();
        ScrollPane scroll = new ScrollPane();
        
        //Bouton back
        Button back = new Button();
        back.setId("button-home2");
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
        addMovie.setStyle("-fx-font-weight: bold;");
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
        title.setId("title-moviedata");
        
        HBox inter = new HBox(50);
        inter.getChildren().addAll(back,title,addMovie);
        inter.setAlignment(Pos.CENTER);
        
       
        
        try {
            //liste des films sous forme de boutons
            controller = new Controller("movie","MoviesData");
            final ArrayList<Movies> movies = controller.dispAllMovies();
            ArrayList<Button> movie = new ArrayList<>();  
            for(int i=0;i<movies.size();i++){
                movie.add(new Button());
                movie.get(i).setText(movies.get(i).getTitle());
            }
            VBox box = new VBox(20);
            box.setAlignment(Pos.CENTER);
            for(int i=0;i<movie.size();i++){
                box.getChildren().add(movie.get(i));
            }      

            
            //Action sur les boutons
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
        } catch (SQLException | ClassNotFoundException | ParseException ex) {
            Logger.getLogger(MainClass.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //liste des films sous forme de boutons
               
       return pane; 
    }
    
    public BorderPane acessSessionData() throws SQLException, ClassNotFoundException, ParseException
    {
        final BorderPane pane = new BorderPane();

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
        
        
            controller = new Controller("movie","MoviesData");
            final ArrayList<Movies> movies = controller.dispAllMovies();
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
            movie.get(0).setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event){
                    pane.setCenter(ModifSess(movies.get(0),movies));
                } 
            });
       return pane; 
    }
    
    public BorderPane ModifMovie(final Movies movie){
        BorderPane pane = new BorderPane();
        
        //Bouton back
        Button back = new Button();
        back.setId("button-home2");
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
        
        final Label error = new Label();
        error.setAlignment(Pos.CENTER);
        error.setTextFill(RED);
        error.setText("");
        
        //Bouton Validate
        Button validate = new Button("Validate");
        validate.setId("button-home");
        validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                if(auteur.getText().equals("") || titre.getText().equals("") || description.getText().equals("") || genre.getText().equals("") || Integer.valueOf(note.getText())<0 || Integer.valueOf(note.getText())>10)
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
        delete.setId("button-home");
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
    
    public BorderPane AddMovie() throws ParseException{
        BorderPane pane = new BorderPane();
         
        
        //Bouton back
        Button back = new Button();
        back.setId("button-home2");
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
        
        
        final Label error = new Label();
        error.setAlignment(Pos.CENTER);
        error.setTextFill(RED);
        error.setText("");

        
        //Bouton Ajout Image
        Button image = new Button("Import Image");
        image.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser file = new FileChooser();  
                file.setTitle("Open File");  
                file.showOpenDialog(second);
            }
        });
        
        //Bouton Ajout Film
        Button add = new Button("Add Movie");
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller = new Controller("addmovie","");
                ArrayList<Movies> movies;
                try {
                    
                    movies = controller.dispAllMovies();
                    ArrayList IDs = new ArrayList();
                    for(int i=0;i<movies.size();i++){
                        IDs.add(movies.get(i).getId());
                    }
                    if(ID.getText().equals("") || !IDs.contains(Integer.valueOf( ID.getText())) ) {
                        if(auteur.getText().equals("") || titre.getText().equals("") || description.getText().equals("") || genre.getValue()==null || Integer.valueOf(note.getText())<0 || Integer.valueOf(note.getText())>10 && checkDateFormat(dat.getText())==0)
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
    
    public BorderPane ModifSess(final Movies movie,final ArrayList<Movies> movies)
    {
        BorderPane tot = new BorderPane();
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
        VBox node1 = new VBox(20),node2 = new VBox(20),node3 = new VBox(20);
        node1.getChildren().add(date_txt);
        node2.getChildren().add(heure_txt);
        node3.getChildren().add(nbr_txt);
        for(int i=0;i<sess.size();i++){
            String temp = sess.get(i).getDate().toString();
            TextField date = new TextField(temp);
            TextField heure = new TextField(sess.get(i).getHoraire());
            String nbrr = Integer.toString(sess.get(i).getNbr_places_max());
            TextField np = new TextField(nbrr);
            Label max = new Label("");
            node1.getChildren().add(date);
            node2.getChildren().add(heure);
            node3.getChildren().add(np);
        }
        nvx.addColumn(0,node1);
        nvx.addColumn(1,node2);
        nvx.addColumn(2,node3);
        tot.setCenter(nvx);
        return tot;
    }
    
    public BorderPane accesCustomerData() throws SQLException, ParseException, ClassNotFoundException{
        BorderPane pane = new BorderPane();
        ScrollPane scroll = new ScrollPane();
        
        //Bouton back
        Button back = new Button();
        back.setId("button-home2");
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
        
        
        //Boutton Ajouter Customer
        Button addCustomer = new Button("(+) Add a customer");
        addCustomer.setStyle("-fx-font-weight: bold;");
        addCustomer.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                //tab.setContent(AddCustomer());
            }
        }); 
        
        //titre
        Label title = new Label("Delete a Customer from the DataBase");
        title.setId("title-customerdata");
        
        HBox inter = new HBox(50);
        inter.getChildren().addAll(back,title);
        inter.setAlignment(Pos.CENTER);
        
        //Liste des Customer sous forme de boutons
        controller = new Controller("","");
        final ArrayList<Members> customers = controller.AllCustomers();
        ArrayList<Button> cust = new ArrayList<>();
        for(int i=0;i<customers.size();i++){
            cust.add(new Button());
            cust.get(i).setText("Delete customer : "+customers.get(i).getLogin()+ ", id : "+customers.get(i).getId());
        }
        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        for(int i=0;i<cust.size();i++){
            box.getChildren().add(cust.get(i));
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
                        try {
                            tab.setContent(accesMoviesData());
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
