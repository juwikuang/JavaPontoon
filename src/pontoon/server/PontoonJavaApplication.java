//Run this with
//java -jar PontoonJavaApplication.jar
//100136054
package pontoon.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import pontoon.PontoonLogger;
import pontoon.config.config;

public class PontoonJavaApplication {

    public static void main(String[] args) throws Exception {
        //static txtHandler = new FileHandler("Logging.txt");
        //static LOGGER.addHandler(txtHandler);

        Socket s = null;
        ServerSocket ss2 = null;
        PontoonLogger.logAndPrintOut("Server Listening......");
        try {
            ss2 = new ServerSocket(config.getPort());
            PontoonLogger.logAndPrintOut("My port is "+config.getPort());
        } catch (IOException e) {
            e.printStackTrace();
            PontoonLogger.logAndPrintOut("Server error");
        }

        while (true) {
            try {
                s = ss2.accept();
                PontoonLogger.logAndPrintOut("connection Established");
                ServerThread st = new ServerThread(s);
                st.start();

            } catch (Exception e) {
                PontoonLogger.logAndPrintOut(Level.SEVERE, "Connection Error");
            }
        }
    }
}
