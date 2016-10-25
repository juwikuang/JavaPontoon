package pontoon.server;

import pontoon.speakers.DualDealerSpeaker;
import pontoon.speakers.MeleeDealerSpeaker;
import pontoon.speakers.Speaker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

    String line = null;
    BufferedReader is = null;
    PrintWriter os = null;
    Socket s = null;
    long threadId=0;

    public ServerThread(Socket s) {
        this.s = s;
    }

    public void run() {
        threadId = Thread.currentThread().getId();
        System.out.print(s.getInetAddress());
        try {
            is = new BufferedReader(new InputStreamReader(s.getInputStream()));
            os = new PrintWriter(s.getOutputStream());

        } catch (IOException e) {
            System.out.println("IO error in server thread");
        }

        try {
            //First message, game type(dealer) select
            Speaker dealer = null;
            line = is.readLine();
            line=line.toUpperCase();
            switch(line)
            {
                case "DUAL":
                    dealer = new DualDealerSpeaker();
                    os.println("starting a dual game...");
                    break;
                case "MELEE":
                    dealer = new MeleeDealerSpeaker();
                    os.println("Starting a melee game...");
                    break;
                default:
                    dealer = new DualDealerSpeaker();
                    os.println("Dont understand, dual game created.");
                    break;
            }
            os.flush();

            //hand it to the dealer.
            line = is.readLine();
            while (line.compareTo("QUIT") != 0) {
                System.out.println("Client said:"+line);
                String reply = dealer.reply(line);
                os.println(reply);
                os.flush();
                System.out.println("Response to Client  :  " + reply);
                line = is.readLine();
            }
        } catch (IOException e) {
            line = this.getName(); //reused String line for getting thread name
            System.out.println("IO Error/ Client " + line + " terminated abruptly");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            line = this.getName(); //reused String line for getting thread name
            System.out.println("Client " + line + " Closed");
        } catch (Exception ex) {
            ex.printStackTrace();
            //Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Client " + line + " Closed");
        } finally {
            try {
                System.out.println("Connection Closing..");
                if (is != null) {
                    is.close();
                    System.out.println(" Socket Input Stream Closed");
                }

                if (os != null) {
                    os.close();
                    System.out.println("Socket Out Closed");
                }
                if (s != null) {
                    s.close();
                    System.out.println("Socket Closed");
                }

            } catch (IOException ie) {
                System.out.println("Socket Close Error");
            }
        }
    }
}
