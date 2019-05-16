package shop.local.valueobjects;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;

import shop.local.domain.exceptions.FalscheBestandsgroesseException;

public class Warenkorb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 632667503880964465L;

	private Vector<ArtikelImWarenkorb> warenkorbEintraege;

	public Warenkorb() {
		warenkorbEintraege = new Vector<ArtikelImWarenkorb>();

	}

	public Vector<ArtikelImWarenkorb> getWarenkorbEintraege() {
		return new Vector<ArtikelImWarenkorb>(warenkorbEintraege);
	}

	public void ArtikelHinzufügen(Artikel neuerArtikel) {
		Iterator<ArtikelImWarenkorb> it = warenkorbEintraege.iterator();
		while (it.hasNext()) {
			ArtikelImWarenkorb artikelImWarenkorb = it.next();
			if (artikelImWarenkorb.getArtikel().equals(neuerArtikel)) {
				artikelImWarenkorb.erhoeheAnzahl();
				return;
			}
		}

		warenkorbEintraege.add(new ArtikelImWarenkorb(neuerArtikel));
	}

	public void ArtikelAnzahlAendern(Artikel artikel, int anzahl) throws FalscheBestandsgroesseException {
		if (artikel.getBestand() < anzahl) {
			return;
		}

		// ((Massengutartikel)artikel) ist notwendig, um den datentyp der variablen auf Massengutartikel umzuwandeln
		if (artikel instanceof Massengutartikel) {
			if (!((Massengutartikel) artikel).ueberpruefeAnzahl(anzahl)) {
				throw new FalscheBestandsgroesseException(
						"Es handelt sich um ein Massengutartikel. Anzahl muss ein vielfaches von "
								+ ((Massengutartikel) artikel).getPackungsgroesse() + ".");
			}
		}

		Iterator<ArtikelImWarenkorb> it = warenkorbEintraege.iterator(); // gucken ob Artikel im Warenkorb
		while (it.hasNext()) {
			ArtikelImWarenkorb artikelImWarenkorb = it.next();
			if (artikelImWarenkorb.getArtikel().equals(artikel)) {
				// Artikel im Warenkorb gefunden!
				if (anzahl > 0) {
					artikelImWarenkorb.setAnzahl(anzahl);
				} else {
					warenkorbEintraege.remove(artikelImWarenkorb);
				}
				return;
			}
		}
		if (anzahl < 1) {
			return;
		}

		ArtikelImWarenkorb neuerEintrag = new ArtikelImWarenkorb(artikel);
		neuerEintrag.setAnzahl(anzahl);
		warenkorbEintraege.add(neuerEintrag);
	}

	public void WarenkorbLeeren() {
		warenkorbEintraege.clear();
	}

	public void setWarenkorbEintraege(Vector<ArtikelImWarenkorb> warenkorbEintraege) {
		this.warenkorbEintraege = warenkorbEintraege;

	}

}
