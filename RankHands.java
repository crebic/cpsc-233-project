import java.util.ArrayList;

import javax.smartcardio.Card;

public class RankHands {
	//Players hand and board cards 
	private ArrayList<Card> allCards = new ArrayList<Card>();

	//Appends the 2 hand cards and boards card into one list
	public void createCompareList(/* boardCardsArray, playerHandArray */) 
	{ 
		for (Card eachCard: boardCardsArray) {
			allCards.add(eachCard); 
		}
		for (Card cards: playerHandArray ) {
			allCards.add(eachCard); 
		}
		
		
	
	}
	//Checks for Flush, Straight Flush, and Royal Flush
	public void checkFlushes() 
	{
		int spadeCounter = 0; 
		for(Card eachCard: board) {
			if(eachCard.getSuit().equals("spade")) {
				spadeCounter += 1; 
			}
			
		}
		
		int heartCounter = 0; 
		for(Card eachCard: board) {
			if(eachCard.getSuit().equals("heart")) {
				heartCounter += 1; 
			}
			
		}
		
		int clubCounter = 0; 
		for(Card eachCard: board) {
			if(eachCard.getSuit().equals("club")) {
				clubCounter += 1; 
			}
			
		}
		
		int diamondCounter = 0; 
		for(Card eachCard: board) {
			if(eachCard.getSuit().equals("diamond")) {
				diamondCounter += 1; 
			}
			
		}
		
	
	}
	public void checkRoyalFlush() 
	{
		
	}
	
	public void checkStraightFlush() 
	{
		
	}
	
	public void checkFourOfAKind()
	{
		
	}
	
	public void checkFullHouse() 
	{
		
	}
	
	public void checkFlush()
	{
		
	}
	
	public void checkStraight()
	{
		
	}
	
	public void checkThreeOfAKind()
	{
		
	}
	
	public void checkTwoPair() 
	{
		
	}
	
	public void checkPair() {
	}
	
	public void checkHighCard()
	{
		
	}

}
