package shop.local.valueobjects;

public class ArtikelImWarenkorb{
	
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