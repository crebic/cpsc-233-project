package game;

import java.util.ArrayList;
import java.util.Random;

//AI for Poker game
/* Version 1 April 10 2019
* @Author T09 G1
*/
public class AI {

    /*
    * @Return int - the amount the AI will bet 
    * @Param amountToCall - the amount the bot has to call if another player raised
    * @Param self - the AI player cards
    * @Param playerList - an arrayList containing all players in the current game
    * @Param tableCards -the current cards on the board 
    * The method determines the amount the AI will bet depending on the condition of the round 
    */
    public static int bet(int amountToCall, Player self, ArrayList<Player> playerList, ArrayList<Card> tableCards) {
        Random rnd = new Random();
        double thoughtOutcome = rnd.nextDouble();
        int chips = self.getChipCount();
        int betSize = 0;
        //Checks for winner with the current cards 
        ArrayList<Player> winnerList = RankHands.rankHands(playerList, tableCards);
        
        //Runs if the AI is not folded
        if (!self.isFolded) {
            
            //If the AI is the winner 
            if (winnerList.contains(self)) {
             
                //Strategies for winning
                if (thoughtOutcome > 0.96 && self.getPotInvestment() < (0.3 * chips)) {
                    betSize = 0;
                } else if (thoughtOutcome > 0.35) {
                    if (amountToCall < (chips / 5))
                        betSize = (int) (amountToCall * (1 + rnd.nextDouble()));
                    else
                        betSize = amountToCall;

                } else {
                    if (amountToCall > (chips / 4)) {
                        betSize = amountToCall;
                    } else if (amountToCall > 0) {
                        betSize = (int) (amountToCall * (1 + rnd.nextDouble()));
                    } else {
                        betSize = (int) (chips * rnd.nextDouble() / 8);
                    }
                }
                
            } else {
                //strategy for losing
                if (thoughtOutcome > 0.33 && amountToCall < (chips / 6)) {
                    betSize = amountToCall;
                }
            }
            
            //Fold if the amount the AI wants to bet is less than what another player has raised by
            if (betSize < amountToCall) {
                betSize = 0;
                self.isFolded = true;
            }
            
            //Update AI player information
            self.setAmountBetThisRound(self.getAmountBetThisRound() + betSize);
            self.removeChips(betSize);
            self.setPotInvestment(self.getPotInvestment() + betSize);
        }
        
        return betSize;

    }

}
