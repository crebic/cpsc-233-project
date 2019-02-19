import java.util.*;

import javax.smartcardio.Card;

public class RankHands {
	
	//Players hand and board cards 
	private ArrayList<Card> allCards = new ArrayList<Card>();
	private ArrayList<Integer> allCardNumbers = new ArrayList<Integer>(); 

	public double checkAllRanks() {
		createCompareList();
		double value = 0.0;
		value = checkRoyalFlush();
		if(value > 0.01) {
			return value;
		} else {
			value = checkStraightFlush();
		}
		if(value > 0.01) {
			return value;
		} else {
			value = checkFourOfAKind();
		}
		if(value > 0.01) {
			return value;
		} else {
			value = checkFullHouse();
		}
		if(value > 0.01) {
			return value;
		} else {
			value = checkFlush();
		}
		if(value > 0.01) {
			return value;
		} else {
			value = checkStraight();
		}
		if(value > 0.01) {
			return value;
		} else {
			value = checkThreeOfAKind();
		}
		if(value > 0.01) {
			return value;
		} else {
			value = checkTwoPair();
		}
		if(value > 0.01) {
			return value;
		} else {
			value = checkPair();
		}
		value = checkHighCard();
		return value; 

	}
	
	public double checkBoard() {
		
	}
	
	
	
	//Appends the 2 hand cards and boards card into one list
	public void createCompareList(/* GameState boardCardsArray, Player playerHandArray */) { 
		for (Card eachCard: boardCardsArray) {
			allCards.add(eachCard); 
			allCardNumbers.add(eachCard.getValue())
		}
		for (Card eachCard: playerHandArray ) {
			allCards.add(eachCard); 
			allCardNumbers.add(eachCard.getValue())

		}
		
		Collections.sort(allCardNumbers);		

	}
		
	
	public double checkRoyalFlush() {
		if(allCardNumbers.get(6) != 14) {
			return 0;
		}
		
		boolean ace = false;
		boolean ace2 = false;
		boolean king = false;
		boolean queen = false;
		boolean jack = false;
		boolean ten = false;
	
		String suit = "";
		String suit2="";
		int counter = 0;
		
		for(Card eachCard: allCards) {
			if(eachCard.getValue() == 14 && counter == 0) {
				suit = eachCard.getSuit();
				ace = true; 
				counter +=1;
			}
			if(eachCard.getValue() == 14 && counter ==1 ) {
				suit2 = eachCard.getSuit(); 
				ace2 = true; 
			}
		}
		
		for(Card eachCard: allCards) { 
			if(eachCard.getValue() == 13 && eachCard.getSuit() == suit) {
				king = true;
			}
			if(eachCard.getValue() == 12 && eachCard.getSuit() == suit) {
				queen = true;
			}
			if(eachCard.getValue() == 11 && eachCard.getSuit() == suit) {
				jack = true;
			}
			if(eachCard.getValue() == 10 && eachCard.getSuit() == suit) {
				ten = true;
			}
		}
		if(ace && king && queen && jack && ten) {
			return 10.0;
		}
		if(ace2 == true) {
			king = false;
			queen = false;
			jack = false;
			ten = false;
			for(Card eachCard: allCards) { 
				if(eachCard.getValue() == 13 && eachCard.getSuit() == suit2) {
					king = true;
				}
				if(eachCard.getValue() == 12 && eachCard.getSuit() == suit2) {
					queen = true;
				}
				if(eachCard.getValue() == 11 && eachCard.getSuit() == suit2) {
					jack = true;
				}
				if(eachCard.getValue() == 10 && eachCard.getSuit() == suit2) {
					ten = true;
				}
			}
		}
		if(ace && king && queen && jack && ten) {
			return 10.0;
		}
		else {
			return 0.0;
		}
	}
		
		

	
	public double checkStraightFlush() {
		int card1Num = playerHandArray.getValue(0);
		int card2Num = playerHandArray.getValue(1);
		String card1Suit = playerHandArray.getSuit(0);
		String card2Suit = playerHandArray.getSuit(1);
		int sfCounter = 0;

		//index 0 is spades, index 1 is hearts, index 2 is diamonds, index 3 is clubs
		//the "value" of the cards in the count ArrayList is the number of each suit rather than the value of the card
		ArrayList<Card> count = new ArrayList<Card>();
		count.add(Card(0,"spade"));
		count.add(Card(0,"heart"));
		count.add(Card(0,"diamond"));
		count.add(Card(0,"club"));

		//identifies number of each suit
		for (Card eachCard: allCards){
		    if (eachCard.getSuit().equals("spade")){
			Card tempCard = count.get(0);
			tempCard.value += 1;
			count.set(0, tempCard);
		    }
		    else if (eachCard.getSuit().equals("heart")){
			Card tempCard = count.get(1);
			tempCard.value += 1;
			count.set(1, tempCard);
		    }
		    else if (eachCard.getSuit().equals("diamond")){
			Card tempCard = count.get(2);
			tempCard.value += 1;
			count.set(2, tempCard);
		    }
		    else{
			Card tempCard = count.get(3);
			tempCard.value += 1;
			count.set(3, tempCard);
		    }
		}

		//theSuit is the suit we are using for the straight flush
		for (int i = 0; i <= 3;i++){
		    if(count.get(i).value >= 5){
			String theSuit = count.get(i).value;
		    }
		}

		//creates ArrayList of all cards with the same suit
		//the arraylist will contain the value of each card instead of the card itself
		ArrayList<Integer> sameSuitCard = new ArrayList<Integer>();
		for(int i = 0; i <= 6; i++){
		    if (count.get(i).getSuit().equals(theSuit)){
			sameSuitCard.add(count.get(i).getValue());
		    }
		}

		//sorts arraylist of sameSuitCard from lowerest to highest
		Collection.sort(sameSuitCard);

		//checks if there is a straight
		int straightCount = 0;
		for (int i = 0; i < 5; i++){
		    if (sameSuitCard.get(i) == sameSuitCard.get(i+1)-1){
			straightCount += 1;
		    }
		    else 
			break;
		}

		//returns 9.xx if there is a straight flush and 0.0 if there isn't
		if (straightCount == 5) {
		    return 9.0 + allCardNumbers.get(6)/100;
		}
		else
		    return 0.0;
	}
	
	public double checkFourOfAKind() {
		Card cardOne = playerHandArray.get(0); 
		Card cardTwo = playerHandArray.get(1);
		double playerCardOne = cardOne.getValue();
		double playerCardTwo = cardTwo.getValue();
		

		int counter1 = 1;
		int counter2 = 1;
		
		if(playerCardOne == playerCardTwo) {
			counter1 = 2;
		}
		
		for (Card eachCard: boardCardsArray) {
			if(eachCard.getValue() == playerCardOne) {
				counter1 += 1; 
			} 
			if(eachCard.getValue() == playerCardTwo) {
				counter2 += 1;
			}
				
		}
		if(counter1 == 4) {
			return 8.0 + (playerCardOne/ 100.0); 
		}
		else if (counter2 == 4) {
			return 8.0 + (playerCardTwo / 100.0);
		}
		else return 0;

	}
	
	public double checkFullHouse() {
		int threeCounter = 0; 
		double cardValue = 0 ;
		
		for(int i = 0; i < 4; i++) {
	
			for(int j = 1+i; j < 7 ;j++) {
				if(allCards.get(i) == allCards.get(j)) {
					threeCounter += 1;
					cardValue = allCards.get(i); 
				}
			}
			
			if(pairCounter != 3) {
				return 0;
			}
		}
		 
		
		int pairCounter = 0; 
		double cardValue2 = 0 ;
		
		for(int i = 0; i < 6; i++) {
			
			for(int j = 1+i; j < 7 ;j++) {
				if((allCards.get(i) == allCards.get(j)) && (allCards.get(i) != cardValue)){
					pairCounter2 += 1;
					cardValue2 = allCards.get(i); 
				}
			}
			
			if(pairCounter2 == 2) {
				if (cardValue > cardValue2) return 3 + cardValue/100;
				else return 3 + cardValue2/100;
			}
		
		
				
		}
		return 0; 
	}
	
	public void checkFlush() {
		int flushCount = 0;
		String card1 = playerHandArray.getSuit(0);
		String card2 = playerHandArray.getSuit(1);

		//checks if board has a flush
		int boardFlushCount = 0;
		for (int i = 0; i < 5; i++){
		    if (boardCardArray.get(i).getValue() == boardCardArray.get(i+1).getValue()-1){
			boardFlushCount += 1;
		    }
		}


		if (boardFlushCount == 5){
		    return 6.0 + allCardNumbers.get(6)/100;
		}

		//checks if cards in hands are the same
		else if (card2 == card1){
		    flushCount += 1;
		    for (Card eachCard: boardCardArray){
			if (eachCard.getSuit() == card1){
			    flushCount += 1;
			}
		    }
		    if (flushCount == 5){
			return 6.0 + allCardNumbers.get(6)/100;
		    }
		    else{
			return 0.0;
		    }
		}
		else{
		    int flushCount2 = 0;
		    for (Card eachCard: boardCardArray){
			if (eachCard.getSuit() == card1){
			    flushCount += 1;
			}
			if (eachCard.getSuit() == card2){
			    flushCount2 += 0;
			}
		    }
		    if (flushCount == 5){
			return 6.0 + allCardNumbers.get(6)/100;
		    }
		    else if (flushCount2 == 5){
			return 6.0 + allCardNumbers.get(6)/100;
		    }
		    else{
			return 0.0;
		    }
		}
	}
	
	public double checkStraight() {
		Card cardOne = playerHandArray.get(0); 
		Card cardTwo = playerHandArray.get(1);
		double playerCardOne = cardOne.getValue();
		double playerCardTwo = cardTwo.getValue();
		
		int counter1 = 1;
		int counter2 = 1;
		int counter3 = 1;
		int highest = 0; 
		int nextCard = 0;
		
		//Cards 3-7
		for(int i = 6; i >=2; i--) {
			if(i == 6) {
				highest = allCardNumbers.get(6);
				nextCard = allCardNumbers.get(6);
			}
			if(allCardNumbers.get(i) == nextCard-1) {
				counter1 +=1;
				nextCard -=1; 
			}
			else {
				break;
			}
		}
		if(counter1 == 5) {
			return 5 + (highest/100.0);
		}
		//Cards 2-6
		for(int i = 5; i >=1; i--) {
			if(i == 5) {
				highest = allCardNumbers.get(5);
				nextCard = allCardNumbers.get(5);
			}
			if(allCardNumbers.get(i) == nextCard-1) {
				counter2 +=1;
				nextCard -=1; 
			} else {
				break
			}
			
		}
		if(counter2 == 5) {
			return 5 + (highest/100.0);
		}

		//Cards 1-5
		for(int i = 4; i >=0; i--) {
			if(i == 4) {
				highest = allCardNumbers.get(4);
				nextCard = allCardNumbers.get(4);
			}
			if(allCardNumbers.get(i) == nextCard-1) {
				counter3 +=1;
				nextCard -=1; 
			} else {
				break;
			}
		}
		if(counter1 == 5) {
			return 5 + (highest/100.0);
		} else {
			return 0.0;
		}
		
		
		
	}
	
	public double checkThreeOfAKind() {
		Card cardOne = playerHandArray.get(0); 
		Card cardTwo = playerHandArray.get(1);
		double playerCardOne = cardOne.getValue();
		double playerCardTwo = cardTwo.getValue();
		int counter1 = 1;
		int counter2 = 1; 
		int counter3 = 2;
		if(cardOne == cardTwo) {
			for(Card eachCard: boardCardsArray) {
				if(eachCard.getValue() == cardOne) {
					counter3 +=1;
				}
			}
				
		}

		else {
			
			for (Card eachCard: boardCardsArray) {
				if(eachCard.getValue() == playerCardOne) {
					counter1 += 1; 
				} 
				if(eachCard.getValue() == playerCardTwo) {
					counter2 += 1;
				}
					
			}
			
		}
		if(counter3 == 3) {
			return 4.0 + (playerCardOne/ 100.0); 
		}
		else if (counter1 == 3) {
			return 4.0 + (playerCardOne / 100.0);
		} else if(counter2 == 3 ) {
			return 4.0 + (playerCardTwo / 100.0);
		}
		else return 0;

		
	}
	
	public double checkTwoPair() {
		int pairCounter = 0; 
		double cardValue = 0 ;
		
		Card cardOne = playerHandArray.get(0); 
		Card cardTwo = playerHandArray.get(1);
		double playerCardOne = cardOne.getValue();
		double playerCardTwo = cardTwo.getValue();
		
		if(playerCardOne == playerCardTwo) {
			return 2.0 + (playerCardOne / 100.0);
		}
		int counter1 = 1;
		int counter2 = 1; 
		
		
		for (Card eachCard: boardCardsArray) {
			if(eachCard.getValue() == playerCardOne) {
				counter1 += 1; 
			} 
			if(eachCard.getValue() == playerCardTwo) {
				counter2 += 1;
			}
				
		}
		double highest = 0; 
		if(playerCardOne > playerCardTwo) {
			highest = playerCardOne; 
		}
		else {
			highest = playerCardTwo;
		}
		if(counter1 == 2 && counter2 == 2) {
			
			return 3.0 + (highest/ 100.0); 
		}
		else return 0;
		
	}

	
	public double checkPair() {
		Card cardOne = playerHandArray.get(0); 
		Card cardTwo = playerHandArray.get(1);
		double playerCardOne = cardOne.getValue();
		double playerCardTwo = cardTwo.getValue();
		
		if(playerCardOne == playerCardTwo) {
			return 2.0 + (playerCardOne / 100.0);
		}
		
		int counter1 = 1;
		int counter2 = 1;
		
		
		for (Card eachCard: boardCardsArray) {
			if(eachCard.getValue() == playerCardOne) {
				counter1 += 1; 
			} 
			if(eachCard.getValue() == playerCardTwo) {
				counter2 += 1;
			}
				
		}
		if(counter1 == 2) {
			return 2.0 + (playerCardOne/ 100.0); 
		}
		else if (counter2 == 2) {
			return 2.0 + (playerCardTwo / 100.0);
		}
		else return 0;
		
	}
		
	public double checkHighCard() {
		double highestValue = 0;
		for(Card eachCard: allCards) {
			if(eachCard.getValue() > highestValue) {
				highestValue = eachCard.getValue();
			}
			
		}
		return 1 + (highestValue/100); 
	} 

	
}
