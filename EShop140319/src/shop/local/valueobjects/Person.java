package shop.local.valueobjects;

import java.io.Serializable;

public class Person implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 2189999211715756752L;

	private String name;
	private String passwort;
	private int id;
	private Adresse adresse;
	private String iban;

	// Konstruktor um einen neuen Kunden / Mitarbeiter einzuf�gen

	public Person(int id, String name, String wohnort, String plz, String str, String strnr, String iban,
			String passwort) {

		this.id = id;
		this.name = name;
		this.passwort = passwort;
		this.iban = iban;
		// Hier wird die Adresse ausgelagert
		this.adresse = new Adresse(wohnort, plz, str, strnr);
	}

	// Konstruktor zum einloggen

	public Person(int id, String passwort) {

		this.passwort = passwort;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public String getPasswort() {
		return passwort;
	}

}
