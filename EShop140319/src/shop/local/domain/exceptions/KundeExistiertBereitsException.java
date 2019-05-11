package shop.local.domain.exceptions;

import shop.local.valueobjects.Kunde;

public class KundeExistiertBereitsException extends Exception {
	public KundeExistiertBereitsException(Kunde kunde, String zusatzMsg) {
		super("Der Kunde " + kunde.getName() + zusatzMsg);
	}
}

