/* Test for RankHands class Version 2
*@Author T09 G1
*April 10, 2019
*/
import java.util.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class RankHandsTest {
  //These variable are required to test rankHands and will be used in all tests
  public Player player = new Player("b",5);
  public Player player2 = new Player("C",5);
  public ArrayList<Card> tableCards = new ArrayList<>();

  //Checks to see if it properly ranks high card
  @Test
  public void test_highCard_recognizesHighCard() {
    //Setup
      player.setHand(new Card(2,"D"), new Card(5, "D"));
      player2.setHand(new Card(3,"D"), new Card(9, "H"));
      tableCards.add(new Card(10, "C"));
      tableCards.add(new Card(8, "C"));
      tableCards.add(new Card(11, "C"));
      tableCards.add(new Card(4, "H"));
      tableCards.add(new Card(6, "D"));

      ArrayList<Player> playerList = new ArrayList();
      playerList.add(player);
      playerList.add(player2);

      RankHands test = new RankHands();
      test.rankHands(playerList,tableCards);
      assertEquals("Does not recognize high card",0.1105 , player.getScoreThisRound(),0.001);
  }

  //Test if high card vs. high card works, in this case player 1 has the better card so they should win
  @Test
  public void test_highCard_HighCardVsHighCard() {
    //Setup
    player.setHand(new Card(2,"D"), new Card(12, "D"));
    player2.setHand(new Card(3,"D"), new Card(9, "H"));

    tableCards.add(new Card(10, "C"));
    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(11, "C"));
    tableCards.add(new Card(4, "H"));
    tableCards.add(new Card(6, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 1 should win with the higher high card",true ,test.rankHands(playerList,tableCards).contains(player));

  }

  //Check high card vs pair, in this case player 2 has a pair and should win
  @Test
  public void test_highCard_HighCardVsPair(){
    //setup
    player.setHand(new Card(2,"D"), new Card(12, "D"));
    player2.setHand(new Card(3,"D"), new Card(9, "H"));

    tableCards.add(new Card(10, "C"));
    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(11, "C"));
    tableCards.add(new Card(9, "H"));
    tableCards.add(new Card(6, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 2 should win with the pair",true ,test.rankHands(playerList,tableCards).contains(player2));

  }

  //Checks to see if it properly ranks a pair (2 same cards)
  @Test
  public void test_checkPair_recognizesPair() {
    //Setup
    player.setHand(new Card(8,"D"), new Card(7, "H"));
    player2.setHand(new Card(8,"H"), new Card(11, "D"));
    tableCards.add(new Card(2, "C"));
    tableCards.add(new Card(3, "C"));
    tableCards.add(new Card(4, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(7, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);


    //Execute and verify
    RankHands test = new RankHands();
    test.rankHands(playerList,tableCards);
    assertEquals("Does not recognize pairs ",1.07 ,player.getScoreThisRound() ,0.001);
  }

  //Pair for player 2 is higher, so it should win
  @Test
  public void test_checkPair_pairVsPair() {
    //Setup
    player.setHand(new Card(2,"D"), new Card(8, "D"));
    player2.setHand(new Card(3,"D"), new Card(9, "H"));

    tableCards.add(new Card(10, "C"));
    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(11, "C"));
    tableCards.add(new Card(9, "H"));
    tableCards.add(new Card(6, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 2 should win with the higher pair",true ,test.rankHands(playerList,tableCards).contains(player2));
  }

  //Three of a kind is greater than pair, so player 2 should win
  @Test
  public void test_checkPair_pairVsThreeOfAKind() {
    //Setup
    player.setHand(new Card(6,"C"), new Card(5, "H"));
    player2.setHand(new Card(9,"H"), new Card(9, "S"));

    tableCards.add(new Card(13, "C"));
    tableCards.add(new Card(3, "C"));
    tableCards.add(new Card(4, "D"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(6, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 2 should win with the three of a kind",true ,test.rankHands(playerList,tableCards).contains(player2));
  }

  //Checks to see if it properly ranks two pairs(2 different same cards)
  @Test
  public void test_checkTwoPair_recognizesTwoPair() {
    //Setup
    player.setHand(new Card(8,"C"), new Card(11, "H"));
    tableCards.add(new Card(11, "C"));
    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(4, "D"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(6, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);

    //Execute and verify
    RankHands test = new RankHands();
    test.rankHands(playerList,tableCards);
    assertEquals("Does not recognize two pairs ",2.1111 ,player.getScoreThisRound() ,0.001);

  }

  //Two pair for player 1 is better, so player 1 should win
  @Test
  public void test_checkTwoPair_twoPairVsTwoPair() {
    //Setup
    player.setHand(new Card(8,"C"), new Card(10, "H"));
    player2.setHand(new Card(9,"H"), new Card(2, "S"));

    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(2, "C"));
    tableCards.add(new Card(10, "D"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(6, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 1 should win with the higher two pair ",true ,test.rankHands(playerList,tableCards).contains(player));

  }

  //Three of a kind is greater than a two pair, so player 2 win
  @Test
  public void test_checkTwoPair_twoPairVsThreeOfAKind() {
    //Setup
    player.setHand(new Card(8,"C"), new Card(10, "H"));
    player2.setHand(new Card(9,"H"), new Card(9, "S"));

    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(2, "C"));
    tableCards.add(new Card(10, "D"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(6, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 2 should win with the three of a kind ",true ,test.rankHands(playerList,tableCards).contains(player2));

  }

  //Checks to see if it properly ranks a three of a kind(3 same cards)
  @Test
  public void test_checkThreeOfAKind_recognizesThreeOfAKind() {
    //Setup
    player.setHand(new Card(8,"C"), new Card(7, "H"));
    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(2, "C"));
    tableCards.add(new Card(10, "D"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(8, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);

    //Execute and verify
    RankHands test = new RankHands();
    test.rankHands(playerList,tableCards);
    assertEquals("Does not recognize three of a kind ",3.0810 ,player.getScoreThisRound() ,0.001);
  }

  //Trhee of a kind for player 2 is better than three of a kind for player 1, so player 2 should win
  @Test
  public void test_checkThreeOfAKind_threeOfAKindVsThreeOfAKind() {
    //Setup
    player.setHand(new Card(8,"C"), new Card(5, "H"));
    player2.setHand(new Card(9,"H"), new Card(9, "S"));

    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(10, "D"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(6, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 2 should win with the higher three of a kind ",true ,test.rankHands(playerList,tableCards).contains(player2));
  }

  //A straight is greater than a 3 of a kind, so player 2 should win
  @Test
  public void test_checkThreeOfAKind_threeOfAKindVsStraight() {
    //Setup
    player.setHand(new Card(8,"C"), new Card(9, "H"));
    player2.setHand(new Card(6,"H"), new Card(7, "S"));

    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(4, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 2 should win with the straight ",true ,test.rankHands(playerList,tableCards).contains(player2));
  }

  //Checks to see if it properly ranks straights(5 consecutive numbered cards)
  @Test
  public void test_checkStraight_recognizesStraight() {
    //Setup
    player.setHand(new Card(9,"C"), new Card(7, "H"));
    tableCards.add(new Card(6, "C"));
    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(4, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);

    //Execute and verify
    RankHands test = new RankHands();
    test.rankHands(playerList,tableCards);
    assertEquals("Does not recognize straights",4.0900 ,player.getScoreThisRound() ,0.001);
  }

  //Straight for player 1 is better than straight for player 2, so player 1 should win
  @Test
  public void test_checkStraight_straightVsStraight() {
    //Setup
    player.setHand(new Card(11,"C"), new Card(6, "H"));
    player2.setHand(new Card(6,"H"), new Card(7, "S"));
    tableCards.add(new Card(7, "C"));
    tableCards.add(new Card(8, "C"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(10, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 1 should win with the higher straight",true ,test.rankHands(playerList,tableCards).contains(player));
  }

  //Test straight vs. flush, flush (player 2) should win in this case
  @Test
  public void test_checkStraight_straightVsFlush() {
    //Setup
    player.setHand(new Card(9,"C"), new Card(7, "H"));
    player2.setHand(new Card(12,"D"), new Card(11,"D"));
    tableCards.add(new Card(6, "C"));
    tableCards.add(new Card(8, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(4, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 2 should win with the flush ",true ,test.rankHands(playerList,tableCards).contains(player2));
  }

  //Checks to see if it properly ranks flush(5 same suits)
  @Test
  public void test_checkFlush_recognizesFlush() {
    //Setup
    player.setHand(new Card(9,"D"), new Card(7, "H"));
    tableCards.add(new Card(6, "C"));
    tableCards.add(new Card(8, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(4, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);

    //Execute and verify
    RankHands test = new RankHands();
    test.rankHands(playerList,tableCards);
    assertEquals("Does not recognize flush",5.09 , player.getScoreThisRound(),0.001);
  }

  //Flush for player 2 2 is better than flush for player 1, so player 2 should win
  @Test
  public void test_checkFlush_flushVsFlush() {
    //Setup
    player.setHand(new Card(9,"C"), new Card(7, "D"));
    player2.setHand(new Card(12,"D"), new Card(11,"D"));
    tableCards.add(new Card(6, "C"));
    tableCards.add(new Card(8, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(4, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 2 should win with the higher flush ",true ,test.rankHands(playerList,tableCards).contains(player2));
  }

  //Full house is greater than flush, so player 1 should win
  @Test
  public void test_checkFlush_flushVsFullHouse() {
    //Setup
    player.setHand(new Card(12,"C"), new Card(7, "C"));
    player2.setHand(new Card(5,"C"), new Card(11,"D"));
    tableCards.add(new Card(12, "C"));
    tableCards.add(new Card(12, "D"));
    tableCards.add(new Card(7, "D"));
    tableCards.add(new Card(4, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 1 should win with the full house",true ,test.rankHands(playerList,tableCards).contains(player));
  }

  //Checks to see if it properly ranks full house(a three of a kind and a pair)
  @Test
  public void test_checkFullHouse_recognizesFullHouse() {
    //Setup
    player.setHand(new Card(9,"D"), new Card(7, "H"));
    tableCards.add(new Card(6, "C"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(7, "D"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);

    //Execute and verify
    RankHands test = new RankHands();
    test.rankHands(playerList,tableCards);
    assertEquals("Does not recognize full house",6.09 , player.getScoreThisRound(),0.001);

  }


  //Full house for player 1 is greater than full house for player 2, so player 1 should win
  @Test
  public void test_checkFullHouse_fullHouseVsFullHouse() {
    //Setup
    player.setHand(new Card(12,"C"), new Card(7, "C"));
    player2.setHand(new Card(5,"C"), new Card(5,"D"));
    tableCards.add(new Card(12, "C"));
    tableCards.add(new Card(12, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(7, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 1 should win with the higher full house ",true ,test.rankHands(playerList,tableCards).contains(player));
  }


  //Four of a kind is greater than a full house, so player 1 should win
  @Test
  public void test_checkFullHouse_fullHouseVsFourOfAKind() {
    //Setup
    player.setHand(new Card(12,"C"), new Card(2, "C"));
    player2.setHand(new Card(5,"C"), new Card(5,"D"));
    tableCards.add(new Card(12, "C"));
    tableCards.add(new Card(12, "D"));
    tableCards.add(new Card(12, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 1 should win with a four of a kind ",true ,test.rankHands(playerList,tableCards).contains(player));
  }

  //Checks to see if it properly ranks a four of a kind
  @Test
  public void test_checkFourOfAKind_recognizesFourOfAKind() {
    //Setup
    player.setHand(new Card(9,"D"), new Card(7, "H"));
    tableCards.add(new Card(9, "C"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(9, "D"));
    tableCards.add(new Card(4, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);

    //Execute and verify
    RankHands test = new RankHands();
    test.rankHands(playerList,tableCards);
    assertEquals("Does not recognize four of a kind",7.09 , player.getScoreThisRound(),0.001);

  }

  //Four of a kind for player 2 is greater than four of a kind for player 1, so player 2 should win
  @Test
  public void test_checkFourOfAKind_fourOfAKindVsFourOfAKind() {
    //setup
    player.setHand(new Card(5,"C"), new Card(5, "C"));
    player2.setHand(new Card(12,"C"), new Card(12,"D"));
    tableCards.add(new Card(12, "C"));
    tableCards.add(new Card(12, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(3, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 2 should win with the higher for of a kind",true ,test.rankHands(playerList,tableCards).contains(player2));
  }

  //Straight flush is greater than four of a kind, so player 2 should win
  @Test
  public void test_checkFourOfAKind_fourOfAKindVsStraightFlush() {
    //setup
    player.setHand(new Card(2,"D"), new Card(3, "D"));
    player2.setHand(new Card(9,"C"), new Card(9,"S"));
    tableCards.add(new Card(9, "C"));
    tableCards.add(new Card(9, "H"));
    tableCards.add(new Card(6, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(4, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);
    playerList.add(player2);

    //Execute and verify
    RankHands test = new RankHands();
    assertEquals("Player 2 should win with the straight flush",true ,test.rankHands(playerList,tableCards).contains(player2));
  }
/*
  //Checks to see if it properly ranks royal flush (5 consecutive cards, same suits), which should be
  //9 + the highest card value / 100.0
  @Test
  public void test_checkStraightFlush_recognizesStraightFlush() {
    //Setup
    player.setHand(new Card(2,"D"), new Card(3, "D"));
    tableCards.add(new Card(9, "C"));
    tableCards.add(new Card(9, "H"));
    tableCards.add(new Card(6, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(4, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);

    //Execute and verify
    RankHands test = new RankHands();
    test.rankHands(playerList,tableCards);
    assertEquals("Does not recognize high card",8.09 , player.getScoreThisRound(),0.001);
  }

  //Checks to see if it properly ranks royal flush (Ten, Jack, Queen, King, Ace), which should be
  //10 + the highest card value / 100.0
  @Test
  public void test_checkRoyalFlush_recognizesRoyalFlush() {
    //Setup
    player.setHand(new Card(10,"D"), new Card(11, "D"));
    tableCards.add(new Card(12, "D"));
    tableCards.add(new Card(13, "D"));
    tableCards.add(new Card(1, "D"));
    tableCards.add(new Card(5, "D"));
    tableCards.add(new Card(4, "D"));

    ArrayList<Player> playerList = new ArrayList();
    playerList.add(player);

    //Execute and verify
    RankHands test = new RankHands();
    test.rankHands(playerList,tableCards);
    assertEquals("Does not recognize high card",9.14 , player.getScoreThisRound(),0.001);
  }

*/
}
