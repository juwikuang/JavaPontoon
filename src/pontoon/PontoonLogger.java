package pontoon;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PontoonLogger {

    static private FileHandler txtHandler;
    static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    static boolean initailsed;

    public static void initailse() throws Exception {
        if (!initailsed) {
            txtHandler = new FileHandler("Logging.txt");
        }

        initailsed = true;
    }

    public static void logAndPrintOut(String message) throws Exception {
        initailse();
        System.out.println(message);
        LOGGER.log(Level.INFO, message);
    }

    public static void logAndPrintOut(Level level, String message) throws
            Exception {
        initailse();
        System.out.println(message);
        LOGGER.log(level, message);
    }

    public static void logGameResult(GameResultType gameResult, Hand playerHand,
            Hand dealerHand) {
        File theDir = new File("logs");
        theDir.mkdir();
        
        FileWriter fw = null;
        try {
            Random rn = new Random();
            int rNumber = rn.nextInt(10000);
            String dateString = new SimpleDateFormat("yyyyMMdd_HHmmss").format(
                    new Date());
            String fileName = "logs/gameResult_" + dateString + rNumber + ".txt";
            fw = new FileWriter(fileName);
            fw.write(gameResult.toString()+"\r\n");
            fw.write("User Hand:\r\n");
            fw.write(playerHand.toString()+"\r\n");
            fw.write("Dealer Hand:\r\n");
            fw.write(dealerHand.toString()+"\r\n");
            fw.flush();
            //fw.close();
        } catch (IOException ex) {
            Logger.getLogger(PontoonLogger.class.getName()).
                    log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(PontoonLogger.class.getName()).
                        log(Level.SEVERE, null, ex);
            }
        }
    }
}
