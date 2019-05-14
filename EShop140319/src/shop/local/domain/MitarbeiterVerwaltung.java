package shop.local.domain;

import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.persistance.PersistenceManager;
import shop.local.valueobjects.Mitarbeiter;

public class MitarbeiterVerwaltung {
	
	static final String MITARBEITERSAVE = "Mitarbeiter.save";
	
	private Vector <Mitarbeiter> mitarbeiterListe = new Vector <Mitarbeiter>();
	private PersistenceManager pm;
	
	public MitarbeiterVerwaltung(PersistenceManager pm) {
		this.pm = pm;
		try {
			liesDaten(MITARBEITERSAVE);
		} catch (ClassNotFoundException | IOException e) {
			System.out.println(" Mitarbeiter.save fehlt ");
		}
	}
	
	public void Save() {
		try {
			schreibeDaten(MITARBEITERSAVE);
		} catch (IOException e) {
			System.out.println(" Mitarbeiter.save fehlt ");
		}
	}
	
	
	//Methode, die Mitarbeiter einf�gt
	
	public void einfuegen(Mitarbeiter einMitarbeiter) throws MitarbeiterExistiertBereitsException {
		
		if (mitarbeiterListe.contains(einMitarbeiter)) {
			throw new MitarbeiterExistiertBereitsException(einMitarbeiter, " existiert bereits. Sie koennen sich einloggen.");
		}
		mitarbeiterListe.add(einMitarbeiter); //in mitarbeiterListe wird Mitarbeiter hinzugefügt 
	
	}

	// Methode, die Mitarbeiternummer vergibt: 500+
	public int mitarbeiterAnzahl() {
		int neueId = mitarbeiterListe.size();
		return neueId;
	}

	
	//Methode, die prueft, ob Passwort und Id korrekt sind und Mitarbeiter zurueckgibt
	
	public Mitarbeiter ueberpruefenMitarbeiterLogin (int id, String passwort) {
		Iterator<Mitarbeiter> it = mitarbeiterListe.iterator();
		while (it.hasNext()) {
			Mitarbeiter einMitarbeiter = it.next();
			if (einMitarbeiter.getId() == id && einMitarbeiter.getPasswort().equals(passwort)) {
				//mitarbeiterListe.add(einMitarbeiter);
				return einMitarbeiter;
			}
		}
	
		return null;
	}
	

	public void schreibeDaten(String datei) throws IOException {
		// PersistenzManager f�r Schreibvorg�nge �ffnen
		pm.openForWriting(datei);

		Iterator<Mitarbeiter> it = mitarbeiterListe.iterator();
		while (it.hasNext()) {
			Mitarbeiter m = it.next();
			pm.speichereMitarbeiter(m);
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

			Mitarbeiter m;
			do {
				// Artikel-Objekt einlesen
				m = pm.ladeMitarbeiter();
				if (m != null) {
					// Artikel in Liste einf�gen
					mitarbeiterListe.add(m);
				}
			} while (m != null);
		} catch (IOException e) {
			// TODO: exception
		}
		// Persistenz-Schnittstelle wieder schlie�en
		pm.close();
	}
	
	
}
