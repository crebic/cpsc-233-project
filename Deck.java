import Card;//fix

import java.util.*;

public class Deck{

	public ArrayList<Card> deck = new ArrayList<>(); //TODO should this be private???
	
	public Deck(){
		//constructor
		String suit;
		
		for (int i = 0; i < 4; i++){
			
			switch(i){
				
				case(0):
					suit = "spade";
				case(1):
					suit = "heart";
				case(2):
					suit = "club";
				case(3):
					suit = "diamond";
			}
			
			for (int j = 1; j <= 13; j++){
				deck.add(new Card(suit, j));
			}
		}
	}

	public void deal(Player[] lst, ArrayList<Card> tableCards){
		for (Player p: lst){
			p.setPair(deck.remove(random.randRange(0, deck.length - 1)), deck.remove(random.randRange(0, deck.length - 1)));
		}
		for (int i = 0; i < 5; i++){
			tableCards[i] = deck.remove(random.randRange(0, deck.length - 1));
		}
	}

	public void resetDeck(){
		
		//resets the deck to it's original form after dealing hands
		
		deck.clear();
		
		String suit;
		
		for (int i = 0; i < 4; i++){
			
			switch(i){
				
				case(0):
					suit = "spade";
				case(1):
					suit = "heart";
				case(2):
					suit = "club";
				case(3):
					suit = "diamond";
			}
			
			for (int j = 1; j <= 13; j++){
				deck.add(new Card(suit, j));
			}
		}
	}







}