/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletcptunnel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adam
 */
public class Server implements Runnable {
    Thread me;
    private boolean start = false;
    ServerSocket serverSocket;
    
    private String inetAddress;
    private int port;
    private static String inetAddressPoint;
    private static int portPoint;

    public Server(String inetAddress, int port, String inetAddressPoint, int portPoint) {
        this.inetAddress = inetAddress;
        this.port = port;
        Server.inetAddressPoint = inetAddressPoint;
        Server.portPoint = portPoint;
    }

    public String getInetAddress() {
        return inetAddress;
    }

    public int getPort() {
        return port;
    }

    public static String getInetAddressPoint() {
        return inetAddressPoint;
    }

    public static int getPortPoint() {
        return portPoint;
    }

    public void start() {
        me = new Thread(this);
        start = true;
        me.start();
    }

    @Override
    public void run() {
        this.start = true;
        try {
            this.serverSocket = new ServerSocket(this.port, 50, InetAddress.getByName(inetAddress));
            while(true) {
                Socket s = this.serverSocket.accept();
                System.out.println("Connection accepted.");
                new Thread(new ConnectionConsumer(s)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
