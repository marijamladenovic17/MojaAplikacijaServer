/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import db.DBBroker;
import db.Konstanta;
import domen.Clan;
import domen.Drzevljanstvo;
import domen.GrupaZadatka;
import domen.Kandidat;
import domen.Karton;
import domen.Komisija;
import domen.Nacionalnost;
import domen.PomocIzmena;
import domen.Resenje;
import domen.Sluzbenik;
import domen.SrednjaSkola;
import domen.Test;
import domen.Zadatak;
import domen.ZanimanjeRoditelja;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import transfer.ServerskiOdgovor;

/**
 *
 * @author PC
 */
public class Kontroler {

    ArrayList<Sluzbenik> sluzbenici;
    DBBroker db;
    public static Kontroler instance;

    public Kontroler() {
        sluzbenici = new ArrayList<>();
        Sluzbenik s1 = new Sluzbenik("Marija", "maki", "maki");
        Sluzbenik s2 = new Sluzbenik("Joka", "joki", "joki");
        sluzbenici.add(s1);
        sluzbenici.add(s2);
        db = new DBBroker();
    }

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    public boolean proveriSluzbenika(String username, String password) {
        for (Sluzbenik sluzbenik : sluzbenici) {
            if (sluzbenik.getUsername().equals(username) && sluzbenik.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Komisija> vratiSveKomisije() {
        ArrayList<Komisija> komisije = new ArrayList<>();
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            komisije = db.vratiKomisije();
            db.zatvoriKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return komisije;
    }

    public boolean proveriKomisiju(String username, String password) {
        ArrayList<Komisija> komisije = new ArrayList<>();
        boolean daLiPostojiKom = false;
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            komisije = db.vratiKomisije();
            for (Komisija komisija : komisije) {
                if (komisija.getUsername().equals(username) && komisija.getPassword().equals(password)) {
                    daLiPostojiKom = true;
                }
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return daLiPostojiKom;
    }

    public List<Clan> vratiClanove() {
        List<Clan> listaClanova = new ArrayList<>();
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            listaClanova = db.vratiClanove();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaClanova;
    }

    public boolean ubaciKomisiju(Komisija novaKom) {
        boolean ubacen = false;
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            db.ubaciKomisiju(novaKom);
            for (Clan cl : novaKom.getListaClanova()) {
                db.izmeniClan(cl, novaKom);
            }
            ubacen = true;
            db.commit();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } finally {
            try {
                db.zatvoriKonekciju();

            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ubacen;
    }

    public boolean promeniKomisiju(Komisija novaKom) {
        boolean ubacen = false;
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            db.promeniKomisiju(novaKom);
            db.commit();
            ubacen = true;
        } catch (ClassNotFoundException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ubacen;
    }

    public ArrayList<Drzevljanstvo> vratiDrzevljanstvo() {
        ArrayList<Drzevljanstvo> listaDrzev = new ArrayList<>();
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            listaDrzev = db.vratiDrzevljanstvo();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaDrzev;
    }

    public ArrayList<ZanimanjeRoditelja> vratiSZ() {
        ArrayList<ZanimanjeRoditelja> listaZanimanja = new ArrayList<>();
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            listaZanimanja = db.vratiZanimanja();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaZanimanja;
    }

    public ArrayList<Nacionalnost> vratiNacionalnost() {
        ArrayList<Nacionalnost> listaNacionalnosti = new ArrayList<>();
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            listaNacionalnosti = db.vratiNacionalnost();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listaNacionalnosti;
    }

    public ArrayList<SrednjaSkola> vratiSS() {
        ArrayList<SrednjaSkola> listaSrednjeSkole = new ArrayList<>();
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            listaSrednjeSkole = db.vratiSrednjuSkolu();

            db.zatvoriKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaSrednjeSkole;
    }

    public boolean sacuvajKandidata(Kandidat kandidat) {
        boolean ubacen = false;
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            db.sacuvajKandidata(kandidat);
            db.commit();
            ubacen = true;
        } catch (ClassNotFoundException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ubacen;
    }

    public ArrayList<Test> vratiTestove() {
        ArrayList<Test> listaTest = new ArrayList<>();
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            listaTest = db.vratiTest();

            db.zatvoriKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaTest;
    }

    public boolean sacuvajGrupeZadataka(GrupaZadatka gz) {
        boolean ubacen = false;
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            db.sacuvajGZ(gz);
            ArrayList<Resenje> listr = (ArrayList<Resenje>) gz.getListaResenihZadataka();
            for (Resenje resenje : listr) {
                db.sacuvajResenje(resenje, gz);
            }
            db.commit();
            ubacen = true;
        } catch (ClassNotFoundException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ubacen;
    }

    public int vratiKartonID() {
        int br = 0;
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            br = db.vratiKartonID();

            db.zatvoriKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return br;
    }

    public ArrayList<GrupaZadatka> vratiGrupuZadataka() {
        ArrayList<GrupaZadatka> listag = new ArrayList<>();
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            listag = db.vratiGZ();

            db.zatvoriKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return listag;
    }

    public Kandidat proveriKarton(String brojPrijave) {
        Kandidat kan = new Kandidat();
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            kan = db.vratiKandidata(brojPrijave);

            db.zatvoriKonekciju();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kan;

    }

    public boolean unesiKarton(Karton karton) {
        boolean ubacen = false;
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            db.sacuvajKarton(karton);
            ArrayList<Zadatak> lz = karton.getListaOdg();
            for (Zadatak zadatak : lz) {
                db.sacuvajODG(zadatak, karton);
            }
            db.commit();
            ubacen = true;
        } catch (ClassNotFoundException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }

            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ubacen;
    }

//    public Karton nadjiKarton(int sifra) {
//        return null;
//    }
    public int vratiMaxIdKomisija() {
        int br = 0;
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            br = db.vratiKomisijaID();

            db.zatvoriKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return br;
    }

    public int vratiMaxIdKartona() {
        int br = 0;
        try {

            db.ucitajDriver();
            db.otvoriKonekciju();
            br = db.vratiKartonID();

            db.zatvoriKonekciju();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return br;
    }

    public Karton vratiKarton(int kartBroj) {
        Karton kart = null;

        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            kart = db.vratiKarton(kartBroj);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return kart;
    }

    public Karton vratiKartonUnosJedan(int kartBroj) {
        Karton kart = null;

        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            kart = db.vratiKartonUnosPrvi(kartBroj);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return kart;
    }

    public Kandidat vratiKandidata(String kanBroj) {
        Kandidat kan = null;

        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            kan = db.vratiKandidata(kanBroj);
        } catch (ClassNotFoundException ex) {
            kan = null;
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            kan = null;
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return kan;
    }

    public boolean promeniKarton(Karton kaa) {
        boolean s = false;
        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            db.izmeniKarton(kaa);
            db.commit();
            s = true;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return s;

    }

    public boolean izmeniZadatke(PomocIzmena pi) {
        boolean izmenjen = false;

        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            ArrayList<Zadatak> zadaciZaIzmenu = pi.getZadaciZaIzmenu();
            for (Zadatak zadatak : zadaciZaIzmenu) {
                db.izmeniZadatak(zadatak, pi.getKratonId());
            }
            db.commit();
            izmenjen = true;

        } catch (ClassNotFoundException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            try {
                db.rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return izmenjen;
    }

    public ServerskiOdgovor spojKartone() {
        ServerskiOdgovor so = new ServerskiOdgovor();
        String loseUneseni = "";
        boolean imaLosih = false;

        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            ArrayList<Karton> kartoni = db.vratiKartone();
            int ukupanBrojKartona = kartoni.size();
            for (int i = 0; i < ukupanBrojKartona; i++) {
                Karton kart1 = kartoni.get(i);
                for (int j = i + 1; j < ukupanBrojKartona; j++) {
                    Karton kart2 = kartoni.get(j);
                    if (kart1.getBrKartona() == kart2.getBrKartona()) {
                        int brojOdgovora = kart1.getListaOdg().size();
                        ArrayList<Zadatak> odgovori1 = kart1.getListaOdg();
                        ArrayList<Zadatak> odgovori2 = kart2.getListaOdg();
                        for (int m = 0; m < brojOdgovora; m++) {
                            if (odgovori1.get(m).getOdgovor() != odgovori2.get(m).getOdgovor()) {
                                loseUneseni = loseUneseni + kart1.getBrKartona() + " ";
                                imaLosih = true;
                                break;
                            }
                        }
                        break;
                    }
                }
            }

            if (imaLosih) {
                so.setPoruka("Spajanje kartona zavrseno. Lose uneseni kartoni: " + loseUneseni);
            } else {
                so.setPoruka("Spajanje kartona zavrseno. Nema lose unesenih. ");
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return so;
    }

    public ArrayList<Karton> vratiKartoneYaProveru(int kartonskiBroj) {
        ArrayList<Karton> kartoni = null;

        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            kartoni = db.vratiKartoneProvera(kartonskiBroj);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return kartoni;
    }

    public ServerskiOdgovor izracunajPoene() {
        ServerskiOdgovor so = new ServerskiOdgovor();
        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            ArrayList<Karton> kartoni = db.vratiKartoneZaPoene();
            ArrayList<Karton> prviUnos = new ArrayList<>();
            for (Karton karton : kartoni) {
                if (karton.getBrUnosa() == 1) {
                    prviUnos.add(karton);
                }
            }

            for (Karton kart : prviUnos) {
                double suma = 0;
                int brojac = kart.getListaOdg().size();
                ArrayList<Resenje> resenja = (ArrayList<Resenje>) kart.getGrupaZadataka().getListaResenihZadataka();
                ArrayList<Zadatak> zadaci = kart.getListaOdg();
                for (int i = 0; i < brojac; i++) {
                    char res = resenja.get(i).getOdgovor();
                    char zad = zadaci.get(i).getOdgovor();
                    if (zad == res) {
                        suma += 5;
                    } else {
                        if (zad == 'N') {
                            suma += 0;
                        } else {
                            suma -= 3;
                        }
                    }
                }
                if (suma < 0) {
                    suma = 0;
                }

                db.upisiRezultat(kart.getKartonID(), suma);
            }
            db.commit();
            so.setPoruka("Uspesno izracunati poeni");

        } catch (ClassNotFoundException ex) {
            try {
                db.rollback();
                so.setPoruka("Doslo je do greske");
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            try {
                db.rollback();
                so.setPoruka("Doslo je do greske");
            } catch (SQLException ex1) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return so;
    }

    public void promeniUsername(String username) {
        db.izmeniProperty(Konstanta.USER, username);
    }

    public void promeniDriver(String driver) {
        db.izmeniProperty(Konstanta.DRIVER, driver);

    }

    public void promeniPass(String pass) {
        db.izmeniProperty(Konstanta.PASS, pass);
    }

    public void promeniUrl(String url) {
        db.izmeniProperty(Konstanta.URL, url);
    }

    public ArrayList<Karton> vratiSveKartone() {
        ArrayList<Karton> kartoni = null;
        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            kartoni = db.vratiSveKartone();
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return kartoni;
    }

    public ArrayList<Kandidat> vratiSveKandidate() {
        ArrayList<Kandidat> kandidati = null;
        
        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            kandidati = db.vratiSveKandidate();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return kandidati;
    }

    public void upisiKandidatuPoene(int suma, String jmbg) {
        
        
        try {
            db.ucitajDriver();
            db.otvoriKonekciju();
            db.upisiKandidatuPoene(suma, jmbg);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                db.zatvoriKonekciju();
            } catch (SQLException ex) {
                Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
