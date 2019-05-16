package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;

public class ArtikelExistiertBereitsException extends Exception {

	public ArtikelExistiertBereitsException(Artikel artikel, String msg) {
		super("Artikel mit Titel " + artikel.getBezeichnung() + " und Nummer " + artikel.getNummer() 
				+ msg);
	}
}
