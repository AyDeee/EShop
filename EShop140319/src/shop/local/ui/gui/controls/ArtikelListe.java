package shop.local.ui.gui.controls;

import java.util.Collections;

import javax.swing.JTable;

import shop.local.valueobjects.Artikel;

public class ArtikelListe extends JTable {

	public ArtikelListe(java.util.List<Artikel> artikel) {
		super();

		// TableModel erzeugen ...
		ArtikelListItem tableModel = new ArtikelListItem(artikel);
		// ... bei JTable "anmelden" und ...
		setModel(tableModel);
		// ... Daten an Model übergeben
		updateArtikelList(artikel);
	}

	public void updateArtikelList(java.util.List<Artikel> artikel) {

		// Sortierung (mit Lambda-Expression)
		// Collections.sort(buecher, (b1, b2) ->
		// b1.getTitel().compareTo(b2.getTitel())); // Sortierung nach Titel
		Collections.sort(artikel, (b1, b2) -> b1.getNummer() - b2.getNummer()); // Sortierung nach Nummer

		// TableModel von JTable holen und ...
		ArtikelListItem tableModel = (ArtikelListItem) getModel();
		// ... Inhalt aktualisieren
		tableModel.setArtikel(artikel);
	}

}
