package shop.local.valueobjects;

public class LogbuchEintrag {
	private boolean einlagern;
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
