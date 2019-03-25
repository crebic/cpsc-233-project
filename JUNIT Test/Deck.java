import java.util.Random;
import java.util.*;

public class Deck{

	private ArrayList<Card> deck = new ArrayList<>(); 
	
	
	//@ default constructor uses a for loop to generate a deck of cards 
	
	public Deck(){
		
		//constructor
		String suit;
		
		for (int i = 0; i < 4; i++){
			
			switch(i){
				
				case(0):
					suit = "spade";
					break;
				case(1):
					suit = "heart";
					break;
				case(2):
					suit = "club";
					break;
				case(3):
					suit = "diamond";
					break;
				default:
					suit = "uiu";
			}
			
			//card with value 14 is the Ace card
			for (int j = 2; j <= 14; j++){
				deck.add(new Card(j, suit));
			}
		}
	}


	//* method deals cards to players by removing the card from the deck and associating it with Player p
	//* @param Player is an array of our players
	//* @param an arraylist of our table cards
	public void deal(Player[] lst, ArrayList<Card> tableCards){
		Random rand = new Random();
		for (Player p: lst){
			p.setPair(deck.remove(rand.nextInt(deck.size() - 1)), deck.remove(rand.nextInt(deck.size() - 1)));
		}
		for (int i = 0; i < 5; i++){
			tableCards.add(i, deck.remove(rand.nextInt(deck.size() - 1)));
		}
	}


	//* method that clears and remarks the deck array
	public void resetDeck(){
		
		//resets the deck to it's original form after dealing hands
		
		deck.clear();
		
		String suit;
		
		for (int i = 0; i < 4; i++){
			
			switch(i){
				
				case(0):
					suit = "spade";
					break;
				case(1):
					suit = "heart";
					break;
				case(2):
					suit = "club";
					break;
				case(3):
					suit = "diamond";
					break;
				default:
					suit = "";
			}
			
			for (int j = 2; j <= 14; j++){
				deck.add(new Card(j, suit));
			}
		}
	}







}