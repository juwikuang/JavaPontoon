package pontoon.MultiPlayerGenerator;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import pontoon.config.config;
import pontoon.speakers.Speaker;

public class PlayerThread extends Thread {

    long threadId = 0;
    int threshold = 0;
    Speaker player;

    public PlayerThread(int threshold) {
        this.threshold = threshold;
        this.player = new PlayerSpeaker(threshold);
    }

    @Override
    public void run() {
        Socket socket = null;
        String line;
        BufferedReader socketInput = null;
        PrintWriter socketOutput = null;

        try {
            InetAddress address = InetAddress.getByName(config.getServerName());

            socket = new Socket(address, config.getPort());
            socketInput = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            socketOutput = new PrintWriter(socket.getOutputStream());
            System.out.println("Client Address : " + address);
            String response = null;
            line = player.reply("");
            while (!line.equalsIgnoreCase("QUIT")) {
                socketOutput.println(line);
                socketOutput.flush();
                response = socketInput.readLine();
                //response = response.replaceAll("\\x7c\\x7c", "\r\n");
                System.out.println("Server Response : " + response);
                line = player.reply(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket read Error");
        } catch (Exception ex) {
            Logger.getLogger(PlayerThread.class.getName()).log(Level.SEVERE,
                    null, ex);
        } finally {
            try {
                socketInput.close();
                socketOutput.close();
                socket.close();
                System.out.println("Connection Closed");
            } catch (IOException ex) {
                Logger.getLogger(PlayerThread.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }
}
