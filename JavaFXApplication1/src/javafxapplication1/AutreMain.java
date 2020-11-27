/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart.Data; 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.RED;
import javafx.stage.Stage;
import java.util.Collections;

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
        connected.setStyle("-fx-font-size : 2em; -fx-text-fill:#F5DEB3; -fx-font-weight : bold");
        
        VBox ad = new VBox(10);
        ad.setStyle("-fx-background-color :#8B4513");
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
        nvx.setStyle("-fx-background-color:#8B4513");
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
                tab.setContent(seeStatistics());
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
    
    public ScrollPane seeStatistics(){
        ScrollPane scroll = new ScrollPane();
        scroll.setStyle("-fx-background-color:#8B4513");
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
        ArrayList<Session> sessions;
        PieChart piechart = new PieChart();
        
        int senior = 0, children = 0, regular = 0;
        
        Controller controller = new Controller("statistics","member");
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
            for (Session session : sessions) {
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
        bar.setTitle("Number of places according to the movie");
        
        XYChart.Series<String,Float> series = new XYChart.Series<>();
        
        for(int i=0;i<movies.size();i++){
            series.getData().add(new XYChart.Data(movies.get(i).getTitle(),nbr.get(i)));
        }
        
        ArrayList<String> date = new ArrayList<>();
        boolean condi=false;
        for (Session session : sessions) {
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
            for (Session session : sessions) {
                if (session.getDate().toString().equals(date.get(i))) {
                    double temp = toto.get(i);
                    toto.set(i, temp + session.getAmount());
                }
            }
        }
        
        NumberAxis xaxe = new NumberAxis(0,date.size(),1);  
        NumberAxis yaxe = new NumberAxis(0,1500,10);  
          
        //Defining Label for Axis   
        xaxis.setLabel("Days");
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
    
    public VBox totalTabStat(ArrayList<Movies> movies, ArrayList<Session> sessions,ArrayList<Integer> nbr_sess, ArrayList<Double> amount){
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        final PieChart piechart2 = new PieChart();
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
            list2.add(new PieChart.Data(movies.get(i).getTitle()+" : "+amount.get(i)*100/recette+"%", amount.get(i)*100/recette));
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
        
        for (Session session : sessions) {
            if (session.getHoraire().length() < 5) {
                before10 += session.getAmount();
            } else if (session.getHoraire().compareTo("14h00") < 0) {
                before10 += session.getAmount();
            } else if (session.getHoraire().compareTo("18h00") < 0) {
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
    
    public BorderPane changePromotion(){
        BorderPane toto=new BorderPane();
        toto.setStyle("-fx-background-color:#8B4513");
        VBox tot = new VBox(10);
        tot.setAlignment(Pos.CENTER);
        Label lab = new Label("Change actual offers : ");
        lab.setStyle("-fx-font-size:1.2em; -fx-text-fill: #F5DEB3;");
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
    
    public BorderPane accessMembEmploy(){
        BorderPane tot = new BorderPane();
        tot.setStyle("-fx-background-color : #8B4513");
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
                //tab.setContent(homePage());
            }
        });
        but2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){
                //tab.setContent(homePage());
            }
        });
        tot.setLeft(inter);
        tot.setCenter(nvx);
        tot.setRight(emptyRight(but.getWidth()));
        return tot;
    }
    
    public BorderPane accessCinemaData(){
        BorderPane tot = new BorderPane();
        tot.setStyle("-fx-background-color : #8B4513");
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
    
    public HBox emptyRight(double width){
        HBox nvx = new HBox();
        Button but = new Button();
        but.setPrefWidth(width);
        but.setId("back-but-hide");
        nvx.getChildren().add(but);
        return nvx;
    }
    
}
