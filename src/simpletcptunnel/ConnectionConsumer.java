/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletcptunnel;

import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author adam
 */
public class ConnectionConsumer implements Runnable{  
    private Socket socket;

    public ConnectionConsumer(Socket socket) {
        this.socket = socket;
    }
    
    
    public void run() {
        try {
            Socket processing = socket;
            InetAddress serverAddress = InetAddress.getByName(Server.getInetAddressPoint());
            int serverPort = Server.getPortPoint();
            Socket tunnelConnection = new Socket(serverAddress, serverPort);
            if(tunnelConnection.isConnected()) {
                Thread a1 = new Thread(new ReaderToWriter(socket, tunnelConnection));
                Thread a2 = new Thread(new ReaderToWriter(tunnelConnection, socket));
                a1.start();
                a2.start();
                a1.join();
                a2.join();
            }
            tunnelConnection.close();
            processing.close();
            System.out.println("CLOSED");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
