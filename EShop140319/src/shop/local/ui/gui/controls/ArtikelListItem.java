package shop.local.ui.gui.controls;

import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.ArtikelImWarenkorb;
import shop.local.valueobjects.IArtikel;
import shop.local.valueobjects.Massengutartikel;

public class ArtikelListItem<T extends IArtikel> extends AbstractTableModel{


	private List<T> artikel;
    private String[] spaltenNamen = { "Nummer","Titel", "Anzahl", "Preis in €", "Packungsgroesse" };

    
    public ArtikelListItem() {
    	super(); 
    	// Ich erstelle eine Kopie der Bücherliste,
    	// damit beim Aktualisieren (siehe Methode setBooks())
    	// keine unerwarteten Seiteneffekte entstehen.
    	artikel = new Vector<T>();
    }
    
    public ArtikelListItem(List<T> aktuelleArtikel) {
    	this();
    	artikel.addAll(aktuelleArtikel);
    }

    public void setArtikel(List<T> aktuelleArtikel){
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
		IArtikel gewaehlterArtikel = artikel.get(row);
        switch (col) {
            case 0:
                return gewaehlterArtikel.getNummer();
            case 1:
                return gewaehlterArtikel.getTitel();
            case 2:
                return gewaehlterArtikel.getAnzahl();
            case 3:
            	return gewaehlterArtikel.getPreis();
            case 4:
            	if(gewaehlterArtikel instanceof Massengutartikel) {
            		return ((Massengutartikel) gewaehlterArtikel).getPackungsgroesse();
            	}
            	if(gewaehlterArtikel instanceof ArtikelImWarenkorb) {
            		ArtikelImWarenkorb a = (ArtikelImWarenkorb) gewaehlterArtikel;
            		if(a.getArtikel() instanceof Massengutartikel) {
                		return ((Massengutartikel) a.getArtikel()).getPackungsgroesse();
                	}
            	}
            	return 1;
            	
            default:
                return null;
        }
	}

	public T getItem(int row) {
		return artikel.get(row);
	}
	
}
