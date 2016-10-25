
package machineDuelPlayer;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import pontoon.GameResultType;
import pontoon.Hand;
import pontoon.config.config;

/**
 *
 * @author Eric
 */
public class MachineDuelPlayer {

    //args 0: melee/dual
    //args 1: player count
    //args 2: player threshold, splited by comma
    public static void main(String args[]) throws Exception {
        //InetAddress address = InetAddress.getLocalHost();
        InetAddress address = InetAddress.getByName(config.getServerName());
        Socket s1 = null;
        String line;
        BufferedReader is = null;
        PrintWriter os = null;
        int threshold = 11;
        //int runtimes=10;
        int eachtimes = 1000;
        int current_round = 0;
        FileWriter writer = new FileWriter("result.csv");
        writer.append("win,threshold,hand\r\n");
        try {
            s1 = new Socket(address, config.getPort());

            is = new BufferedReader(
                    new InputStreamReader(s1.getInputStream()));
            os = new PrintWriter(s1.getOutputStream());
            System.out.println("Client Address : " + address);
            System.out.println("Enter start to start a game,");
            System.out.println("or QUIT to disconnect.");
            String server_message = null;
            //line = br.readLine();
            //First message, tell the sever i am machine
            os.println("DUAL");
            os.flush();
            server_message = is.readLine();
            System.out.println("Server Response : " + server_message);
            //Second message, tell the sever i am machine
            os.println("JSON");
            os.flush();
            server_message = is.readLine();
            System.out.println("Server Response : " + server_message);
            //3rd message, tell the server to start the game
            os.println("START");
            os.flush();
            server_message = is.readLine();
            System.out.println("Server Response : " + server_message);
            while (server_message.compareTo("QUIT") != 0) {
                if (server_message.startsWith("{")
                        && server_message.endsWith("}")) {
                    //analyse
                    Hand myHand = pontoon.PontoonJsonParser.ToHand(
                            server_message);
                    GameResultType gameResult
                            = pontoon.PontoonJsonParser.ToGameResult(
                                    server_message);
                    if (gameResult != GameResultType.Continue) {
                        int win = 0;
                        if (gameResult == GameResultType.Win || gameResult
                                == GameResultType.DealerBust) {
                            win = 1;
                        }
                        writer.append(win + "," + threshold + ",\"" + myHand.
                                toString() + "\"\r\n");
                        current_round++;
                        if (current_round >= eachtimes) {
                            current_round = 0;
                            threshold++;
                        }

                        if (threshold >= 21) {
                            os.println("QUIT");
                        } else {
                            os.println("START");
                        }

                    } else if (myHand.getValidMax() > threshold) {
                        os.println("S");
                    } else {
                        os.println("T");
                    }
                } else {
                    os.println("QUIT");
                }

                os.flush();
                server_message = is.readLine();
                //server_message=server_message.replaceAll("\\x7c\\x7c", "\r\n");
                System.out.println("Server Response : " + server_message);
                if (server_message == null) {
                    break;
                }
                //line = br.readLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket read Error");
        } finally {
            is.close();
            os.close();
            s1.close();
            System.out.println("Connection Closed");
        }
    }
}

/*
Code used in r to generate histgram.
######################################




 */
