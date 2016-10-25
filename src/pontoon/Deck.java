//100136054
package pontoon;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Deck {

    List<Card> cards = new ArrayList<>();

    public Deck(int repeat) {
        //from 1 to 13, 1 for ace, 11 for jack, 12 for queen and 13 for king
        for (int i = 0; i < 13; i++) {
            for (CardType cType : CardType.values()) {
                for (int j = 0; j < repeat; j++) {
                    cards.add(new Card(i, cType));
                }

            }
        }
    }

    public Card deal() {
        Random rn = new Random();
        int randomIndex = rn.nextInt(cards.size());
        Card dealingCard = cards.get(randomIndex);
        cards.remove(randomIndex);
        return dealingCard;
    }
}
