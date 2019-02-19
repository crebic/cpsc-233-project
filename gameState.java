
import java.util.Scanner;
 public class gameState{
	 
//* instance variables

private static int pot;
private int currentPlayer;
private int callAmount;
private int playerTurn = 0;
private int numberOfPlayers = 0;
public List<Card> tableCards = new ArrayList<Card>();
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
	
public void raise(){
	System.out.println("how much would you like to bet?");
			Scanner scanner = new Scanner(System.in);
			int raiseAmount = scanner.nextInt();
	
	if (raiseAmount <= Player.getChips()){
		pot += raiseAmount;
		Player.addChips(-raiseAmount);
		callAmount = raiseAmount; 
	}
}

public static void fold(Player BOB){
	Player.changeFoldedState(true);
}

public static void call(Player BOB){
	if (callAmount <= BOB.getChips()){
		BOB.addChips(-callAmount);
		pot += callAmount;
	}
	
}
	

	
public static void (String[] args){
	gameState gs = new gameState();
	
		//TODO number of chips as an arguement
		numberOfPlayers = 5;
		while (true){
			players = new Player[numberOfPlayers];
		int currentTurn = playerTurn % numberOfPlayers;
		Player currentPlayer = players[currentTurn];
		currentPlayer.addChips(5000);
		//at the end of this main method increment player turn number
		System.out.println("What's your next move?");
		if (callAmount > 0) {
			System.out.println("$" + callAmount + " to call.");
			System.out.println("1: Raise 	2: Fold		3: Call");	
		} else System.out.println("1: Raise 	2: Fold		3: Call		4: Check");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();
		switch(choice){
		case 1:
			gs.raise(currentPlayer);
		case 2:
			gs.fold(currentPlayer);
		case 3:
			gs.call(currentPlayer);}
		playerTurn++;
		//Tester below
		System.out.println(currentPlayer.getChips());
		System.out.println(pot);
		System.out.println(playerTurn);
		//Tester above
		}
	
	}
	
	

}
