/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletcptunnel;


/**
 *
 * @author adam
 */
public class SimpleTCPTunnel {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        String inetAddress = "0.0.0.0";
        int port = 8000;
        
        String inetAddressPoint = "localhost";
        int portPoint = 5000;
        
        if(args.length >= 2) {
            inetAddress = args[0];
            port = Integer.valueOf(args[1]);
        }
        
        if(args.length >= 4) {
            inetAddressPoint = args[2];
            portPoint = Integer.valueOf(args[3]);
        }
        
        Server s = new Server(inetAddress, port, inetAddressPoint, portPoint);
        s.start();
        
    }
    
}
