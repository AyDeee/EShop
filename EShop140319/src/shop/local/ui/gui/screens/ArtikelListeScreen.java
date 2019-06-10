package shop.local.ui.gui.screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.controls.ArtikelListe;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.IArtikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;

public class ArtikelListeScreen extends Screen {

	ArtikelListe<Artikel> liste;
	private JTextField searchTextField;
	private JButton searchButton;
	private JComboBox cmbAuswahlListe;
	
	public ArtikelListeScreen(ShopClientGUI gui) {
		super(gui);
	}

	@Override
	protected void InitializePanel() {
		// GridBagLayout
		// (Hinweis: Das ist schon ein komplexerer LayoutManager, der mehr kann als hier
		// gezeigt.
		// Hervorzuheben ist hier die Idee, explizit Constraints (also Nebenbedindungen)
		// für
		// die Positionierung / Ausrichtung / Größe von GUI-Komponenten anzugeben.)
		GridBagLayout gridBagLayout = new GridBagLayout();
		this.setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.gridy = 0; // Zeile 0
		c.weighty = 0;

		JLabel searchLabel = new JLabel("Suchbegriff:");
		c.gridx = 0; // Spalte 0
		c.weightx = 0; // 20% der gesamten Breite
		c.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(searchLabel, c);
		this.add(searchLabel);

		c.fill = GridBagConstraints.HORIZONTAL;
		//c.anchor = GridBagConstraints.WEST;

		searchTextField = new JTextField();
		searchTextField.setToolTipText("Suchbegriff eingeben.");
		c.gridx = 1; // Spalte 1
		c.weightx = 0.6; // 60% der gesamten Breite
		gridBagLayout.setConstraints(searchTextField, c);
		this.add(searchTextField);
		
		KeyListener loginKey = (KeyListener) new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchButton.doClick();
				}
			}
		};

		searchTextField.addKeyListener(loginKey);
		
		searchButton = new JButton("Such!");
		c.gridx = 2; // Spalte 2
		c.weightx = 0.2; // 20% der gesamten Breite
		gridBagLayout.setConstraints(searchButton, c);
		this.add(searchButton);

		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				String suchbegriff = searchTextField.getText();
				java.util.List<Artikel> suchErgebnis;
				if (suchbegriff.isEmpty()) {
					suchErgebnis = Shop().gibAlleArtikel();
				} else {
					suchErgebnis = Shop().sucheNachTitel(suchbegriff);
				}

				liste.updateArtikelList(suchErgebnis);
			}
		});
		
		String[] auswahl = { "Nummer sortieren", "Alphabetisch sortieren" };
		cmbAuswahlListe = new JComboBox(auswahl);
		// cmbAuswahlListe.setSize(cmbAuswahlListe.getPreferredSize());
		// add(cmbAuswahlListe, BorderLayout.NORTH);
		c.insets = new Insets(50, 0, 0, 0);
		c.gridx = 1;
		c.weightx = 0.2;
		gridBagLayout.setConstraints(cmbAuswahlListe, c);
		cmbAuswahlListe.setSelectedIndex(0);
		this.add(cmbAuswahlListe);
		
		cmbAuswahlListe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateListe();
			}
		});
		
		liste = new ArtikelListe<Artikel>(Shop().gibAlleArtikel());
		JScrollPane scrollPane = new JScrollPane(liste);
		c.insets = new Insets(20, 0, 0, 0);
		c.gridy = 1; // Zeile 1
		c.gridx = 0; // Spalte 0
		c.gridwidth = 3;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		gridBagLayout.setConstraints(scrollPane, c);
		this.add(scrollPane);
	}
	
	public void updateListe() {
		if (cmbAuswahlListe.getSelectedItem() == "Nummer sortieren") {
			liste.sortierenNummer(Shop().gibAlleArtikel());
		}else {
			liste.sortierenAlphabetisch(Shop().gibAlleArtikel());
		}
		
	}
	
	public Artikel getSelectedArtikel() {
		int selectedRow = liste.getSelectedRow();
		return liste.getItem(selectedRow);
	}

	public ArtikelListe<Artikel> getListe() {
		return liste;
	}

}
