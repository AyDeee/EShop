package shop.local.valueobjects;

import java.util.Vector;
import shop.local.domain.EShop;

public class Rechnung {
	private int datum;
	private Kunde kunde;
	private Vector <ArtikelImWarenkorb> gekaufteArtikel;
	
	public Rechnung(Kunde kunde, Vector <ArtikelImWarenkorb> gekaufteArtikel) {	
		datum = EShop.getDayOfYear(); 
		this.kunde = kunde;
		this.gekaufteArtikel = gekaufteArtikel; 
	}

	public String Print() {
		StringBuilder builder = new StringBuilder();
		builder.append("-----Rechnung-----" + System.lineSeparator());
		builder.append("Datum: " + datum + System.lineSeparator());
		builder.append("Kunde: " + kunde.getName() + kunde.getId() + System.lineSeparator());
		float gesamtPreis = 0f;
		for (ArtikelImWarenkorb artikelImWarenkorb : gekaufteArtikel) {
			Artikel artikel = artikelImWarenkorb.getArtikel();
			builder.append(artikel.getBezeichnung() + " " + artikelImWarenkorb.getAnzahl() + " " + artikelImWarenkorb.getAnzahl() * artikel.getPreis() + "€" + System.lineSeparator());
			gesamtPreis += artikel.getPreis() * artikelImWarenkorb.getAnzahl();
		}
		builder.append(System.lineSeparator());
		builder.append("Gesamt: " + gesamtPreis + "€"+ System.lineSeparator());
		
		builder.append("------------------" + System.lineSeparator());
		return builder.toString();
	}

	
	
	
}
