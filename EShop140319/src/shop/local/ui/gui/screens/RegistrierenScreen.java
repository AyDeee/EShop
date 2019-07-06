package shop.local.ui.gui.screens;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.ArtikelExistiertBereitsException;
import shop.local.domain.exceptions.FalscheBestandsgroesseException;
import shop.local.domain.exceptions.KundeExistiertBereitsException;
import shop.local.domain.exceptions.MitarbeiterExistiertBereitsException;
import shop.local.ui.gui.ShopClientGUI;
import shop.local.valueobjects.Artikel;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class RegistrierenScreen extends Screen{
	
	JLabel nameLabel;
	JTextField registerNameTextFeld;
	JLabel idLabel;
	JTextField registerIdTextFeld;
	JLabel passwordLabel;
	JTextField registerPwTextFeld;
	JLabel plzLabel;
	JTextField registerPLZTextFeld;
	JLabel ortLabel;
	JTextField registerOrtTextFeld;
	JLabel strasseLabel;
	JTextField registerStrTextFeld;
	JLabel nummerLabel;
	JTextField registerNrTextFeld;
	JLabel ibanLabel;
	JTextField registerIbanTextFeld;
	
    JButton registerButton;
	
	private EShop shop;
	JComboBox cmbAuswahlListe;
	
	boolean m = false;
	private JButton back;
	
	public RegistrierenScreen(ShopClientGUI gui) {
		super(gui);
		
	}


	protected void InitializePanel() {
	    

	    JPanel regPanel = new JPanel();
	    regPanel.setBorder(BorderFactory.createTitledBorder("REGISTRIEREN"));

	    JPanel regButtonPanel = new JPanel();

	    GridBagLayout gridBagLayout = new GridBagLayout();
	    regPanel.setLayout(gridBagLayout);
	    GridBagConstraints c = new GridBagConstraints();
	    c.fill = GridBagConstraints.HORIZONTAL;
	    
	    String[] auswahl = {"Kunde","Mitarbeiter"};
		cmbAuswahlListe = new JComboBox(auswahl);
		//cmbAuswahlListe.setSize(cmbAuswahlListe.getPreferredSize());
		//add(cmbAuswahlListe, BorderLayout.NORTH);
		c.gridx = 2;
	    c.weightx = 0.1;
	    c.anchor = GridBagConstraints.EAST;
	    gridBagLayout.setConstraints(cmbAuswahlListe, c);
	    cmbAuswahlListe.setSelectedIndex(0);
	    regPanel.add(cmbAuswahlListe);

	    //NAME
	    nameLabel = new JLabel("Name:");
	    c.gridx = 0;
	    c.weightx = 0.1;
	    c.anchor = GridBagConstraints.EAST;
	    gridBagLayout.setConstraints(nameLabel, c);
	    regPanel.add(nameLabel);

	    registerNameTextFeld = new JTextField();
	    c.gridx = 1;
	    c.weightx = 0.6;
	    gridBagLayout.setConstraints(registerNameTextFeld, c);
	    regPanel.add(registerNameTextFeld);

	    //PASSWORT
	    passwordLabel = new JLabel("Passwort:");
	    c.gridx = 0;
	    c.weightx = 0.1;
	    c.anchor = GridBagConstraints.EAST;
	    gridBagLayout.setConstraints(passwordLabel, c);
	    regPanel.add(passwordLabel);

	    registerPwTextFeld = new JTextField();
	    c.gridx = 1;
	    c.weightx = 0.6;
	    gridBagLayout.setConstraints(registerPwTextFeld, c);
	    regPanel.add(registerPwTextFeld);
	    
	    //ORT
	    ortLabel = new JLabel("Ort:");
	    c.gridx = 0;
	    c.weightx = 0.1;
	    c.anchor = GridBagConstraints.EAST;
	    gridBagLayout.setConstraints(ortLabel, c);
	    regPanel.add(ortLabel);

	    registerOrtTextFeld = new JTextField();
	    c.gridx = 1;
	    c.weightx = 0.6;
	    gridBagLayout.setConstraints(registerOrtTextFeld, c);
	    regPanel.add(registerOrtTextFeld);

	    //PLZ
	    plzLabel = new JLabel("PLZ:");
	    c.gridx = 0;
	    c.weightx = 0.1;
	    c.anchor = GridBagConstraints.EAST;
	    gridBagLayout.setConstraints(plzLabel, c);
	    regPanel.add(plzLabel);

	    registerPLZTextFeld = new JTextField();
	    c.gridx = 1;
	    c.weightx = 0.6;
	    gridBagLayout.setConstraints(registerPLZTextFeld, c);
	    regPanel.add(registerPLZTextFeld);
	    

	    //STRASSE
	    strasseLabel = new JLabel("Strasse:");
	    c.gridx = 0;
	    c.weightx = 0.1;
	    c.anchor = GridBagConstraints.EAST;
	    gridBagLayout.setConstraints(strasseLabel, c);
	    regPanel.add(strasseLabel);

	    registerStrTextFeld = new JTextField();
	    c.gridx = 1;
	    c.weightx = 0.6;
	    gridBagLayout.setConstraints(registerStrTextFeld, c);
	    regPanel.add(registerStrTextFeld);
	    
	    //NR
	    nummerLabel = new JLabel("Hausnummer:");
	    c.gridx = 0;
	    c.weightx = 0.1;
	    c.anchor = GridBagConstraints.EAST;
	    gridBagLayout.setConstraints(nummerLabel, c);
	    regPanel.add(nummerLabel);

	    registerNrTextFeld = new JTextField();
	    c.gridx = 1;
	    c.weightx = 0.6;
	    gridBagLayout.setConstraints(registerNrTextFeld, c);
	    regPanel.add(registerNrTextFeld);
	    
	    //NR
	    ibanLabel = new JLabel("Iban:");
	    c.gridx = 0;
	    c.weightx = 0.1;
	    c.anchor = GridBagConstraints.EAST;
	    gridBagLayout.setConstraints(ibanLabel, c);
	    regPanel.add(ibanLabel);

	    registerIbanTextFeld = new JTextField();
	    c.gridx = 1;
	    c.weightx = 0.6;
	    gridBagLayout.setConstraints(registerIbanTextFeld, c);
	    regPanel.add(registerIbanTextFeld);


	    registerButton = new JButton("Registrieren");
	    
	    c.gridx = 2;
	    c.weightx = 0.2;
	    c.anchor = GridBagConstraints.SOUTH;
	    gridBagLayout.setConstraints(registerButton, c);
	    regButtonPanel.add(registerButton);
	    

	    this.setLayout(new BorderLayout());
	    this.add(regPanel, BorderLayout.CENTER);
	    this.add(regButtonPanel, BorderLayout.SOUTH);

	    this.setSize(1280, 720);
	    this.setVisible(true);
	    
	    registerButton.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									
									if (cmbAuswahlListe.getSelectedItem() == "Mitarbeiter") {
										mitarbeiterEinfuegen();
									}else {
										kundeEinfuegen();
									}

								}
							});
	    
	    back = new JButton("zurueck");

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gui.ChangeScreen(ScreenState.Startup);
			}
		});  
		
		regButtonPanel.add(back);
}

	
	private void mitarbeiterEinfuegen() {
		
		String name = registerNameTextFeld.getText();
		String pw = registerPwTextFeld.getText();
		String ort = registerOrtTextFeld.getText();
		String plz = registerPLZTextFeld.getText();
		String strasse = registerStrTextFeld.getText();
		String nr = registerNrTextFeld.getText();
		String iban = registerIbanTextFeld.getText();
		

		if ( !name.isEmpty() && !pw.isEmpty()&& !ort.isEmpty()&& !plz.isEmpty() && !strasse.isEmpty()
				&& !nr.isEmpty() && !iban.isEmpty()) {
			
			try {
				Mitarbeiter mitarbeiter = Shop().fuegeMitarbeiterEin(name, strasse, nr, plz, ort, iban, pw);
				gui.GetShop().Save();
				JOptionPane.showMessageDialog(null, "Deine User-ID ist"+ mitarbeiter.getId(), "title", JOptionPane.INFORMATION_MESSAGE);
				gui.ChangeScreen(ScreenState.Mitarbeiter);
			} catch (MitarbeiterExistiertBereitsException me) {
				// Hier Fehlerbehandlung
				System.out.println(me.getMessage());
				JOptionPane.showMessageDialog(null, "Dieser Mitarbeiter existiert bereits", "",JOptionPane.ERROR_MESSAGE);
				// me.printStackTrace()
			}
			
		}
	}
	
	private void kundeEinfuegen() {
		
		String name = registerNameTextFeld.getText();
		String pw = registerPwTextFeld.getText();
		String ort = registerOrtTextFeld.getText();
		String plz = registerPLZTextFeld.getText();
		String strasse = registerStrTextFeld.getText();
		String nr = registerNrTextFeld.getText();
		String iban = registerIbanTextFeld.getText();
		

		if ( !name.isEmpty() && !pw.isEmpty()&& !ort.isEmpty()&& !plz.isEmpty() && !strasse.isEmpty()
				&& !nr.isEmpty() && !iban.isEmpty()) {
			
			try {
				Kunde kunde = Shop().fuegeKundeEin(name, strasse, nr, plz, ort, iban, pw);
				gui.GetShop().Save();
				JOptionPane.showMessageDialog(null, "Deine User-ID ist"+ kunde.getId(), "title", JOptionPane.INFORMATION_MESSAGE);
				gui.ChangeScreen(ScreenState.Kunde);
			} catch (KundeExistiertBereitsException ke) {
				// Hier Fehlerbehandlung
				System.out.println(ke.getMessage());
				JOptionPane.showMessageDialog(null, "Dieser Kunde existiert bereits", "",JOptionPane.ERROR_MESSAGE);
				// me.printStackTrace()
			}
			
		}
	}
	

}






