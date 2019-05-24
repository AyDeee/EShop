package shop.local.ui.cui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.Iterator;

import java.util.Vector;

import shop.local.domain.EShop;
import shop.local.domain.Logbuch;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.FalscheBestandsgroesseException;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.ArtikelImWarenkorb;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Rechnung;

public class ShopClientCUI {

	private EShop shop;
	private BufferedReader in;
	private boolean datenInitialisieren;

	public ShopClientCUI() throws IOException {
		File f = new File(Logbuch.LOGSAVE);
		if (f.exists()) {
			// wenn mindestens das Logbuch gespeichert wurde, brauchen keine Testdaten
			// erzeigt werden
			datenInitialisieren = false;
		} else {
			datenInitialisieren = true;
		}

		// die Shop-Verwaltung erledigt die Aufgaben,
		// die nichts mit Ein-/Ausgabe zu tun haben
		shop = new EShop();
		// Stream-Objekt fuer Texteingabe ueber Konsolenfenster erzeugen
		in = new BufferedReader(new InputStreamReader(System.in));
	}

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

	// Verarbeitung einloggen oder registrieren -->line = Eingabe
	private void verarbeiteEingabeStartMenueMitarbeiter(String line) {

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

		switch (line) {
		case "e":
			try {
				System.out.println("Login:");
				System.out.println("ID");
				System.out.print("> ");
				nr = liesEingabe();
				id = Integer.parseInt(nr);
				System.out.println("Passwort");
				System.out.print("> ");
				passwort = liesEingabe();
			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
				break;
			}

			mitarbeiter = shop.loginUeberpruefungMitarbeiter(id, passwort);

			if (mitarbeiter != null) {
				System.out.println("Einloggen erfolgreich - Willkommen " + mitarbeiter.getName() + "!");
				do {
					gibMitarbeiterMenueAus();
					try {
						input = liesEingabe();
						verarbeiteEingabeMitarbeiter(input, mitarbeiter);
					} catch (IOException e) {
						System.out.println("Fehlerhafte Eingabe");
					}
				} while (!input.equals("q"));
			} else {
				System.out.println("Leider waren deine Daten nicht richtig. Versuche es erneut.");
			}
			break;

		case "r":
			try {
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

			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
				break;
			}
			try {
				mitarbeiter = shop.fuegeMitarbeiterEin(name, str, hausnummer, plz, wohnort, iban, passwort);
				System.out.println("Registrieren erfolgreich - Ihre ID: " + mitarbeiter.getId());
			} catch (MitarbeiterExistiertBereitsException me) {
				// Hier Fehlerbehandlung
				System.out.println(me.getMessage());
				// me.printStackTrace()
			}
			if (mitarbeiter != null) {
				do {
					System.out.println("Willkommen " + mitarbeiter.getName() + "!");
					gibMitarbeiterMenueAus();
					try {
						input = liesEingabe();
						verarbeiteEingabeMitarbeiter(input, mitarbeiter);
					} catch (IOException e) {
						System.out.println("Fehlerhafte Eingabe");
					}
				} while (!input.equals("q"));

			}

			break;
		}
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

	private void verarbeiteEingabeMitarbeiter(String line, Mitarbeiter mitarbeiter) {
		String nummer;
		int nr;
		String titel;
		Vector<Artikel> liste;
		String best;
		int bestand;
		Artikel artikel;
		String preisS; // preis String, wird danach in float umgewandelt
		String packungsgroesseS;
		int packungsgroesse;
		float preis;

		// Eingabe bearbeiten:
		switch (line) {
		case "a":
			liste = shop.gibAlleArtikel();
			gibArtikellisteAus(liste);
			break;
		case "d":
			// lies die notwendigen Parameter, einzeln pro Zeile
			try {
				System.out.print("nummer > ");
				nummer = liesEingabe();
				nr = Integer.parseInt(nummer);
			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
				break;
			}
			try {
				Artikel art = shop.loescheArtikel(nr, mitarbeiter);
				System.out.println(art.getBezeichnung() + " wurde geloescht!");
			} catch (ArtikelExistiertNichtException aen) {
				System.out.println(aen.getMessage());

			}
			break;
		case "e":
			// lies die notwendigen Parameter, einzelns pro Zeile
			try {
				System.out.print("nummer > ");
				nummer = liesEingabe(); // man kann immer nur einen String einlesen und dann zum Int konverten
				nr = Integer.parseInt(nummer);
				if (nr < 0) {
					System.out.println("Die Artikelnummer darf nicht kleiner als 0 sein!");
					break;
				}

				System.out.print("titel  > ");
				titel = liesEingabe();
				System.out.print("bestand > ");
				best = liesEingabe();
				bestand = Integer.parseInt(best);
				if (bestand < 1) {
					System.out.println("Der Bestand darf nicht kleiner als 1 sein!");
					break;
				}

				System.out.print("preis > ");
				preisS = liesEingabe();
				preis = Float.parseFloat(preisS);

				if (preis < 0) {
					System.out.println("Der Preis darf nicht kleiner als 0 sein!");
					break;
				}
				System.out.print("packungsgroesse (Für Einzelartikel Enter klicken) > ");
				packungsgroesseS = liesEingabe();

			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
				e.printStackTrace();
				break;
			}
			try {
				if (packungsgroesseS.isEmpty()) {
					shop.fuegeArtikelEin(titel, nr, bestand, preis, mitarbeiter);
				} else {
					packungsgroesse = Integer.parseInt(packungsgroesseS);
					shop.fuegeMassengutArtikelEin(titel, nr, bestand, preis, packungsgroesse, mitarbeiter);
				}
				System.out.println("Einfuegen ok");

			} catch (ArtikelExistiertBereitsException e) {
				System.out.println("Fehler beim Einfuegen");
			} catch (FalscheBestandsgroesseException be) {
				System.out.println(be.getMessage());
			}
			break;

		case "f":
			try {
				System.out.print("nummer > ");
				nummer = liesEingabe(); // man kann immer nur einen String einlesen und dann zum Int konverten
				nr = Integer.parseInt(nummer);
			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
				break;
			}
			try {
				artikel = shop.sucheNachNummer(nr);
				System.out.print(artikel);
			} catch (ArtikelExistiertNichtException aen) {
				System.out.println(aen.getMessage());
			}
			break;
		case "sa":
			liste = shop.gibAlleArtikel(); // macht Liste von allen Artikeln
			gibArtikellisteAlphAus(liste);

			break;
		case "sn":
			liste = shop.gibAlleArtikel();
			gibArtikellisteNumAus(liste);

			break;

		case "b":
			try {
				System.out.print("Nummer  > ");
				nummer = liesEingabe();
				nr = Integer.parseInt(nummer);
				System.out.print("Bestand > ");
				best = liesEingabe();
				bestand = Integer.parseInt(best);
			} catch (Exception e) {
				System.out.print("Fehlerhafte Eingabe");
				break;
			}
			try {
				artikel = shop.bestandErhoehen(nr, bestand, mitarbeiter);
				System.out.println("Der Bestand vom Artikel " + "'" + artikel.getBezeichnung() + "'"
						+ " mit der Artikelnummer " + "'" + artikel.getNummer() + "'" + " wurde auf "
						+ artikel.getBestand() + " geaendert.");

			} catch (FalscheBestandsgroesseException be) {
				System.out.println(be.getMessage());
			} catch (ArtikelExistiertNichtException aen) {
				System.out.println(aen.getMessage());
			}
			break;

		case "l": // Log ausgeben
			try {
				System.out.print("Nummer (Klick Enter um alle auzugeben) > ");
				nummer = liesEingabe(); // man kann immer nur einen String einlesen und dann zum Int konverten

				if (nummer.isEmpty()) {
					String ausgabe = shop.getLogbuch().Print();
					System.out.print(ausgabe);
				} else {
					nr = Integer.parseInt(nummer);
					String ausgabe = shop.getLogbuch().Print(nr);
					System.out.print(ausgabe);
				}
			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
			}

		}

	}

	/**
	 * - - - - - - - - - - KUNDE - - - - - - - - - - - -
	 */

	// Registrieren oder einloggen
	private void verarbeiteEingabeStartMenueKunde(String line) {
		Kunde kunde = null;
		String input = "";

		// Variabeln fuer Mitarbeiter Login
		int id;
		String passwort;
		String nr;

		// Variabeln fuer Kundenregistrierung
		String name;
		String wohnort;
		String plz;
		String str;
		String hausnummer;
		String iban;

		switch (line) {
		case "e":
			try {
				System.out.println("Login:");
				System.out.println("ID");
				System.out.print("> ");
				nr = liesEingabe();
				id = Integer.parseInt(nr);
				System.out.println("Passwort");
				System.out.print("> ");
				passwort = liesEingabe();

			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
				break;
			}
			kunde = shop.loginUeberpruefungKunde(id, passwort);

			if (kunde != null) {
				System.out.println("Einloggen erfolgreich - Willkommen " + kunde.getName() + "!");
				do {
					gibKundenMenueAus();
					try {
						input = liesEingabe();
						verarbeiteEingabeKunde(input, kunde);
					} catch (IOException e) {
						System.out.println("Fehlerhafte Eingabe");
					}
				} while (!input.equals("q"));
			} else {
				System.out.println("Leider waren deine Daten nicht richtig. Versuche es erneut.");
			}
			break;

		case "r":
			try {
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

			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
				break;
			}

			try {
				kunde = shop.fuegeKundeEin(name, str, hausnummer, plz, wohnort, iban, passwort);
				System.out.println("Registrieren erfolgreich - Ihre ID: " + kunde.getId()); 
			} catch (KundeExistiertBereitsException ke) { 
				//KUNDE EXISTIERT BEREITS RAUS NEHMEN? 
				System.out.println(ke.getMessage());
				// me.printStackTrace()
			}
			if (kunde != null) {
				do {
					System.out.println("Willkommen " + kunde.getName() + "!");
					gibKundenMenueAus();
					try {
						input = liesEingabe();
						verarbeiteEingabeKunde(input, kunde);
					} catch (IOException e) {
						System.out.println("Fehlerhafte Eingabe");
					}
				} while (!input.equals("q"));
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

	private void verarbeiteEingabeKunde(String line, Kunde eingeloggterKunde) {
		String nummer;
		int nr;
		Vector<Artikel> liste;
		String best;
		int bestand;
		Artikel artikel = null;

		// Eingabe bearbeiten:
		switch (line) {
		case "a":
			liste = shop.gibAlleArtikel();
			gibArtikellisteAus(liste);
			break;
		case "f":
			try {
				System.out.print("nummer > ");
				nummer = liesEingabe(); // man kann immer nur einen String einlesen und dann zum Int konverten
				nr = Integer.parseInt(nummer);
			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
				break;
			}

			try {
				artikel = shop.sucheNachNummer(nr);
				System.out.print(artikel);
			} catch (ArtikelExistiertNichtException aen) {
				System.out.println(aen.getMessage());
			}
			break;

		case "sa":
			liste = shop.gibAlleArtikel();
			gibArtikellisteAlphAus(liste);

			break;
		case "sn":
			liste = shop.gibAlleArtikel();
			gibArtikellisteNumAus(liste);

			break;

		case "we": // fügt nur einen Artikel hinzu
			try {
				System.out.print("Artikelnummer > ");
				nummer = liesEingabe();
				nr = Integer.parseInt(nummer);
			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
				break;
			}

			try {
				artikel = shop.sucheNachNummer(nr);
				shop.anzahlAendernArtikelInWarenkorb(nr, 1, eingeloggterKunde);
				System.out.println("Sie haben den Artikel " + artikel.getBezeichnung() + " erfolgreich eingefuegt");
			} catch (ArtikelExistiertNichtException aen) {
				System.out.println(aen.getMessage());
			} catch (FalscheBestandsgroesseException e) {
				System.out.println(e.getMessage());
			}

			// shop.anzahlAendernArtikelInWarenkorb(nr,1, eingeloggterKunde);
			// TODO wenn artikel nicht eingef�gt wird, weil nicht mehr verf�gbar, meldung an
			// den kunden

			break;

		case "wa": // fügt Artikel hinzu un man kann direkt die Anzahl angeben/ändern
			try {
				System.out.print("Artikelnummer > ");
				nummer = liesEingabe();
				nr = Integer.parseInt(nummer);

				System.out.print("Anzahl > ");
				best = liesEingabe();
				bestand = Integer.parseInt(best);
			} catch (Exception e) {
				System.out.println("Fehlerhafte Eingabe");
				break;
			}
			
			try {
				artikel = shop.sucheNachNummer(nr);
			

			if (artikel.getBestand() < bestand) {
				System.out.println("Es sind leider nur noch " + artikel.getBestand() + " Artikel verf�gbar");
				System.out.println("Versuche es erneut");
			} else {
				while (bestand == 0) {
					System.out.println("Eingabe war ungueltig, versuche es erneut.");
					System.out.print("Anzahl > ");
					try {
						best = liesEingabe();
					} catch (IOException e) {				
						System.out.println("Fehlerhafte Eingabe");

					}
					bestand = Integer.parseInt(best);
				}
				try {
					shop.anzahlAendernArtikelInWarenkorb(nr, bestand, eingeloggterKunde);
					System.out.println("Sie haben den Artikel " + artikel.getBezeichnung() + " erfolgreich eingefuegt");
				} catch (FalscheBestandsgroesseException e) {
					System.out.println(e.getMessage());
				}

			}
			} catch (ArtikelExistiertNichtException e1) {
				System.out.println("Artikel existiert nicht.");
			}
			break;

		case "wl": // Warenkorb leeren
			eingeloggterKunde.getWarenkorb().WarenkorbLeeren();
			break;

		case "w":
			System.out.println("Warenkorb:");
			Vector<ArtikelImWarenkorb> vorhandeneArtikel = eingeloggterKunde.getWarenkorb().getWarenkorbEintraege();

			Iterator<ArtikelImWarenkorb> it = vorhandeneArtikel.iterator(); // Iterator<ArtikelImWarenkorb> nur für
																			// diesen Typ
			int laufendeNummer = 1;

			// if(!it.hasNext()) {
			// System.out.print("Dein Warenkorb ist leer!");
			// }

			if (vorhandeneArtikel.size() == 0) {
				System.out.print("Dein Warenkorb ist leer!");
			}

			while (it.hasNext()) {
				ArtikelImWarenkorb aktuellerArtikel = it.next();
				System.out.println(laufendeNummer + ". " + aktuellerArtikel.getArtikel().getBezeichnung() + " , "
						+ aktuellerArtikel.getAnzahl());
				laufendeNummer++;
			}

			break;
		case "k":
			Rechnung rechnung;
			try {
				rechnung = shop.kaufeArtikelImWarenkorb(eingeloggterKunde);
			
			String ausgabe = rechnung.Print();
			System.out.print(ausgabe);
			} catch (ArtikelExistiertNichtException e) {
				
			}
		}

	}

	/**
	 * - - - - - - - - - - ARTIKEL - - - - - - - - - - - -
	 */

	/*
	 * Interne (private) Methode zum Ausgeben der Artikelliste
	 */
	private void gibArtikellisteAus(Vector<Artikel> liste) {

		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			Iterator<Artikel> it = liste.iterator();
			while (it.hasNext()) {
				Artikel artikel = (Artikel) it.next();

				System.out.println(artikel);
			}
		}
	}

	private void gibArtikellisteAlphAus(Vector<Artikel> liste) {
		if (liste.isEmpty()) {
			System.out.println("Liste ist leer.");
		} else {
			liste.sort(Artikel.ArtikelNameComparator);
			Iterator<Artikel> it = liste.iterator();
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
	 * Methode zur Ausführung der Hauptschleife: - Menü ausgeben - Eingabe des
	 * Benutzers einlesen - Eingabe verarbeiten und Ergebnis ausgeben (EVA-Prinzip:
	 * Eingabe-Verarbeitung-Ausgabe)
	 * 
	 * @throws MitarbeiterExistiertBereitsException
	 * @throws ArtikelExistiertBereitsException
	 * @throws FalscheBestandsgroesseException
	 */
	public void run() {
		// Variable für Eingaben von der Konsole
		String input = "";
		if (datenInitialisieren == true) {
			Mitarbeiter m = null;
			try {
				m = shop.fuegeMitarbeiterEin("klaus", "musterstrasse", "3", "12345", "musterstadt", "DE2345678764544",
						"123");
				shop.fuegeMitarbeiterEin("sarah", "musteralle", "5", "15675", "musterdorf", "DE2345678764544", "123");
			} catch (MitarbeiterExistiertBereitsException me) {
				me.printStackTrace();
			}

			try {
				shop.fuegeArtikelEin("stuhl", 12, 1, 10.0f, m);
				shop.fuegeArtikelEin("banane", 13, 100, 1.30f, m); // bei float immer f dahinter
				shop.fuegeArtikelEin("tisch", 11, 4, 15.0f, m);

			} catch (ArtikelExistiertBereitsException | FalscheBestandsgroesseException e) {			
				e.printStackTrace();
			}

			try {
				shop.fuegeKundeEin("Kunde1", "Baumstr", "6", "12344", "ort", "DE12345675432", "123");
				shop.fuegeKundeEin("Kunde2", "strasse", "1", "12344", "ort", "DE12345675432", "123");
				shop.fuegeKundeEin("Kunde3", "strasse", "1", "12344", "ort", "DE12345675432", "123");
			} catch (KundeExistiertBereitsException ke) {
				ke.printStackTrace();
			}

		}

		while (true) {

			gibMenueAus();
			try {
				input = liesEingabe();
			} catch (IOException e1) {
				System.out.println("Fehlerhafte Eingabe");
				continue;
			}

			if (input.equals("q")) {
				break;
			}

			// ---------------------Mitarbeiter----------------------------------
			do {
				if (input.equals("m")) { // wenn die Eingabe == m dann öffnet sich das Startmenü (regis oder login)
					gibStartMenueAus();
					try {
						input = liesEingabe();
						verarbeiteEingabeStartMenueMitarbeiter(input);
					} catch (IOException e) {				
						System.out.println("Fehlerhafte Eingabe");
					}

					// ---------------------Kunde----------------------------------------

				} else if (input.equals("k")) {

					gibStartMenueAus();
					try {
						input = liesEingabe();
						verarbeiteEingabeStartMenueKunde(input);
					}catch(Exception e) {
						System.out.println("Fehlerhafte Eingabe");
					}

					// ---------------------Fehlerhafte Eingabe---------------------------

				} else {
					System.out.println("Eingabe nicht erkannt, versuche es erneut");
					gibMenueAus();
					try {
						input = liesEingabe();
					}catch(Exception e) {
						System.out.println("Fehlerhafte Eingabe");
					}
				}
			} while (!input.equals("q"));

		}
		shop.Save();
	}

	/**
	 * Die main-Methode...
	 * 
	 * @throws MitarbeiterExistiertBereitsException
	 * @throws ArtikelExistiertBereitsException
	 * @throws FalscheBestandsgroesseException
	 */
	public static void main(String[] args) {
		ShopClientCUI cui;
		try {
			cui = new ShopClientCUI();
			cui.run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
