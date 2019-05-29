package shop.local.ui.gui.screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.controls.ArtikelListe;
import shop.local.valueobjects.Artikel;

public class ArtikelListeScreen extends Screen {

	ArtikelListe<Artikel> liste;
	JButton button;

	public ArtikelListeScreen(ShopClientGUI gui) {
		super(gui);

	}

	@Override
	protected void InitializePanel() {
		// Layout des Frames: BorderLayout
		setLayout(new BorderLayout());

		liste = new ArtikelListe<Artikel>(Shop().gibAlleArtikel());
		JScrollPane scrollPane = new JScrollPane(liste);
		add(scrollPane, BorderLayout.CENTER);
		button = new JButton("Login");

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gui.ChangeScreen(ScreenState.Login);
			}
		});

		add(button, BorderLayout.SOUTH);

	}

	public Artikel getSelectedArtikel() {
		int selectedRow = liste.getSelectedRow();
		return liste.getItem(selectedRow);
	}

}
