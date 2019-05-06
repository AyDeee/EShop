package shop.local.domain;

import java.util.Iterator;
import java.util.Vector;

import shop.local.valueobjects.Mitarbeiter;

public class MitarbeiterVerwaltung {
	

	
	private Vector <Mitarbeiter> mitarbeiterListe = new Vector <Mitarbeiter>();
	
	
	
	//Methode, die Mitarbeiter einf�gt
	
	public void einfuegen(Mitarbeiter einMitarbeiter)  {

		mitarbeiterListe.add(einMitarbeiter); //in mitarbeiterListe wird Mitarbeiter hinzugefügt 
	
	}

	// Methode, die Mitarbeiternummer vergibt: 500+
	public int mitarbeiterAnzahl() {
		int neueId = mitarbeiterListe.size();
		return neueId;
	}

	
	//Methode, die pr�ft, ob Passwort und Id korrekt sind und Mitarbeiter zur�ckgibt
	
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
	

	
	
}
