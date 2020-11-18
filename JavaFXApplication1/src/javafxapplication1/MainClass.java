/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

//import com.sun.javafx.scene.layout.region.BackgroundFill;
import static java.awt.Color.*;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Panel;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import static java.awt.Color.red;
import static java.awt.Color.yellow;
import java.awt.Rectangle;
import java.io.File;
import java.net.MalformedURLException;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import static javax.swing.text.StyleConstants.Background;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

/**
 *
 * @author clemf
 */
public class MainClass extends Application {
    @Override
    public void start(Stage primaryStage) throws MalformedURLException {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //int width = gd.getDisplayMode().getWidth();
        //int height = gd.getDisplayMode().getHeight();
        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Home");
        Tab tab2 = new Tab("CinePass");
        Tab tab3 = new Tab("Search");
        Tab tab4 = new Tab("Connection");
        tab4.setContent(getPane());
        tab1.setContent(getPanetab1());
        tabPane.getTabs().add(tab1);
        tabPane.getTabs().add(tab2);
        tabPane.getTabs().add(tab3);
        tabPane.getTabs().add(tab4);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        VBox vbox = new VBox();
        vbox.getChildren().add(tabPane);
        Scene scene = new Scene(vbox, 400, 600);
        //scene.getStylesheets().add("Style.css");
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setTitle("CHANGE");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
    
    public BorderPane getPane(){
        BorderPane pane = new BorderPane();
        //BorderPane p = new BorderPane();
        //p.setCenter(getGridtab4());
        
        pane.setCenter(getGridtab4());
        return pane;
    }
    
    public GridPane getGridtab4(){
        GridPane root=new GridPane(); 
        Label Email = new Label("Your ID number :");  
        Label Password = new Label("Password :");  
        Email.setId("label-tab4");
        Password.setId("label-tab4");
//Adding text-field to the form   
        TextField tf1=new TextField();
        TextField tf2=new TextField(); 
        
        tf1.setId("text-tab4");
        tf2.setId("text-tab4");
        
//creating submit button   
        Button Submit=new Button ("Submit");  
  
//setting ID for the submit button so that the particular style rules can be applied to it.   
        Submit.setId("submit"); 
//Creating reset button   
        Button Reset=new Button ("Reset");  
  
//Creating title   
        Label title = new Label();  
        title.setText("Sign Up"); 
        title.setUnderline(true);  
        title.setId("title");  
//creating grid-pane  
  
//adding the the nodes to the GridPane's rows   
        
        root.addRow(0, title);
        root.addRow(3, Email);
        root.addRow(4, tf1);
        root.addRow(6, Password);
        root.addRow(7, tf2);
        root.addRow(9, Submit,Reset);
  
//setting horizontal and vertical gaps between the rows   
        root.setHgap(10);  
        root.setVgap(10);
        root.setId("root-tab4");
        return root;
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
    
    
}
