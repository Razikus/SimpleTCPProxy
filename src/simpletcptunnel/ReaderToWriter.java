/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simpletcptunnel;

import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adam
 */
public class ReaderToWriter implements Runnable{
    private Socket from;
    private Socket to;

    public ReaderToWriter(Socket from, Socket to) {
        this.from = from;
        this.to = to;
    }
    
    public boolean isFromConnected() {
        return from.isConnected() && !from.isClosed() && from.isBound() && !from.isInputShutdown();
    }
    
    public boolean isToConnected() {
        return !to.isOutputShutdown() && to.isConnected() && to.isBound();
    }

    @Override
    public void run() {
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        int timeout = 600;
        int process = 0;
        while(isFromConnected() && isToConnected()) {
            try{
                while(( bytesRead = from.getInputStream().read( buffer ) ) >= 0 ) {
                    to.getOutputStream().write(buffer, 0, bytesRead);
                    process = 0;
                }
                if(bytesRead == -1) {
                    break;
                }
            } catch(Exception e) {
                break;
            }
            try {
                Thread.sleep(100);
                process++;
            } catch (InterruptedException ex) {
                Logger.getLogger(ReaderToWriter.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(process > timeout) {
                break;
            }
        }
    }
    
    
}
