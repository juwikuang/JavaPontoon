/*
generates players
 */
package pontoon.MultiPlayerGenerator;

/**
 *
 * @author Eric
 */
public class PlayerGenerator {

    public static void main(String args[]) throws Exception {
        for(int i=11 ;i<21;i++)
        {
            PlayerThread pt=new PlayerThread(i);
            pt.start();
        }
    }
}
