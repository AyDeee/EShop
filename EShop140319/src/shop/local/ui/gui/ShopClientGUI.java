package shop.local.ui.gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import shop.local.domain.EShop;

public class ShopClientGUI {

	EShop shop;
	
	ArtikelListe liste;
	LoginScreen login;

	Screen currentScreen;
	JFrame mainwindow;

	public ShopClientGUI() {

		shop = new EShop();
		
		mainwindow = new JFrame();

		InitializeScreens();

		currentScreen = liste;
		mainwindow.add(currentScreen.GetPanel());
		mainwindow.pack();
		mainwindow.setSize(new Dimension(800, 600));
		mainwindow.setVisible(true);
		mainwindow.setLocationRelativeTo(null);
	}

	private void InitializeScreens() {
		liste = new ArtikelListe(this);
		login = new LoginScreen(this);
	}

	public void ChangeScreen(int screenId) {
		Dimension size = mainwindow.getSize();
		mainwindow.remove(currentScreen.GetPanel());

		switch (screenId) {
		case 0:
			mainwindow.add(liste.GetPanel());
		case 1:
			mainwindow.add(login.GetPanel());
		default:
		}
		mainwindow.pack();
		mainwindow.setSize(size);
	}

	public EShop GetShop() {
		return shop;
	}
	
	public static void main(String[] args) {

		ShopClientGUI GUI = new ShopClientGUI();
	}


}
