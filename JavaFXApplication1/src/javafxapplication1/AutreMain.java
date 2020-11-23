/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import javafx.stage.Stage;

/**
 *
 * @author clemf
 */
public class AutreMain{

    private final Employees actual;
    private final Stage second;
    private final Tab tab = new Tab();
    private Label connected = new Label("");
    
    
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
                tab.setContent(changePromotion());
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
    
    public VBox changePromotion(){
        VBox tot = new VBox(30);
        tot.setAlignment(Pos.CENTER);
        Label lab = new Label("Change actual offers : ");
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
        final TextField txt1 = new TextField();
        final TextField txt2 = new TextField();
        final TextField txt3 = new TextField();
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
                        System.out.println("ah oe");
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
        final Label lab4 = new Label();
        
        box1.getChildren().addAll(button1,txt1);
        box2.getChildren().addAll(button2,txt2);
        box3.getChildren().addAll(button3,txt3);
        box4.getChildren().addAll(pwd);
        Button button = new Button("Change actual promotions");
        tot.getChildren().addAll(lab,box1,lab1,box2,lab2,box3,lab3,box4,lab4,button);
        
        button.setOnAction(new EventHandler<ActionEvent>() {     
            @Override  
            public void handle(ActionEvent arg0) {
                int condi=-1;
                if(!pwd.getText().equals(actual.getPassword())){
                    lab4.setText("Wrong password");
                    lab4.setTextFill(RED);
                }
                else{
                    Controller controller = new Controller("managePromotions","employeeTab");
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
                    }
                }
            }
        });
        
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
                //tab.setContent(homePage());
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
    
}
