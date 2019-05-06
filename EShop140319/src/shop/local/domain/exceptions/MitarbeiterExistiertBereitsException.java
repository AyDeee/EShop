package shop.local.domain.exceptions;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Mitarbeiter;

public class MitarbeiterExistiertBereitsException extends Exception {
	public MitarbeiterExistiertBereitsException(Mitarbeiter mitarbeiter, String zusatzMsg) {
		super(zusatzMsg);
	}
}
