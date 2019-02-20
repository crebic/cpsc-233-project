import java.util.ArrayList;
import java.util.Scanner;
 public class gameState{
	 
//* instance variables

private int pot = 0;
private int currentPlayer;
private int callAmount;
private int playerTurn = 0;
private int numberOfPlayers = 0;
public ArrayList<Card> tableCards = new ArrayList<Card>();
private Player[] players;


gameState()
{
}


gameState(int newPot, int newCurrentPlayer){
	this.pot = newPot;
	this.currentPlayer = newCurrentPlayer;
	
	}

public int getPot(){
	return pot;
	}
	
	
//* for player turn use modulus of number of players to determine current players turn.
//* method for player turn


/*
public int getCurrentPlayer(){
	return new currentPlayer;
	}
*/
	
public void raise(Player currentPlayer){
	System.out.println("how much would you like to bet?");
			Scanner scanner = new Scanner(System.in);
			int raiseAmount = scanner.nextInt();
		
	if (raiseAmount <= currentPlayer.getChips()){
				callAmount = raiseAmount; 

		currentPlayer.addChips(-raiseAmount);
		pot += raiseAmount;
	}
}

public void fold(Player BOB){
	Player newPlayer = new Player();
	newPlayer.changeFoldedState(true);
}

public void call(Player BOB){
	if (callAmount <= BOB.getChips()){
		BOB.addChips(-callAmount);
		pot += callAmount;
	}
	
}
	

/*	
public static void main (String[] args){
	gameState gs = new gameState();
	*/
	
	public void populateTable(int startingChips)
	{
			//add four bots for now, human player is first seat
			for(int x = 0; x < 5; x++)
			{
				players[x] = new Player(startingChips, x+1);
			}
	}
	
	public void flop(){
		for(int x = 0; x < 3; x++)
		{
			System.out.print(tableCards.get(x).getValue() + tableCards.get(x).getSuit().substring(0,1).toUpperCase() + " ");
		}
	}
	public void turn(){
		
	}
	public void river(){
		
	}
	
	public void initialize(){
		//TODO number of chips as an arguement
		numberOfPlayers = 5;
					players = new Player[numberOfPlayers];
			System.out.println("How many chips would you like to start with?");
			Scanner scanner = new Scanner(System.in);
			int startingChips = scanner.nextInt();
			populateTable(startingChips);
			System.out.println("\nYOU ARE PLAYER 1");
			Deck deck = new Deck();
			deck.deal(players, tableCards);
			System.out.println("THE FLOP IS:\n");
			flop();
		while (true){

			int currentTurn = playerTurn % numberOfPlayers;
			Player currentPlayer = new Player(players[currentTurn]);
					int seatNumber = currentPlayer.getSeatNumber();
		System.out.println("\nPlayer " + seatNumber + "'s turn.");
			//at the end of this main method increment player turn number
			System.out.println("What's your next move?");
			if (callAmount > 0) {
				System.out.println("$" + callAmount + " to call.");
				System.out.println("1: Raise 	2: Fold		3: Call");	
			} else System.out.println("1: Raise 	2: Fold		3: Call		4: Check");
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
				}
			playerTurn++;
			//Tester below
			System.out.println("\nCurrent players chips: " + currentPlayer.getChips());
			System.out.println("Pot amount: " + pot);
			System.out.println("Turn/Seat number: " + playerTurn);
			System.out.println("Call amount: " + callAmount);
			//Tester above
		}
	
	}
	
	 

	
	



}