/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.Clan;
import domen.Drzevljanstvo;
import domen.GrupaZadatka;
import domen.Kandidat;
import domen.Karton;
import domen.Komisija;
import domen.Nacionalnost;
import domen.Rang_Lista;
import domen.Resenje;
import domen.Sluzbenik;
import domen.SrednjaSkola;
import domen.Stavka_Rang_Liste;
import domen.Test;
import domen.Zadatak;
import domen.ZanimanjeRoditelja;
import java.io.IOException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PC
 */
public class DBBroker {

    Connection konekcija;
    ReaderProperty rp;

    public DBBroker() {

        try {
            rp = new ReaderProperty();
        } catch (IOException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void ucitajDriver() throws ClassNotFoundException {
        Class.forName(rp.vratiVrednost(Konstanta.DRIVER));
    }

    public void otvoriKonekciju() throws SQLException {

        String url = rp.vratiVrednost(Konstanta.URL);
        String user = rp.vratiVrednost(Konstanta.USER);
        String pass = rp.vratiVrednost(Konstanta.PASS);

        konekcija = DriverManager.getConnection(url, user, pass);
        konekcija.setAutoCommit(false);

    }

    public void otvoriKonekciju(String email_validation) throws SQLException {
        konekcija = DriverManager.getConnection("jdbc:mysql://localhost:3306/email_validation", "root", "");
        konekcija.setAutoCommit(false);
    }

    public void zatvoriKonekciju() throws SQLException {
        konekcija.close();
    }

    public void commit() throws SQLException {
        konekcija.commit();
    }

    public void rollback() throws SQLException {
        konekcija.rollback();

    }

    public ArrayList<Komisija> vratiKomisije() throws SQLException {
        ArrayList<Komisija> komisije = new ArrayList<>();
        String upit = "SELECT * FROM komisija";
        Statement stat = konekcija.createStatement();
        ResultSet rs = stat.executeQuery(upit);

        while (rs.next()) {
            int komid = rs.getInt("komisijaID");
            String user = rs.getString("user");
            String pass = rs.getString("password");
            ArrayList<Clan> cla = vratiClan(komid);
            Komisija k = new Komisija(komid, user, pass, cla);

            komisije.add(k);
        }
        return komisije;
    }

    public List<Clan> vratiClanove() throws SQLException {
        String upit = "SELECT * FROM clan ";
        List<Clan> listaClanova = new ArrayList<>();
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);

        while (rs.next()) {
            int idClana = rs.getInt(1);
            String ime = rs.getString(2);
            String prezime = rs.getString(3);
            int komID = rs.getInt(4);
            Clan c = new Clan(idClana, ime, prezime, komID);
            System.out.println("Ubacen");
            listaClanova.add(c);
        }
        rs.close();
        st.close();
        return listaClanova;

    }

    public void ubaciKomisiju(Komisija novaKom) throws SQLException {
        String upit = "INSERT INTO komisija(komisijaID,user,password) VALUES(?,?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setInt(1, novaKom.getKomisijaID());
        ps.setString(2, novaKom.getUsername());
        ps.setString(3, novaKom.getPassword());

        ps.executeUpdate();
        ps.close();
    }

    public void promeniKomisiju(Komisija novaKom) {
        String upit = "UPDATE komisija SET komisijaID=?,user=?,password=?,clan1=?,clan2=?,clan3=? WHERE komisijaID=" + novaKom.getKomisijaID();
        try {

            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setInt(1, novaKom.getKomisijaID());
            ps.setString(2, novaKom.getUsername());
            ps.setString(3, novaKom.getPassword());
            ps.setInt(4, novaKom.getListaClanova().get(0).getClanID());
            ps.setInt(5, novaKom.getListaClanova().get(1).getClanID());
            ps.setInt(6, novaKom.getListaClanova().get(2).getClanID());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ArrayList<Clan> vratiClan(int c1) {
        ArrayList<Clan> clanovi = new ArrayList<>();
        String upit = "SELECT * FROM clan WHERE komisijaID=" + c1;
        Clan c = new Clan();
        try {

            Statement st = konekcija.createStatement();
            ResultSet rs = st.executeQuery(upit);

            while (rs.next()) {
                int idClana = rs.getInt(1);
                String ime = rs.getString(2);
                String prezime = rs.getString(3);
                int komID = rs.getInt(4);
                c = new Clan(idClana, ime, prezime, komID);
                clanovi.add(c);

            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clanovi;
    }

    public ArrayList<Drzevljanstvo> vratiDrzevljanstvo() throws SQLException {
        String upit = "SELECT * FROM drzevljanstvo";
        ArrayList<Drzevljanstvo> listaDrzevljanstva = new ArrayList<>();
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);

        while (rs.next()) {
            int idDrzevljanstvo = rs.getInt(1);
            String naziv = rs.getString(2);
            listaDrzevljanstva.add(new Drzevljanstvo(idDrzevljanstvo, naziv));
        }
        rs.close();
        st.close();
        return listaDrzevljanstva;
    }

    public ArrayList<ZanimanjeRoditelja> vratiZanimanja() throws SQLException {
        String upit = "SELECT * FROM ZanimanjeRoditelja";
        ArrayList<ZanimanjeRoditelja> listaZanimanjaRoditelja = new ArrayList<>();
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);

        while (rs.next()) {
            int idZanimanje = rs.getInt(1);
            String naziv = rs.getString(2);
            ZanimanjeRoditelja zr = new ZanimanjeRoditelja(idZanimanje, naziv);
            listaZanimanjaRoditelja.add(zr);
        }
        rs.close();
        st.close();
        return listaZanimanjaRoditelja;
    }

    public ArrayList<Nacionalnost> vratiNacionalnost() throws SQLException {
        String upit = "SELECT * FROM Nacionalnost";
        ArrayList<Nacionalnost> listaNacionalnosti = new ArrayList<>();
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);

        while (rs.next()) {
            int idNcionalnosti = rs.getInt(1);
            String naziv = rs.getString(2);
            listaNacionalnosti.add(new Nacionalnost(idNcionalnosti, naziv));
        }
        rs.close();
        st.close();
        return listaNacionalnosti;
    }

    public ArrayList<SrednjaSkola> vratiSrednjuSkolu() throws SQLException {
        String upit = "SELECT * FROM SrednjaSkola";
        ArrayList<SrednjaSkola> listaSrednjeSkole = new ArrayList<>();
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);

        while (rs.next()) {
            int idSkole = rs.getInt(1);
            String naziv = rs.getString(2);
            listaSrednjeSkole.add(new SrednjaSkola(idSkole, naziv));
        }
        rs.close();
        st.close();
        return listaSrednjeSkole;
    }

    public void sacuvajKandidata(Kandidat kandidat) throws SQLException {
        String upit = "INSERT INTO kandidat(prezime,sifraPrijave,jmbg,imeRoditelja,ime,pol,mobilni,fiksni,drzavljanstvoID,sifraZanimanjaRoditelja,sifraSS,nacionalnostID,email,brBodovaIzSkole,smer) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setString(1, kandidat.getPrezime());
        ps.setString(2, kandidat.getSifraPrijave());
        ps.setString(3, kandidat.getJmbg());
        ps.setString(4, kandidat.getImeRoditelja());
        ps.setString(5, kandidat.getIme());
        ps.setString(6, kandidat.getPol());
        ps.setString(7, kandidat.getMobilni());
        ps.setString(8, kandidat.getFiksni());
        ps.setInt(9, kandidat.getDrzevljanstvo().getDrzevljanstvoID());
        ps.setInt(10, kandidat.getZanimanjeRoditelja().getZanimanjeRoditelja());
        ps.setInt(11, kandidat.getSrednjaSkola().getSifraSrednjeSkole());
        ps.setInt(12, kandidat.getNacionalnost().getNacionalnostID());
        ps.setString(13, kandidat.getEmail());
        ps.setDouble(14, kandidat.getBrBodovaIzSkole());
        ps.setInt(15, kandidat.getSmer());
        ps.executeUpdate();
        ps.close();
    }

    public ArrayList<Test> vratiTest() throws SQLException {
        String upit = "SELECT * FROM test";
        ArrayList<Test> listaTest = new ArrayList<>();
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);

        while (rs.next()) {
            int idTesta = rs.getInt(1);
            String naziv = rs.getString(2);
            listaTest.add(new Test(idTesta, naziv));
        }
        rs.close();
        st.close();
        return listaTest;
    }

    public void sacuvajGZ(GrupaZadatka gz) throws SQLException {
        String upit = "INSERT INTO grupazadatka(brojGrupe,testID) VALUES(?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setInt(1, gz.getBrGrupe());
        ps.setInt(2, gz.getTest().getTestID());
        ps.executeUpdate();
        ps.close();
    }

    public void sacuvajResenje(Resenje resenje, GrupaZadatka gz) throws SQLException {
        String upit = "INSERT INTO resenje(rbZadatka,odgovor,brojGrupe) VALUES(?,?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);

        ps.setInt(1, resenje.getRbZadatka());
        ps.setString(2, resenje.getOdgovor() + "");
        ps.setInt(3, gz.getBrGrupe());

        ps.executeUpdate();
        ps.close();
    }

    public int vratiKartonID() throws SQLException {
        int b = 0;
        String upit = "SELECT MAX(KartonID) as max FROM karton";
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(upit);
        while (rs.next()) {
            b = rs.getInt(1);
        }
        return b + 1;
    }

    public ArrayList<GrupaZadatka> vratiGZ() throws SQLException {
        String upit = "SELECT * FROM grupazadatka";
        ArrayList<GrupaZadatka> listaGrupaZadatka = new ArrayList<>();
        Statement st = konekcija.createStatement();

        ResultSet rs = st.executeQuery(upit);

        while (rs.next()) {
            int brGrupe = rs.getInt(1);
            int testID = rs.getInt(2);
            Test t = vratiTest(testID);
            ArrayList<Resenje> listaRz = vratiListuRZ(brGrupe);
            GrupaZadatka gz = new GrupaZadatka(brGrupe, listaRz, t);
            listaGrupaZadatka.add(gz);
        }
        rs.close();
        st.close();
        return listaGrupaZadatka;
    }

    private Test vratiTest(int testID) throws SQLException {
        String upit = "SELECT * FROM test WHERE testID=" + testID;
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(upit);
        Test t = new Test();
        while (rs.next()) {
            int TestID = testID;
            String naziv = rs.getString(2);
            t.setTestID(TestID);
            t.setNazivTesta(naziv);
        }
        return t;
    }

    private ArrayList<Resenje> vratiListuRZ(int brGrupe) throws SQLException {
        String upit = "SELECT * from resenje WHERE brojGrupe=" + brGrupe;
        ArrayList<Resenje> lr = new ArrayList<>();
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(upit);
        while (rs.next()) {

            int rbZadatka = rs.getInt(2);
            String odg = rs.getString(3);
            char odgovor = odg.charAt(0);
            int bg = rs.getInt(4);
            Resenje r = new Resenje(rbZadatka, odgovor);
            lr.add(r);
        }
        rs.close();
        s.close();
        return lr;
    }

    public Kandidat vratiKandidata(String brojPrijave) throws SQLException {
        String upit = "SELECT * from kandidat WHERE sifraPrijave='" + brojPrijave + "'";
        Kandidat k = null;
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(upit);
        while (rs.next()) {
            String prezime = rs.getString(1);
            String sifraPrijave = rs.getString(2);
            String jmbg = rs.getString(3);
            String imeRoditelja = rs.getString(4);
            String ime = rs.getString(5);
            String pol = rs.getString(6);
            String mobilni = rs.getString(7);
            String fiksni = rs.getString(8);
            k = new Kandidat();
            k.setIme(ime);
            k.setPrezime(prezime);
            k.setSifraPrijave(brojPrijave);
            k.setJmbg(jmbg);

        }
        rs.close();
        s.close();
        return k;
    }

    public void sacuvajKarton(Karton karton) throws SQLException {
        String upit = "INSERT INTO karton(brojUnosa,kartonID,brojKartona,brojGrupe) VALUES(?,?,?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);

        ps.setInt(1, karton.getBrUnosa());
        ps.setInt(2, karton.getKartonID());
        ps.setInt(3, karton.getBrKartona());
        ps.setInt(4, karton.getGrupaZadataka().getBrGrupe());

        ps.executeUpdate();
        ps.close();
    }

    public int vratiKomisijaID() throws SQLException {
        int b = 0;
        String upit = "SELECT MAX(KomisijaID) as max FROM komisija";
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(upit);
        while (rs.next()) {
            b = rs.getInt(1);
        }
        return ++b;
    }

    public void sacuvajODG(Zadatak zadatak, Karton k) throws SQLException {
        String upit = "INSERT INTO zadatak(kartonID,rbZadatka,odgovor) VALUES(?,?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setInt(1, k.getKartonID());
        ps.setInt(2, zadatak.getRbZadatka());
        ps.setString(3, zadatak.getOdgovor() + "");
        ps.executeUpdate();
        ps.close();
    }

    public Karton vratiKarton(int kartBroj) throws SQLException {
        Karton kart = null;
        String upit = "SELECT * FROM karton k JOIN grupazadatka g ON k.brojGrupe = g.brojGrupe JOIN test t ON g.testID = t.testID WHERE k.brojKartona = " + kartBroj + " LIMIT 1";
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        while (rs.next()) {
            Test t = new Test(rs.getInt("t.testID"), rs.getString("nazivTesta"));
            GrupaZadatka gz = new GrupaZadatka(rs.getInt("g.brojGrupe"), null, t);
            kart = new Karton(rs.getInt("kartonID"), rs.getInt("brojKartona"), rs.getInt("brojUnosa"), gz, null);
            int kID = kart.getKartonID();
            ArrayList<Zadatak> zadaci = vratiZadatke(kID);
            kart.setListaOdg(zadaci);
            return kart;
        }

        return kart;
    }

    private ArrayList<Zadatak> vratiZadatke(int kID) throws SQLException {
        ArrayList<Zadatak> zadaci = new ArrayList<>();
        String upit = "SELECT * FROM zadatak WHERE kartonID =" + kID;
        Statement stat = konekcija.createStatement();
        ResultSet rs = stat.executeQuery(upit);
        while (rs.next()) {
            Zadatak zad = new Zadatak(rs.getInt("rbZadatka"), rs.getString("odgovor").toUpperCase().charAt(0));
            zadaci.add(zad);

        }

        return zadaci;
    }

    public Karton vratiKartonUnosPrvi(int kartBroj) throws SQLException {
        Karton kart = null;
        String upit = "SELECT * FROM karton k JOIN grupazadatka g ON k.brojGrupe = g.brojGrupe JOIN test t ON g.testID = t.testID WHERE k.brojKartona = " + kartBroj + " AND brojUnosa=1 LIMIT 1";
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        while (rs.next()) {
            Test t = new Test(rs.getInt("t.testID"), rs.getString("nazivTesta"));
            GrupaZadatka gz = new GrupaZadatka(rs.getInt("g.brojGrupe"), null, t);
            kart = new Karton(rs.getInt("kartonID"), rs.getInt("brojKartona"), rs.getInt("brojUnosa"), gz, null);
            int kID = kart.getKartonID();
            ArrayList<Zadatak> zadaci = vratiZadatke(kID);
            kart.setListaOdg(zadaci);
            return kart;
        }

        return kart;
    }

    public void izmeniKarton(Karton kaa) throws SQLException {
        String upit = "UPDATE karton SET kandidatID='" + kaa.getKandidat().getJmbg() + "' WHERE kartonID=" + kaa.getKartonID();
        Statement s = konekcija.createStatement();
        s.executeUpdate(upit);

    }

    public void izmeniZadatak(Zadatak zadatak, int kartID) throws SQLException {
        String upit = "UPDATE zadatak SET odgovor = '" + zadatak.getOdgovor() + "' WHERE kartonID = " + kartID + " AND rbZadatka =" + zadatak.getRbZadatka();
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.executeUpdate();
    }

    public ArrayList<Karton> vratiKartone() throws SQLException {
        ArrayList<Karton> kartoni = new ArrayList<>();
        String upit = "SELECT * FROM karton k JOIN grupazadatka g ON k.brojGrupe = g.brojGrupe JOIN test t ON g.testID = t.testID";
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        while (rs.next()) {
            Test t = new Test(rs.getInt("t.testID"), rs.getString("nazivTesta"));
            GrupaZadatka gz = new GrupaZadatka(rs.getInt("g.brojGrupe"), null, t);
            Karton kart = new Karton(rs.getInt("kartonID"), rs.getInt("brojKartona"), rs.getInt("brojUnosa"), gz, null);
            int kID = kart.getKartonID();
            ArrayList<Zadatak> zadaci = vratiZadatke(kID);
            kart.setListaOdg(zadaci);
            kartoni.add(kart);
        }
        return kartoni;
    }

    public ArrayList<Karton> vratiKartoneProvera(int kartonskiBroj) throws SQLException {
        ArrayList<Karton> kartoni = new ArrayList<>();
        String upit = "SELECT * FROM karton k JOIN grupazadatka g ON k.brojGrupe = g.brojGrupe JOIN test t ON g.testID = t.testID WHERE k.brojKartona = " + kartonskiBroj;
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        while (rs.next()) {
            Test t = new Test(rs.getInt("t.testID"), rs.getString("nazivTesta"));
            GrupaZadatka gz = new GrupaZadatka(rs.getInt("g.brojGrupe"), null, t);
            Karton kart = new Karton(rs.getInt("kartonID"), rs.getInt("brojKartona"), rs.getInt("brojUnosa"), gz, null);
            int kID = kart.getKartonID();
            ArrayList<Zadatak> zadaci = vratiZadatke(kID);
            kart.setListaOdg(zadaci);
            kartoni.add(kart);
        }

        return kartoni;
    }

    public ArrayList<Karton> vratiKartoneZaPoene() throws SQLException {
        ArrayList<Karton> kartoni = new ArrayList<>();
        String upit = "SELECT * FROM karton k JOIN grupazadatka g ON k.brojGrupe = g.brojGrupe JOIN test t ON g.testID = t.testID";
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        while (rs.next()) {
            Test t = new Test(rs.getInt("t.testID"), rs.getString("nazivTesta"));
            int brojG = rs.getInt("g.brojGrupe");
            ArrayList<Resenje> resenja = vratiResenjaGrupe(brojG);
            GrupaZadatka gz = new GrupaZadatka(rs.getInt("g.brojGrupe"), resenja, t);
            Karton kart = new Karton(rs.getInt("kartonID"), rs.getInt("brojKartona"), rs.getInt("brojUnosa"), gz, null);
            int kID = kart.getKartonID();
            ArrayList<Zadatak> zadaci = vratiZadatke(kID);
            kart.setListaOdg(zadaci);
            kartoni.add(kart);
        }

        return kartoni;
    }

    private ArrayList<Resenje> vratiResenjaGrupe(int aInt) throws SQLException {
        ArrayList<Resenje> resenja = new ArrayList<>();
        String upit = "select * from resenje where brojGrupe =" + aInt;
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        while (rs.next()) {
            Resenje r = new Resenje(rs.getInt("rbZadatka"), rs.getString("odgovor").toUpperCase().charAt(0));
            resenja.add(r);
        }

        return resenja;
    }

    public void upisiRezultat(int kartonID, double suma) throws SQLException {
        String upit = "UPDATE karton SET rezultatTesta =" + suma + " WHERE kartonID= " + kartonID + " AND brojUnosa = 1";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.executeUpdate();
    }

    public void izmeniClan(Clan cl, Komisija novaKom) throws SQLException {
        String upit = "UPDATE clan SET komisijaID=" + novaKom.getKomisijaID() + " WHERE clanID=" + cl.getClanID();
        Statement s = konekcija.createStatement();
        s.executeUpdate(upit);
    }

    public void izmeniProperty(String key, String vrednost) {
        rp.upisiVrednost(key, vrednost);
    }

    public ArrayList<Karton> vratiSveKartone() throws SQLException {
        ArrayList<Karton> kartoni = new ArrayList<>();
        String upit = "SELECT * FROM karton k JOIN kandidat ka ON k.kandidatID = ka.jmbg JOIN grupazadatka g ON k.brojGrupe = g.brojGrupe JOIN test t ON g.testID = t.testID where brojUnosa = 1";
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        while (rs.next()) {
            Test t = new Test(rs.getInt("t.testID"), rs.getString("nazivTesta"));
            GrupaZadatka gz = new GrupaZadatka(rs.getInt("g.brojGrupe"), null, t);
            Kandidat kandidat = new Kandidat();
            kandidat.setJmbg(rs.getString("ka.jmbg"));
            Karton kart = new Karton(rs.getInt("kartonID"), rs.getInt("brojKartona"), rs.getInt("brojUnosa"), gz, kandidat);
            int kID = kart.getKartonID();
            double rezultat = rs.getDouble("rezultatTesta");
            kart.setRezultatTesta(rezultat);
            ArrayList<Zadatak> zadaci = vratiZadatke(kID);
            kart.setListaOdg(zadaci);
            kartoni.add(kart);
        }
        return kartoni;
    }

    public ArrayList<Kandidat> vratiSveKandidate() throws SQLException {
        ArrayList<Kandidat> kandidati = new ArrayList<>();
        String upit = "select * from kandidat";
        Statement stat = konekcija.createStatement();
        ResultSet rs = stat.executeQuery(upit);
        while (rs.next()) {
            String prezime = rs.getString("prezime");
            String sifraPrijave = rs.getString("sifraPrijave");
            String jmbg = rs.getString("jmbg");
            String imeRoditelja = rs.getString("imeRoditelja");
            String ime = rs.getString("ime");
            String pol = rs.getString("pol");
            String mobilni = rs.getString("mobilni");
            String fiksni = rs.getString("fiksni");
            double bodoviSkola = rs.getDouble("brBodovaIzSkole");

            Kandidat kandidat = new Kandidat();
            kandidat.setFiksni(fiksni);
            kandidat.setJmbg(jmbg);
            kandidat.setIme(ime);
            kandidat.setPol(pol);
            kandidat.setPrezime(prezime);
            kandidat.setSifraPrijave(sifraPrijave);
            kandidat.setMobilni(mobilni);
            kandidat.setImeRoditelja(imeRoditelja);
            kandidat.setBrBodovaIzSkole(bodoviSkola);
            kandidati.add(kandidat);
        }
        return kandidati;
    }

    public void upisiKandidatuPoene(double suma, String jmbg) throws SQLException {
        String upit = "update kandidat set ukupanRezultat= " + suma + " where jmbg = '" + jmbg + "'";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.executeUpdate();
    }

    public ArrayList<Kandidat> vratiSveKandidateZaRL() throws SQLException {
        ArrayList<Kandidat> kandidati = new ArrayList<>();
        String upit = "select * from kandidat order by ukupanRezultat desc";
        Statement stat = konekcija.createStatement();
        ResultSet rs = stat.executeQuery(upit);
        while (rs.next()) {
            String prezime = rs.getString("prezime");
            String sifraPrijave = rs.getString("sifraPrijave");
            String jmbg = rs.getString("jmbg");
            String imeRoditelja = rs.getString("imeRoditelja");
            String ime = rs.getString("ime");
            String pol = rs.getString("pol");
            String mobilni = rs.getString("mobilni");
            String fiksni = rs.getString("fiksni");
            int ur = rs.getInt("ukupanRezultat");

            Kandidat kandidat = new Kandidat();
            kandidat.setUkupanBrojPoena(ur);
            kandidat.setFiksni(fiksni);
            kandidat.setJmbg(jmbg);
            kandidat.setIme(ime);
            kandidat.setPol(pol);
            kandidat.setPrezime(prezime);
            kandidat.setSifraPrijave(sifraPrijave);
            kandidat.setMobilni(mobilni);
            kandidat.setImeRoditelja(imeRoditelja);
            kandidati.add(kandidat);
        }
        return kandidati;
    }

    public void sacuvajStavku(Stavka_Rang_Liste srl, int rlID) throws SQLException {
        String upit = "INSERT INTO stavka_rang_liste(rlID, redniBroj, jmbg, brojPoena) VALUES(?,?,?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setInt(1, rlID);
        ps.setInt(2, srl.getRedniBroj());
        ps.setString(3, srl.getKandidat().getJmbg());
        ps.setDouble(4, srl.getBrojPoena());
        ps.executeUpdate();
    }

    public int sacuvajRangListu(Rang_Lista rl) throws SQLException {
        int id = 0;
        String upit = "INSERT INTO rang_lista(godina, smer) VALUES(?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setInt(1, rl.getGodina());
        ps.setString(2, rl.getSmer());
        ps.executeUpdate();
        id = vratiMaxIDRL();
        return id;
    }

    public ArrayList<Sluzbenik> vratiSluzbenike() throws SQLException {
        ArrayList<Sluzbenik> sluzbs = new ArrayList<>();
        String upit = "SELECT * FROM sluzbenik";
        Statement stat = konekcija.createStatement();
        ResultSet rs = stat.executeQuery(upit);
        while (rs.next()) {
            String imePrezime = rs.getString("imePrezime");
            String username = rs.getString("username");
            String password = rs.getString("password");
            Sluzbenik s = new Sluzbenik(username, username, password);
            sluzbs.add(s);
        }
        return sluzbs;
    }

    public ArrayList<Kandidat> vratiSveKandidateZaISIT() throws SQLException {
        ArrayList<Kandidat> kandidati = new ArrayList<>();
        String upit = "select * from kandidat where smer=1 or smer = 4 order by ukupanRezultat desc";
        Statement stat = konekcija.createStatement();
        ResultSet rs = stat.executeQuery(upit);
        while (rs.next()) {
            String prezime = rs.getString("prezime");
            String sifraPrijave = rs.getString("sifraPrijave");
            String jmbg = rs.getString("jmbg");
            String imeRoditelja = rs.getString("imeRoditelja");
            String ime = rs.getString("ime");
            String pol = rs.getString("pol");
            String mobilni = rs.getString("mobilni");
            String fiksni = rs.getString("fiksni");
            int ur = rs.getInt("ukupanRezultat");

            Kandidat kandidat = new Kandidat();
            kandidat.setUkupanBrojPoena(ur);
            kandidat.setFiksni(fiksni);
            kandidat.setJmbg(jmbg);
            kandidat.setIme(ime);
            kandidat.setPol(pol);
            kandidat.setPrezime(prezime);
            kandidat.setSifraPrijave(sifraPrijave);
            kandidat.setMobilni(mobilni);
            kandidat.setImeRoditelja(imeRoditelja);
            kandidati.add(kandidat);
        }
        return kandidati;
    }

    private int vratiMaxIDRL() throws SQLException {
        int id = 0;
        String upit = "SELECT MAX(rlID) as max FROM rang_lista";
        Statement s = konekcija.createStatement();
        ResultSet rs = s.executeQuery(upit);
        while (rs.next()) {
            id = rs.getInt(1);
        }
        return id;
    }

    public ArrayList<Kandidat> vratiSveKandidateZaMen() throws SQLException {
        ArrayList<Kandidat> kandidati = new ArrayList<>();
        String upit = "select * from kandidat where smer=2 or smer = 4 order by ukupanRezultat desc";
        Statement stat = konekcija.createStatement();
        ResultSet rs = stat.executeQuery(upit);
        while (rs.next()) {
            String prezime = rs.getString("prezime");
            String sifraPrijave = rs.getString("sifraPrijave");
            String jmbg = rs.getString("jmbg");
            String imeRoditelja = rs.getString("imeRoditelja");
            String ime = rs.getString("ime");
            String pol = rs.getString("pol");
            String mobilni = rs.getString("mobilni");
            String fiksni = rs.getString("fiksni");
            int ur = rs.getInt("ukupanRezultat");

            Kandidat kandidat = new Kandidat();
            kandidat.setUkupanBrojPoena(ur);
            kandidat.setFiksni(fiksni);
            kandidat.setJmbg(jmbg);
            kandidat.setIme(ime);
            kandidat.setPol(pol);
            kandidat.setPrezime(prezime);
            kandidat.setSifraPrijave(sifraPrijave);
            kandidat.setMobilni(mobilni);
            kandidat.setImeRoditelja(imeRoditelja);
            kandidati.add(kandidat);
        }
        return kandidati;
    }

    public ArrayList<Kandidat> vratiSveKandidateZaDalj() throws SQLException {
        ArrayList<Kandidat> kandidati = new ArrayList<>();
        String upit = "select * from kandidat where smer=3 or smer = 4 order by ukupanRezultat desc";
        Statement stat = konekcija.createStatement();
        ResultSet rs = stat.executeQuery(upit);
        while (rs.next()) {
            String prezime = rs.getString("prezime");
            String sifraPrijave = rs.getString("sifraPrijave");
            String jmbg = rs.getString("jmbg");
            String imeRoditelja = rs.getString("imeRoditelja");
            String ime = rs.getString("ime");
            String pol = rs.getString("pol");
            String mobilni = rs.getString("mobilni");
            String fiksni = rs.getString("fiksni");
            int ur = rs.getInt("ukupanRezultat");

            Kandidat kandidat = new Kandidat();
            kandidat.setUkupanBrojPoena(ur);
            kandidat.setFiksni(fiksni);
            kandidat.setJmbg(jmbg);
            kandidat.setIme(ime);
            kandidat.setPol(pol);
            kandidat.setPrezime(prezime);
            kandidat.setSifraPrijave(sifraPrijave);
            kandidat.setMobilni(mobilni);
            kandidat.setImeRoditelja(imeRoditelja);
            kandidati.add(kandidat);
        }
        return kandidati;
    }

    public ArrayList<Rang_Lista> vratiSveRangListe() throws SQLException {
        ArrayList<Rang_Lista> rang_liste = new ArrayList<>();
        String upit = "select * from rang_lista";
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        while (rs.next()) {
            int id = rs.getInt("rlID");
            int godina = rs.getInt("godina");
            String naziv = rs.getString("smer");
            ArrayList<Stavka_Rang_Liste> stavke = vratiStavkeRangListe(id);
            Rang_Lista rl = new Rang_Lista(naziv, godina);
            rl.setStavke(stavke);
            rang_liste.add(rl);
        }
        return rang_liste;
    }

    private ArrayList<Stavka_Rang_Liste> vratiStavkeRangListe(int id) throws SQLException {
        ArrayList<Stavka_Rang_Liste> stavke = new ArrayList<>();
        String upit = "SELECT * FROM stavka_rang_liste s JOIN kandidat k ON s.jmbg = k.jmbg WHERE s.rlID = " + id + " order by redniBroj asc";
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        while (rs.next()) {
            String prezime = rs.getString("prezime");
            String sifraPrijave = rs.getString("sifraPrijave");
            String jmbg = rs.getString("k.jmbg");
            String imeRoditelja = rs.getString("imeRoditelja");
            String ime = rs.getString("ime");
            String pol = rs.getString("pol");
            String mobilni = rs.getString("mobilni");
            String fiksni = rs.getString("fiksni");
            int ur = rs.getInt("ukupanRezultat");

            Kandidat kandidat = new Kandidat();
            kandidat.setUkupanBrojPoena(ur);
            kandidat.setFiksni(fiksni);
            kandidat.setJmbg(jmbg);
            kandidat.setIme(ime);
            kandidat.setPol(pol);
            kandidat.setPrezime(prezime);
            kandidat.setSifraPrijave(sifraPrijave);
            kandidat.setMobilni(mobilni);
            kandidat.setImeRoditelja(imeRoditelja);
            int idR = rs.getInt("rlID");
            int redB = rs.getInt("redniBroj");
            double poeni = rs.getDouble("brojPoena");
            Stavka_Rang_Liste srl = new Stavka_Rang_Liste(redB, kandidat);
            srl.setBrojPoena(poeni);
            stavke.add(srl);
        }

        return stavke;
    }

    public void ubaciObavestenje(String[] obavestenje) throws SQLException {
        String upit = "INSERT INTO obavestenje(text,naslov) VALUES(?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setString(1, obavestenje[0]);
        ps.setString(2, obavestenje[1]);
        ps.executeUpdate();
        ps.close();
    }

    public Rang_Lista vratiKonkretnuListu(String smer) throws SQLException {
        Rang_Lista rl = null;
        String upit = "select * from rang_lista where smer='" + smer + "'";
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);
        while (rs.next()) {
            int id = rs.getInt("rlID");
            int godina = rs.getInt("godina");
            String naziv = rs.getString("smer");
            ArrayList<Stavka_Rang_Liste> stavke = vratiStavkeRangListe(id);
            rl = new Rang_Lista(naziv, godina);
            rl.setStavke(stavke);
        }
        return rl;
    }

    public Kandidat vratiKandidata1(String email) throws SQLException {
        String upit = "SELECT * from usertable WHERE email='" + email + "'";
        Kandidat kandidat = null;
        Statement st = konekcija.createStatement();
        ResultSet rs = st.executeQuery(upit);

        while (rs.next()) {
            String ime = rs.getString(2);
            String prezime = rs.getString(3);
            String email1 = rs.getString(4);
            String imeRoditelja = rs.getString(8);
            String jmbg = rs.getString(9);
            String pol = rs.getString(10);
            String mobilni = rs.getString(11);
            String fiksni = rs.getString(12);

            kandidat = new Kandidat();
            kandidat.setIme(ime);
            kandidat.setPrezime(prezime);
            kandidat.setEmail(email1);
            kandidat.setImeRoditelja(imeRoditelja);
            kandidat.setJmbg(jmbg);
            kandidat.setPol(pol);
            kandidat.setMobilni(mobilni);
            kandidat.setFiksni(fiksni);

        }
        rs.close();
        st.close();

        return kandidat;
    }

}
