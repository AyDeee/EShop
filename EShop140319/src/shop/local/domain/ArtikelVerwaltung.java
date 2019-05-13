package shop.local.domain;



import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.persistance.PersistenceManager;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Person;
import shop.local.valueobjects.Warenkorb;


/**
 * Klasse zur Verwaltung von Artikeln.
 * 
 */
public class ArtikelVerwaltung {

	static final String ARTIKELSAVE = "Artikel.save";
	
	// Verwaltung des Artikelbestands in einer verketteten Liste
	private Vector <Artikel> artikelListe = new Vector <Artikel>();
	private Logbuch logbuch; 
	private PersistenceManager pm;
	
	public ArtikelVerwaltung(Logbuch logbuch, PersistenceManager pm) {
		this.pm = pm;
		this.logbuch = logbuch; 
		try {
			liesDaten(ARTIKELSAVE);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Save() {
		try {
			schreibeDaten(ARTIKELSAVE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Methode, die ein Artikel an das Ende der Artikelliste einf�gt.
	 * 
	 */
	public void einfuegen(Artikel einArtikel, Person person) throws ArtikelExistiertBereitsException {
		if (artikelListe.contains(einArtikel)) { //überprüft ob Nummer schon vergeben
			throw new ArtikelExistiertBereitsException(einArtikel);
		}

		// das �bernimmt die ArtikelListe:
		artikelListe.add(einArtikel);
		logbuch.NeuerEintrag(true, person, einArtikel, einArtikel.getBestand());
	}

	
	//Methode zum Loeschen eines Artikels aus dem Bestand. 
	
	public void loeschen(int id, Person person) throws ArtikelExistiertNichtException {
		int index = 0;
		while(index < artikelListe.size())
		{
			Artikel currentArtikel = artikelListe.get(index);
			if(currentArtikel.getNummer() == id) {
				artikelListe.remove(index);
				logbuch.NeuerEintrag(false, person, currentArtikel, currentArtikel.getBestand());
				return;//funktioniert nur wenn eine id nur einmalig vergeben werden kann
			}
			else if (currentArtikel.getNummer() != id) {
				throw new ArtikelExistiertNichtException(currentArtikel);
			}
			else {
				index++;
			}
		}	
	}
	
	

	/**
	 * Methode, die anhand einer bezeichnung nach Artikel sucht. Es wird eine Liste von Artikeln
	 * zur�ckgegeben, die alle Artikel mit exakt �bereinstimmendem bezeichnung enth�lt.
	 * 
	 * @return Liste der Artikel mit gesuchtem Titel (evtl. leer)
	 */
	public Vector <Artikel> sucheArtikel(String bezeichnung) {
		Vector <Artikel> ergebnis = new Vector <Artikel>();
		Iterator <Artikel> it = artikelListe.iterator();
		
		while (it.hasNext()) {
			Artikel artikel = it.next();
			if (artikel.getBezeichnung().equals(bezeichnung)) {
				ergebnis.add(artikel);
			}			
		}
		return ergebnis;
	}
	
	//TODO kommt immer eine Index out of bounds Exception
	public boolean identischeNummer (int nr) {
		Iterator <Artikel> it = artikelListe.iterator();
		
		while (it.hasNext()) {
			Artikel artikel = it.next();
			if (artikel.getNummer() == nr) {
				return true;
			}
		}
		return false;	
	}
	
	
	/**
	 * Methode, die einen bestimmten Artikel aus der Artikelliste sucht
	 * @param artikel
	 * @return Artikel aus der Liste
	 */
	
	public Artikel sucheEindeutigenArtikel(int nummer) {

//		for(Artikel currentArtikel: artikelListe)				Variante for each Schleife 
//		{
//			if(currentArtikel.getNummer() == nummer) {
//				return currentArtikel;
//			}
//		}
		
		int index = 0;
		while(index<artikelListe.size())
		{
			Artikel currentArtikel = artikelListe.get(index);
			if(currentArtikel.getNummer() == nummer) {
				return currentArtikel;
			}
			else {
				index++;	
			}
			
		}
		return null;
	}
	
	/**
	 * Methode, die eine KOPIE des Artikelbestands zur�ckgibt.
	 * (Eine Kopie ist eine gute Idee, wenn ich dem Empf�nger 
	 * der Daten nicht erm�glichen m�chte, die Original-Daten 
	 * zu manipulieren.)
	 * 
	 * @return Liste aller Artikel im Artikelbestand (Kopie)
	 */
	public Vector <Artikel> getArtikelListe() {
		return new Vector <Artikel>(artikelListe);
	
	}
	
	/**
	 * Methode zum Schreiben der Artikeldaten in eine Datei.
	 * 
	 * @param datei
	 *            Datei, in die der Artikelbestand geschrieben werden soll
	 * @throws IOException
	 */
	public void schreibeDaten(String datei) throws IOException {
		// PersistenzManager f�r Schreibvorg�nge �ffnen
		pm.openForWriting(datei);

		Iterator<Artikel> it = artikelListe.iterator();
		while (it.hasNext()) {
			Artikel a = it.next();
			pm.speichereArtikel(a);
		}
		// Persistenz-Schnittstelle wieder schlie�en
		pm.close();
	}

	/**
	 * Methode zum Einlesen von daten aus einer Datei.
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void liesDaten(String datei) throws IOException, ClassNotFoundException {
		// PersistenzManager f�r Lesevorg�nge �ffnen
		try {
			pm.openForReading(datei);

			Artikel einArtikel;
			do {
				// Artikel-Objekt einlesen
				einArtikel = pm.ladeArtikel();
				if (einArtikel != null) {
					// Artikel in Liste einf�gen
					artikelListe.add(einArtikel);
				}
			} while (einArtikel != null);
		} catch (IOException e) {
			// TODO: exception
		}
		// Persistenz-Schnittstelle wieder schlie�en
		pm.close();
	}
	


}
