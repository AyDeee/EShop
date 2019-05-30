package shop.local.ui.gui.screens;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.FalscheBestandsgroesseException;
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

	private JLabel willkommen;
	private Mitarbeiter eingeloggterMitarbeiter;
	private JButton logout;
	private JButton addButton;
	private JTextField bezeichnungTextFeld;
	private JTextField nummerTextFeld;
	private JTextField bestandTextFeld;
	private JTextField preisTextFeld;
	private JTextField packungTextFeld;
	private EShop shop;
	private AddArtikelListener addListener;
	
	public MitarbeiterScreen(ShopClientGUI gui) {
		super(gui);
		setupEvents();

	}
	
	private void setupEvents() {
		addButton.addActionListener(
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ae) {
						System.out.println("Event: " + ae.getActionCommand());
						artikelEinfuegen();
					}
				});
	}

	@Override
	protected void InitializePanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		
		willkommen = new JLabel();
		willkommen.setText("Hallo " 
									//+ eingeloggterMitarbeiter.getName()  Nullpointer??
																		);
		add(willkommen);
		
		this.add(new JLabel("Bezeichnung:"));
		bezeichnungTextFeld = new JTextField();
		this.add(bezeichnungTextFeld);
		this.add(new JLabel("Nummer:"));
		nummerTextFeld = new JTextField();
		this.add(nummerTextFeld);
		this.add(new JLabel("Bestand:"));
		bestandTextFeld = new JTextField();
		this.add(bestandTextFeld);
		this.add(new JLabel("Preis:"));
		preisTextFeld = new JTextField();
		this.add(preisTextFeld);
		this.add(new JLabel("Packungsgroesse:"));
		packungTextFeld = new JTextField();
		this.add(packungTextFeld);

		this.add(new JLabel()); // Abstandshalter
		addButton = new JButton("Einfuegen");
		this.add(addButton);
		
		
		logout = new JButton("Abmelden");

		logout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gui.ChangeScreen(ScreenState.Startup);
			}
		});
		

		
		
		
	
		add(logout);
	}
	


	private void artikelEinfuegen() {
		String bezeichnung = bezeichnungTextFeld.getText();
		String nummer = nummerTextFeld.getText();
		String bestand = bestandTextFeld.getText();
		String preis = preisTextFeld.getText();
		String packung = packungTextFeld.getText();

		
		if (!nummer.isEmpty() && !bezeichnung.isEmpty()&& !bestand.isEmpty()&& !preis.isEmpty()) {
			try {
				int nummerAlsInt = Integer.parseInt(nummer);
				int bestandAlsInt = Integer.parseInt(bestand);
				float preisAlsFloat = Float.parseFloat(preis);
				int packungAlsInt = Integer.parseInt(packung);
				Artikel artikel;
				try {
					artikel= shop.fuegeArtikelEin(bezeichnung, nummerAlsInt, bestandAlsInt, preisAlsFloat, eingeloggterMitarbeiter);
					//artikel = shop.fuegeMassengutArtikelEin(bezeichnung, nummerAlsInt, bestandAlsInt, preisAlsFloat, packungAlsInt, eingeloggterMitarbeiter);
					nummerTextFeld.setText("");
					bezeichnungTextFeld.setText("");
					bestandTextFeld.setText("");
					preisTextFeld.setText("");
					packungTextFeld.setText("");

					// Am Ende Listener, d.h. unseren Frame benachrichtigen:
					addListener.onArtikelAdded(artikel);
				} catch (FalscheBestandsgroesseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (ArtikelExistiertBereitsException bebe) {
				System.err.println(bebe.getMessage());
			} 
		}
	}

	public void SetMitarbeiter(Mitarbeiter mitarbeiter) {
		eingeloggterMitarbeiter = mitarbeiter;
	}
		


}
