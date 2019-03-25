/* Test for RankHands class
*@Author T09 G1
*March 22, 2019
*/
import java.util.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class RankHandsTest {
  //These variable are required to test rankHands and will be test
  //RankHands, and it is required for almost all of the tests
  ArrayList<Card> boardCards = new ArrayList<Card>();
  ArrayList<Player> players = new ArrayList<Player>();
  ArrayList<Integer> notWinners = new ArrayList<Integer>();

  //Checks to see if it properly ranks high card, which should be
  //1 + the highest card value / 100.0
  @Test
  public void test_checkHighCard_recognizesHighCard() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(13,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(9,"diamond"));
    boardCards.add(new Card(6,"diamond"));

    players.add(new Player(10, 1, new Card(8, "club"), new Card(12,"heart")) );
    players.add(new Player(10, 1, new Card(5, "heart"), new Card(11,"diamond")) );


    //Execute and verify
    RankHands test = new RankHands();
    test.ranking(boardCards, players, notWinners);
    assertEquals("Does not recognize high card",1.12 , test.getPlayersValue().get(0),0.001);
  }

  //Test if high card vs. high card works, in this case player 2 has the better card (13) so they should win
  @Test
  public void test_checkHighCard_HighCardVsHighCard() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(10,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(9,"diamond"));
    boardCards.add(new Card(6,"diamond"));

    players.add(new Player(10, 1, new Card(8, "club"), new Card(12,"heart")) );
    players.add(new Player(10, 1, new Card(5, "heart"), new Card(13,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Does not rank high card vs. high card properly",2 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Check high card vs pair, in this case player 2 has a pair and player 1 has a high card, so
  //player 2 should win
  @Test
  public void test_checkHighCard_HighCardVsPair(){
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(13,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(9,"diamond"));
    boardCards.add(new Card(6,"diamond"));

    players.add(new Player(10, 1, new Card(8, "club"), new Card(12,"heart")) );
    players.add(new Player(10, 1, new Card(5, "heart"), new Card(13,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Pair is greater than high card",2 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Checks to see if it properly ranks a pair (2 same cards), which should be
  //2 + the same card value / 100.0
  @Test
  public void test_checkPair_recognizesPair() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(5,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(8, "club"), new Card(7,"heart")) );
    players.add(new Player(10, 1, new Card(8, "heart"), new Card(11,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    test.ranking(boardCards, players, notWinners);
    assertEquals("Does not recognize pairs ",2.07 ,test.getPlayersValue().get(0) ,0.001);
  }

  //Pair 2 is higher, so it should win
  @Test
  public void test_checkPair_pairVsPair() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(13,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(9,"diamond"));
    boardCards.add(new Card(6,"diamond"));

    players.add(new Player(10, 1, new Card(6, "club"), new Card(12,"heart")) );
    players.add(new Player(10, 1, new Card(9, "heart"), new Card(13,"diamond")) );

    RankHands test = new RankHands();
    assertEquals("Pair 2 is greater than Pair 1",2 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Three of a kind is greater than pair, so player 2 should win
  @Test
  public void test_checkPair_pairVsThreeOfAKind() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(13,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(9,"diamond"));
    boardCards.add(new Card(6,"diamond"));

    players.add(new Player(10, 1, new Card(6, "club"), new Card(5,"heart")) );
    players.add(new Player(10, 1, new Card(9, "heart"), new Card(13,"diamond")) );

    RankHands test = new RankHands();
    assertEquals("Two Pair is greater than a Pair ", 2 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Checks to see if it properly ranks two pairs(2 different same cards), which should be
  //4 + the highest card value / 100.0
  @Test
  public void test_checkTwoPair_recognizesTwoPair() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(8,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(8, "club"), new Card(7,"heart")) );
    players.add(new Player(10, 1, new Card(8, "heart"), new Card(11,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    test.ranking(boardCards, players, notWinners);
    assertEquals("Does not recognize two pair",3.08 ,test.getPlayersValue().get(0) ,0.001);
  }

  //Two pair 1 is better, so player 1 should win
  @Test
  public void test_checkTwoPair_twoPairVsTwoPair() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(8,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(9,"diamond"));
    boardCards.add(new Card(6,"diamond"));

    players.add(new Player(10, 1, new Card(6, "club"), new Card(9,"heart")) );
    players.add(new Player(10, 1, new Card(8, "heart"), new Card(3,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Two Pair 1 is greater than Two Pair 2", 1.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Three of a kind is greater than a two pair, so player 2 should be
  @Test
  public void test_checkTwoPair_twoPairVsThreeOfAKind() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(13,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(9,"diamond"));
    boardCards.add(new Card(6,"diamond"));

    players.add(new Player(10, 1, new Card(6, "club"), new Card(9,"heart")) );
    players.add(new Player(10, 1, new Card(9, "heart"), new Card(9,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Three of a kind is greater than two pair", 2.0 , test.ranking(boardCards,players,notWinners),0.001);

  }

  //Checks to see if it properly ranks a three of a kind(3 same cards), which should be
  //4 + the triple card value / 100.0
  @Test
  public void test_checkThreeOfAKind_recognizesThreeOfAKind() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(8,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(8, "club"), new Card(7,"heart")) );
    players.add(new Player(10, 1, new Card(8, "heart"), new Card(8,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    test.ranking(boardCards, players, notWinners);
    assertEquals("Does not recognize three of a kind",4.08 ,test.getPlayersValue().get(1) ,0.001);
  }

  //Trhee of a kind 1 is better than three of a kind 2, so player 1 should win
  @Test
  public void test_checkThreeOfAKind_threeOfAKindVsThreeOfAKind() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(13,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(9,"diamond"));
    boardCards.add(new Card(10,"diamond"));

    players.add(new Player(10, 1, new Card(10, "club"), new Card(10,"heart")) );
    players.add(new Player(10, 1, new Card(9, "heart"), new Card(9,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Three of a kind 1 is greater than three of a kind 2", 1.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //A straight is greater than a 3 of a kind, so player 2 should win
  @Test
  public void test_checkThreeOfAKind_threeOfAKindVsStraight() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(4,"club"));
    boardCards.add(new Card(5,"club"));
    boardCards.add(new Card(6,"diamond"));
    boardCards.add(new Card(9,"diamond"));
    boardCards.add(new Card(9,"diamond"));

    players.add(new Player(10, 1, new Card(10, "club"), new Card(9,"heart")) );
    players.add(new Player(10, 1, new Card(7, "heart"), new Card(8,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Straight is greater than a three of a kind", 2.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Checks to see if it properly ranks straights(5 consecutive numbered cards), which should be
  //5 + the highest card value / 100.0
  @Test
  public void test_checkStraight_recognizesStraight() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(8,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(5, "club"), new Card(6,"heart")) );
    players.add(new Player(10, 1, new Card(8, "heart"), new Card(8,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    test.ranking(boardCards, players, notWinners);
    assertEquals("Does not recognize three of a kind",5.08 ,test.getPlayersValue().get(0) ,0.001);
  }

  //Straight 2 is better than straight 1, so player 2 should win
  @Test
  public void test_checkStraight_straightVsStraight() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(7,"club"));
    boardCards.add(new Card(8,"club"));
    boardCards.add(new Card(6,"diamond"));
    boardCards.add(new Card(9,"diamond"));
    boardCards.add(new Card(9,"diamond"));

    players.add(new Player(10, 1, new Card(10, "club"), new Card(9,"heart")) );
    players.add(new Player(10, 1, new Card(10, "heart"), new Card(11,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Straight2 is greater than straight 1", 2.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Test straight vs. flush, flush (player 2) should win in this case
  @Test
  public void test_checkStraight_straightVsFlush() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(13,"diamond"));
    boardCards.add(new Card(3,"diamond"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(5,"diamond"));
    boardCards.add(new Card(6,"heart"));

    players.add(new Player(10, 1, new Card(2, "club"), new Card(0,"heart")) );
    players.add(new Player(10, 1, new Card(7, "diamond"), new Card(10,"heart")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Flush is higher than Straight",2 , test.ranking(boardCards, players, notWinners),0.001);
  }

  //Checks to see if it properly ranks flush(5 same suits), which should be
  //6 + the highest card value / 100.0
  @Test
  public void test_checkFlush_recognizesFlush() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(3,"diamond"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(8,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(8, "club"), new Card(7,"heart")) );
    players.add(new Player(10, 1, new Card(9, "heart"), new Card(8,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    test.ranking(boardCards, players, notWinners);
    assertEquals("Does not recognize flush",6.08 ,test.getPlayersValue().get(1) ,0.001);
  }

  //Test flush 2 is better than flush 1, so player 2 should win
  @Test
  public void test_checkFlush_flushVsFlush() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(3,"diamond"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(8,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(10, "diamond"), new Card(2,"diamond")) );
    players.add(new Player(10, 1, new Card(12, "diamond"), new Card(6,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Straight2 is greater than straight 1", 2.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Full house is greater than flush, so player 1 should win
  @Test
  public void test_checkFlush_flushVsFullHouse() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(7,"club"));
    boardCards.add(new Card(3,"diamond"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(8,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(7, "club"), new Card(3,"heart")) );
    players.add(new Player(10, 1, new Card(12, "diamond"), new Card(6,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Straight 1 is greater than flush 2", 1.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Checks to see if it properly ranks full house(a three of a kind and a pair), which should be
  //7 + the three of a kind card value / 100.0
  @Test
  public void test_checkFullHouse_recognizesFullHouse() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(7,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(8,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(8, "club"), new Card(7,"heart")) );
    players.add(new Player(10, 1, new Card(8, "heart"), new Card(8,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    test.ranking(boardCards, players, notWinners);
    assertEquals("Does not recognize full house",7.08 ,test.getPlayersValue().get(1) ,0.001);

  }

  //Full house 2 is greater than full house 1, so player 2 should win
  @Test
  public void test_checkFullHouse_fullHouseVsFullHouse() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(2,"diamond"));
    boardCards.add(new Card(3,"diamond"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(3, "club"), new Card(3,"heart")) );
    players.add(new Player(10, 1, new Card(4, "diamond"), new Card(4,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Full house 2 is greater than full house 1", 2.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Four of a kind is greater than a full house, so player 2 should win
  @Test
  public void test_checkFullHouse_fullHouseVsFourOfAKind() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(2,"diamond"));
    boardCards.add(new Card(3,"diamond"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(3, "club"), new Card(3,"heart")) );
    players.add(new Player(10, 1, new Card(2, "diamond"), new Card(2,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Four of a kind is greater than full house", 2.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Checks to see if it properly ranks a four of a kind, which should be
  //8 + the four of a kind card value / 100.0
  @Test
  public void test_checkFourOfAKind_recognizesFourOfAKind() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(8,"club"));
    boardCards.add(new Card(4,"diamond"));
    boardCards.add(new Card(8,"diamond"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(2, "club"), new Card(7,"heart")) );
    players.add(new Player(10, 1, new Card(8, "heart"), new Card(8,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    test.ranking(boardCards, players, notWinners);
    assertEquals("Does not recognize four of a kind",8.08 ,test.getPlayersValue().get(1) ,0.001);

  }

  //Four of a kind 2 is greater than four of a kind 1, so player 2 should win
  @Test
  public void test_checkFourOfAKind_fourOfAKindVsFourOfAKind() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(5,"club"));
    boardCards.add(new Card(3,"diamond"));
    boardCards.add(new Card(3,"spade"));
    boardCards.add(new Card(5,"spade"));
    boardCards.add(new Card(7,"diamond"));

    players.add(new Player(10, 1, new Card(3, "club"), new Card(3,"heart")) );
    players.add(new Player(10, 1, new Card(5, "diamond"), new Card(5,"heart")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Four of a kind is greater than full house", 2.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Straight flush is greater than four of a kind, so player 1 should win
  @Test
  public void test_checkFourOfAKind_fourOfAKindVsStraightFlush() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(13,"club"));
    boardCards.add(new Card(6,"club"));
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(5,"spade"));
    boardCards.add(new Card(5,"club"));

    players.add(new Player(10, 1, new Card(3, "club"), new Card(4,"club")) );
    players.add(new Player(10, 1, new Card(5, "diamond"), new Card(5,"heart")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Four of a kind is greater than full house", 1.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Checks to see if it properly ranks royal flush (5 consecutive cards, same suits), which should be
  //9 + the highest card value / 100.0
  @Test
  public void test_checkStraightFlush_recognizesStraightFlush() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(3,"diamond"));
    boardCards.add(new Card(4,"club"));
    boardCards.add(new Card(5,"club"));
    boardCards.add(new Card(10,"diamond"));

    players.add(new Player(10, 1, new Card(3, "club"), new Card(6,"club")) );
    players.add(new Player(10, 1, new Card(8, "heart"), new Card(8,"diamond")) );

    //Execute and verify
    RankHands test = new RankHands();
    test.ranking(boardCards, players, notWinners);
    assertEquals("Does not recognize straight flush",9.06 ,test.getPlayersValue().get(0) ,0.001);
  }

  //Straight flush 2 is greater than straight flush 1, so player 2 should win
  @Test
  public void test_checkStraightFlush_straightFlushVsStraightFlush() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(4,"club"));
    boardCards.add(new Card(5,"club"));
    boardCards.add(new Card(10,"club"));

    players.add(new Player(10, 1, new Card(1, "club"), new Card(12,"club")) );
    players.add(new Player(10, 1, new Card(7, "club"), new Card(6,"club")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Four of a kind is greater than full house", 2.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Royal flush is the greatest hand, so it should win over other ranks
  @Test
  public void test_checkStraightFlush_straightFlushVsRoyalFlush() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(2,"club"));
    boardCards.add(new Card(3,"club"));
    boardCards.add(new Card(10,"club"));
    boardCards.add(new Card(11,"club"));
    boardCards.add(new Card(12,"club"));

    players.add(new Player(10, 1, new Card(9, "club"), new Card(8,"club")) );
    players.add(new Player(10, 1, new Card(14, "club"), new Card(13,"club")) );

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Four of a kind is greater than full house", 2.0 , test.ranking(boardCards,players,notWinners),0.001);
  }

  //Checks to see if it properly ranks royal flush (Ten, Jack, Queen, King, Ace), which should be
  //10 + the highest card value / 100.0
  @Test
  public void test_checkRoyalFlush_recognizesRoyalFlush() {
    //Setup
    notWinners.add(-1);
    boardCards.add(new Card(14,"club"));
    boardCards.add(new Card(13,"club"));
    boardCards.add(new Card(12,"club"));
    boardCards.add(new Card(5,"club"));
    boardCards.add(new Card(10,"diamond"));

    players.add(new Player(10, 1, new Card(11, "club"), new Card(9,"club")) );
    players.add(new Player(10, 1, new Card(11, "club"), new Card(10,"club")) );

    //Execute and verify
    RankHands test = new RankHands();
    test.ranking(boardCards, players, notWinners);
    assertEquals("Does not recognize royal flush",10.0 ,test.getPlayersValue().get(1) ,0.001);
  }



}
