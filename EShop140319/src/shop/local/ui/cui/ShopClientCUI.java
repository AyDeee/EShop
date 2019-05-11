package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Iterator;

import java.util.Vector;

import shop.local.domain.ArtikelVerwaltung;
import shop.local.domain.EShop;
import shop.local.domain.Logbuch;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.ArtikelImWarenkorb;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Rechnung;



/**
 * Klasse für sehr einfache Benutzungsschnittstelle für die
 * Bibliothek. Die Benutzungsschnittstelle basiert auf Ein-
 * und Ausgaben auf der Kommandozeile, daher der Name CUI
 * (Command line User Interface).
 * 
 * @author teschke
 * @version 1 (Verwaltung der Buecher in verketteter Liste)
 */
public class ShopClientCUI {

	private EShop shop;
	private BufferedReader in;
	private ArtikelVerwaltung artikelVerwaltung;
	private boolean datenInitialisieren;
	
	public ShopClientCUI() throws IOException {
		File f = new File(Logbuch.LOGSAVE);
		if(f.exists()) { 
			//wenn mindestens das Logbuch gespeichert wurde, brauchen keine Testdaten erzeigt werden
			datenInitialisieren = false;
		}
		else
		{
			datenInitialisieren = true;
		}
		
		// die Shop-Verwaltung erledigt die Aufgaben, 
		// die nichts mit Ein-/Ausgabe zu tun haben
		shop = new EShop();
		artikelVerwaltung = shop.getArtikelVerwaltung();
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		in = new BufferedReader(new InputStreamReader(System.in));
	}
	
	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zum Einlesen von Benutzereingaben.
	 */
	private String liesEingabe() throws IOException {
		// einlesen von Konsole
		
		return in.readLine();
	}
	
	
	
	
	private void gibMenueAus() {
		System.out.println("Weiter als Mitarbeiter  'm'");
		System.out.println("Weiter als Kunde 	'k'");
		System.out.println("Beenden:        	'q'");
	}
	
	
	private void gibStartMenueAus() {
		System.out.println("einloggen  	'e'");
		System.out.println("registrieren  	'r'");
		System.out.println("Beenden:        'q'");
	}
	
	
	
/**
 * - - - - - - MITARBEITER - - - - - - - - - 
 */
	
	//Verarbeitung einloggen oder registrieren -->line = Eingabe
	private void verarbeiteEingabeStartMenueMitarbeiter(String line) throws IOException, MitarbeiterExistiertBereitsException {
		
		
		Mitarbeiter mitarbeiter = null;
		String input = "";
		
		// Variablen fuer den Mitarbeiter Login
		int id;
		String passwort;
		String nr;
		

		// Variablen fuer die Kundenregistrierung
		String name;
		String wohnort;
		String plz;
		String str;
		String hausnummer;
		String iban;
		
		
		switch(line) {
		case "e":
			System.out.println("Login:");
			System.out.println("ID");
			System.out.print("> ");
			nr = liesEingabe();
			id = Integer.parseInt(nr);
			System.out.println("Passwort");
			System.out.print("> ");
			passwort = liesEingabe();

			mitarbeiter = shop.loginUeberpruefungMitarbeiter(id, passwort);
			
			if (mitarbeiter != null) {
				System.out.println("Einloggen erfolgreich - Willkommen "+ mitarbeiter.getName()+"!");
				do {
					gibMitarbeiterMenueAus();
				try {
					input = liesEingabe();
					verarbeiteEingabeMitarbeiter(input, mitarbeiter);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					}
				} while (!input.equals("q"));
			} else {
				System.out.println("Leider waren deine Daten nicht richtig. Versuche es erneut.");	
			}
			break;
			
		case "r":
			System.out.println("Registrieren:");
			System.out.println("Name>");
			name = liesEingabe();
			System.out.println("Passwort>");
			passwort = liesEingabe();
			System.out.println("Strasse>");
			str = liesEingabe();
			System.out.println("Hausnummer>");
			hausnummer = liesEingabe();
			System.out.println("PLZ>");
			plz = liesEingabe();
			System.out.println("Wohnort>");
			wohnort = liesEingabe();
			System.out.println("IBAN>");
			iban = liesEingabe();
			
			try {
				mitarbeiter = shop.fuegeMitarbeiterEin(name, str, hausnummer, plz, wohnort, iban, passwort);
				System.out.println("Registrieren erfolgreich - Ihre ID: "+ mitarbeiter.getId());
			}catch (MitarbeiterExistiertBereitsException me) {
				// Hier Fehlerbehandlung...
				System.out.println(me.getMessage());
				//me.printStackTrace()
			} 
				if (mitarbeiter != null) {
					do {
						System.out.println("Willkommen "+ mitarbeiter.getName()+"!");
						gibMitarbeiterMenueAus();
						try {
							input = liesEingabe();
							verarbeiteEingabeMitarbeiter(input, mitarbeiter);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}while (!input.equals("q"));
					
				}
		
			break;
			}
			
			// TODO evtl. PLZ als Int| der Einfachheit halber sind alle Eingaben Strings
			//mitarbeiter = shop.fuegeMitarbeiterEin(name, str, hausnummer, plz, wohnort, iban, passwort);
		}
	

	private void gibMitarbeiterMenueAus() {	
		System.out.print("Befehle: \n  ---------------------");
		System.out.print("         \n  Artikel ausgeben:  			'a'");
		System.out.print("         \n  Artikel loeschen: 			'd'");
		System.out.print("         \n  Artikel einfuegen: 			'e'");
		System.out.print("         \n  Artikel suchen:  			'f'");
		System.out.print("         \n  Artikel alphabetisch sortieren:	'sa'");
		System.out.print("         \n  Artikel sortieren nach Nummer:	'sn'");
		System.out.print("         \n  Artikelbestand erhoehen/verringern:	'b'");
		System.out.print("         \n  Log anzeigen: 			'l'");
		System.out.print("         \n  ---------------------");
		System.out.println("         \n  Beenden:     	'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
	}
	
	/* (non-Javadoc)
	 * 
	 * Interne (private) Methode zur Verarbeitung von Eingaben
	 * und Ausgabe von Ergebnissen.
	 */
	private void verarbeiteEingabeMitarbeiter(String line, Mitarbeiter mitarbeiter) throws IOException {
		String nummer;
		int nr;
		String titel;
		Vector <Artikel> liste;
		String best;
		int bestand;
		Artikel artikel;
		String preisS; //preis String, wird danach in float umgewandelt
		float preis;
		
		// Eingabe bearbeiten:
		switch (line) {
		case "a":
			liste = shop.gibAlleArtikel();
			gibArtikellisteAus(liste);
			break;
		case "d":
			// lies die notwendigen Parameter, einzeln pro Zeile
			System.out.print("nummer > ");
			nummer = liesEingabe();
			nr = Integer.parseInt(nummer);			
			shop.loescheArtikel(nr, mitarbeiter);
			break;
		case "e":
			// lies die notwendigen Parameter, einzelns pro Zeile
			System.out.print("nummer > ");
			nummer = liesEingabe();			//man kann immer nur einen String einlesen und dann zum Int konverten
			nr = Integer.parseInt(nummer);
			System.out.print("titel  > ");
			titel = liesEingabe();
			System.out.print("bestand > ");
			best = liesEingabe();
			bestand = Integer.parseInt(best);
			System.out.print("preis > ");
			preisS = liesEingabe();
			preis = Float.parseFloat(preisS);
			
			try {
				shop.fuegeArtikelEin(titel, nr, bestand, preis, mitarbeiter);
				System.out.println("Einfuegen ok");
			} catch (ArtikelExistiertBereitsException e) {
				// Hier Fehlerbehandlung...
				System.out.println("Fehler beim Einfuegen");
				e.printStackTrace(); //zeigt den genauen Ort wo im Programm die Exception erzeugt wurde
			}
			break;
		case "f":
			System.out.print("titel  > ");
			titel = liesEingabe();
			liste = shop.sucheNachTitel(titel);
			System.out.print(liste);
			break;
		case "sa":
			liste = shop.gibAlleArtikel(); //macht Liste von allen Artikeln
			gibArtikellisteAlphAus(liste);
			
			
			break;
		case "sn":
			liste = shop.gibAlleArtikel();
			gibArtikellisteNumAus(liste);
			
			break;
			
			
		case "b":
			System.out.print("Nummer  > ");
			nummer = liesEingabe();
			nr = Integer.parseInt(nummer);
			System.out.print("Bestand > "); 
			best = liesEingabe(); 
			bestand = Integer.parseInt(best); 
			artikel = shop.bestandErhoehen(nr, bestand, mitarbeiter);
			
			System.out.println("Der Bestand vom Artikel "+ "'"+ artikel.getBezeichnung() + "'" + " mit der Artikelnummer " + "'"+artikel.getNummer()+"'"+" wurde auf "+artikel.getBestand()+" geaendert.");
					
			break;
			
			
		case "l": // Log ausgeben
		
			String ausgabe = shop.getLogbuch().Print();
			System.out.print(ausgabe);
			break;
		}
		
		
	}
	
	
	
/**
 * - - - - - - - - - - KUNDE - - - - -  - - - - - - -
 */

	//Registrieren oder einloggen
	private void verarbeiteEingabeStartMenueKunde(String line) throws IOException, KundeExistiertBereitsException  {
		Kunde kunde = null;
		String input = "";
		
		// Variabeln f�r Mitarbeiter Login
		int id;
		String passwort;
		String nr;
		

		// Variabeln f�r die Kundenregistrierung
		String name;
		String wohnort;
		String plz;
		String str;
		String hausnummer;
		String iban;
		
		
		switch(line) {
		case "e":
			System.out.println("Login:");
			System.out.println("ID");
			System.out.print("> ");
			nr = liesEingabe();
			id = Integer.parseInt(nr);
			System.out.println("Passwort");
			System.out.print("> ");
			passwort = liesEingabe();

			kunde = shop.loginUeberpruefungKunde(id, passwort);
			
			if (kunde != null) {
				System.out.println("Einloggen erfolgreich - Willkommen "+ kunde.getName()+"!");
				do {
				gibKundenMenueAus();
				try {
					input = liesEingabe();
					verarbeiteEingabeKunde(input, kunde);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} 
				while (!input.equals("q"));
			} else {
				System.out.println("Leider waren deine Daten nicht richtig. Versuche es erneut.");	
			}
			break;
			
		case "r":
			System.out.println("Registrieren:");
			System.out.println("Name>");
			name = liesEingabe();
			System.out.println("Passwort>");
			passwort = liesEingabe();
			System.out.println("Strasse>");
			str = liesEingabe();
			System.out.println("Hausnummer>");
			hausnummer = liesEingabe();
			System.out.println("PLZ>");
			plz = liesEingabe();
			System.out.println("Wohnort>");
			wohnort = liesEingabe();
			System.out.println("IBAN>");
			iban = liesEingabe();

			try {
				kunde = shop.fuegeKundeEin(name, str, hausnummer, plz, wohnort, iban, passwort);
				System.out.println("Registrieren erfolgreich - Ihre ID: "+ kunde.getId()); //IST MOM. DRIN ALS PRUEFUNG FUER MICH
			}catch (KundeExistiertBereitsException ke) {
				// Hier Fehlerbehandlung...
				System.out.println(ke.getMessage());
				//me.printStackTrace()
			} 
			if (kunde != null) {
				do {
					System.out.println("Willkommen "+ kunde.getName()+"!");
					gibKundenMenueAus();
					try {
						input = liesEingabe();
						verarbeiteEingabeKunde(input, kunde);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}while (!input.equals("q"));
			}
		
			break;
		}
		
	}

	

		

	
	
	
	private void gibKundenMenueAus() {
		System.out.print("Befehle: \n  Artikel anzeigen:                  'a'");
		System.out.print("         \n  Artikel alphabetisch sortieren:    'sa'");
		System.out.print("         \n  Artikel sortieren nach Nummer:     'sn'");
		System.out.print("         \n  Artikel suchen:                    'f'");
		System.out.print("         \n  Artikel in Warenkorb legen:        'we'");
		System.out.print("         \n  Artikelanzahl im Warenkorb aendern:'wa'");
		System.out.print("         \n  Warenkorb anzeigen:                'w'");
		System.out.print("         \n  Warenkorb leeren:		     'wl'");
		System.out.print("         \n  Warenkorb kaufen:                  'k'");
		System.out.print("         \n  ---------------------");
		System.out.println("         \n  Beenden:        'q'");
		System.out.print("> "); // Prompt
		System.out.flush(); // ohne NL ausgeben
			
		}
	
	
	private void verarbeiteEingabeKunde(String line, Kunde eingeloggterKunde) throws IOException {
		String nummer;
		int nr;
		String titel;
		Vector <Artikel> liste;
		String best;
		int bestand;
		Artikel artikel;
		
		// Eingabe bearbeiten:
		switch (line) {
		case "a":
			liste = shop.gibAlleArtikel();
			gibArtikellisteAus(liste);
			break;
		case "f":
			System.out.print("titel  > ");
			titel = liesEingabe();
			liste = shop.sucheNachTitel(titel);
			System.out.print(liste);
			break;
		case "sa":
			liste = shop.gibAlleArtikel();
			gibArtikellisteAlphAus(liste);
			
			
			break;
		case "sn":
			liste = shop.gibAlleArtikel();
			gibArtikellisteNumAus(liste);
			
			break;
			
		case "we":									// fügt nur einen Artikel hinzu
			System.out.print("Artikelnummer > ");
			nummer = liesEingabe();
			nr = Integer.parseInt(nummer);
			
			artikel=shop.sucheNachNummer(nr);

		    shop.anzahlAendernArtikelInWarenkorb(nr,1, eingeloggterKunde);
		  //TODO wenn artikel nicht eingef�gt wird, weil nicht mehr verf�gbar, meldung an den kunden
			System.out.println("Sie haben den Artikel "+ artikel.getBezeichnung()+ " erfolgreich eingef�gt");
	
			break;
			
		case "wa":									// fügt Artikel hinzu un man kann direkt die Anzahl angeben/ändern
			
			System.out.print("Artikelnummer > ");
			nummer = liesEingabe();
			nr = Integer.parseInt(nummer);
			
			System.out.print("Anzahl > ");
			best = liesEingabe();
			bestand = Integer.parseInt(best);
			
			artikel=shop.sucheNachNummer(nr);
			
			if (artikel.getBestand()<bestand) {
				System.out.println("Es sind leider nur noch " + artikel.getBestand() +" Artikel verf�gbar");
				System.out.println("Versuche es erneut");
			}else {
			while (bestand==0) {
				System.out.println("Eingabe war ung�ltig, versuche es erneut.");
				System.out.print("Anzahl > ");
				best = liesEingabe();
				bestand = Integer.parseInt(best);
			}
			shop.anzahlAendernArtikelInWarenkorb(nr,bestand, eingeloggterKunde);
		    System.out.println("Sie haben den Artikel "+ artikel.getBezeichnung()+ " erfolgreich eingef�gt");
			}
			break;
			
		case "wl": //Warenkorb leeren
			eingeloggterKunde.getWarenkorb().WarenkorbLeeren();
			break;
			
		case "w":
			System.out.println("Warenkorb:");
			Vector<ArtikelImWarenkorb> vorhandeneArtikel = eingeloggterKunde.getWarenkorb().getWarenkorbEintraege();
			
			Iterator<ArtikelImWarenkorb> it = vorhandeneArtikel.iterator(); //Iterator<ArtikelImWarenkorb> nur für diesen Typ
			int laufendeNummer = 1;
			
//			if(!it.hasNext()) {
//				System.out.print("Dein Warenkorb ist leer!");
//			}
			
			if(vorhandeneArtikel.size() == 0) {
				System.out.print("Dein Warenkorb ist leer!");
			}
			
			while (it.hasNext()) {
				ArtikelImWarenkorb aktuellerArtikel = it.next();
				System.out.println(laufendeNummer + ". " + aktuellerArtikel.getArtikel().getBezeichnung() + " , " + aktuellerArtikel.getAnzahl());
				laufendeNummer++;
			}
	
			break;
		case "k": 
			Rechnung rechnung = shop.kaufeArtikelImWarenkorb(eingeloggterKunde);
			String ausgabe = rechnung.Print();
			System.out.print(ausgabe);
		}

			
		}

	

	
	
	
/**
 * - - - - - - - - - - ARTIKEL  - - - - - - - - - - - -
 */

	/* 
	 * Interne (private) Methode zum Ausgeben der Artikelliste
	 */
	private void gibArtikellisteAus(Vector <Artikel> liste) {
		
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator <Artikel> it = liste.iterator();
			while (it.hasNext()) {
				Artikel artikel = (Artikel) it.next();
				
				System.out.println(artikel);
			}
		}
	}
	
	
	private void gibArtikellisteAlphAus(Vector <Artikel> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			liste.sort(Artikel.ArtikelNameComparator);
			Iterator <Artikel> it = liste.iterator();
			while (it.hasNext()) {
				Artikel artikel = (Artikel) it.next();
				
				System.out.println(artikel);
			}
		}


	}
	
	
	private void gibArtikellisteNumAus(Vector<Artikel> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			liste.sort(Artikel.ArtikelNummerComparator);
			Iterator<Artikel> it = liste.iterator();
			while (it.hasNext()) {
				Artikel artikel = (Artikel) it.next();
				
				System.out.println(artikel);
			}
		}


	}
	
	
	
	

	/**
	 * Methode zur Ausführung der Hauptschleife:
	 * - Menü ausgeben
	 * - Eingabe des Benutzers einlesen
	 * - Eingabe verarbeiten und Ergebnis ausgeben
	 * (EVA-Prinzip: Eingabe-Verarbeitung-Ausgabe)
	 * @throws MitarbeiterExistiertBereitsException 
	 */
	public void run() throws MitarbeiterExistiertBereitsException, KundeExistiertBereitsException {
		// Variable für Eingaben von der Konsole
		String input = ""; 
		if(datenInitialisieren == true)
		{
			Mitarbeiter m = null;
			try {
				m = shop.fuegeMitarbeiterEin("klaus","musterstrasse","3","12345","musterstadt","DE2345678764544","123");
				shop.fuegeMitarbeiterEin("sarah","musteralle","5","15675","musterdorf","DE2345678764544","123");
			}catch (MitarbeiterExistiertBereitsException me) {
				me.printStackTrace();
			}
			try {
				shop.fuegeArtikelEin("stuhl", 12, 1, 10.0f,m);
				shop.fuegeArtikelEin("banane", 13, 100, 1.30f,m); //bei float immer f dahinter
				shop.fuegeArtikelEin("tisch", 11, 4, 15.0f,m);
	
			} catch (ArtikelExistiertBereitsException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				shop.fuegeKundeEin("Kunde1", "Baumstr"	, "6","12344","ort", "DE12345675432", "123");
				shop.fuegeKundeEin("Kunde2", "strasse"	, "1","12344","ort", "DE12345675432", "123");
				shop.fuegeKundeEin("Kunde3", "strasse"	, "1","12344","ort", "DE12345675432", "123");
			}catch (KundeExistiertBereitsException ke) {
				ke.printStackTrace();
			}
			
		}
		

		while(true) {
			
		
		gibMenueAus();
		try {
			input=liesEingabe();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (input.equals("q"))
		{
			break;
		}
		
		//---------------------Mitarbeiter----------------------------------
		do {
			if (input.equals("m")){ //wenn die Eingabe == m dann öffnet sich das Startmenü (regis oder login)
				gibStartMenueAus();
				try {
					input = liesEingabe();
					verarbeiteEingabeStartMenueMitarbeiter(input);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		//---------------------Kunde----------------------------------------
				
			}else if(input.equals("k")) {
				
				gibStartMenueAus();
				try {
					input = liesEingabe();
					verarbeiteEingabeStartMenueKunde(input);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
		//---------------------Fehlerhafte Eingabe---------------------------		

			}else {
				System.out.println("Eingabe nicht erkannt, versuche es erneut");
				gibMenueAus();
				try {
					input=liesEingabe();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}while (!input.equals("q"));
		
		}
		shop.Save();
	}
		

	/**
	 * Die main-Methode...
	 * @throws MitarbeiterExistiertBereitsException 
	 */
	public static void main(String[] args) throws MitarbeiterExistiertBereitsException, KundeExistiertBereitsException {
		ShopClientCUI cui;
		try {
			cui = new ShopClientCUI();
			cui.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
