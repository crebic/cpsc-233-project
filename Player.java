

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
    //* 
    Player(Player player)
    {
        chips = player.getChips();
        seatNumber = player.getSeatNumber();
        isFolded = player.getDidFold();
        cardPair = player.getPair();
        isPlaying = player.getIsPlaying();
    }

    //getters
    public int getChips()
    {
        return chips;
    }
    public int getSeatNumber()
    {
        return seatNumber;
    }
    public Card[] getPair()
    {
		return cardPair;

    }
	public String getPairAsString()
	{
		return (cardPair[0].getValue() + cardPair[0].getSuit().substring(0,1).toUpperCase() + " " + cardPair[1].getValue() + cardPair[1].getSuit().substring(0,1).toUpperCase());
	}
    public boolean getDidFold()
    {
        return isFolded;
    }
    public boolean getIsPlaying()
    {
        return isPlaying;
    }
	
    
    //setters
    public void setPair(Card card1, Card card2)
    {
        cardPair[0] = card1;
        cardPair[1] = card2;
    }
    public void addChips(int chipsAdded)
    {
        chips += chipsAdded;
    }
    public void deductChips(int chipsDeducted)
    {
        chips -= chipsDeducted;
    }
    public void changeFoldedState(boolean folded)
    {
        isFolded = folded;
    }
    public void changePlayingState(boolean playing)
    {
        isPlaying = playing;
    }

}