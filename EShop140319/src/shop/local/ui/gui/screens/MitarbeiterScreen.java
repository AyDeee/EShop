package shop.local.ui.gui.screens;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.domain.exceptions.FalscheBestandsgroesseException;
import shop.local.ui.gui.LogbuchFrame;
import shop.local.ui.gui.RechnungsFrame;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.ui.gui.controls.ArtikelListe;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.ArtikelImWarenkorb;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import shop.local.valueobjects.Person;

public class MitarbeiterScreen extends Screen {
	
	public interface AddArtikelListener {
		public void onArtikelAdded(Artikel artikel);
	}


	private Mitarbeiter eingeloggterMitarbeiter;
	private JButton logout;
	private JButton addButton;
	private JTextField bezeichnungTextField;
	private JTextField nummerTextField;
	private JTextField bestandTextField;
	private JTextField preisTextField;
	private JTextField packungTextField;
	private EShop shop;
	private AddArtikelListener addListener;
	private JButton artikelLoeschen;
	private JTextField nummerTextField2;
	private JTextField nummerTextField3;
	private JTextField bestandTextField2;
	private JButton aendernButton;
	private JButton logbuchButton;
	private JButton abmeldenButton;
	private JButton einfuegenButton;
	private JButton loeschenButton;
	
	public MitarbeiterScreen(ShopClientGUI gui, EShop shop) {
		super(gui);
		this.shop = shop;
		setupEvents();
		
	}
	
	private void setupEvents() {
		einfuegenButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						System.out.println("Event: " + ae.getActionCommand());
						try {
							artikelEinfuegen();
						} catch (ArtikelExistiertBereitsException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (FalscheBestandsgroesseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
		loeschenButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						System.out.println("Event: " + ae.getActionCommand());
						
							try {
								artikelLoeschen();
							} catch (ArtikelExistiertNichtException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

					}
				});
		aendernButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						System.out.println("Event: " + ae.getActionCommand());
								try {
									artikelAnzahlAendern();
								} catch (FalscheBestandsgroesseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ArtikelExistiertNichtException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
					}
				});
		
		logbuchButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						System.out.println("Event: " + ae.getActionCommand());
						new LogbuchFrame(shop.getLogbuch());
					}
				});
	}

	@Override
	protected void InitializePanel() {
		
	
//		JPanel pane = new JPanel(new GridBagLayout());
//
//		add(pane);
		GridBagConstraints c = new GridBagConstraints();
//		c.weightx = 1;
//		c.weighty = 1;
//		c.fill = GridBagConstraints.HORIZONTAL;
		
		
		setLayout(new GridBagLayout());
		
		JLabel text = new JLabel("Artikel Einfuegen");
		c.gridx = 0;
//		c.gridwidth = 3;
		c.gridy = 0;
		add(text, c);
		
		JLabel bezeichnungText = new JLabel("Bezeichnung: ");
		c.gridx = 0;
		c.gridy = 1;
//		c.weightx = 0.5;
//		c.gridwidth = 1;
//		c.gridheight = 1;
		add(bezeichnungText, c);
		
		bezeichnungTextField = new JTextField(7);
		c.gridx = 1;
		c.gridy = 1;
//		c.weightx = 0.5;
//		c.gridwidth = 3;
//		c.gridheight = 1;
		add(bezeichnungTextField, c);
		
		JLabel nummerText = new JLabel(" Nummer: ");
		c.gridx = 2;
		c.gridy = 1;
		add(nummerText, c);
		
		nummerTextField = new JTextField(2);
		c.gridx = 3;
		c.gridy = 1;
		add(nummerTextField, c);
		
		JLabel bestandText = new JLabel(" Bestand: ");
		c.gridx = 4;
		c.gridy = 1;
		add(bestandText, c);
		
		bestandTextField = new JTextField(2);
		c.gridx = 5;
		c.gridy = 1;
		add(bestandTextField, c);
		
		JLabel preisText = new JLabel(" Preis: ");
		c.gridx = 6;
		c.gridy = 1;
		add(preisText, c);
		
		preisTextField = new JTextField(2);
		c.gridx = 7;
		c.gridy = 1;
		add(preisTextField, c);

		JLabel packungsgroesseText = new JLabel(" Packung �: ");
		c.gridx = 8;
		c.gridy = 1;
		add(packungsgroesseText, c);
		
		packungTextField = new JTextField(2);
		c.gridx = 9;
		c.gridy = 1;
		add(packungTextField, c);
		
		einfuegenButton = new JButton("Einfuegen");
		c.gridx = 10;
		c.gridy = 1;
		add(einfuegenButton, c);
		
		
		JLabel text2 = new JLabel("Artikel loeschen");
		c.gridx = 0;
		c.gridy = 2;
		add(text2, c);

		JLabel nummerText2 = new JLabel(" Nummer: ");
		c.gridx = 0;
		c.gridy = 3;
		add(nummerText2, c);
		
		nummerTextField2 = new JTextField(7);
		c.gridx = 1;
		c.gridy = 3;
		add(nummerTextField2, c);
		
		loeschenButton = new JButton("Loeschen");
		c.gridx = 10;
		c.gridy = 3;
		add(loeschenButton, c);
		
		JLabel text3 = new JLabel("Artikelbestand aendern");
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 2;
		add(text3, c);

		JLabel nummerText3 = new JLabel("Nummer:");
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		add(nummerText3, c);
		
		nummerTextField3 = new JTextField(7);
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 1;
		add(nummerTextField3, c);
		
		JLabel bestandText2 = new JLabel(" Bestand: ");
		c.gridx = 2;
		c.gridy = 5;
		c.gridwidth = 1;
		add(bestandText2, c);
		
		bestandTextField2 = new JTextField(7);
		c.gridx = 3;
		c.gridy = 5;
		c.gridwidth = 2;
		add(bestandTextField2, c);
		
		JLabel best = new JLabel(" +||- ");
		c.gridx = 5;
		c.gridy = 5;
		c.gridwidth = 1;
		add(best, c);
		
		
		aendernButton = new JButton("Aendern");
		c.gridx = 10;
		c.gridy = 5;
		c.gridwidth = 1;
		add(aendernButton, c);
		
		logbuchButton = new JButton("zum Logbuch");
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		add(logbuchButton, c);
		
		abmeldenButton = new JButton("Abmelden");
		c.gridx =1;
		c.gridy = 6;
		c.gridwidth = 1;
		add(abmeldenButton, c);
		
		
		
//		this.add(new JLabel("Bezeichnung:"));
//		bezeichnungTextFeld = new JTextField();
//		this.add(bezeichnungTextFeld);
//		this.add(new JLabel("Nummer:"));
//		nummerTextFeld = new JTextField();
//		this.add(nummerTextFeld);
//		this.add(new JLabel("Bestand:"));
//		bestandTextFeld = new JTextField();
//		this.add(bestandTextFeld);
//		this.add(new JLabel("Preis:"));
//		preisTextFeld = new JTextField();
//		this.add(preisTextFeld);
//		this.add(new JLabel("Packungsgroesse:"));
//		packungTextFeld = new JTextField();
//		this.add(packungTextFeld);
//
//		this.add(new JLabel()); // Abstandshalter
//		addButton = new JButton("Einfuegen");
//		this.add(addButton);
//		artikelLoeschen = new JButton("Loeschen");
//		this.add(artikelLoeschen);
//		
//		
//		logout = new JButton("Abmelden");
//
		abmeldenButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				eingeloggterMitarbeiter = null;
				gui.ChangeScreen(ScreenState.Startup);
			}
		});
//		
//
//
//		
//		
//	
//		add(logout);
	}
	


	private void artikelEinfuegen() throws ArtikelExistiertBereitsException, FalscheBestandsgroesseException {
		String bezeichnung = bezeichnungTextField.getText();
		String nummer = nummerTextField.getText();
		String bestand = bestandTextField.getText();
		String preis = preisTextField.getText();
		String packung = packungTextField.getText();

		
		if (!nummer.isEmpty() && !bezeichnung.isEmpty()&& !bestand.isEmpty()&& !preis.isEmpty()) {

				int nummerAlsInt = Integer.parseInt(nummer);
				int bestandAlsInt = Integer.parseInt(bestand);
				float preisAlsFloat = Float.parseFloat(preis);
				int packungAlsInt = Integer.parseInt(packung);
				Artikel artikel;

				if (packungAlsInt>1) {
					artikel = shop.fuegeMassengutArtikelEin(bezeichnung, nummerAlsInt, bestandAlsInt, preisAlsFloat, packungAlsInt, eingeloggterMitarbeiter);
					// Am Ende Listener, d.h. unseren Frame benachrichtigen:
					gui.onArtikelAdded();
				}else if (packungAlsInt==1) {
					artikel= shop.fuegeArtikelEin(bezeichnung, nummerAlsInt, bestandAlsInt, preisAlsFloat, eingeloggterMitarbeiter);
					// Am Ende Listener, d.h. unseren Frame benachrichtigen:
					gui.onArtikelAdded();
				}
				nummerTextField.setText("");
				bezeichnungTextField.setText("");
				bestandTextField.setText("");
				preisTextField.setText("");
				packungTextField.setText("");
		}
		
	}
	
	private void artikelLoeschen() throws ArtikelExistiertNichtException {
		
		String nummer = nummerTextField2.getText();
		
		if(!nummer.isEmpty()) {
			int nummerAlsInt = Integer.parseInt(nummer);
			Artikel artikel = null;
			artikel = shop.sucheNachNummer(nummerAlsInt);
			artikel= shop.loescheArtikel(artikel.getNummer(), eingeloggterMitarbeiter);
			gui.onArtikelAdded();
		}
		nummerTextField2.setText("");
	}
	
	private void artikelAnzahlAendern() throws FalscheBestandsgroesseException, ArtikelExistiertNichtException  {
		
		String nummer = nummerTextField3.getText();
		String bestand = bestandTextField2.getText();
		
		if(!nummer.isEmpty()) {
			int nummerAlsInt = Integer.parseInt(nummer);
			int bestandAlsInt = Integer.parseInt(bestand);
			Artikel artikel = null;
			artikel = shop.sucheNachNummer(nummerAlsInt);
			artikel = shop.bestandErhoehen(nummerAlsInt, bestandAlsInt, eingeloggterMitarbeiter);
			gui.onArtikelAdded();
		}
		nummerTextField3.setText("");
		bestandTextField2.setText("");
	}
	


	public void SetMitarbeiter(Mitarbeiter mitarbeiter) {
		eingeloggterMitarbeiter = mitarbeiter;
	}
		


}
