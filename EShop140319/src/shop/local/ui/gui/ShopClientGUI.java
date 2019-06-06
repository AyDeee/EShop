package shop.local.ui.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;


import shop.local.domain.EShop;
import shop.local.ui.gui.controls.ArtikelListe;
import shop.local.ui.gui.screens.*;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;

public class ShopClientGUI extends JFrame {

	EShop shop;

	ArtikelListeScreen liste;
	LoginScreen login;
	MitarbeiterScreen mitarbeiter;
	KundenScreen kunde;
	ArtikelListe<Artikel> artikelListe;

	public ShopClientGUI() {

		shop = new EShop();
		setLayout(new BorderLayout());
		InitializeScreens();

		ChangeScreen(ScreenState.Startup);
		setSize(640, 480);
		setVisible(true);
		setLocationRelativeTo(null);
		
	}

	private void InitializeScreens() {
		liste = new ArtikelListeScreen(this);
		mitarbeiter = new MitarbeiterScreen(this, shop);
		login = new LoginScreen(this);
		kunde = new KundenScreen(this);
		
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	
	}

	public void ChangeScreen(ScreenState newState) {
		remove(liste);
		remove(login);
		remove(kunde);
		remove(mitarbeiter);

		switch (newState) {
		case Kunde:					
			add(liste, BorderLayout.CENTER);
			add(kunde, BorderLayout.EAST);
			break;
		case Login:
			add(login, BorderLayout.CENTER);
			break;
		case Mitarbeiter:
			add(liste, BorderLayout.CENTER);
			add(mitarbeiter, BorderLayout.EAST);
			break;
		case Startup:
			add(liste, BorderLayout.CENTER);
			break;
		default:
			break;
		}

		SwingUtilities.updateComponentTreeUI(this);
		invalidate();
		validate();
		repaint();
	}
	
	public void onArtikelAdded(Artikel artikel) {
		// Ich lade hier einfach alle Bücher neu und lasse sie anzeigen
		java.util.Vector<Artikel> artikell = shop.gibAlleArtikel();
		artikelListe = liste.getListe();
		artikelListe.updateArtikelList(artikell);
		shop.Save();
	}

	public EShop GetShop() {
		return shop;
	}

	
	
	public ArtikelListeScreen getListe() {
		return liste;
	}

	public LoginScreen getLogin() {
		return login;
	}

	public MitarbeiterScreen getMitarbeiter() {
		return mitarbeiter;
	}

	public KundenScreen getKunde() {
		return kunde;
	}

	public static void main(String[] args) {

		ShopClientGUI GUI = new ShopClientGUI();
	}

}
