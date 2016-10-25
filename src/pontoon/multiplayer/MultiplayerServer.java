
package pontoon.multiplayer;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import pontoon.Deck;
import pontoon.GameResultType;
import pontoon.Hand;
import pontoon.PontoonLogger;
import pontoon.config.config;
import pontoon.speakers.JsonSpeakingStrategy;
import pontoon.speakers.SpeakingStrategy;

/**
 *
 * @author Eric
 */
public class MultiplayerServer {

    static SpeakingStrategy speakingWay = new JsonSpeakingStrategy();
    static List<ClientPlayerInfo> clist = new ArrayList<>();
    static Deck deck;
    static int times = 10000;
    static int player_count = 10;

    public static void main(String[] args) throws Exception {
        //static txtHandler = new FileHandler("Logging.txt");
        //static LOGGER.addHandler(txtHandler);
        PontoonLogger.logAndPrintOut("Multi player game.");
        //List<Socket> slist=new ArrayList<>();

        ServerSocket serverSocket = null;
        PontoonLogger.logAndPrintOut("Server Listening......");
        try {
            serverSocket = new ServerSocket(config.getPort());

        } catch (IOException e) {
            e.printStackTrace();
            PontoonLogger.logAndPrintOut("Server error");
        }

        while (true) {
            try {
                Socket s = serverSocket.accept();
                clist.add(new ClientPlayerInfo(s));
                int scount = clist.size();
                log(scount + "connections Established");
                if (scount >= player_count) {
                    break;
                }
            } catch (Exception e) {
                errorlog ("Connection Error");
            }
        }
        PontoonLogger.logAndPrintOut(Level.SEVERE, "waiting players");
        //wait every player for starting
        //let started player wait.
        String line;
        int start_count = 0;
        while (true) {
            try {
                for (ClientPlayerInfo client : clist) {
                    if (client.started) {
                        continue;
                    }
                    line = client.receiveMessage();
                    if (line.equalsIgnoreCase("start")) {
                        client.started = true;
                        start_count++;
                    } else {
                        client.sendMessage(line);
                        if (line.startsWith("My name is:")) {
                            client.playerName = line.replace("My name is:", "");
                            log("welcome " + client.playerName);
                        }
                    }
                }
                if (start_count >= player_count) {
                    break;
                }
            } catch (Exception e) {
                PontoonLogger.logAndPrintOut(Level.SEVERE, "Connection Error");
            }
            if (start_count >= player_count) {
                break;
            }
        }

        PontoonLogger.logAndPrintOut(Level.SEVERE, "Game Starts");

        for (int i = 0; i < times; i++) {
            startGame();
            continueGame();
            endGame();
            for (ClientPlayerInfo client : clist) {
                line = client.receiveMessage();//Receive start messge;
                if (line.equalsIgnoreCase("start")) {

                }
            }
        }

        for (ClientPlayerInfo client : clist) {
            client.sendMessage("QUIT");
        }

        for (ClientPlayerInfo client : clist) {
            client.socketInput.close();
            client.socketOutput.close();
            client.socket.close();
        }

        serverSocket.close();

        FileWriter writer = new FileWriter("multiplayer_result.csv");
        writer.write("threshold,marks\r\n");
        for (ClientPlayerInfo client : clist) {
            writer.write(client.playerName + "," + client.marks + "\r\n");
        }
        writer.flush();
        writer.close();
    }

    //game ends, inform players result.
    private static void startGame() throws Exception  {
        //deals out the first two cards to every player.
        log("reset deck");
        deck = new Deck(2);
        for (ClientPlayerInfo client : clist) {
            client.hand = new Hand();
            client.sticked = false;
            client.hand.start(deck.deal(), deck.deal());

            client.sendMessage(speakingWay.toMessage(GameResultType.Continue,
                    client.hand));
        }
    }

    private static void continueGame() {
        int stick_count = 0;
        String line;
        while (true) {
            if (stick_count == 10) {
                break;
            }
            for (ClientPlayerInfo client : clist) {
                if (stick_count == 10) {
                    break;
                }
                if (!client.sticked) {
                    line = client.receiveMessage();
                    if (line.equalsIgnoreCase("t")) {
                        if (client.hand.getMinMark() < 21) {
                            client.hand.twist(deck.deal());
                            client.sendMessage(speakingWay.toMessage(
                                    GameResultType.Continue, client.hand));
                        } else {
                            client.sticked = true;
                            stick_count++;
                        }
                    } else {
                        client.sticked = true;
                        stick_count++;
                    }
                }
            }
        }
    }

    private static void endGame()  throws Exception  {
        Hand bestHand = new Hand();
        for (ClientPlayerInfo client : clist) {
            if (client.hand.compareTo(bestHand) > 0) {
                bestHand = client.hand;
            }
        }

        List<String> winers = new ArrayList<>();
        for (ClientPlayerInfo client : clist) {
            if (client.hand.compareTo(bestHand) == 0) {
                winers.add(client.playerName);
                log(client.playerName + "won!!!");
            }
        }

        int winer_count = winers.size();
        for (ClientPlayerInfo client : clist) {
            if (client.hand.compareTo(bestHand) == 0) {
                client.marks += 1.0 / winer_count;
            }
        }

        //tell players the result
        for (ClientPlayerInfo client : clist) {
            if (client.hand.compareTo(bestHand) == 0) {
                client.sendMessage(speakingWay.toMessage(GameResultType.Win,
                        bestHand));
            } else {
                client.sendMessage(speakingWay.toMessage(GameResultType.Loose,
                        bestHand));
            }
        }
    }

    static void log(String message) throws Exception {
        PontoonLogger.logAndPrintOut(Level.INFO, message);
    }

    static void errorlog(String message) throws Exception  {
        PontoonLogger.logAndPrintOut(Level.SEVERE, message);
    }
}

/*
setwd("F:/DC/PontoonJavaApplication")
marks=read.csv("multiplayer_result.csv", header = T)
plot(marks$threshold, marks$marks, type = "p", main = "Threshold and winning", 
xlab = "Threshold", ylab = "Winning Times")
*/
