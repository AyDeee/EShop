package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;

/**
 * Exception zur Signalisierung, dass ein Buch bereits existiert (z.B. bei einem Einfügevorgang).
 * 
 * @author teschke
 */
public class ArtikelExistiertBereitsException extends Exception {

	/**
	 * Konstruktor
	 * 
	 * @param artikel Das bereits existierende Buch
	 * @param zusatzMsg zusätzlicher Text für die Fehlermeldung
	 */
	public ArtikelExistiertBereitsException(Artikel artikel, String zusatzMsg) {
		super("Artikel mit Titel " + artikel.getTitel() + " und Nummer " + artikel.getNummer() 
				+ " existiert bereits" + zusatzMsg);
	}
}
