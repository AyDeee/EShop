package shop.local.ui.gui.screens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;

import shop.local.domain.exceptions.FalscheBestandsgroesseException;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.controls.ArtikelListe;
import shop.local.valueobjects.Artikel;

public class KundenScreen extends Screen {

	ArtikelListe warenkorbListe;
	JButton logout;
	JButton artikelEinfuegen;
	JButton artikelEntfernen;

	Vector<Artikel> warenkorb;
	
	public KundenScreen(ShopClientGUI gui) {
		super(gui);
	}

	@Override
	protected void InitializePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		warenkorb = new Vector<Artikel>();
		try {
			warenkorb.add(new Artikel("Test", 767,7,2.8f));
		} catch (FalscheBestandsgroesseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		warenkorbListe = new ArtikelListe(warenkorb);
		JScrollPane scrollPane = new JScrollPane(warenkorbListe);
		artikelEinfuegen = new JButton("Einfügen");
		artikelEntfernen = new JButton("Entfernen");
		logout = new JButton("Abmelden");

		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gui.ChangeScreen(ScreenState.Startup);
			}
		});

		add(artikelEinfuegen);
		add(artikelEntfernen);
		add(scrollPane);
		add(logout);

	}

}
