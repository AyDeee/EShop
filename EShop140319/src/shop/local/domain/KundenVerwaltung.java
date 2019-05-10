package shop.local.domain;

import java.io.IOException;
import java.util.Iterator;

import java.util.Vector;

import shop.local.persistance.PersistenceManager;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;

public class KundenVerwaltung {
	
	static final String KUNDENSAVE = "Kunden.save";
	
	private Vector <Kunde> kundenListe = new Vector <Kunde>();
	
	private PersistenceManager pm;
	
	public KundenVerwaltung(PersistenceManager pm) {
		this.pm = pm;
		
		try {
			liesDaten(KUNDENSAVE);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void Save() {
		try {
			schreibeDaten(KUNDENSAVE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Methode, um neuen Kunden zur Liste hinzuzuf�gen
	public void einfuegen(Kunde einKunde)  {
		kundenListe.add(einKunde);
	}

		
	//Methode, die alle Objekte der Kundenliste z�hlt und die Anzahl zur�ckgibt
	public int kundenAnzahl() {
		int neueId= kundenListe.size();
		return neueId;
	}

	//Methode, die die Kundenliste durchgeht, vergleicht ob  id und passwort �bereinstimmt und einen Kunden zur�ckgibt.
	public Kunde ueberpruefeKundenLogin (int id, String passwort) {
		Iterator<Kunde> it = kundenListe.iterator();
		while (it.hasNext()) {
			Kunde einKunde = it.next();
			if (einKunde.getId() == id && einKunde.getPasswort().equals(passwort)) {
				kundenListe.add(einKunde);
				return einKunde;
			}
		}
	
		return null;
	}
	
	
	
	//Methode, die Kunde im vector �ber den index finden und zur�ckgeben
	
	public Kunde sucheEindeutigenKunden(int id, String passwort) {

		Iterator<Kunde> it = kundenListe.iterator();
		while (it.hasNext()) {
			Kunde einKunde = it.next();
			if (einKunde.getId() == id && einKunde.getPasswort().equals(passwort)) {
				return einKunde;
			}
		}
	
		return null;
	}
	
	/**
	 * Methode zum Schreiben der daten in eine Datei.
	 * 
	 * @throws IOException
	 */
	public void schreibeDaten(String datei) throws IOException {
		// PersistenzManager f�r Schreibvorg�nge �ffnen
		pm.openForWriting(datei);

		Iterator<Kunde> it = kundenListe.iterator();
		while (it.hasNext()) {
			Kunde k = it.next();
			pm.speichereKunde(k);
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

			Kunde einKunde;
			do {
				// Artikel-Objekt einlesen
				einKunde = pm.ladeKunde();
				if (einKunde != null) {
					// Artikel in Liste einf�gen
					kundenListe.add(einKunde);
				}
			} while (einKunde != null);
		} catch (IOException e) {
			// TODO: exception
		}
		// Persistenz-Schnittstelle wieder schlie�en
		pm.close();
	}
	
}

