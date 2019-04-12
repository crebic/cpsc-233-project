package game;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class stores all of the intance variables used across other 
 * classes in the game so they can be saved if desired.
 */
class SaveSlot implements Serializable
{
    /**
     * A unique ID that is used for serializing the class.
     */
    private static final long serialVersionUID = 4L;

    /**
     * An arraylist of the players in the game.
     */
    private ArrayList<Player> playerList = new ArrayList<Player>();

    /**
     * AN arraylist of the cards on the table.
     */
    private ArrayList<Card> tableCards = new ArrayList<Card>();

    /**
     * The amount a player can potentially win; the sum of all the bets.
     */
    private int pot = 0;

    /**
     * The deck of cards in use.
     */
    private Deck deck = new Deck();

    /**
     * The current round of the game.
     */
    private int round = 0;

    /**
     * The last amount raised; the minimum amount the current player can bet.
     */
    private int amountToCall = 0;

    /**
     * The player who's turn it is.
     */
    private Player currentPlayer;

    /**
     * The next player who's turn it is.
     */
    private Player nextPlayer;

    /**
     * The last player to raise; the minimum amount one can bet.
     */
    private Player lastPlayerToRaise;

    /**
     * The player to the left of the dealer.
     */
    private Player leftOfDealer;

    /**
     * Set all values as kind of a storing of all the gamestate values, this saveslot 
     * object will be serialized so we can efficiently extract all saved data from it using 
     * the below getters.
     * @param playerlist an arraylist of players.
     * @param tableCards an arraylist of the cards on the table.
     * @param pot the integer amount of chips in the pot.
     * @param deck the deck of cards in use.
     * @param round the round the current game is in.
     * @param amountToCall the integer amount of the last bet made.
     * @param currentPlayer the player who's turn it is.
     * @param nextPlayer the next player who's turn it will be.
     * @param lastPlayerToRaise the last player who raised the bet.
     * @param leftOfDealer the player to the left of the dealer.
     */
    public SaveSlot (ArrayList<Player> playerList, ArrayList<Card> tableCards, int pot, Deck deck, int round, int amountToCall, Player currentPlayer, Player nextPlayer, Player lastPlayerToRaise, Player leftOfDealer)
    {
        super();
        this.playerList = playerList;
        this.tableCards = tableCards;
        this.pot = pot;
        this.deck = deck;
        this.round = round;
        this.amountToCall = amountToCall;
        this.currentPlayer = currentPlayer;
        this.nextPlayer = nextPlayer;
        this.lastPlayerToRaise = lastPlayerToRaise;
        this.leftOfDealer = leftOfDealer;
    }

    /**
     * @return an arraylist of players.
     */
    public ArrayList<Player> getPlayerList()
    {
        return this.playerList;
    }

    /**
     * @return an arraylist of the cards on the table.
     */
    public ArrayList<Card> getTableCards()
    {
        return this.tableCards;
    }

    /**
     * @return the amount of chips in the pot.
     */
    public int getPot()
    {
        return this.pot;
    }

    /**
     * @return the deck of cards.
     */
    public Deck getDeck()
    {
        return this.deck;
    }

    /**
     * @return the current round it is.
     */
    public int getRound()
    {
        return this.round;
    }

    /**
     * @return the amount of the last raise; the minimum amount the current player can bet.
     */
    public int getAmountToCall()
    {
        return this.amountToCall;
    }

    /**
     * @return the player who's turn it is.
     */
    public Player getCurrentPlayer()
    {
        return this.currentPlayer;
    }

    /**
     * @return the next player who's turn it will be.
     */
    public Player getNextPlayer()
    {
        return this.nextPlayer;
    }

    /**
     * @return the last player who raised.
     */
    public Player getLastPlayerToRaise()
    {
        return this.lastPlayerToRaise;
    }

    /**
     * @return the player who is beside the dealer (on the left).
     */
    public Player getLeftOfDealer()
    {
        return this.leftOfDealer;
    }
}
