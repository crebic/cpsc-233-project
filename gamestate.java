import util.*;
 public class gameState{
//* instance variables
private int pot;

private int currentPlayer;

private playerTurn = 0;
private numberOfPlayers = 0;
public List<Card> tableCards = new ArrayList[];
private Player[] players;

gameState(int _pot, int _currentPlayer){
	this.pot = _pot;
	this.currentPlayer = _currentPlayer;
	
	}

public int getPot(){
	return pot;
	}
	
	
//* for player turn use modulus of number of players to determine current players turn.
//* method for player turn
public void turn()


public int getCurrentPlayer(){
	return currentPlayer;
	}
public void raise(int Raise, Player BOB){}

public void fold(){}

public void call(int Raise, Player BOB){}

public void check(int Raise, Player BOB) {}

	

	

public void setPot()
	

	
public static void main(String[] args){
		numberOfPlayers = 5;
			players = new Player[numberOfPlayers];
		int currentTurn = playerTurn % numberOfPlayers;
		Player currentPlayer = players[currentTurn];
		//at the end of this main method increment player turn number
	
	}
	
	



}