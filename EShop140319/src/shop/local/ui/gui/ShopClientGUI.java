package shop.local.ui.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import shop.local.domain.EShop;
import shop.local.ui.gui.screens.*;

public class ShopClientGUI extends JFrame {

	EShop shop;

	ArtikelListeScreen liste;
	LoginScreen login;
	MitarbeiterScreen mitarbeiter;
	KundenScreen kunde;

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
		login = new LoginScreen(this);
		mitarbeiter = new MitarbeiterScreen(this);
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

	public EShop GetShop() {
		return shop;
	}

	public static void main(String[] args) {

		ShopClientGUI GUI = new ShopClientGUI();
	}

}
