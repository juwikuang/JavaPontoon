
package pontoon.multiplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pontoon.Hand;

/**
 *
 * @author Eric
 */
public class ClientPlayerInfo {

    BufferedReader socketInput = null;
    PrintWriter socketOutput = null;
    String playerName;
    Socket socket;
    Hand hand = new Hand();
    double marks = 0;

    boolean started = false;
    boolean sticked;

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param playerName the playerName to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ClientPlayerInfo(Socket socket) {
        try {
            this.socket = socket;
            socketInput = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            socketOutput = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(ClientPlayerInfo.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
    }

    public String receiveMessage() {
        try {
            return this.socketInput.readLine();
        } catch (IOException ex) {
            Logger.getLogger(ClientPlayerInfo.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return "";
    }

    public void sendMessage(String message) {
        this.socketOutput.println(message);
        this.socketOutput.flush();
    }
}
