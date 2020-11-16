/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import com.sun.javafx.scene.layout.region.BackgroundFill;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import static javax.swing.text.StyleConstants.Background;

/**
 *
 * @author clemf
 */
public class MainClass extends Application {
    
    private Tab tab1 = new Tab("Home"),tab2 = new Tab("CinePass"),tab3 = new Tab("Search"),tab4 = new Tab("Connection");
    
    @Override
    public void start(Stage primaryStage) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //int width = gd.getDisplayMode().getWidth();
        //int height = gd.getDisplayMode().getHeight();
        TabPane tabPane = new TabPane();
        
        tab4.setContent(getPane());
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
//creating grid-pane  
        CheckBox c1 = new CheckBox("Check if you are an employee"); 
//adding the the nodes to the GridPane's rows   
        
        root.addRow(0, title);
        root.addRow(3, Email);
        root.addRow(4, tf1);
        root.addRow(6, Password);
        root.addRow(7, tf2);
        root.addRow(9, c1);
        root.addRow(12, submit, reset);
  
//setting horizontal and vertical gaps between the rows   
        root.setHgap(10);  
        root.setVgap(10);
        root.setId("root-tab4");
        return root;
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
        TextField tf4=new TextField();
        TextField tf5=new TextField();
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
                tab4.setContent(getPane());
            }
        });
        
        
        sub.getChildren().addAll(str,hyperlink);
        sub.setId("HBox-tab4");
        
        test.setBottom(sub);
        
        return test;
    }
    
}
