package shop.local.valueobjects;

import java.io.Serializable;

public class Adresse implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 2655436934395636086L; //Eindeutige id für die Persitenz von jeder Klasse

	private String wohnort;
	private String plz;
	private String str;
	private String hausnummer;

	// Adresse Konstruktor
	public Adresse(String wohnort, String plz, String str, String hausnummer) {
		this.wohnort = wohnort;
		this.plz = plz;
		this.setStr(str);
		this.setHausnummer(hausnummer);
	}

	public String getWohnort() {

		return wohnort;
	}

	public String getPlz() {

		return plz;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
