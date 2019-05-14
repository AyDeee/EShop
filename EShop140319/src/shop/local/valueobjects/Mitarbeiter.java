package shop.local.valueobjects;

public class Mitarbeiter extends Person{


	//Konstruktor zum einloggen
	public Mitarbeiter (int id, String pw) {
		super(id, pw);
	}
	

	
	
	//Konstruktor zum Registrieren
	public Mitarbeiter(int id, String name, String str, String nr, String plz, String ort, String iban, String pw) {
		super (id, name, str, nr, plz, ort, iban ,pw);
	}





	public boolean equals(Object anderesObjekt) {
		if (anderesObjekt instanceof Mitarbeiter) {
			Mitarbeiter andererMitarbeiter = (Mitarbeiter) anderesObjekt; //casten (Typumwandlung): umwandeln von einem allg. Datentyp in einen speziellen Datentyp
			return this.getName().equals(andererMitarbeiter.getName()); //vergleicht zwei Mitarbeiter
		} 
		return false;
	}
	



}
