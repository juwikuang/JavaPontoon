/*
A general client, 
talks with a server without any knowledge of business.
 */
package humanPlayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import pontoon.config.config;

public class GeneralHumanPlayer {
    public static void main(String args[]) throws IOException {
        String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir:"+current);
        System.out.println("Current Server:"+config.getServerName());
        System.out.println("Current Port:"+config.getPort());
        InetAddress address =InetAddress.getByName(config.getServerName());
        Socket s1 = null;
        String line;
        BufferedReader br = null;
        BufferedReader is = null;
        PrintWriter os = null;
        try {
            s1 = new Socket(address, config.getPort());
            br = new BufferedReader(
                    new InputStreamReader(System.in));
            is = new BufferedReader(
                    new InputStreamReader(s1.getInputStream()));
            os = new PrintWriter(s1.getOutputStream());
            System.out.println("Client Address : " + address);
            System.out.println("Enter start to start a game,");
            System.out.println("or QUIT to disconnect.");
            String response = null;
            line = br.readLine();
            while (line.compareTo("QUIT") != 0) {
                os.println(line);
                os.flush();
                response = is.readLine();
                response=response.replaceAll("\\x7c\\x7c", "\r\n");
                System.out.println("Server Response : " + response);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Socket read Error");
        } finally {
            is.close();
            os.close();
            br.close();
            s1.close();
            System.out.println("Connection Closed");
        }
    }
}
