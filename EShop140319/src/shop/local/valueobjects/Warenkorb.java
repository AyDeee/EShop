package shop.local.valueobjects;

import java.util.Iterator;
import java.util.Vector;

public class Warenkorb {
	
	
	private Vector <ArtikelImWarenkorb> warenkorbEintraege;
	
	
	
	public Warenkorb () {
		warenkorbEintraege = new Vector <ArtikelImWarenkorb>();
		
	}
	
	
	public Vector <ArtikelImWarenkorb> getWarenkorbEintraege() {
		return new Vector<ArtikelImWarenkorb>(warenkorbEintraege);
	}
	
	public void ArtikelHinzufügen(Artikel neuerArtikel)
	{
		Iterator<ArtikelImWarenkorb> it = warenkorbEintraege.iterator();
		while (it.hasNext()) {
			ArtikelImWarenkorb artikelImWarenkorb = it.next();
			if(artikelImWarenkorb.getArtikel().equals(neuerArtikel)) {
				artikelImWarenkorb.erhoeheAnzahl();
				return;
			}
		}
	
		warenkorbEintraege.add(new ArtikelImWarenkorb(neuerArtikel));
	}
	
	public void ArtikelAnzahlAendern(Artikel artikel, int anzahl)
	{
		if (artikel.getBestand() < anzahl)
		{
			return;
		}
		
		Iterator<ArtikelImWarenkorb> it = warenkorbEintraege.iterator(); // gucken ob Artikel im Warenkorb
		while (it.hasNext()) {
			ArtikelImWarenkorb artikelImWarenkorb = it.next();
			if(artikelImWarenkorb.getArtikel().equals(artikel)) {
				//Artikel im Warenkorb gefunden!
				if(anzahl > 0)
				{
					artikelImWarenkorb.setAnzahl(anzahl);
				}
				else
				{
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
	
	public void WarenkorbLeeren()
	{
		warenkorbEintraege.clear();
	}
	
	public void setWarenkorbEintraege(Vector <ArtikelImWarenkorb> warenkorbEintraege) {
		this.warenkorbEintraege = warenkorbEintraege;
		
	}
	
}
