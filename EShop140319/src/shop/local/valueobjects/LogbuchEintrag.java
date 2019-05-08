package shop.local.valueobjects;

public class LogbuchEintrag {
	
	public enum EreignisTyp { NEU, EINLAGERUNG, AUSLAGERUNG, KAUF, LOESCHUNG };
	
	private boolean einlagern;
	private EreignisTyp aktion;
	private int datum;
	private Artikel artikel;
	private int anzahl;
	private Person person;
	
	public LogbuchEintrag(boolean einlagern,int datum, Artikel artikel, int anzahl, Person person) {
		this.einlagern = einlagern;
		this.datum = datum;
		this.artikel = artikel;
		this.anzahl = anzahl;
		this.person = person;
		
		this.aktion = EreignisTyp.KAUF;
	}

	
	public boolean isEinlagern() {
		return einlagern;
	}


	public int getDatum() {
		return datum;
	}

	public Artikel getArtikel() {
		return artikel;
	}

	public int getAnzahl() {
		return anzahl;
	}

	public Person getPerson() {
		return person;
	}
	
	
}
