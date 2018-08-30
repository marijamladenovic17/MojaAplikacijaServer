/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import forme.FormaServer;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class PokreniServer extends Thread{
    FormaServer fs;

    public PokreniServer(FormaServer fs) {
        this.fs = fs;
    }

    

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9000);
            System.out.println("Povezan!");
            while (true) {                
                Socket soket = ss.accept();
                System.out.println("Povezan klijent!");
                ObradiZahtev oz = new ObradiZahtev(soket);
                oz.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    
   
}
