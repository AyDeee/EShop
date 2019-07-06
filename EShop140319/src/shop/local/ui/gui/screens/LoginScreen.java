package shop.local.ui.gui.screens;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import shop.local.ui.gui.ShopClientGUI;
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

import javax.swing.JComboBox;

public class LoginScreen extends Screen {

	JLabel idLabel;
	JPasswordField loginPwTextField;
	JButton loginButton;
	JLabel passwordLabel;
	JTextField loginNameTextField;
	JButton registerButton;
	JButton back;
	JComboBox cmbAuswahlListe;

	Kunde kundeImShop;
	Mitarbeiter mitarbeiterImShop;

	public LoginScreen(ShopClientGUI gui) {
		super(gui);
	}

	@Override
	protected void InitializePanel() {

		JPanel loginPanel = new JPanel();
		loginPanel.setBorder(BorderFactory.createTitledBorder("LOGIN"));

		JPanel loginButtonPanel = new JPanel();

		GridBagLayout gridBagLayout = new GridBagLayout();
		loginPanel.setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		String[] auswahl = { "Kunde", "Mitarbeiter" };
		cmbAuswahlListe = new JComboBox(auswahl);
		// cmbAuswahlListe.setSize(cmbAuswahlListe.getPreferredSize());
		// add(cmbAuswahlListe, BorderLayout.NORTH);
		c.gridx = 2;
		c.weightx = 0.1;
		c.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(cmbAuswahlListe, c);
		cmbAuswahlListe.setSelectedIndex(0);
		loginPanel.add(cmbAuswahlListe);

		idLabel = new JLabel("ID:");
		c.gridx = 0;
		c.weightx = 0.1;
		c.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(idLabel, c);
		loginPanel.add(idLabel);

		loginNameTextField = new JTextField();
		c.gridx = 1;
		c.weightx = 0.6;
		gridBagLayout.setConstraints(loginNameTextField, c);
		loginPanel.add(loginNameTextField);

		passwordLabel = new JLabel("Passwort:");
		c.gridx = 0;
		c.weightx = 0.1;
		c.anchor = GridBagConstraints.EAST;
		gridBagLayout.setConstraints(passwordLabel, c);
		loginPanel.add(passwordLabel);

		loginPwTextField = new JPasswordField();
		c.gridx = 1;
		c.weightx = 0.6;
		gridBagLayout.setConstraints(loginPwTextField, c);
		loginPanel.add(loginPwTextField);

		loginButton = new JButton("Anmelden");

		c.gridx = 2;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.SOUTH;
		gridBagLayout.setConstraints(loginButton, c);
		loginButtonPanel.add(loginButton);

		KeyListener loginKey = (KeyListener) new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginButton.doClick();
				}
			}
		};

		loginPwTextField.addKeyListener(loginKey);

		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginDatenPruefen();
			}
		});
		
		back = new JButton("zurueck");

		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gui.ChangeScreen(ScreenState.Startup);
			}
		});
		
		loginButtonPanel.add(back);
		
		registerButton = new JButton("Registrieren");

		c.gridx = 2;
		c.weightx = 0.2;
		c.anchor = GridBagConstraints.SOUTH;
		gridBagLayout.setConstraints(registerButton, c);
		loginButtonPanel.add(registerButton);

		
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gui.ChangeScreen(ScreenState.Registrieren);
				
			}
		});
		
		this.setLayout(new BorderLayout());
		this.add(loginPanel, BorderLayout.CENTER);
		this.add(loginButtonPanel, BorderLayout.SOUTH);


	}

	private void loginDatenPruefen() {
		String pw = new String(loginPwTextField.getPassword());

		String nr = loginNameTextField.getText();
		int id = Integer.parseInt(nr);

		if (cmbAuswahlListe.getSelectedItem() == "Mitarbeiter") {
			Mitarbeiter mitarbeiter = Shop().loginUeberpruefungMitarbeiter(id, pw);
			if (mitarbeiter != null) {				
				gui.getMitarbeiter().SetMitarbeiter(mitarbeiter);
				gui.ChangeScreen(ScreenState.Mitarbeiter);

			}
			else {
				JOptionPane.showMessageDialog(null, "Lieber Mitarbeiter, bitte pruefen Sie Ihre Eingaben oder registrieren Sie sich.", "",JOptionPane.ERROR_MESSAGE);
			}
		} else {
			Kunde kunde = Shop().loginUeberpruefungKunde(id, pw);
			if (kunde != null) {
				gui.getKunde().SetKunde(kunde);
				gui.ChangeScreen(ScreenState.Kunde);
			}
			else {
				JOptionPane.showMessageDialog(null, "Sehr geehrter Kunde, bitte pruefen Sie Ihre Eingaben oder registrieren Sie sich.", "",JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	
}

// private void InitializeScreens() {
//
// MitarbeiterScreen mitarbeiter = new MitarbeiterScreen(gui, getMitarbeiter());
//
// }
//
// public Mitarbeiter getMitarbeiter() {
// return mitarbeiterImShop;
// }
