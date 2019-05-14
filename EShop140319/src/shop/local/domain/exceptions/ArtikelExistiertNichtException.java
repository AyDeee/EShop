package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;

public class ArtikelExistiertNichtException extends Exception {
	
	public ArtikelExistiertNichtException(int nummer) {
		super("Artikel existiert nicht.");
	}
	
	public ArtikelExistiertNichtException (Artikel artikel) {
		super("Artikel existiert nicht.");
	}

}


