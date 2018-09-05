/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class ReaderProperty {

    Properties property;

    public ReaderProperty() throws FileNotFoundException, IOException {
        property = new Properties();
        FileInputStream fis = new FileInputStream("db.properties");
        property.load(fis);
    }

    public String vratiVrednost(String key) {
        return property.getProperty(key);
    }

    public void upisiVrednost(String key, String vrednost) {

        FileOutputStream fos;
        try {
            fos = new FileOutputStream("db.properties");
            property.setProperty(key, vrednost);
            property.store(fos, vrednost);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReaderProperty.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReaderProperty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
