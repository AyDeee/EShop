package shop.local.domain.exceptions;



public class ArtikelExistiertNichtException extends Exception {
	
	public ArtikelExistiertNichtException(int nummer, String msg) {
		super("Der Artikel Nr. " + nummer + msg );
	}
	
	
	public ArtikelExistiertNichtException (String nachricht) {
		super(nachricht);
	}
}


