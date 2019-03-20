

class Player{
	
	//* instance variables 
    private int chips;
    private int seatNumber;
    private boolean isFolded;
    Card[] cardPair = new Card[2];
    private boolean isPlaying = true;
	
	//for testing purposes
	//* default constructor 
	Player(){
		
	}
	
	//* constructor 
	//* @param startingChips of type int which is the players starting amount
	//* @param  _seatNumber of type int that shows where the players turn is in order with respect to the other players
	Player(int startingChips, int _seatNumber)
	{
		chips = startingChips;
		seatNumber = _seatNumber;
	}
	
	
	//* constructor 
	//* @param startingChips of type int which is the players starting amount
	//* @param  _seatNumber of type int that shows where the players turn is in order with respect to the other players
	//* @param  card1 and card2 of type Card, basically deals two cards to a player 
    Player(int startingChips, int _seatNumber, Card card1, Card card2)
    {
        //set initial player state
        chips = startingChips;
        seatNumber = _seatNumber;
        cardPair[0] = card1;
        cardPair[1] = card2;
        isFolded = false;
    }
    
    //* constructor 
    //* @param player of type Player, grabs an instance of the Player object, gives that instance chips, a seat number
    //* a folded state, deals their cards and ensures shows if they are still in the game. 
    Player(Player player)
    {
        chips = player.getChips();
        seatNumber = player.getSeatNumber();
        isFolded = player.getDidFold();
        cardPair = player.getPair();
        isPlaying = player.getIsPlaying();
    }

    //getters
    //@return returns chips
    public int getChips()
    {
        return chips;
    }
     
    //@return returns seat number (player location in the betting rounds)
    public int getSeatNumber()
    {
        return seatNumber;
    }
    
    //*@return returns the card pair
    public Card[] getPair()
    {
		return cardPair;

    } 
	
	//* @return returns the card pair information as a string example jack of spades is "11s"
	public String getPairAsString()
	{
		return (cardPair[0].getValue() + cardPair[0].getSuit().substring(0,1).toUpperCase() + " " + cardPair[1].getValue() + cardPair[1].getSuit().substring(0,1).toUpperCase());
	}
	
    //* @return returns a boolean of whether or not a player has folded out of a hand
    public boolean getDidFold()
    {
        return isFolded;
    }
	
    //*@returns a boolean true if a player has chips false otherwise
    public boolean getIsPlaying()
    {
        return isPlaying;
    }
	
    
    //setters 
     //* @param Card type: card1, card2
     //* populates the cardPair array with the params
    public void setPair(Card card1, Card card2)
    {
        cardPair[0] = card1;
        cardPair[1] = card2;
    }
	
    //* @param chipsAdded of type int
    //* adds chipsAdded to the players chip count 
    public void addChips(int chipsAdded)
    {
        chips += chipsAdded;
    }
	
    
    public void deductChips(int chipsDeducted)
    {
        chips -= chipsDeducted;
    }
	
    //* @param boolean type folded
    //* setter for folded state of a player
    public void changeFoldedState(boolean folded)
    {
        isFolded = folded;
    }
    
    //*@param boolean type playing
    //* setter for if a player is still active
    public void changePlayingState(boolean playing)
    {
        isPlaying = playing;
    }

}
