import java.util.ArrayList;
import javax.swing.JOptionPane;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;

//TODO gamestate methods added, event handlers that tie all buttons to gamestate methods, adapt bet to GUI

public class UI extends Application {
	private Deck deck = new Deck();//
	private int pot = 0;
	private int currentPlayer;
	private int callAmount;
	private int playerTurn = 0;
	private int numberOfPlayers = 0;
	public ArrayList<Card> tableCards = new ArrayList<Card>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player playerWithHighestCallAmount;
	private ArrayList<Integer> notWinners = new ArrayList<Integer>();
	VBox root2 = new VBox();
	HBox tableCardsBox = new HBox();
	HBox buttonOptions = new HBox();
	VBox playerStats = new VBox();
	HBox playerCardsBox = new HBox();
	int round = 0;

	public static void main(String[] args) {
		
		launch(args);
	}

	public void start(Stage primaryStage) throws Exception{
		primaryStage.setTitle("Texas Hold'em");

		// root and scene are for main menu, root2 and scence2 are for the game state
		Pane root = new Pane();
		Scene scene = new Scene(root, 800, 600);
		Scene scene2 = new Scene(root2, 800, 600);


		root2.setSpacing(30);
		root2.setAlignment(Pos.CENTER);
		tableCardsBox.setAlignment(Pos.CENTER);

		root.setStyle("-fx-background-color: #228B22;");
		root2.setStyle("-fx-background-color: #228B22;");

		//setting up button options for betting
		Button raise = new Button();
		raise.setText("Raise");
		Button call = new Button();
		call.setText("Call");
		Button check = new Button();
		check.setText("Check");
		Button fold = new Button();
		fold.setText("Fold");

		buttonOptions.getChildren().addAll(raise,call,check,fold);

		buttonOptions.setSpacing(20);
		buttonOptions.setAlignment(Pos.CENTER);

		//raise button just acting as a next round button
		raise.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//not doing anything with raise amount as of now
				String raiseAmount = JOptionPane.showInputDialog("How much would you like to raise?");
				if(round == 0)
				{
					//round++;
					betRound();
				}
				else if(round == 1)
				{
					//round++;
					betRound();
				}
				else if(round == 2)
				{
					//round++;
					betRound();
				}				
			}
		});
		//

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
		root2.getChildren().add(pot);

		primaryStage.setScene(scene);
		primaryStage.show();

		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			// @Param event is the user input that occurs, in this case the button click
			// Method deposits the specified amount when the "Deposit" button is clicked
			public void handle(ActionEvent event) {
				// deposit EventHandler

				int chips = Integer.parseInt(pickStartChipsField.getText());
				int players = Integer.parseInt(pickPlayerAmount.getText());

				initialize(chips,players);
				
				root2.getChildren().add(tableCardsBox);
				
				root2.getChildren().add(buttonOptions);

				//setting up player stats
				Label playerStack = new Label("Your stack: " + chips);
				playerStack.setTextFill(Color.WHITE);
				playerStack.setFont(Font.font("Arial", FontWeight.BOLD, 20));
				playerStats.getChildren().add(playerStack);
				showPlayerCards();
				//
				root2.getChildren().add(playerStats);
				primaryStage.setScene(scene2);
				primaryStage.show();
				root.getChildren().clear();

			}
		});
		
		//new shit below
		while (getPlayersWithChips() > 1){
			deck.resetDeck();
			deck.deal();
			betRound();
			callAmount = 0;
			betRound();
			showTableCards(3);

			callAmount = 0;
			betRound();
			showTableCards(4);

			callAmount = 0;
			//River:
			betRound();
			showTableCards(5);

			RankHands rank = new RankHands();
			notWinners = getNotFoldedPlayers();
			int winner = rank.ranking(tableCards, getNotFoldedPlayers(), notWinners);
			players.get(winner).addChips(pot);
			pot = 0;
			callAmount = 0;
			notWinners.clear();
			//getPlayersWithChips();
			
			
			
			//Tester above
		}
	}

	//I know this is basically copy and pasted, sue me im tired
	void showPlayerCards()
	{
		Card[] playerCards = players.get(0).getPair();

		for(int x = 0; x < 2; x++)
		{
			//load players card images
			String card = "";
			int cardValue = playerCards[x].getValue();

			//replace number with J/Q/K
			if(cardValue == 11)
			{
				card = "J" + playerCards[x].getSuit().substring(0,1).toUpperCase();
			}
			else if(cardValue == 12)
			{
				card = "Q" + playerCards[x].getSuit().substring(0,1).toUpperCase();
			}
			else if(cardValue == 13)
			{
				card = "K" + playerCards[x].getSuit().substring(0,1).toUpperCase();
			}
			else if(cardValue == 14)
			{
				card = "A" + playerCards[x].getSuit().substring(0,1).toUpperCase();
			}
			else
			{
				card = playerCards[x].getValue() + playerCards[x].getSuit().substring(0,1).toUpperCase();
			}
			//add images
			Image image = new Image(card + ".png");
			ImageView imgView = new ImageView();
			imgView.setImage(image);
	
			imgView.setPreserveRatio(true);
			imgView.setFitHeight(150);
			imgView.setFitWidth(150);

			playerCardsBox.getChildren().add(imgView);
		}
		playerStats.getChildren().add(playerCardsBox);
	}

	void showTableCards(int amountOfCards)
	{
		tableCardsBox.getChildren().clear();
		for(int x = 0; x < amountOfCards; x++)
		{
			//load flop images
			String card = "";
			int cardValue = tableCards.get(x).getValue();

			//replace number with J/Q/K
			if(cardValue == 11)
			{
				card = "J" + tableCards.get(x).getSuit().substring(0,1).toUpperCase();
			}
			else if(cardValue == 12)
			{
				card = "Q" + tableCards.get(x).getSuit().substring(0,1).toUpperCase();
			}
			else if(cardValue == 13)
			{
				card = "K" + tableCards.get(x).getSuit().substring(0,1).toUpperCase();
			}
			else if(cardValue == 14)
			{
				card = "A" + tableCards.get(x).getSuit().substring(0,1).toUpperCase();
			}
			else
			{
				card = tableCards.get(x).getValue() + tableCards.get(x).getSuit().substring(0,1).toUpperCase();
			}
			//add images
			Image image = new Image(card + ".png");
			ImageView imgView = new ImageView();
			imgView.setImage(image);
	
			imgView.setPreserveRatio(true);
			imgView.setFitHeight(200);
			imgView.setFitWidth(200);

			tableCardsBox.getChildren().add(imgView);
		}
	}
	public void initialize(int startingChips, int numberOfPlayers) {
			for(int x = 0; x < numberOfPlayers; x++) {
				players.add(new Player(startingChips, x+1));
			}
			//Deck deck = new Deck();
			Player[] playersArray = new Player[players.size()];
			for(int x = 0; x < players.size(); x++)
			{
				playersArray[x] = players.get(x);
			}
			//deck.deal(playersArray, tableCards);

			//getNotFoldedPlayers();
			
			//RankHands rank = new RankHands(); 
			//int winner = rank.ranking(tableCards, getNotFoldedPlayers(), notWinners);
			//System.out.println("The winner is player " +  (winner+1));
			

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

	
	//start of copied gamestate methods
	
	public void river(){
		betRound();
		RankHands rank = new RankHands(); 
		int winner = rank.ranking(tableCards, getNotFoldedPlayers(), notWinners);
		//System.out.println("The winner is player " +  (winner+1));
		//Winner is printed as the correct number
		players.get(winner).addChips(pot);
		pot = 0;
		
	}
	
	public void betRound(){
		boolean foo = false;
		while(!foo){
			
			Scanner scanner = new Scanner(System.in);
			int currentTurn = playerTurn % numberOfPlayers;
			Player currentPlayer = players[currentTurn];
			int playersLeft = 5;
			
			for(int x = 0; x < 5; x++)
			{
				if(players[x].getDidFold())
					playersLeft--;
			}
			if(playersLeft == 1)
				foo = true;
		
			if(currentPlayer.getDidFold())
			{
				playerTurn++;
				continue;
			}
			
			if(currentPlayer.getDidFold() == false)
			{
				int seatNumber = currentPlayer.getSeatNumber();
				
				System.out.println("\nPlayer " + seatNumber + "'s turn.");
				//at the end of this main method increment player turn number
				
				System.out.println("Your hand: ");
				System.out.println(currentPlayer.getPairAsString());
				
				System.out.println("What's your next move?");
				
				
				if (callAmount > 0) {
					System.out.println("$" + callAmount + " to call.");
					System.out.println("1: Raise 	2: Fold		3: Call 	5: End Round");	
				} else System.out.println("1: Raise 	2: Fold		3: Call		4: Check	5: End Round");
				int choice = scanner.nextInt();

				switch(choice){
				case 1:
					raise(currentPlayer);
					break;
				case 2:
					fold(currentPlayer);
					break;
				case 3:
					call(currentPlayer);
					break;
				case 5:
					return;
				
				}
				
				playerTurn++;
				//Tester below
				System.out.println(currentPlayer.getDidFold());
				System.out.println("\nCurrent players chips: " + currentPlayer.getChips());
				System.out.println("Pot amount: " + pot);
				System.out.println("Turn/Seat number: " + playerTurn);
				System.out.println("Call amount: " + callAmount);
				//Tester above
			}
		
		}
	}
	
	
}
