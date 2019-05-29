package shop.local.ui.gui.controls;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import shop.local.valueobjects.Artikel;

public class ArtikelListItem extends AbstractTableModel{


	private List<Artikel> artikel;
    private String[] spaltenNamen = { "Nummer","Titel", "verfügbar", "Preis in €" };

    
    public ArtikelListItem(List<Artikel> aktuelleArtikel) {
    	super(); 
    	// Ich erstelle eine Kopie der Bücherliste,
    	// damit beim Aktualisieren (siehe Methode setBooks())
    	// keine unerwarteten Seiteneffekte entstehen.
    	artikel = new Vector<Artikel>();
    	artikel.addAll(aktuelleArtikel);
    }

    public void setArtikel(List<Artikel> aktuelleArtikel){
        artikel.clear();
        artikel.addAll(aktuelleArtikel);
        fireTableDataChanged();
    }

	@Override
	public int getRowCount() {
		return artikel.size();
	}
	
	@Override
	public int getColumnCount() {
		return spaltenNamen.length;
	}

	@Override
    public String getColumnName(int col) {
        return spaltenNamen[col];
    }

	@Override
	public Object getValueAt(int row, int col) {
		Artikel gewaehlterArtikel = artikel.get(row);
        switch (col) {
            case 0:
                return gewaehlterArtikel.getNummer();
            case 1:
                return gewaehlterArtikel.getBezeichnung();
            case 2:
                return gewaehlterArtikel.getBestand();
            case 3:
            	return gewaehlterArtikel.getPreis();
            default:
                return null;
        }
	}

}
