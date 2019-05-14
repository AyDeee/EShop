package shop.local.valueobjects;

import shop.local.domain.exceptions.FalscheBestandsgroesseException;

public class Massengutartikel extends Artikel{
	
	private int packungsgroesse;
	private int packungen;
	
	public Massengutartikel(String titel, int nr, int bestand, float preis, int packungsgroesse) {
		super (titel, nr, bestand, preis);
		this.packungsgroesse = packungsgroesse;
		this.packungen = packungen;
	}
	
	
	public void setBestand(int bestand, int packungsgroesse) throws FalscheBestandsgroesseException{
		if(bestand < 0) {
			//TODO: exception werfen, wenn bestand kleiner als 0
			throw new FalscheBestandsgroesseException("Der Bestand darf nicht kleiner als 0 sein!");
		}
		if (bestand % packungsgroesse == 0) {
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
	
	public void setPackungen (int bestand, int packungsgroesse ) {
		if (bestand % packungsgroesse == 0) {
			packungen = bestand / packungsgroesse;
		}
	}
	
	
}