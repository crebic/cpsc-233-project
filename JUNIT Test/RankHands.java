import java.util.*;

public class RankHands {

	//Players hand and board cards
	private ArrayList<Card> allCards = new ArrayList<Card>();
	private ArrayList<Integer> allCardNumbers = new ArrayList<Integer>();
	private ArrayList<Card> playerHandArray = new ArrayList<Card>();
	private ArrayList<Card> boardCardArray = new ArrayList<Card>();
	private ArrayList<Double> playersValue = new ArrayList<Double>();

    /**
     * @param boardCards array of cards on board
     * @param players array of players in game
     * @param notWinners number of player(s) that can't win (their identification number)
     * @return number of player with best hand (their identification number)
     Method is called by the gameState/UI class, and set up the ranking of card hands and returns the winning player
     */
	public int ranking(ArrayList<Card> boardCards, ArrayList<Player> players, ArrayList<Integer> notWinners) {
		playerHandArray.clear();
		allCards.clear();
		allCardNumbers.clear();
		for(Player eachPlayer: players) {
			playersValue.add(checkAllRanks(boardCards, eachPlayer.getPair()));
			playerHandArray.clear();
			allCards.clear();
			allCardNumbers.clear();
        }

		double highest = -1;
		int bestPlayer = -1;
		for(int i = 0; i < playersValue.size(); i++) {
			if((playersValue.get(i) > highest) && !notWinners.contains(i)) {
				highest = playersValue.get(i);
				bestPlayer = i+1;
			}
		}
		return bestPlayer;
	}

	public ArrayList<Double> getPlayersValue() {
		return playersValue;
	}

    /**
     * @param boardCards array of cards on board
     * @param players array of player's cards
     *
     * cycles through all possible hands
     * @return value of player's hand
     Method runs through the hierarchy of hand ranks, going from highest to lowest and returns the value of a hand
     a player has
     */
	private double checkAllRanks(ArrayList<Card> boardCards, Card[] players) {
		createCompareList(boardCards, players);
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
		if(value > 0.01) {
			return value;
        } else
            return checkHighCard();
	}

    /**
     * @param boardCardArray array of cards on the board
     * @param playerCards array player's cards
     *
     * appends player's cards and cards on board into one list
     */
	private void createCompareList(ArrayList<Card> boardCardArray, Card[] playerCards) {
		this.boardCardArray = boardCardArray;
		for (Card eachCard: boardCardArray) {
			allCards.add(eachCard);
			allCardNumbers.add(eachCard.getValue());
		}
		for (Card eachCard: playerCards ) {
			allCards.add(eachCard);
			allCardNumbers.add(eachCard.getValue());
			playerHandArray.add(eachCard);
		}
		Collections.sort(allCardNumbers);
	}

	/**
     * @return value of "royal flush" hand and highest card
     * @return 0 if hand does not contain "royal flush"
     * Method checks if a player has a royal flush in their hand (Ten, Jack, Queen, King, Ace and all same suits)
     */
	private double checkRoyalFlush() {

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

	/**
     * @return value of "straight flush" hand and highest card
     * @return 0 if hand does not contain "straight flush"
     * Method checks if player has a straight flush in their hand (5 consecutive card numbers with all same suits)
     */
	private double checkStraightFlush() {
		//index 0 is spades, index 1 is hearts, index 2 is diamonds, index 3 is clubs
		//the "value" of the cards in the count ArrayList is the number of each suit rather than the value of the card
		ArrayList<Card> count = new ArrayList<Card>();
		count.add(new Card(0,"spade"));
		count.add(new Card(0,"heart"));
		count.add(new Card(0,"diamond"));
		count.add(new Card(0,"club"));
		int spadeCounter = 0;
		int heartCounter = 0;
		int diamondCounter = 0;
		int clubCounter = 0;
		//identifies number of each suit
		for (Card eachCard: allCards){
		    if (eachCard.getSuit().equals("spade")){
			spadeCounter += 1;
			count.set(0,new Card(spadeCounter, "spade"));
		    }
		    else if (eachCard.getSuit().equals("heart")){
		    	heartCounter += 1;
		    	count.set(1,new Card(heartCounter, "heart"));
		    }
		    else if (eachCard.getSuit().equals("diamond")) {
		    	diamondCounter += 1;
		    	count.set(2,new Card(diamondCounter, "diamond"));
		    }
		    else{
		    	clubCounter += 1;
		    	count.set(3,new Card(clubCounter, "club"));
		    }
		}

		String theSuit = "";
		//theSuit is the suit we are using for the straight flush
		for (int i = 0; i <= 3;i++){
		    if(count.get(i).getValue() >= 5){
			theSuit = count.get(i).getSuit();
		    }
		}

		//creates ArrayList of all cards with the same suit
		//the arraylist will contain the value of each card instead of the card itself
		ArrayList<Integer> sameSuitCard = new ArrayList<Integer>();
		for(Card eachCard: allCards) {
			if(eachCard.getSuit() == theSuit) {
				sameSuitCard.add(eachCard.getValue());
			}
		}

		//sorts arraylist of sameSuitCard from lowerest to highest
		Collections.sort(sameSuitCard);

		//checks if there is a straight
		int straightCount = 0;
		if(sameSuitCard.size() < 1) {
			return 0.0;
		}
		int currentCard = sameSuitCard.get(0)-1;
		double highest = 0;
		for(int eachCard:sameSuitCard) {
			if(eachCard == currentCard+1) {
				straightCount += 1;
				currentCard = eachCard;
				if(highest < eachCard) {
					highest = eachCard;
				}
			} else if(eachCard == currentCard) {
				;
			}
			else {
				break;
			}
		}

		//returns 9.xx if there is a straight flush and 0.0 if there isn't
		if (straightCount >= 5) {
		    return 9.0 + highest/100.0;
		}
		else
		    return 0.0;
	}

    /**
     * @return value of "four of a kind" hand and highest card
     * @return 0 if hand does not contain "four of a kind"
     * Method checks for four of a kinds (all 4 of one card number)
     */
	private double checkFourOfAKind() {

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

    /**
     * @return value of "full house" hand and highest card
     * @return 0 if hand does not contain "full house"
     * Method checks for a full house (3 of one card number[a triple] and 2 of one card number[a pair])
     */
	private double checkFullHouse() {
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
		int boardCard2 = testBoard.get(2);

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
		if((int)playerCardOne == (int)playerCardTwo) {
			counter1 += 1;
			counter2 -=1;
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

	/**
     * @return value of "flush" hand and highest card
     * @return 0 if hand does not contain "flush"
     * Method checks for a flush (any 5 card numbers with all the same suits)
     */
	private double checkFlush() {
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

    /**
     * @return value of "straight" hand and highest card
     * @return 0 if hand does not contain "straight"
     * Method checks for a straight (5 consecutive card numbers that do not all have the same suit)
     */
	private double checkStraight() {
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
		//Checking straights with mutliple repeats of cards
		ArrayList<Integer> checkStraightWithRepeats = new ArrayList<Integer>();
		for(int eachCard: checkBoard) {
			if(checkStraightWithRepeats.contains(eachCard)) {
				continue;
			} else if(checkStraightWithRepeats.size() >5) {
				checkStraightWithRepeats.remove(0);
				checkStraightWithRepeats.add(eachCard);
			}
			else {
				checkStraightWithRepeats.add(eachCard);
			}
		}
		if(!checkStraightWithRepeats.contains(playerHandArray.get(0).getValue())) {
			checkStraightWithRepeats.add(playerHandArray.get(0).getValue());
		}
		if(!checkStraightWithRepeats.contains(playerHandArray.get(1).getValue())) {
			checkStraightWithRepeats.add(playerHandArray.get(1).getValue());
		}
		Collections.sort(checkStraightWithRepeats);
		while(checkStraightWithRepeats.size() >5) {
			checkStraightWithRepeats.remove(0);
		}
		if(checkStraightWithRepeats.size() >= 5) {
			int straightCounter = 1;
			int cardsToCheck = 0;
			int currentCard = 0;
			for(int eachCard: checkStraightWithRepeats) {
				if(currentCard == 0) {
					currentCard = eachCard;
				}
				if(eachCard == currentCard +1) {
					straightCounter +=1;
				}
				currentCard = eachCard;
			}
			if(straightCounter == 5) {
				return 5.0 + currentCard / 100.0;
			}
		}
		//End of checking repeats with multiple cards
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

	/**
     * @return value of "three of a kind" hand and highest card
     * @return 0 if hand does not contain "three of a kind"
     * Method checks for a triple(3 of the same card numbers)
     */
	private double checkThreeOfAKind() {

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

	/**
     * @return value of "two pairs" hand and highest card
     * @return 0 if hand does not contain "two pairs"
     * Method checks for a two pair (2 cards with the same card number and
     * a different 2 cards with the same card number)
     */
	private double checkTwoPair() {
		double playerCardOne = playerHandArray.get(0).getValue();
		double playerCardTwo = playerHandArray.get(1).getValue();

		if(playerCardOne == playerCardTwo) {
			return 2.0 + (playerCardOne / 100.0);
		}
		int counter1 = 1;
		int counter2 = 1;
		//Check board for two pair
		int boardCard1 = boardCardArray.get(0).getValue();
		int boardCard2 = boardCardArray.get(2).getValue();
		int boardCard3 = boardCardArray.get(3).getValue();
		int boardCounter1 = 0;
		int boardCounter2 = 0;
		int boardCounter3 = 0;
		if(boardCard2 == boardCard3) {
			boardCard2 = 0;
		}
		if(boardCard2 == boardCard1) {
			boardCard2 = 0;
		}
		for (Card eachCard: boardCardArray) {
			if(boardCard1 == eachCard.getValue()) {
				boardCounter1 += 1;
			}
			if(boardCard2 == eachCard.getValue()) {
				boardCounter2 += 1;
			}
			if (boardCard3 == eachCard.getValue()) {
				boardCounter3 +=1;
			}
		}

		if(boardCounter1 == 2 && boardCounter2==2 || boardCounter1 == 2 && boardCounter3 == 2  ||
				boardCounter2 == 3 && boardCounter3 == 3) {
			return 3.0;
        }
		//End of board check

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

		else if(counter1 == 2 && boardCounter1 == 2) {
			if(playerCardOne > boardCard1) {
				return 3.0 + (playerCardOne / 100.0);
			} else {
				return 3.0 + (boardCard1 / 100.0);
			}

		} else if(counter1 == 2 && boardCounter2 == 2) {
			if(playerCardOne > boardCard2) {
				return 3.0 + (playerCardOne / 100.0);
			} else {
				return 3.0 + (boardCard2 / 100.0);
			}

		} else if(counter1 == 2 && boardCounter3 == 2) {
			if(playerCardOne > boardCard3) {
				return 3.0 + (playerCardOne / 100.0);
			} else {
				return 3.0 + (boardCard3 / 100.0);
			}

		} else if(counter2 == 2 && boardCounter1 == 2) {
			if(playerCardTwo > boardCard1) {
				return 3.0 + (playerCardTwo / 100.0);
			} else {
				return 3.0 + (boardCard1 / 100.0);
			}

		} else if(counter2 == 2 && boardCounter2 == 2) {
			if(playerCardTwo > boardCard2) {
				return 3.0 + (playerCardTwo / 100.0);
			} else {
				return 3.0 + (boardCard2 / 100.0);
            }

		} else if(counter2 == 2 && boardCounter3 == 2) {
			if(playerCardTwo > boardCard3) {
				return 3.0 + (playerCardTwo / 100.0);
			} else {
				return 3.0 + (boardCard3 / 100.0);
			}
		}
		else return 0;
	}

	/**
     * @return value of "pair" hand and highest card
     * @return 0 if hand does not contain "pair"
     * Method checks for a pair (two cards with the same card number)
     */
	private double checkPair() {
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
		else if(counter2 == 2) {
			return 2.0 + (playerCardTwo / 100.0);
		}
		else return 0;
	}

	/**
     * @return value of highest card in hand
     * Method checks for the highest single card value
     */
	private double checkHighCard() {
		double highestValue = 0;
		for(Card eachCard: playerHandArray) {
			if(eachCard.getValue() > highestValue) {
				highestValue = eachCard.getValue();
			}
		}
		return 1 + (highestValue/100);
	}
}
