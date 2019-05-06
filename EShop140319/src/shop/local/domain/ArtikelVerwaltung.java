package shop.local.domain;



import java.util.Iterator;
import java.util.Vector;

import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Logbuch;
import shop.local.valueobjects.Person;
import shop.local.valueobjects.Warenkorb;


/**
 * Klasse zur Verwaltung von Artikeln.
 * 
 */
public class ArtikelVerwaltung {

	// Verwaltung des Artikelbestands in einer verketteten Liste
	private Vector <Artikel> artikelListe = new Vector <Artikel>();
	private Logbuch logbuch; 
	
	public ArtikelVerwaltung(Logbuch logbuch) {
		
		this.logbuch = logbuch; 
	}


	/**
	 * Methode, die ein Artikel an das Ende der Artikelliste einf�gt.
	 * 
	 */
	public void einfuegen(Artikel einArtikel, Person person) throws ArtikelExistiertBereitsException {
		if (artikelListe.contains(einArtikel)) { //überprüft ob Nummer schon vergeben
			throw new ArtikelExistiertBereitsException(einArtikel, " - in 'einfuegen()'");
		}

		// das �bernimmt die ArtikelListe:
		artikelListe.add(einArtikel);
		logbuch.NeuerEintrag(true, person, einArtikel, einArtikel.getBestand());
	}

	
	//Methode zum Loeschen eines Artikels aus dem Bestand. 
	
	public void loeschen(int id, Person person) {
		int index = 0;
		while(index < artikelListe.size())
		{
			Artikel currentArtikel = artikelListe.get(index);
			if(currentArtikel.getNummer() == id) {
				artikelListe.remove(index);
				logbuch.NeuerEintrag(false, person, currentArtikel, currentArtikel.getBestand());
				return;//funktioniert nur wenn eine id nur einmalig vergeben werden kann
			} else {
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
			if (artikel.getTitel().equals(bezeichnung)) {
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
	
	


}
