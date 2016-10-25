package pontoon;

/**
 *
 * @author Eric
 */
public class PontoonJsonParser {

    public static GameResultType ToGameResult(String json) throws Exception {
        String temp = json;
        temp = temp.replace("{", "");
        temp = temp.replace("}", "");
        String[] pairs = temp.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0];
            String value = keyValue[1];
            if (key.equalsIgnoreCase("gameResult")) {
                return GameResultType.valueOf(value);
            }
        }
        throw new Exception("Parse GameResultType failed.");

    }

    public static Hand ToHand(String json) {
        Hand myHand = new Hand();
        String temp = json;
        temp = temp.replace("{", "");
        temp = temp.replace("}", "");
        String[] pairs = temp.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            String key = keyValue[0];
            String value = keyValue[1];
            if (key.startsWith("card") && value.contains(" of ")) {
                String[] cardInfo = value.split(" of ");
                String letter = cardInfo[0];
                String color = cardInfo[1];
                Card aCard = new Card(letter, color);
                myHand.twist(aCard);
            }
        }
        return myHand;
    }
}
