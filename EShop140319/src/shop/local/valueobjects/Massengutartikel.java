package shop.local.valueobjects;

import shop.local.domain.exceptions.FalscheBestandsgroesseException;

public class Massengutartikel extends Artikel{
	
	private int packungsgroesse;
	private int packungen;
	
	public Massengutartikel(String titel, int nr, int bestand, float preis, int packungsgroesse) throws FalscheBestandsgroesseException {
		super ();//unschön, aber mit "super" funktioniert es nicht, weil das Super Konstuktor immer am Anfang stehen muss. Wir brauchen allerdings die packungsgoesse zuerst, sodass der bestand nicht auf 0 gesetzt wird!
		this.bezeichnung = titel;
		this.nummer = nr;
		this.packungsgroesse = packungsgroesse;
		setBestand(bestand);
		this.preis = preis;
		this.packungen = packungen;
	}
	
	public boolean ueberpruefeAnzahl (int anzahl) {		
		if (anzahl % packungsgroesse == 0) {
			return true;
		}
		return false;
	}
	
	@Override 
	public void setBestand(int bestand) throws FalscheBestandsgroesseException{
		if(bestand < 0) {
			//TODO: exception werfen, wenn bestand kleiner als 0
			throw new FalscheBestandsgroesseException("Der Bestand darf nicht kleiner als 0 sein!");
		}
		if(packungsgroesse < 1) {
			throw new FalscheBestandsgroesseException("Packung");
		}
		
		if (ueberpruefeAnzahl(bestand)) {
			this.bestand = bestand;
		}else {
			throw new FalscheBestandsgroesseException("Der Bestand muss ein Vielfaches von " + packungsgroesse + " sein.");
		}
		
	}
	
	@Override 
	public int getBestand() {
		return bestand;
	}
	
	public int getPackungen() {
		return packungen;
	}
	
	public int getPackungsgroesse() {
		return packungsgroesse;
	}
	
	public void setPackungen () {
		if (bestand % packungsgroesse == 0) {
			packungen = bestand / packungsgroesse;
		}
	}
	
	
}