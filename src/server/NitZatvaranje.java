/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class NitZatvaranje extends Thread{
    ServerSocket ss;
    PokreniServer ps;
    boolean kraj = false;

    public NitZatvaranje(ServerSocket ss, PokreniServer ps) {
        this.ss = ss;
        this.ps = ps;
    }

    @Override
    public void run() {
        while(!kraj){
            if(ps.isInterrupted()){
                try {
                    ss.close();
                    kraj = true;
                } catch (IOException ex) {
                    Logger.getLogger(NitZatvaranje.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(NitZatvaranje.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
