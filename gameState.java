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
private Player playerWithHighestCallAmount;
private ArrayList<Integer> notWinners = new ArrayList<Integer>();

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
				callAmount += raiseAmount; 
				
		currentPlayer.addChips(-raiseAmount);
		pot += raiseAmount;
		playerWithHighestCallAmount = currentPlayer;
	}
}

public void fold(Player BOB){
	BOB.changeFoldedState(true);
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
					System.out.println("THE FLOP IS:\n");

		for(int x = 0; x < 3; x++)
		{
			System.out.print(tableCards.get(x).getValue() + tableCards.get(x).getSuit().substring(0,1).toUpperCase() + " ");
		}
		betRound();
	}
	public void turn(){
		for(int x = 0; x < 4; x++)
		{
			System.out.print(tableCards.get(x).getValue() + tableCards.get(x).getSuit().substring(0,1).toUpperCase() + " ");
		}
		betRound();
		
		
	}
	public void river(){
		for(int x = 0; x < 5; x++)
		{
			System.out.print(tableCards.get(x).getValue() + tableCards.get(x).getSuit().substring(0,1).toUpperCase() + " ");
		}
		betRound();
		RankHands rank = new RankHands(); 
		int winner = rank.ranking(tableCards, getNotFoldedPlayers(), notWinners);
		System.out.println("The winner is player " +  (winner+1));
		//Winner is printed as the correct number
		players[winner].addChips(pot);
		pot = 0;
		
	}
	
	
	public int getPlayersWithChips(){
		int PlayersStillPlaying = 0;
		for (int x = 0; x < 5; x++){
			if (players[x].getChips() > 0)
				PlayersStillPlaying += 1;
		}
		return PlayersStillPlaying;
	}
	
	
	public ArrayList<Player> getNotFoldedPlayers()
	{
		ArrayList<Player> playersNotFolded = new ArrayList<Player>();
		for(int x = 0; x < 5; x++)
		{
			if(!players[x].getDidFold())
			{
				playersNotFolded.add(players[x]);
			} else {
				playersNotFolded.add(players[x]);
				notWinners.add(x);
			}
		}
		return playersNotFolded;
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
	
	public void preflop()
	{
		betRound();
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
			
			//put into flop ?????
			
			
		while (getPlayersWithChips() > 1){
			
			
			preflop();
			callAmount = 0;
			flop();
			callAmount = 0;
			turn();
			callAmount = 0;
			river();
			callAmount = 0;
			getPlayersWithChips();
			
			
			
			//Tester above
		}
	
	}
	
	// CHANGES : reset call amounts in last while loop lines 236-
	// getPlayersWithChips method 
	// reset pot after its won in river method
	// adding pot to the winning players stack line 112 in river method
	
	 

	
	

}

