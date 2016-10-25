/*
Human readable messages.
 */
package pontoon.speakers;

import pontoon.GameResultType;
import pontoon.Hand;

public class PlainSpeakingStrategy implements SpeakingStrategy {

    String endingMessage = "||Type start to start/restart the game.||"
            + "Type quit to quit the game.";

    @Override
    public String toMessage(Hand playerHand) {
        return "Your hand is :||" + playerHand.toString()
                + "||Twist or Stick?T|S";

    }

    @Override
    public String toMessage(GameResultType gameResult, Hand playerHand) {
        String message = "";
        if (gameResult == GameResultType.Continue) {
            message += "Your hand is :||" + playerHand.toString()
                    + "||Twist or Stick?T|S";
        } else {
            message += "Game Result:" + gameResult.toString()
                    + "||Your hand is :||" + playerHand.toString();
            message += endingMessage;
        }

        return message;
    }

    @Override
    public String toMessage(GameResultType gameResult, Hand playerHand,
            Hand dealerHand) {
        String message = "";
        if (gameResult == GameResultType.Continue) {
            message += "Your hand is :||" + playerHand.toString()
                    + "||Twist or Stick?T|S";
        } else {
            message += "Game Result:" + gameResult.toString()
                    + "||Your hand is :||" + playerHand.toString()
                    + "||Dealer hand is:||" + dealerHand.toString();
            message += endingMessage;
        }

        return message;
    }

}
