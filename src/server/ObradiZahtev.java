/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import domen.Clan;
import domen.Drzevljanstvo;
import domen.GrupaZadatka;
import domen.Kandidat;
import domen.Karton;
import domen.Komisija;
import domen.Nacionalnost;
import domen.PomocIzmena;
import domen.SrednjaSkola;
import domen.Test;
import domen.ZanimanjeRoditelja;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import konstante.Operacije;
import logika.Kontroler;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author PC
 */
public class ObradiZahtev extends Thread {

    Socket soket;

    public ObradiZahtev(Socket soket) {
        this.soket = soket;
    }

    @Override
    public void run() {
        while (true) {
            KlijentskiZahtev kz = prihvatiKZ();
            ServerskiOdgovor so = new ServerskiOdgovor();
            switch (kz.getOperacija()) {
                case Operacije.DA_LI_JE_REGISTROVAN_SLUZBENIK:

                    String[] niz = (String[]) kz.getParametar();

                    boolean postoji = Kontroler.getInstance().proveriSluzbenika(niz[0], niz[1]);
                    if (postoji) {
                        so.setPoruka("Uspesno ulogovani korisnik!");
                        so.setOdgovor("!");

                    } else {
                        so.setPoruka("Sluzbenik sa ovim korisnickim nalogom ne postoji!");

                    }

                    break;
                case Operacije.VRATI_CLANOVE:

                    ArrayList<Clan> lisClanova = (ArrayList<Clan>) Kontroler.getInstance().vratiClanove();
                    so.setOdgovor(lisClanova);

                    break;
                case Operacije.VRATI_MAX_ID_KOMISIJA:

                    int br = Kontroler.getInstance().vratiMaxIdKomisija();
                    so.setOdgovor(br);

                    break;
                case Operacije.UNOS_KOMISIJE:
                    Komisija kom = (Komisija) kz.getParametar();
                    boolean sacuvaj = Kontroler.getInstance().ubaciKomisiju(kom);
                    if (sacuvaj) {
                        so.setPoruka("Uspesno ubacena komisija!");
                        so.setOdgovor("!");
                    } else {
                        so.setPoruka("Neuspesno  ubacena komisija!");
                    }

                    break;
                case Operacije.VRATI_KOMISIJU:

                    ArrayList<Komisija> lisKom = (ArrayList<Komisija>) Kontroler.getInstance().vratiSveKomisije();
                    so.setOdgovor(lisKom);

                    break;
                case Operacije.IZMENA_KOMISIJE:
                    Komisija kom1 = (Komisija) kz.getParametar();
                    boolean sacuvajKom = Kontroler.getInstance().promeniKomisiju(kom1);
                    if (sacuvajKom) {
                        so.setPoruka("Uspesno promenjena komisija!");

                        so.setOdgovor("!");
                    } else {
                        so.setPoruka("Neuspesno  promenjena komisija!");
                    }

                    break;
                case Operacije.VRATI_DRZ:

                    ArrayList<Drzevljanstvo> listaD = Kontroler.getInstance().vratiDrzevljanstvo();
                    so.setOdgovor(listaD);

                    break;
                case Operacije.VRATI_NAC:

                    ArrayList<Nacionalnost> listaN = Kontroler.getInstance().vratiNacionalnost();
                    so.setOdgovor(listaN);

                    break;

                case Operacije.VRATI_SIF_SS:

                    ArrayList<SrednjaSkola> listaSS = Kontroler.getInstance().vratiSS();
                    so.setOdgovor(listaSS);

                    break;
                case Operacije.VRATI_SIF_ZANIM:

                    ArrayList<ZanimanjeRoditelja> listaSZ = Kontroler.getInstance().vratiSZ();
                    so.setOdgovor(listaSZ);

                    break;
                case Operacije.UNOS_KANDIDATA:
                    Kandidat kand = (Kandidat) kz.getParametar();
                    boolean sacuvano = Kontroler.getInstance().sacuvajKandidata(kand);
                    if (sacuvano) {
                        so.setPoruka("Uspesno ubacen kandidat!");
                        so.setOdgovor("!");
                    } else {
                        so.setPoruka("Neuspesno  ubacen kandidat!");
                        so.setOdgovor("!!");
                    }

                    break;
                case Operacije.VRATI_TEST:

                    ArrayList<Test> listaT = Kontroler.getInstance().vratiTestove();
                    so.setOdgovor(listaT);

                    break;
                case Operacije.UNOS_GRUPE_ZADATAKA:
                    GrupaZadatka gz = (GrupaZadatka) kz.getParametar();
                    boolean sacuvano2 = Kontroler.getInstance().sacuvajGrupeZadataka(gz);
                    if (sacuvano2) {
                        so.setPoruka("Uspesno ubacena grupa zadatka!");
                        so.setOdgovor("!");
                    } else {
                        so.setPoruka("Neuspesno  ubacen grupa zadatka!");
                        so.setOdgovor("!!");
                    }

                    break;
                case Operacije.VRATI_TESTA_LI_JE_REGISTROVANA_KOMISIJA:

                    String[] niz1 = (String[]) kz.getParametar();
                    boolean daLiPostojiUBazi1 = Kontroler.getInstance().proveriKomisiju(niz1[0], niz1[1]);

                    if (daLiPostojiUBazi1) {
                        so.setPoruka("Uspesno ulogovana komisija!");
                        so.setOdgovor(true);

                    } else {
                        so.setPoruka("Komisija sa ovim korisnickim nalogom ne postoji!");
                        so.setOdgovor(false);
                    }

                    break;
                case Operacije.DA_LI_POSTOJI_KOMISIJA:

                    ArrayList<Komisija> komisije = Kontroler.getInstance().vratiSveKomisije();
                    so.setOdgovor(komisije);

                    break;
                case Operacije.VRATI_MAX_ID_KARTONA:

                    int broj = Kontroler.getInstance().vratiMaxIdKartona();
                    so.setOdgovor(broj);

                    break;
                case Operacije.VRATI_GRUPU_ZADATKA:
                    ArrayList<GrupaZadatka> listGZ = Kontroler.getInstance().vratiGrupuZadataka();
                    so.setOdgovor(listGZ);

                    break;
                case Operacije.SACUVAJ_KARTON:
                    Karton k = (Karton) kz.getParametar();
                    boolean sacuvaj3 = Kontroler.getInstance().unesiKarton(k);
                    if (sacuvaj3) {
                        so.setPoruka("Uspesno ubacen karton!");
                        so.setOdgovor("!");
                    } else {
                        so.setPoruka("Neuspesno  ubacen karton!");
                        so.setOdgovor("!!");
                    }

                    break;

                case Operacije.VRATI_KARTON:
                    int kartBroj = (int) kz.getParametar();
                    Karton karton = Kontroler.getInstance().vratiKarton(kartBroj);
                    if (karton == null) {
                        so.setPoruka("Karton nije pronadjen pod zadatom sifrom!");
                    } else {
                        so.setPoruka("Karton je pronadjen pod zadatom sifrom!");
                        so.setOdgovor(karton);

                    }
                    break;
                case Operacije.VRATI_KARTON_UNOS_1:
                    int kartBroj1 = (int) kz.getParametar();
                    Karton karton1 = Kontroler.getInstance().vratiKartonUnosJedan(kartBroj1);
                    if (karton1 == null) {
                        so.setPoruka("Karton nije pronadjen pod zadatom sifrom! ");
                    } else {
                        so.setPoruka("Karton je pronadjen pod zadatom sifrom! - Unos prvi");
                        so.setOdgovor(karton1);

                    }
                    break;
                case Operacije.VRATI_KANDIDATA:
                    String kanBroj = (String) kz.getParametar();
                    Kandidat kandidat = Kontroler.getInstance().vratiKandidata(kanBroj);
                    if (kandidat != null) {

                        so.setPoruka("Kandidat je pronadjen pod zadatom sifrom prijave!");
                        so.setOdgovor(kandidat);
                        System.out.println(kandidat.getIme());
                    } else {
                        so.setPoruka("Kandidat nije pronadjen pod zadatom sifrom! ");

                    }
                    break;
                case Operacije.SACUVAJ_KARTON_VERIFIKACIJA:
                    Karton kaa = (Karton) kz.getParametar();
                    boolean sacuvao_kar=Kontroler.getInstance().promeniKarton(kaa);
                    if (sacuvao_kar) {
                        so.setPoruka("Karton je izmenjen ");
                    } else {
                        so.setPoruka("Karton nije izmenjen!");
                    }
                    break;
                    
                    
                    case Operacije.IZMENI_ZADATKE:
                        PomocIzmena pi = (PomocIzmena) kz.getParametar();
                        boolean uspesnoIzmenjeno = Kontroler.getInstance().izmeniZadatke(pi);
                        if (uspesnoIzmenjeno) {
                        so.setPoruka("Uspesno izmenjeni zadaci!");
                        so.setOdgovor("!");
                    } else {
                        so.setPoruka("Neuspesno  izmenjeni zadaci!");
                        so.setOdgovor("!!");
                    }
                       break;
                    

            }
            posaljiSO(so);
        }
    }

    private KlijentskiZahtev prihvatiKZ() {
        KlijentskiZahtev kz = new KlijentskiZahtev();

        try {
            ObjectInputStream ois = new ObjectInputStream(soket.getInputStream());
            kz = (KlijentskiZahtev) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(ObradiZahtev.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ObradiZahtev.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kz;
    }

    private void posaljiSO(ServerskiOdgovor so) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(soket.getOutputStream());
            oos.writeObject(so);
        } catch (IOException ex) {
            Logger.getLogger(ObradiZahtev.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
