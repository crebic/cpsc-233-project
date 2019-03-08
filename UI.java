import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javafx.scene.layout.HBox;

import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
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

public class UI extends Application {
	private int pot = 0;
	private int currentPlayer;
	private int callAmount;
	private int playerTurn = 0;
	private int numberOfPlayers = 0;
	public ArrayList<Card> tableCards = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player playerWithHighestCallAmount;
	private ArrayList<Integer> notWinners = new ArrayList<Integer>();

	public static void main(String[] args) {
		launch(args);
	}

	public void start(Stage primaryStage) {
		primaryStage.setTitle("Texas Hold'em");

		// root and scene are for main menu, root2 and scence2 are for the game state
		Pane root = new Pane();
		Pane root2 = new Pane();
		Scene scene = new Scene(root, 800, 600);
		Scene scene2 = new Scene(root2, 800, 600);
		root.setStyle("-fx-background-color: #228B22;");
		root2.setStyle("-fx-background-color: #228B22;");

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
		gameName.setFont(Font.font("Arial", FontWeight.BOLD, 60)); // gameName.setStyle("-fx-font: 50 arial;");
		gameName.setLayoutX(175);
		gameName.setLayoutY(100);
		root.getChildren().add(gameName);
		int amount = 0;
		Label pot = new Label("Current Pot: " + this.pot);
		pot.setTextFill(Color.WHITE);
		pot.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		pot.setLayoutX(100);
		pot.setLayoutY(100);
		root2.getChildren().add(pot);
		primaryStage.setScene(scene);
		primaryStage.show();

		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			// @Param event is the user input that occurs, in this case the button click
			// Method deposits the specified amount when the "Deposit" button is clicked
			public void handle(ActionEvent event) {
				// deposit EventHandler
				primaryStage.setScene(scene2);
				primaryStage.show();
				int chips = Integer.parseInt(pickStartChipsField.getText());
				int players = Integer.parseInt(pickPlayerAmount.getText());
				intialize(chips, players);

			}
		});
	}

	public void intialize(int startingChips, int numberOfPlayers) {
			for(int x = 0; x < numberOfPlayers; x++) {
				players.add(new Player(startingChips, x+1));
			}
			Deck deck = new Deck();
			deck.deal(players, tableCards);

			getNotFoldedPlayers();
			
			RankHands rank = new RankHands(); 
			int winner = rank.ranking(tableCards, getNotFoldedPlayers(), notWinners);
			System.out.println("The winner is player " +  (winner+1));
			

	}

	public ArrayList<Player> getNotFoldedPlayers()
	{
		ArrayList<Player> playersNotFolded = new ArrayList<Player>();
		for(int x = 0; x < players.size(); x++)
		{
			if(!players.get(x).getDidFold())
			{
				playersNotFolded.add(players.get(x));
			} else {
				playersNotFolded.add(players.get(x));
				notWinners.add(x);
			}
		}
		return playersNotFolded;
	}
}
