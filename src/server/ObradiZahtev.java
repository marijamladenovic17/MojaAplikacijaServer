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
import domen.Rang_Lista;
import domen.Sluzbenik;
import domen.SrednjaSkola;
import domen.Stavka_Rang_Liste;
import domen.Test;
import domen.ZanimanjeRoditelja;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import konstante.Operacije;
import logika.Kontroler;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;
import java.util.Date;

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
                        so.setOdgovor("!!");
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
                        so.setOdgovor("!!");
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
                        so.setOdgovor("!");
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
                    boolean sacuvao_kar = Kontroler.getInstance().promeniKarton(kaa);
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

                case Operacije.SPOJ_KARTONE:

                    so = Kontroler.getInstance().spojKartone();
                    break;

                case Operacije.VRATI_KARTONE_PROVERA:
                    int kartonskiBroj = (int) kz.getParametar();
                    ArrayList<Karton> kartoniProvera = Kontroler.getInstance().vratiKartoneYaProveru(kartonskiBroj);
                    if (kartoniProvera == null) {
                        so.setPoruka("Karton nije pronadjen pod zadatom sifrom!");
                    } else {
                        so.setPoruka("Karton je pronadjen pod zadatom sifrom!");
                        so.setOdgovor(kartoniProvera);

                    }
                    break;

                case Operacije.IZRACUNAJ_POENE:

                    so = Kontroler.getInstance().izracunajPoene();
                    break;

                case Operacije.NADJI_PRIJAVLJENOG:

                    so.setOdgovor(null);
                    String[] nizz = new String[2];
                    nizz = (String[]) kz.getParametar();
                    ArrayList<Sluzbenik> sluzbenici = Kontroler.getInstance().vratiSveSluzbenike();
                    for (Sluzbenik sluzbenik : sluzbenici) {
                        if (sluzbenik.getUsername().equals(nizz[0]) && sluzbenik.getPassword().equals(nizz[1])) {
                            so.setOdgovor(sluzbenik);
                        }
                    }
                    if (so.getOdgovor() == null) {
                        ArrayList<Komisija> koms = Kontroler.getInstance().vratiSveKomisije();
                        for (Komisija komis : koms) {
                            if (komis.getUsername().equals(nizz[0]) && komis.getPassword().equals(nizz[1])) {
                                so.setOdgovor(komis);
                            }
                        }
                    }
                    break;
                case Operacije.KREIRAJ_RL_ISIT:

                    boolean postojiRLisit = false;
                    ArrayList<Rang_Lista> rangListe = Kontroler.getInstance().vratiRangListe();
                    for (Rang_Lista rngL : rangListe) {
                        if (rngL.getSmer().equals("Informacioni sistemi i tehnologije")) {
                            postojiRLisit = true;
                            so.setPoruka("Rang lista vec postoji");
                            so.setOdgovor(rngL);
                        }
                    }
                    if (postojiRLisit == false) {
                        ArrayList<Karton> kartoni = Kontroler.getInstance().vratiSveKartone();
                        ArrayList<Kandidat> kandidati = Kontroler.getInstance().vratiSveKandidate();
                        for (Kandidat kandidat1 : kandidati) {

                            int brojac = 0;
                            double suma = 0;
                            for (Karton kartonn : kartoni) {
                                if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())) {
                                    brojac++;
                                }
                            }
                            if (brojac == 1) {
                                for (Karton kartonn : kartoni) {
                                    if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())) {
                                        suma += kartonn.getRezultatTesta();
                                    }
                                }
                                suma = suma * 0.6;
                            } else {
                                for (Karton kartonn : kartoni) {
                                    if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())
                                            && kartonn.getGrupaZadataka().getTest().getNazivTesta().equals("Matematika")) {
                                        suma = suma + (0.3 * kartonn.getRezultatTesta());
                                    }
                                    if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())
                                            && kartonn.getGrupaZadataka().getTest().getNazivTesta().equals("Test opste informisanosti")) {
                                        suma += kartonn.getRezultatTesta();
                                    }
                                }
                            }
                            suma += kandidat1.getBrBodovaIzSkole();

                            Kontroler.getInstance().upisiKandidatuPoene(suma, kandidat1.getJmbg());
                        }

                        ArrayList<Kandidat> kandids = Kontroler.getInstance().vratiKandidateZaISIT();

                        Rang_Lista rl = new Rang_Lista();
                        ArrayList<Stavka_Rang_Liste> stavke = new ArrayList<>();
                        int rb = 0;
                        double prethodni = 1000;

                        for (Kandidat kd : kandids) {
                            Stavka_Rang_Liste srl = new Stavka_Rang_Liste();
                            srl.setKandidat(kd);
                            if (kd.getUkupanBrojPoena() < prethodni) {
                                rb++;
                            }
                            srl.setRedniBroj(rb);
                            srl.setBrojPoena(kd.getUkupanBrojPoena());
                            stavke.add(srl);
                            prethodni = kd.getUkupanBrojPoena();
                        }
                        rl.setStavke(stavke);
                        LocalDate ld = LocalDate.now();
                        rl.setGodina(ld.getYear());
                        rl.setSmer("Informacioni sistemi i tehnologije");
                        boolean uspesnoNapravljenaRangLista = Kontroler.getInstance().sacuvajRangListu(rl);
                        so.setOdgovor(rl);
                        if (uspesnoNapravljenaRangLista) {
                            so.setPoruka("Uspesno napravljena rang-lista");
                        } else {
                            so.setPoruka("Nesto nije u redu");
                        }
                    }

                    break;
                case Operacije.KREIRAJ_RL_MEN:

                    boolean postojiRLmen = false;
                    ArrayList<Rang_Lista> rangListe2 = Kontroler.getInstance().vratiRangListe();
                    for (Rang_Lista rngL : rangListe2) {
                        if (rngL.getSmer().equals("Menadzment")) {
                            postojiRLmen = true;
                            so.setPoruka("Rang lista vec postoji");
                            so.setOdgovor(rngL);
                        }
                    }
                    if (postojiRLmen == false) {

                        ArrayList<Karton> kartoniM = Kontroler.getInstance().vratiSveKartone();
                        ArrayList<Kandidat> kandidatiM = Kontroler.getInstance().vratiSveKandidate();
                        for (Kandidat kandidat1 : kandidatiM) {

                            int brojac = 0;
                            double suma = 0;
                            for (Karton kartonn : kartoniM) {
                                if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())) {
                                    brojac++;
                                }
                            }
                            if (brojac == 1) {
                                for (Karton kartonn : kartoniM) {
                                    if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())) {
                                        suma += kartonn.getRezultatTesta();
                                    }
                                }
                                suma = suma * 0.6;
                            } else {
                                for (Karton kartonn : kartoniM) {
                                    if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())
                                            && kartonn.getGrupaZadataka().getTest().getNazivTesta().equals("Matematika")) {
                                        suma = suma + (0.3 * kartonn.getRezultatTesta());
                                    }
                                    if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())
                                            && kartonn.getGrupaZadataka().getTest().getNazivTesta().equals("Test opste informisanosti")) {
                                        suma += kartonn.getRezultatTesta();
                                    }
                                }
                            }
                            suma += kandidat1.getBrBodovaIzSkole();

                            Kontroler.getInstance().upisiKandidatuPoene(suma, kandidat1.getJmbg());
                        }

                        ArrayList<Kandidat> kandidsM = Kontroler.getInstance().vratiKandidateZaMen();

                        Rang_Lista rlM = new Rang_Lista();
                        ArrayList<Stavka_Rang_Liste> stavkeM = new ArrayList<>();
                        int rbM = 0;
                        double prethodniM = 1000;

                        for (Kandidat kd : kandidsM) {
                            Stavka_Rang_Liste srl = new Stavka_Rang_Liste();
                            srl.setKandidat(kd);
                            if (kd.getUkupanBrojPoena() < prethodniM) {
                                rbM++;
                            }
                            srl.setRedniBroj(rbM);
                            srl.setBrojPoena(kd.getUkupanBrojPoena());
                            stavkeM.add(srl);
                            prethodniM = kd.getUkupanBrojPoena();
                        }
                        rlM.setStavke(stavkeM);
                        LocalDate ldM = LocalDate.now();
                        rlM.setGodina(ldM.getYear());
                        rlM.setSmer("Menadzment");
                        boolean uspesnoNapravljenaRangListaM = Kontroler.getInstance().sacuvajRangListu(rlM);
                        so.setOdgovor(rlM);
                        if (uspesnoNapravljenaRangListaM) {
                            so.setPoruka("Uspesno napravljena rang-lista");
                        } else {
                            so.setPoruka("Nesto nije u redu");
                        }
                    }

                    break;

                case Operacije.KREIRAJ_RL_ISIT_DALJ:

                    boolean postojiRLdalj = false;
                    ArrayList<Rang_Lista> rangListeD = Kontroler.getInstance().vratiRangListe();
                    for (Rang_Lista rngL : rangListeD) {
                        if (rngL.getSmer().equals("Informacioni sistemi i tehnologije - sistem na daljinu")) {
                            postojiRLdalj = true;
                            so.setPoruka("Rang lista vec postoji");
                            so.setOdgovor(rngL);
                        }
                    }
                    if (postojiRLdalj == false) {

                        ArrayList<Karton> kartoniD = Kontroler.getInstance().vratiSveKartone();
                        ArrayList<Kandidat> kandidatiD = Kontroler.getInstance().vratiSveKandidate();
                        for (Kandidat kandidat1 : kandidatiD) {

                            int brojac = 0;
                            double suma = 0;
                            for (Karton kartonn : kartoniD) {
                                if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())) {
                                    brojac++;
                                }
                            }
                            if (brojac == 1) {
                                for (Karton kartonn : kartoniD) {
                                    if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())) {
                                        suma += kartonn.getRezultatTesta();
                                    }
                                }
                                suma = suma * 0.6;
                            } else {
                                for (Karton kartonn : kartoniD) {
                                    if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())
                                            && kartonn.getGrupaZadataka().getTest().getNazivTesta().equals("Matematika")) {
                                        suma = suma + (0.3 * kartonn.getRezultatTesta());
                                    }
                                    if (kartonn.getKandidat().getJmbg().equals(kandidat1.getJmbg())
                                            && kartonn.getGrupaZadataka().getTest().getNazivTesta().equals("Test opste informisanosti")) {
                                        suma += kartonn.getRezultatTesta();
                                    }
                                }
                            }
                            suma += kandidat1.getBrBodovaIzSkole();

                            Kontroler.getInstance().upisiKandidatuPoene(suma, kandidat1.getJmbg());
                        }

                        ArrayList<Kandidat> kandidsD = Kontroler.getInstance().vratiKandidateZaDalj();

                        Rang_Lista rlD = new Rang_Lista();
                        ArrayList<Stavka_Rang_Liste> stavkeD = new ArrayList<>();
                        int rbD = 0;
                        double prethodniD = 1000;

                        for (Kandidat kd : kandidsD) {
                            Stavka_Rang_Liste srl = new Stavka_Rang_Liste();
                            srl.setKandidat(kd);
                            if (kd.getUkupanBrojPoena() < prethodniD) {
                                rbD++;
                            }
                            srl.setRedniBroj(rbD);
                            srl.setBrojPoena(kd.getUkupanBrojPoena());
                            stavkeD.add(srl);
                            prethodniD = kd.getUkupanBrojPoena();
                        }
                        rlD.setStavke(stavkeD);
                        LocalDate ldD = LocalDate.now();
                        rlD.setGodina(ldD.getYear());
                        rlD.setSmer("Informacioni sistemi i tehnologije - sistem na daljinu");
                        boolean uspesnoNapravljenaRangListaD = Kontroler.getInstance().sacuvajRangListu(rlD);
                        so.setOdgovor(rlD);
                        if (uspesnoNapravljenaRangListaD) {
                            so.setPoruka("Uspesno napravljena rang-lista");
                        } else {
                            so.setPoruka("Nesto nije u redu");
                        }
                    }

                    break;
                     case Operacije.SACUVAJ_OBAVESTENJA:
                         String[] obavestenje =  (String[]) kz.getParametar();
                         boolean ubacen = Kontroler.getInstance().ubaciObavestenje(obavestenje);
                         if(ubacen) {
                             so.setPoruka("Uspesno sacuvano obavestenje!");
                         } else {
                             so.setPoruka("Neuspesno sacuvano obavestenje!");
                         }
                    
                    break;
                     case Operacije.VRATI_RANG_LISTU:
                         String smer = (String) kz.getParametar();
                         so = Kontroler.getInstance().vratiOdredjenuRL(smer);
                         if(so.getOdgovor()==null){
                             so.setPoruka("Neuspesno");
                         }else{
                             so.setPoruka("Uspesno");}
                         break;
                         case Operacije.VRATI_KANDIDATA_ZA_VERIFIKACIJU:
                    String email = (String) kz.getParametar();
                    Kandidat kandidat1 = Kontroler.getInstance().vratiKandidataVer(email);
                    if (kandidat1!= null) {

                        so.setPoruka("Kandidat je pronadjen pod zadatim e-mail-om!");
                        so.setOdgovor(kandidat1);
                        
                    } else {
                        so.setPoruka("Kandidat nije pronadjen pod zadatim e-mail-om ");

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
