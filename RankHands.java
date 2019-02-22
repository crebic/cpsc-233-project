import java.util.*;


public class RankHands {
	
	//Players hand and board cards 
	private ArrayList<Card> allCards = new ArrayList<Card>();
	private ArrayList<Integer> allCardNumbers = new ArrayList<Integer>(); 
	private ArrayList<Card> playerHandArray = new ArrayList<Card>();
	private ArrayList<Card> boardCardArray = new ArrayList<Card>();

	
	
	public double checkAllRanks(ArrayList<Card> boardCards, ArrayList<Card> playerCards) {
		createCompareList(boardCards, playerCards);
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
	
	
	
	
	//Appends the 2 hand cards and boards card into one list
	public void createCompareList(ArrayList<Card> boardCardsArray, ArrayList<Card> playerHandArray) { 
		
		for (Card eachCard: boardCardArray) {
			allCards.add(eachCard); 
			allCardNumbers.add(eachCard.getValue());
		}
		for (Card eachCard: playerHandArray ) {
			allCards.add(eachCard); 
			allCardNumbers.add(eachCard.getValue());

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

		double playerCardOne = playerHandArray.get(0).getValue();
		double playerCardTwo = playerHandArray.get(1).getValue();
		

		
		//Check if board has four of a kind
		ArrayList<Integer> testBoard = new ArrayList<Integer>();
		for(Card eachCard: boardCardArray) {
			testBoard.add(eachCard.getValue());
		}
		Collections.sort(testBoard);
		
		int boardCardOne = testBoard.get(0);
		int boardCardTwo = testBoard.get(4);
		int boardCounter1 = 0;
		int boardCounter2 = 0;
		
		for (int eachCardValue: testBoard) {
			if(boardCardOne == eachCardValue) {
				boardCounter1 += 1;
			}
			if(boardCardTwo == eachCardValue) {
				boardCounter2 += 1;
			}
		}
		
		if(boardCounter1 == 4 || boardCounter2 == 4) {
			return 8.0;
		}
		//End of board check

		int counter1 = 1;
		int counter2 = 1;
		
		if(playerCardOne == playerCardTwo) {
			counter1 = 2;
		}
		
		for (Card eachCard: boardCardArray) {
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
		double playerCardOne = playerHandArray.get(0).getValue();
		double playerCardTwo = playerHandArray.get(1).getValue();
		
		int counter1 = 1;
		int counter2 = 1;
		int boardCounter1 = 0;
		int boardCounter2 = 0;
		
		//Test if board has a fullhouse 
		ArrayList<Integer> testBoard = new ArrayList<Integer>();
		for(Card eachCard: boardCardArray) {
			testBoard.add(eachCard.getValue());
		}
		Collections.sort(testBoard);
		int boardCard1 = testBoard.get(0);
		int boardCard2 = testBoard.get(4);
		
		for(int eachValue: testBoard) {
			if(boardCard1 == eachValue) {
				boardCounter1 += 1;
			}
			if(boardCard2 == eachValue) {
				boardCounter2 += 1;
			}
		}
		
		if( (boardCounter1 == 3 && boardCounter2 == 2) || (boardCounter2 == 3 && boardCounter1 == 2)) {
			return 7.0;
		} 
		//End of board test
		
		
		for(Card eachCard: boardCardArray) {
			if(playerCardOne == eachCard.getValue()) {
				counter1 += 1;
			}
			if(playerCardTwo == eachCard.getValue()) {
				counter2 += 1;
			}
		}
		if(playerCardOne == playerCardTwo) {
			counter1 += 1;
		}
				
		if(counter1 == 3 && counter2 == 3) {
			if(playerCardOne > playerCardTwo) {
				return 7.0 + playerCardOne / 100.0;
			} else {
				return 7.0 + playerCardTwo / 100.0;
			}
		} else if (counter1 == 3 && boardCounter1 ==3) {
			if(playerCardOne > boardCard1) {
				return 7.0 + playerCardOne / 100.0;
			} else {
				return 7.0 + boardCard1 / 100.0;
			}
		} else if (counter1 == 3 && boardCounter2 ==3) {
			if(playerCardOne > boardCard2) {
				return 7.0 + playerCardOne / 100.0;
			} else {
				return 7.0 + boardCard2 / 100.0;
			}
		} else if (counter2 == 3 && boardCounter1 ==3) {
			if(playerCardTwo > boardCard1) {
				return 7.0 + playerCardTwo / 100.0;
			} else {
				return 7.0 + boardCard1 / 100.0;
			}
		} else if (counter2 == 3 && boardCounter2 ==3) {
			if(playerCardTwo > boardCard2) {
				return 7.0 + playerCardTwo / 100.0;
			} else {
				return 7.0 + boardCard2 / 100.0;
			}
		}
		else if (counter1 == 3 && counter2 == 2) {
			return 7.0 + playerCardOne / 100.0;
		} else if (counter2 == 3 && counter1 == 2) {
			return 7.0 + playerCardTwo / 100.0;
		}
		//Full house with board triple/double and hand triple/double
		else if(counter1 == 3 && boardCounter1 == 2) {
			return 7.0 + playerCardOne / 100.0;
			
		} else if(counter1 == 2 && boardCounter1 == 3) {
			return 7.0 + boardCard1 / 100.0;

		} else if(counter1 == 3 && boardCounter2 == 2) {
			return 7.0 + playerCardOne / 100.0;

		} else if(counter1 == 2 && boardCounter2 == 3) {
			return 7.0 + boardCard2 / 100.0;

		} else if(counter2 == 3 && boardCounter1 == 2) {
			return 7.0 + playerCardTwo / 100.0;

		} else if(counter2 == 2 && boardCounter1 == 3) {
			return 7.0 + boardCard1 / 100.0;

		} else if(counter2 == 3 && boardCounter2 == 2) {
			return 7.0 + playerCardTwo / 100.0;

		} else if(counter2 == 2 && boardCounter2 == 3) {
			return 7.0 + boardCard2 / 100.0;

		} else {
			return 0.0;
		}
		

		
	}
	
	public double checkFlush() {
		String playerCardSuit1 = playerHandArray.get(0).getSuit();
		String playerCardSuit2 = playerHandArray.get(1).getSuit();

		//checks if board has a flush
		int boardFlushCounter = 0;
		String testSuit = boardCardArray.get(0).getSuit();
		for(Card eachCard: boardCardArray) {
			if(testSuit == eachCard.getSuit()) {
				boardFlushCounter += 1;
			}
		}


		if (boardFlushCounter == 5){
		    return 6.0 + allCardNumbers.get(6)/100.0;
		}

		int flushCounter = 0;
		//checks if cards in hands are the same
		
		ArrayList<Integer> flush = new ArrayList<Integer>();
		ArrayList<Integer> flush2 = new ArrayList<Integer>();

		//If hand cards are same suit 
		if(playerCardSuit1 == playerCardSuit2) {
			flush.add(playerHandArray.get(0).getValue());
			flush.add(playerHandArray.get(1).getValue());
			
			//Adds any card with same suits to an arrayList
			for (Card eachCard: boardCardArray) {
				if(playerCardSuit1 == eachCard.getSuit()) {
					flush.add(eachCard.getValue());
				}
				
			}
			//Sort by smallest to largest numbers
			Collections.sort(flush);
			//If array is 5 or greater there is a flush 
			if(flush.size() >=5) {
				return 6.0 + (flush.get(flush.size()-1) /100.0);
			} else {
				return 0.0;
			}
			
			
		}
		flush.add(playerHandArray.get(0).getValue());
		flush2.add(playerHandArray.get(1).getValue());
		
		for(Card eachCard: boardCardArray) {
			if(eachCard.getSuit() == playerCardSuit1) {
				flush.add(eachCard.getValue());
			} else if(eachCard.getSuit() == playerCardSuit2) {
				flush2.add(eachCard.getValue());
			}
		}
		//Sort flush arrayList by smallest to largest
		Collections.sort(flush);
		Collections.sort(flush2);

		
		//If flush array is at least 5 there is a flush
		if(flush.size() >=5) {
			return 6.0 + (flush.get(flush.size()-1) /100.0);
		} else if (flush2.size() >=5) {
			return 6.0 + (flush2.get(flush2.size()-1) /100.0);
		} else {
			return 0.0;
		}
		
	
		
	}
	
	public double checkStraight() {
		//Check board for straight
		ArrayList<Integer> checkBoard = new ArrayList<Integer>();

		for(Card eachCard: boardCardArray) {
			checkBoard.add(eachCard.getValue());
		}
		Collections.sort(checkBoard);		
		
		int boardCheck = 0;
		int boardCard = checkBoard.get(0)-1;
		
		for (int eachCard: checkBoard) {
			if(eachCard-1 == boardCard) {
				boardCard = eachCard;
				boardCheck +=1; 
			}
		}
		if(boardCheck == 5) {
			return 5.0;
		}
		//End of checking board for straight
		
		ArrayList<Integer> cardArray = new ArrayList<Integer>();
		for (int i = allCardNumbers.size()-1; i >=0; i--) {
			cardArray.add(allCardNumbers.get(i));
		}
		
		int straightCounter = 1;
		int cardsToCheck = 0;
		int currentCard = 0;
		
		for(int i = 0; i <3; i ++) {
			straightCounter = 1;
			cardsToCheck = 0;
			currentCard = 0;
			
			for(int eachCard: cardArray) {
				if(cardsToCheck == 5) {
					break;
				}
				if(currentCard == 0) {
					currentCard = eachCard;
				}
				if(eachCard == 0) {
					cardsToCheck -=1;
				}
				else if(eachCard == currentCard-1) {
					straightCounter += 1;
					currentCard = eachCard;
				}
				cardsToCheck +=1;
			}

			if(straightCounter >= 5) {
				return 5.0 + (cardArray.get(i) / 100.0);
			}
			cardArray.set(i, 0);

			
		}
		return 0.0;
	}
		
		
		

	public double checkThreeOfAKind() {

		double playerCardOne = playerHandArray.get(0).getValue();
		double playerCardTwo = playerHandArray.get(1).getValue();
		int counter1 = 1;
		int counter2 = 1; 
		//If player has pair in hand 
		if(playerCardOne == playerCardTwo) {
			//Check for one more match 
			for(Card eachCard: boardCardArray) {
				if(eachCard.getValue() == playerCardOne) {
					return 4.0 + (playerCardOne/100.0);
				}
			}
				
		}

		else {
			
			for (Card eachCard: boardCardArray) {
				if(eachCard.getValue() == playerCardOne) {
					counter1 += 1; 
				} 
				if(eachCard.getValue() == playerCardTwo) {
					counter2 += 1;
				}
					
			}
			
		}

		if(counter1 == 3 && counter2 == 3) {
			if(playerCardOne > playerCardTwo) {
				return 4.0 + (playerCardOne / 100.0);
			} else {
				return 4.0 + (playerCardTwo / 100.0);
			}
		} else if (counter1 == 3) {
			return 4.0 + (playerCardOne / 100.0);
		} else if(counter2 == 3 ) {
			return 4.0 + (playerCardTwo / 100.0);
		}
		else return 0;

		
	}
	
	public double checkTwoPair() {
		double playerCardOne = playerHandArray.get(0).getValue();
		double playerCardTwo = playerHandArray.get(1).getValue();
		
		if(playerCardOne == playerCardTwo) {
			return 2.0 + (playerCardOne / 100.0);
		}
		int counter1 = 1;
		int counter2 = 1; 
		
		
		for (Card eachCard: boardCardArray) {
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
		double playerCardOne = playerHandArray.get(0).getValue();
		double playerCardTwo = playerHandArray.get(1).getValue();
		
		if(playerCardOne == playerCardTwo) {
			return 2.0 + (playerCardOne / 100.0);
		}
		
		int counter1 = 1;
		int counter2 = 1;
		
		
		for (Card eachCard: boardCardArray) {
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
		for(Card eachCard: playerHandArray) {
			if(eachCard.getValue() > highestValue) {
				highestValue = eachCard.getValue();
			}
			
		}
		return 1 + (highestValue/100); 
	} 

	
}
