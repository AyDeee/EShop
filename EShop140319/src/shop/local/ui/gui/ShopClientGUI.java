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
	RegistrierenScreen registrieren;
	MitarbeiterScreen mitarbeiter;
	KundenScreen kunde;
	LoginButtonScreen loginButton;
	
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
		registrieren = new RegistrierenScreen(this);
		kunde = new KundenScreen(this);
		loginButton = new LoginButtonScreen(this);
		
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
	
	}

	public void ChangeScreen(ScreenState newState) {
		remove(liste);
		remove(login);
		remove(registrieren);
		remove(kunde);
		remove(mitarbeiter);
		remove(loginButton);

		switch (newState) {
		case Kunde:					
			add(liste, BorderLayout.CENTER);
			add(kunde, BorderLayout.EAST);
			break;
		case Login:
			add(login, BorderLayout.CENTER);
			break;
		case Registrieren:
			add(registrieren, BorderLayout.CENTER);
			break;
		case Mitarbeiter:
			add(liste, BorderLayout.CENTER);
			add(mitarbeiter, BorderLayout.SOUTH);
			break;
		case Startup:
			add(liste, BorderLayout.CENTER);
			add(loginButton, BorderLayout.NORTH);
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
	
	public RegistrierenScreen getRegistrieren() {
		return registrieren;
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
	
	public LoginButtonScreen getLoginButton() {
		return loginButton;
	}
}
