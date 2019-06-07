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
	    
	    
	    String[] auswahl = {"Auswahl","Kunde","Mitarbeiter"};
		cmbAuswahlListe = new JComboBox(auswahl);
		//cmbAuswahlListe.setSize(cmbAuswahlListe.getPreferredSize());
		//add(cmbAuswahlListe, BorderLayout.NORTH);
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

	    registerButton = new JButton("Registrieren");
	    
	    c.gridx = 2;
	    c.weightx = 0.2;
	    c.anchor = GridBagConstraints.SOUTH;
	    gridBagLayout.setConstraints(registerButton, c);
	    loginButtonPanel.add(registerButton);

	    this.setLayout(new BorderLayout());
	    this.add(loginPanel, BorderLayout.CENTER);
	    this.add(loginButtonPanel, BorderLayout.SOUTH);
	}


	    
	    
		public void actionPerformed(ActionEvent e) {
			
			if (e.getSource() == cmbAuswahlListe) {
				JComboBox cb = (JComboBox)e.getSource();
				String msg = (String)cb.getSelectedItem();
				
				switch(msg) {
					case "Auswahl": 
						//reminder.setText("Geben Sie an ob Sie ein Mitarbeiter oder Kunde sind.");
						break;
					case "Kunde" :
						
						
						break;
					case "Mitarbeiter":
						
						break;
					default: System.out.println("Ein Fehler ist aufgetreten.");
				}
				
			}
			
		}
	}


//	private void InitializeScreens() {
//
//		MitarbeiterScreen mitarbeiter = new MitarbeiterScreen(gui, getMitarbeiter());
//
//	}
//
//	public Mitarbeiter getMitarbeiter() {
//		return mitarbeiterImShop;
//	}


