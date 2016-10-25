package pontoon.speakers;

import pontoon.Hand;
import pontoon.GameResultType;

public interface SpeakingStrategy {

    String toMessage(Hand playerHand);

    String toMessage(GameResultType gameResult, Hand playerHand);

    String toMessage(GameResultType gameResult,
            Hand playerHand, Hand dealerHand);
}
