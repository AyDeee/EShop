package shop.local.valueobjects;

import java.io.Serializable;

import shop.local.domain.exceptions.FalscheBestandsgroesseException;

public class ArtikelImWarenkorb implements Serializable, IArtikel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 510314208126221534L;

	public ArtikelImWarenkorb(Artikel neuerArtikel) {
		artikel = neuerArtikel;
		erhoeheAnzahl();
	}

	private Artikel artikel;
	private int anzahl = 0;

	public Artikel getArtikel() {
		return artikel;
	}

	public void erhoeheAnzahl() {
		if (artikel instanceof Massengutartikel) {
			anzahl += ((Massengutartikel)artikel).getPackungsgroesse();//Typumwandlung --> artikel ist vom Typ Artikel jetzt wird es in Massengut umgewandelt
		}
		else
		{
		anzahl++;
		}
	}

	public int getAnzahl() {
		return anzahl;
	}

	public void setAnzahl(int neueAnzahl) {
		if (artikel instanceof Massengutartikel) {
			Massengutartikel masse = (Massengutartikel)artikel;
			if (masse.ueberpruefeAnzahl(neueAnzahl)) {
				anzahl = neueAnzahl;
			}
		}
		else
		{
			anzahl = neueAnzahl;
		}
		
	}

	@Override
	public String getTitel() {
		return artikel.getTitel();
	}

	@Override
	public int getNummer() {
		return artikel.getNummer();
	}

	@Override
	public float getPreis() {
		return artikel.getPreis();
	}
}