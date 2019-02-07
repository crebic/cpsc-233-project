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
	public void checkSuit() 
	{
		
		for(Card eachCard: board) {
			
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
