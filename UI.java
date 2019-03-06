import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.layout.HBox;

import javafx.scene.layout.StackPane;
import javafx.scene.control.Label; 
import javafx.scene.text.Font; 

import javafx.scene.layout.FlowPane;

import javafx.scene.layout.GridPane;

import javafx.scene.layout.BorderPane;

import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;

import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;
import javafx.event.EventHandler; 

import javafx.scene.paint.Color;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

public class UI extends Application{
    public void start(Stage primaryStage){
        primaryStage.setTitle("Texas Hold'em");

        Pane root = new Pane();
        Scene scene = new Scene(root,800,600);
        root.setStyle("-fx-background-color: #228B22;");

        Button btn1 = new Button(); 
        btn1.setText("Start"); 
        btn1.setLayoutX(380); 
        btn1.setLayoutY(500); 
        root.getChildren().add(btn1);

        TextField pickStartChipsField = new TextField("Enter Chip Amount"); 
        pickStartChipsField.setPrefWidth(200); 
        pickStartChipsField.setLayoutX(200); 
        pickStartChipsField.setLayoutY(200); 
        root.getChildren().add(pickStartChipsField);        
        
        TextField pickPlayerAmount = new TextField("Enter Number of Players"); 
        pickPlayerAmount.setPrefWidth(150); 
        pickPlayerAmount.setLayoutX(200); 
        pickPlayerAmount.setLayoutY(250); 
        root.getChildren().add(pickPlayerAmount);

        Text gameName = new Text("Texas Hold'em"); 
        gameName.setFill(Color.WHITE);
        gameName.setFont(Font.font("Arial",FontWeight.BOLD,60)); //gameName.setStyle("-fx-font: 50 arial;");
        gameName.setLayoutX(175); 
        gameName.setLayoutY(100); 
        root.getChildren().add(gameName);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}