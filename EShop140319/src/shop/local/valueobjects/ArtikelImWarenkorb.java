package shop.local.valueobjects;

import java.io.Serializable;

public class ArtikelImWarenkorb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 510314208126221534L;

	public ArtikelImWarenkorb(Artikel neuerArtikel) {
		artikel = neuerArtikel;
		anzahl = 1;
	}

	private Artikel artikel;
	private int anzahl;

	public Artikel getArtikel() {
		return artikel;
	}

	public void erhoeheAnzahl() {
		anzahl++;
	}

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int neueAnzahl) {
		anzahl = neueAnzahl;
	}
}