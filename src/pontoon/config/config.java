/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pontoon.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nzt15bau
 */
public class config {
    
    static private String serverName="127.0.0.1";
    static private int port=55551;
   private static boolean loaded;
    private static void loadConfig() throws FileNotFoundException
    {
        if(loaded)return;
        
        try {
            if(!new File("config.txt").exists())return;
            
            BufferedReader fr=new BufferedReader(new FileReader("config.txt"));
            serverName= fr.readLine();
            port=Integer.parseInt(fr.readLine());
        } catch (IOException ex) {
            Logger.getLogger(config.class.getName()).log(Level.SEVERE, null, ex);
        }
        loaded=true;
    }
    
    public static String getServerName() throws FileNotFoundException
    {
        loadConfig();
        return serverName;
    }
            
    public static int getPort() throws FileNotFoundException{
        loadConfig();
        return port;
    }
}

