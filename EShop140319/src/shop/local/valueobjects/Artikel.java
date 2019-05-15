package shop.local.valueobjects;

import java.io.Serializable;
import java.util.Comparator;

import shop.local.domain.exceptions.FalscheBestandsgroesseException;

/**
 * Klasse zur Repr�sentation einzelner Artikel
 * 
 */
public class Artikel implements Serializable {

	/**test
	 * 
	 */
	private static final long serialVersionUID = 3052253964821851636L;
	
	// Attribute zur Beschreibung eines Artikels:
	private String bezeichnung;
	private int nummer;
	protected int bestand; //Klassen die erben können darauf zugreifen
	private float preis;

	
	public Artikel(String titel, int nr, int bestand, float preis) {
		nummer = nr;
		this.bezeichnung = titel;
		this.bestand = bestand;
		this.preis = preis;
	}

	//Konstruktor zum Bestand erh�hen
	
	public Artikel(int nummer2) {
		this.nummer= nummer2;
	}
	

	/**
	 * Standard-Methode von Object �berschrieben.
	 * @see java.lang.Object#toString()
	 */
	
	public boolean equals(Object anderesObjekt) {
		if (anderesObjekt instanceof Artikel) {
			Artikel andererArtikel = (Artikel) anderesObjekt;
			return ((nummer == andererArtikel.nummer)
					);
		} 
		return false;
	}

	
	
	
	/*
	 * Ab hier Accessor-Methoden
	 */
	
	public int getNummer() {
		return nummer;
	}

	public float getPreis() {
		return preis;
	}
	
	public String getBezeichnung() {
		return bezeichnung;
	}
	
	public int getBestand() {
		return bestand;
	}
	
	public String toString() { //was ist das für ein Artikel
		return ("nr: "+ nummer + " /Titel: " + bezeichnung + " /Bestand: "+ bestand);
	}
	
	public void setBestand(int bestand) throws FalscheBestandsgroesseException
	{
		if(bestand < 0) {
			throw new FalscheBestandsgroesseException("Der Bestand darf nicht kleiner als 0 sein!");
		}
		this.bestand = bestand;
	}
	
	
	//F�r Artikel nach Nummer ausgeben
	//Vergleicht immer zwei Artikelnummern
	
	public static Comparator<Artikel> ArtikelNummerComparator = new Comparator<Artikel>() {

	    public int compare(Artikel artikel1, Artikel artikel2) {
	    	
	      int artikelNummer1 = artikel1.getNummer();
	      int artikelNummer2 = artikel2.getNummer();
	      String nummer1 = Integer.toString(artikelNummer1); //wandelt in Sting um
	      String nummer2 = Integer.toString(artikelNummer2);
	      
	      return nummer1.compareTo(nummer2);
	    }
	};

		
	
	
	//F�r Artikel Alphabetisch ausgeben
	//Vergleicht immer zwei Artikelnamen
	public static Comparator<Artikel> ArtikelNameComparator = new Comparator<Artikel>() {

	    public int compare(Artikel artikel1, Artikel artikel2) {
	    	
	      String artikelName1 = artikel1.getBezeichnung().toUpperCase(); //macht aus groß und klein-schreibung nur Großschreibung
	      String artikelName2 = artikel2.getBezeichnung().toUpperCase(); //toLowerCase() ist das Gegenteil
	      
	      
	      return artikelName1.compareTo(artikelName2);
	    }
	};
	
}

