package shop.local.valueobjects;


/**
 * Klasse zur Repräsentation einzelner Kunden.
 * 
 */
public class Kunde extends Person {
	
	Warenkorb warenkorb;


	//Konstruktor zum  Registrieren
	public Kunde(int id, String name, String wohnort, String plz, String str, String strnr,
			String iban, String passwort) {
		super(id, name, wohnort, plz, str, strnr, iban, passwort);
		//jedem Kunden wird ein leerer Warenkorb zugewiesen
		warenkorb = new Warenkorb();
		

	}
	
	
	//Konstruktor zum Einloggen
	public Kunde (int id, String pw) {
		super(id, pw);
	}

	

	public boolean equals(Object anderesObjekt) {
		if (anderesObjekt instanceof Kunde) {
			Kunde andererKunde = (Kunde) anderesObjekt;
			return ((this.getId() == andererKunde.getId()));
		} 
		return false;
	}


	public Warenkorb getWarenkorb() {
		return warenkorb;
	}



}
