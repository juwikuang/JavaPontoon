/*
JSON messages.
 */
package pontoon.speakers;

import pontoon.GameResultType;
import pontoon.Hand;

public class JsonSpeakingStrategy implements SpeakingStrategy {

    @Override
    public String toMessage(GameResultType gameResult, Hand playerHand) {
        String json = "{gameResult:" + gameResult + ";";
        for (int i = 0; i < playerHand.getCards().size(); i++) {
            json += "card" + (i + 1) + ":" + playerHand.getCards().get(i).
                    toString() + ";";
        }
        json += "validMax:" + playerHand.getValidMax() + ";";
        json += "max:" + playerHand.getMaxMark() + ";";
        json += "min:" + playerHand.getMinMark() + ";";
        json += "}";
        return json;
    }

    @Override
    public String toMessage(Hand playerHand) {
        return toMessage(GameResultType.Continue, playerHand);
    }

    @Override
    public String toMessage(GameResultType gameResult, Hand playerHand,
            Hand dealerHand) {
        return toMessage(GameResultType.Continue, playerHand);
    }
}
