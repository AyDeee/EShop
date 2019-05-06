package shop.local.valueobjects;

public class Massengutartikel extends Artikel{
	
	private int packungsgroesse;
	private int packungen;
	
	public Massengutartikel(String titel, int nr, int bestand, float preis, int packungsgroesse, int packungen) {
		super (titel, nr, bestand, preis);
		this.packungsgroesse = packungsgroesse;
		this.packungen = packungen;
	}
	
	@Override
	public void setBestand(int bestand)
	{
		if(bestand < 0) {
			//TODO: exception werfen, wenn bestand kleiner als 0
			return;
		}
		if (bestand % packungsgroesse == 0) {
			packungen = bestand / packungsgroesse;
			this.bestand = bestand;
		}
		
	}
	
	
}