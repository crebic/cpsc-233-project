class Player{
    private int chips;
    private int seatNumber;
    private boolean isFolded;
    Card[] cardPair = new Card[2];
    private boolean isPlaying = true;

    Player(int startingChips, int _seatNumber, Card card1, Card card2)
    {
        //set initial player state
        chips = startingChips;
        seatNumber = _seatNumber;
        cardPair[0] = card1;
        cardPair[1] = card2;
        isFolded = false;
    }
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
    public void changeFoldedState(bool folded)
    {
        isFolded = folded;
    }
    public void changePlayingState(bool playing)
    {
        isPlaying = playing;
    }
}