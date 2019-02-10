class Player{
    private int chips;
    private int seatNumber;
    private bool isFolded;
    Card[] cardPair = new Card[2];
    private bool isPlaying = true;

    Player(int startingChips, int _seatNumber, Card card1, Card card2)
    {
        //set initial player state
        chips = startingChips;
        seatNumber = _seatNumber;
        cardPair[0] = card1;
        cardPair[1] = card2;
        isFolded = false;
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
    public bool getDidFold()
    {
        return isFolded;
    }
    public bool getIsPlaying()
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
    public void changeFoldedState(bool folded)
    {
        isFolded = folded;
    }
    public void changePlayingState(bool playing)
    {
        isPlaying = playing;
    }
}