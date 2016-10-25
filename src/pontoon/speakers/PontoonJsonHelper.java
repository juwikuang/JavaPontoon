
package pontoon.speakers;

import java.util.Map;
import pontoon.Hand;
import pontoon.GameResultType;

/**
 *
 * @author Eric
 */
public class PontoonJsonHelper {

    public static String toJson(Map<String, String> map) {
        String json = "{";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            json += "" + entry.getKey() + ":" + entry.getValue() + ";";
        }
        json += "}";
        return json;
    }

    /**
     * (encapsulate message in json)
     *
     * @param h the hand
     * @param gameResult one of the three values win, loose, continue
     * @return etc
     */
    public static String toJson(Hand h, GameResultType gameResult) {
        String json = "{gameResult:" + gameResult + ";";
        for (int i = 0; i < h.getCards().size(); i++) {
            json += "card" + (i + 1) + ":" + h.getCards().get(i).toString()
                    + ";";
        }
        json += "{validMax:" + h.getValidMax() + ";";
        json += "{min:" + h.getValidMax() + ";";
        json += "{max:" + h.getMaxMark() + ";";
        json += "{min:" + h.getMinMark() + ";";
        json += "}";
        return json;
    }
}
