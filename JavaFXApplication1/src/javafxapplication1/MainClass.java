/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;


import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    public void start(Stage primaryStage) throws FileNotFoundException {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        //int width = gd.getDisplayMode().getWidth();
        //int height = gd.getDisplayMode().getHeight();
        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Home");
        Tab tab2 = new Tab("CinePass");
        Tab tab3 = new Tab("Search");
        Tab tab4 = new Tab("Connection");
        tab2.setContent((getDiscount()));
        
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
    
    public BorderPane getDiscount() throws FileNotFoundException{
        BorderPane pane = new BorderPane();
        pane.setCenter(getGridtab2());
        
        Button button = new Button("Sign-in/Sign-up");
        button.setId("button-tab2");
        button.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
        pane.setBottom(button);
        BorderPane.setMargin(button,new Insets(10,0,0,550));
        
        return pane;
    }
    
    
    
    public GridPane getGridtab2() throws FileNotFoundException{
        GridPane root=new GridPane();
        root.setId("root-tab2");
        
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
