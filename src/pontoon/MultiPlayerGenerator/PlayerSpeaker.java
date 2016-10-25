package pontoon.MultiPlayerGenerator;

import pontoon.GameResultType;
import pontoon.Hand;
import pontoon.speakers.Speaker;

public class PlayerSpeaker implements Speaker {

    int sentenceIndex = 0;

    int threshold = 15;

    public PlayerSpeaker(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public String reply(String server_message) throws Exception {
        sentenceIndex++;
        if (server_message.equalsIgnoreCase("quit")) {
            return "quit";
        }

        if (sentenceIndex == 0) {
            return "Melee";
        }
        if (sentenceIndex == 1) {
            return "Json";
        }
        if (sentenceIndex == 2) {
            return "My name is:" + threshold;
        }
        if (sentenceIndex == 3) {
            return "Start";
        }
        if (server_message.startsWith("{") && server_message.endsWith("}")) {
            //analyse
            Hand myHand = pontoon.PontoonJsonParser.ToHand(server_message);
            GameResultType gameResult
                    = pontoon.PontoonJsonParser.ToGameResult(server_message);
            if (gameResult != GameResultType.Continue) {
                int win = 0;
                if (gameResult == GameResultType.Win
                        || gameResult == GameResultType.DealerBust) {
                    win = 1;
                }
                return "Start";
            } else if (myHand.getValidMax() > threshold) {
                return "S";
            } else {
                return "T";
            }
        } else {
            return "START";
        }
    }
}
