package shop.local.domain;
import java.util.Calendar;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.FalscheBestandsgroesseException;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.ArtikelImWarenkorb;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Massengutartikel;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Person;
import shop.local.valueobjects.Rechnung;
import shop.local.valueobjects.Warenkorb;
import shop.local.persistance.PersistenceManager;

public class EShop {

	private ArtikelVerwaltung meineArtikel;
	private MitarbeiterVerwaltung meineMitarbeiter;
	private KundenVerwaltung meineKunden;
	private Logbuch logbuch;
	private PersistenceManager pm;
	

	public EShop() {
		pm = new PersistenceManager();
		logbuch = new Logbuch(pm);
		meineArtikel = new ArtikelVerwaltung(logbuch, pm);
		meineMitarbeiter= new MitarbeiterVerwaltung(pm);
		meineKunden=new KundenVerwaltung(pm);


	}

	public void Save() {
		meineKunden.Save();
		meineMitarbeiter.Save();
		meineArtikel.Save();
		logbuch.Save();
	}
	
/**
 * 
 * - - - - - - - - ARTIKEL - - - - - - - - - - - - 
 * 
 */

	//Methode, die eine Liste aller im Bestand befindlichen Artikel zur�ckgibt
	public Vector<Artikel> gibAlleArtikel() {
		// einfach delegieren an meineBuecher
		return meineArtikel.getArtikelListe();
	}


	//Methode zum suchen von Artikeln nach titel
	public Vector<Artikel> sucheNachTitel(String titel) {
		// einfach delegieren an meineBuecher
		return meineArtikel.sucheArtikel(titel); 
	}
	
	public Artikel sucheNachNummer(int nummer) throws ArtikelExistiertNichtException {
		
		Artikel richtigerArtikel = meineArtikel.sucheEindeutigenArtikel(nummer);
		if (richtigerArtikel != null) {
			return richtigerArtikel;
			
		}else {
			throw new ArtikelExistiertNichtException(nummer," ist nicht im Eshop.");
		}
		
	}
	
	
	public Massengutartikel fuegeMassengutArtikelEin(String titel, int nummer, int bestand, float preis, int packungsgroesse,  Person person) throws ArtikelExistiertBereitsException {
		Massengutartikel einArtikel = new Massengutartikel (titel, nummer, bestand, preis, packungsgroesse);
		meineArtikel.einfuegen(einArtikel, person);
		return einArtikel;
	}
	
	//Methode zum Einf�gen eines neuen Artikels in den Bestand.
	public Artikel fuegeArtikelEin(String titel, int nummer, int bestand, float preis, Person person) throws ArtikelExistiertBereitsException {
		Artikel einArtikel = new Artikel(titel, nummer, bestand, preis);
		meineArtikel.einfuegen(einArtikel, person);
		return einArtikel;
	}
	
	//Methode zum l�schen eines Artikels
	public void loescheArtikel(int nummer, Person person) throws ArtikelExistiertNichtException {
		Artikel art = meineArtikel.gibArtikel(nummer);
		if (art != null) {
			meineArtikel.loeschen(nummer, person);
			
		}
		
		
	}
	
	//Methode, die den Bestand eines Artikels erh�ht
	public Artikel bestandErhoehen(int nr, int bestandserhoehung, Person person) throws FalscheBestandsgroesseException, ArtikelExistiertNichtException{
		
		Artikel richtigerArtikel = meineArtikel.sucheEindeutigenArtikel(nr);
		
		int neuerBestand = richtigerArtikel.getBestand() + bestandserhoehung;
		
		if(bestandserhoehung > 0)
		{
			logbuch.NeuerEintrag(true, person, richtigerArtikel, bestandserhoehung);// true war Einlagern false war Auslagern
//			logbuch.NeuerEintrag(EreignisTyp.EINLAGERUNG, person, artikel, anzahl);
			richtigerArtikel.setBestand(neuerBestand);
		}else {
			logbuch.NeuerEintrag(false, person, richtigerArtikel, bestandserhoehung);
//			logbuch.NeuerEintrag(EreignisTyp.AUSLAGERUNG, person, artikel, anzahl);
			
			richtigerArtikel.setBestand(neuerBestand);
		}
		
		
		
		return richtigerArtikel;
	}
	
	
	public Rechnung kaufeArtikelImWarenkorb(Kunde kunde) throws ArtikelExistiertNichtException {
		// KaufVerwaltung
		// TODO return kv.kaufeArtikel...(kunde);
		Warenkorb warenkorb = kunde.getWarenkorb();
		
		//Artikel aus dem Shop entfernen
		for (ArtikelImWarenkorb artikelImWarenkorb : warenkorb.getWarenkorbEintraege()) {
			Artikel artikel = artikelImWarenkorb.getArtikel();
			if (artikel.getBestand() > artikelImWarenkorb.getAnzahl()) {
				// Bestandsreduzierung über ArtikelVerwaltung
				try {
					artikel.setBestand(artikel.getBestand() - artikelImWarenkorb.getAnzahl());
				} catch (FalscheBestandsgroesseException e) {}
				logbuch.NeuerEintrag(false, kunde, artikel, artikelImWarenkorb.getAnzahl());
			}
			else if (artikel.getBestand() == artikelImWarenkorb.getAnzahl())
			{
				loescheArtikel(artikel.getNummer(), kunde);
			}
			else
			{
				//fehler: mehr gekauft, als vorhanden
			}
		}
		
		Rechnung rechnung = new Rechnung(kunde, warenkorb.getWarenkorbEintraege());
		warenkorb.WarenkorbLeeren();
		return rechnung;
	}
	
	
	
/**	
 * 
 * 
 * - - - -WARENKORB- - -  -
 * 
 */
	
	

	public void artikelInWarenkorb(int artikelnummer, Kunde eingeloggterKunde) throws ArtikelExistiertNichtException {
				
		Artikel a = meineArtikel.sucheEindeutigenArtikel(artikelnummer);
		if(a == null)
		{
			return;
		}
		eingeloggterKunde.getWarenkorb().ArtikelHinzufügen(a);
	}
	
	public void anzahlAendernArtikelInWarenkorb(int artikelnummer,int anzahl, Kunde eingeloggterKunde)throws ArtikelExistiertNichtException {
		
		Artikel a = meineArtikel.sucheEindeutigenArtikel(artikelnummer);
		if(a == null)
		{
			return;
		}
		eingeloggterKunde.getWarenkorb().ArtikelAnzahlAendern(a, anzahl);
	}
	
	public static int getDayOfYear()
	{
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.DAY_OF_YEAR); 
	}
	
/**
 * 
 * 
 * - - - - - -- - - - MITARBEITER - - - - - - -  -
 * 
 */
	//Methode zum einfuegen eines neuen Mitarbeiters
	public Mitarbeiter fuegeMitarbeiterEin( String name, String str, String nr, String plz, String ort, String iban, String pw) throws MitarbeiterExistiertBereitsException{
		int id = meineMitarbeiter.mitarbeiterAnzahl(); //hier wird die Vektorgröße ausgegeben, funktionier weil der erste Mitarbeiter die id 0 bekommt
		Mitarbeiter m = new Mitarbeiter(id, name, str, nr, plz, ort, iban, pw);
		meineMitarbeiter.einfuegen(m);
		
		return m;
		
	}
	
	
	
	//Methode die prueft, ob es diesen Mitarbeiter mit der id und diesem Passwort gibt
	public Mitarbeiter loginUeberpruefungMitarbeiter(int id, String passwort) {

		Mitarbeiter mitarbeiter = (meineMitarbeiter.ueberpruefenMitarbeiterLogin(id, passwort));
		
		return mitarbeiter;
	}
	
	
/**
 * 
 * 
 * - - - - - - - - - KUNDE - - - - - - - - - - - - 
 * 
 */
	//Methode zum einfügen eines neuen Kunden
	public Kunde fuegeKundeEin( String name, String str, String nr, String plz, String ort, String iban, String pw) throws KundeExistiertBereitsException {
		int id = meineKunden.kundenAnzahl();
		Kunde k = new Kunde(id, name, str, nr, plz, ort, iban, pw);
		
		meineKunden.einfuegen(k);
		
		return k;
		
		
	}


		
	
	//Methode, die pr�ft, ob es Kunden mit id und passwort gibt
	public Kunde loginUeberpruefungKunde(int id, String passwort) {
		Kunde kunde = (meineKunden.ueberpruefeKundenLogin(id, passwort));
		
		return kunde;
	}

	public Logbuch getLogbuch() {
		return logbuch;
	}
	
	public ArtikelVerwaltung getArtikelVerwaltung() {
		return meineArtikel;
	}
	

}
