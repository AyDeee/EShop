package shop.local.ui.gui.screens;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.FalscheBestandsgroesseException;
import shop.local.ui.gui.RechnungsFrame;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.controls.ArtikelListe;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.ArtikelImWarenkorb;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Massengutartikel;
import shop.local.valueobjects.Rechnung;

public class KundenScreen extends Screen {

	ArtikelListe<ArtikelImWarenkorb> warenkorbListe;
	JButton logout;
	JButton artikelEinfuegen;
	JButton artikelEntfernen;
	JButton warenkorbLeeren;
	Kunde eingeloggterKunde;
	JButton kaufen;
	JButton anzahlAendern;
	JTextField ArtikelAnzahlAendernTextField;

	public KundenScreen(ShopClientGUI gui) {
		super(gui);
	}

	@Override
	protected void InitializePanel() {

		JPanel kundenPanel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		kundenPanel.setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		// setLayout(new GridBagLayout());

		// -----------------Einfügen Button-------------------------------------
		artikelEinfuegen = new JButton("Einfügen");
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 0, 0);
		kundenPanel.add(artikelEinfuegen, c);
		// gridBagLayout.setConstraints(artikelEinfuegen, c);

		artikelEinfuegen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ArtikelListeScreen artikelListe = gui.getListe();
				Artikel selectedArtikel = artikelListe.getSelectedArtikel();
				eingeloggterKunde.getWarenkorb().ArtikelHinzufuegen(selectedArtikel);
				updateWarenkorb();
			}
		});

		// -----------------Entfernen Button-------------------------------------
		artikelEntfernen = new JButton("Entfernen");
		c.gridx = 1; // Spalte 2
		c.gridy = 0;
		c.insets = new Insets(10, 0, 0, 0);
		kundenPanel.add(artikelEntfernen, c);
		// gridBagLayout.setConstraints(artikelEntfernen, c);

		artikelEntfernen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = warenkorbListe.getSelectedRow();
				if	(selectedRow < 0) {
					return;
				}
				
				ArtikelImWarenkorb selectedArtikel = warenkorbListe.getItem(selectedRow);
				try {
					int aenderung = selectedArtikel.getAnzahl() - 1;
					if (selectedArtikel.getArtikel() instanceof Massengutartikel) {
						int packungsgroesse = ((Massengutartikel)selectedArtikel.getArtikel()).getPackungsgroesse();
						aenderung = selectedArtikel.getAnzahl() - packungsgroesse;
					}
					eingeloggterKunde.getWarenkorb().ArtikelAnzahlAendern(selectedArtikel.getArtikel(),
							aenderung);
				
				} catch (FalscheBestandsgroesseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateWarenkorb();
				int rows =warenkorbListe.getRowCount();
				if (rows > 0) {
					warenkorbListe.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		// -----------------Anzahl aendern Button----------------------------------
		anzahlAendern = new JButton("Anzahl aendern");
		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(10, 0, 0, 0);
		kundenPanel.add(anzahlAendern, c);
		// gridBagLayout.setConstraints(artikelEntfernen, c);

		anzahlAendern.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = warenkorbListe.getSelectedRow();
				if	(selectedRow < 0) {
					return;
				}
				ArtikelImWarenkorb selectedArtikel = warenkorbListe.getItem(selectedRow);
				try {
					
					String nr = ArtikelAnzahlAendernTextField.getText();
					int anzahl = Integer.parseInt(nr);
					eingeloggterKunde.getWarenkorb().ArtikelAnzahlAendern(selectedArtikel.getArtikel(), anzahl);
				} catch (FalscheBestandsgroesseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				updateWarenkorb();				
			}
		});

		// -----------------ArtikelAnzahlAendernTextField----------------------------
		ArtikelAnzahlAendernTextField = new JTextField();
		ArtikelAnzahlAendernTextField.setToolTipText("Anzahl des Artikels aendern");
		c.gridx = 3; // Spalte 1
		c.gridy = 0;
		c.insets = new Insets(10, 0, 0, 0);
		c.fill = GridBagConstraints.HORIZONTAL;
		kundenPanel.add(ArtikelAnzahlAendernTextField, c);
		// gridBagLayout.setConstraints(ArtikelAnzahlAendernTextField, c);

		KeyListener loginKey = (KeyListener) new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					anzahlAendern.doClick();
				}
			}
		};

		ArtikelAnzahlAendernTextField.addKeyListener(loginKey);

		// -----------------Abmelden Button------------------------------------------
		logout = new JButton("Abmelden");
		c.gridx = 0; // Spalte 1
		c.gridy = 10;
		c.insets = new Insets(10, 0, 0, 0);
		c.anchor = GridBagConstraints.SOUTH;
		kundenPanel.add(logout, c);
		// gridBagLayout.setConstraints(logout, c);

		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eingeloggterKunde = null;
				gui.ChangeScreen(ScreenState.Startup);
			}
		});

		// -----------------Warenkorb leeren Button----------------------------------
		warenkorbLeeren = new JButton("Warenkorb leeren");
		c.gridx = 0; // Spalte 1
		c.gridy = 5;
		c.insets = new Insets(10, 0, 0, 0);
		kundenPanel.add(warenkorbLeeren, c);
		// gridBagLayout.setConstraints(warenkorbLeeren, c);

		warenkorbLeeren.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (eingeloggterKunde.getWarenkorb() != null) {
					eingeloggterKunde.getWarenkorb().WarenkorbLeeren();
					updateWarenkorb();
				}
			}
		});

		// -----------------Kaufen Button--------------------------------------------
		kaufen = new JButton("Kaufen");
		c.gridx = 3; // Spalte 1
		c.gridy = 5;
		c.insets = new Insets(10, 0, 0, 0);
		c.anchor = GridBagConstraints.EAST;
		kundenPanel.add(kaufen, c);
		// gridBagLayout.setConstraints(kaufen, c);

		kaufen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (eingeloggterKunde.getWarenkorb() != null) {
					Rechnung rechnung = null;
					try {
						rechnung = gui.GetShop().kaufeArtikelImWarenkorb(eingeloggterKunde);
					} catch (ArtikelExistiertNichtException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					eingeloggterKunde.getWarenkorb().WarenkorbLeeren();
					updateWarenkorb();
					gui.getListe().updateListe();
					new RechnungsFrame(rechnung);
				}
			}
		});

		// -----------------Warenkorb--------------------------------------------
		warenkorbListe = new ArtikelListe<ArtikelImWarenkorb>();
		JScrollPane scrollPane = new JScrollPane(warenkorbListe);
		c.insets = new Insets(20, 0, 0, 0);
		c.gridx = 0; // Spalte 0
		c.gridy = 4; // Zeile 1
		c.gridwidth = 4;
		// c.weighty = 1;
		// c.fill = GridBagConstraints.BOTH;
		kundenPanel.add(scrollPane, c);
		// gridBagLayout.setConstraints(scrollPane, c);

		scrollPane.setBorder(BorderFactory.createTitledBorder("Warenkorb"));

		// ----------------ADDDDDEEEENNN-------------------------------------------

		this.setLayout(new BorderLayout());
		this.add(kundenPanel, BorderLayout.NORTH);

	}

	public void SetKunde(Kunde kunde) {
		eingeloggterKunde = kunde;
		updateWarenkorb();
	}

	public void updateWarenkorb() {

		warenkorbListe.updateArtikelList(eingeloggterKunde.getWarenkorb().getWarenkorbEintraege());
		int selectedRow = warenkorbListe.getSelectedRow();
		if (selectedRow > -1) {
			warenkorbListe.setRowSelectionInterval(selectedRow, selectedRow);
		}
	}
}
