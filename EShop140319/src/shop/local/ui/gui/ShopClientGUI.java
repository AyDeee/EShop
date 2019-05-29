package shop.local.ui.gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import shop.local.domain.EShop;
import shop.local.ui.gui.screens.ArtikelListeScreen;
import shop.local.ui.gui.screens.LoginScreen;
import shop.local.ui.gui.screens.Screen;

public class ShopClientGUI extends JFrame {

	EShop shop;

	ArtikelListeScreen liste;
	LoginScreen login;

	Screen currentScreen;

	public ShopClientGUI() {

		shop = new EShop();
		setLayout(new BorderLayout());
		
		InitializeScreens();

		currentScreen = liste;
		add(currentScreen, BorderLayout.CENTER);
		setSize(640, 480);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void InitializeScreens() {		
		liste = new ArtikelListeScreen(this);
		login = new LoginScreen(this);
		
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	public void ChangeScreen(int screenId) {
		remove(currentScreen);

		switch (screenId) {
		case 0:
			add(liste, BorderLayout.CENTER);
		case 1:
			add(login, BorderLayout.CENTER);
		default:
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
