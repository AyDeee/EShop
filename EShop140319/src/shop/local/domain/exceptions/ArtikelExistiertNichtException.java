package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;

public class ArtikelExistiertNichtException extends Exception {
	
	public ArtikelExistiertNichtException(Artikel artikel) {
		super("Artikel existiert nicht und kann nicht geloescht werden.");
	}

}


