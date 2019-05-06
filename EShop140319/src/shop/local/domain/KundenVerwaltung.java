package shop.local.domain;

import java.util.Iterator;

import java.util.Vector;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;

public class KundenVerwaltung {
	
	private Vector <Kunde> kundenListe = new Vector <Kunde>();
	
	
	
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
	
	
}

