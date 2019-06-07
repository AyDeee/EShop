package shop.local.ui.gui.screens;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import shop.local.domain.exceptions.FalscheBestandsgroesseException;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.controls.ArtikelListe;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.ArtikelImWarenkorb;
import shop.local.valueobjects.Kunde;

public class KundenScreen extends Screen {

	ArtikelListe<ArtikelImWarenkorb> warenkorbListe;
	JButton logout;
	JButton artikelEinfuegen;
	JButton artikelEntfernen;

	Kunde eingeloggterKunde;
	
	public KundenScreen(ShopClientGUI gui) {
		super(gui);
	}

	@Override
	protected void InitializePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		

		warenkorbListe = new ArtikelListe<ArtikelImWarenkorb>();
		JScrollPane scrollPane = new JScrollPane(warenkorbListe);
		artikelEinfuegen = new JButton("Einfügen");
		artikelEinfuegen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArtikelListeScreen artikelListe = gui.getListe();
				Artikel selectedArtikel = artikelListe.getSelectedArtikel();
				eingeloggterKunde.getWarenkorb().ArtikelHinzufuegen(selectedArtikel);
				updateWarenkorb();
			}
		});
		
		
		artikelEntfernen = new JButton("Entfernen");
		artikelEntfernen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = warenkorbListe.getSelectedRow();
				ArtikelImWarenkorb selectedArtikel =  warenkorbListe.getItem(selectedRow);
				try {
					eingeloggterKunde.getWarenkorb().ArtikelAnzahlAendern(selectedArtikel.getArtikel(), selectedArtikel.getAnzahl() - 1);
				} catch (FalscheBestandsgroesseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateWarenkorb();
			}
		});
		
		logout = new JButton("Abmelden");

		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eingeloggterKunde = null;
				gui.ChangeScreen(ScreenState.Startup);
			}
		});

		add(artikelEinfuegen);
		add(artikelEntfernen);
		add(scrollPane);
		add(logout);

	}

	public void SetKunde(Kunde kunde) {
		eingeloggterKunde = kunde;
	}
	
	public void updateWarenkorb() {
		int selectedRow = warenkorbListe.getSelectedRow();
		warenkorbListe.updateArtikelList(eingeloggterKunde.getWarenkorb().getWarenkorbEintraege());
		warenkorbListe.setRowSelectionInterval(selectedRow, selectedRow);
	}
}
