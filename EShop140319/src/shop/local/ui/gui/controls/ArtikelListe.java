package shop.local.ui.gui.controls;

import java.util.Collections;
import java.util.List;

import javax.swing.JTable;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.IArtikel;

public class ArtikelListe<T extends IArtikel> extends JTable {

	public ArtikelListe() {
		super();
		// TableModel erzeugen ...
		ArtikelListItem<T> tableModel = new ArtikelListItem<T>();
		// ... bei JTable "anmelden" und ...
		setModel(tableModel);
	}

	public ArtikelListe(List<T> artikel) {
		this();
		// ... Daten an Model übergeben
		updateArtikelList(artikel);
	}

	public void updateArtikelList(List<T> artikel) {

		// Sortierung (mit Lambda-Expression)
		// Collections.sort(buecher, (b1, b2) ->
		// b1.getTitel().compareTo(b2.getTitel())); // Sortierung nach Titel
		Collections.sort(artikel, (b1, b2) -> b1.getNummer() - b2.getNummer()); // Sortierung nach Nummer

		// TableModel von JTable holen und ...
		ArtikelListItem<T> tableModel = (ArtikelListItem<T>) getModel();
		// ... Inhalt aktualisieren
		tableModel.setArtikel(artikel);
	}

	public T getItem(int row) {
		ArtikelListItem<T> tableModel = (ArtikelListItem<T>) getModel();
		return tableModel.getItem(row);
	}
	


}
