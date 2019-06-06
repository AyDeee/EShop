package shop.local.ui.gui.screens;

import javax.swing.JButton;

import shop.local.ui.gui.ShopClientGUI;
import shop.local.valueobjects.Kunde;
import shop.local.valueobjects.Mitarbeiter;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JComboBox;


public class LoginScreen extends Screen {

	JFrame frame;
	JButton button;
	JLabel labelId;
	JLabel labelPw;
	JLabel reminder;
	JComboBox cmbAuswahlListe;
	JTextField id;
	JTextField passwort;
	Kunde kundeImShop;
	Mitarbeiter mitarbeiterImShop;
	
	
	public LoginScreen(ShopClientGUI gui) {
		super(gui);
	}
	
	@Override
	protected void InitializePanel() {
		setLayout(new BorderLayout());
		//Dropdown
		String[] auswahl = {"Auswahl","Kunde","Mitarbeiter"};
		cmbAuswahlListe = new JComboBox(auswahl);

		
		//Label
		labelId = new JLabel("Id: ");
		//labelId.setBounds(1,10,100,45);
	
		labelPw = new JLabel("Passwort: ");
		//labelPw.setBounds(1, 10, 100, 45);
		
		reminder = new JLabel();
		//Text
		id = new JTextField();
		//id.setBounds(214,98,154,23);
		//frame.getContentPane().add(id);
		//id.setColumns(10);
		
		
		
		passwort = new JTextField ();
		
		button = new JButton("Ok");
		//button.setBounds(1, 10, 100, 80);
		

		//add(labelId);
		//add(labelPw);
		add(reminder);

		cmbAuswahlListe.setSize(cmbAuswahlListe.getPreferredSize());
		add(cmbAuswahlListe, BorderLayout.NORTH);
		
		cmbAuswahlListe.setSelectedIndex(0);
		cmbAuswahlListe.addActionListener(new ActionListener() {
 
			
			@Override	
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == cmbAuswahlListe) {
					JComboBox cb = (JComboBox)e.getSource();
					String msg = (String)cb.getSelectedItem();
					
					switch(msg) {
						case "Auswahl": 
							reminder.setText("Geben Sie an ob Sie ein Mitarbeiter oder Kunde sind.");
							break;
						case "Kunde":
							button.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									kundeImShop = Shop().loginUeberpruefungKunde(0, "123");
									gui.getKunde().SetKunde(kundeImShop);
									gui.ChangeScreen(ScreenState.Kunde);
								}
							});
							break;
						case "Mitarbeiter":
							button.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									mitarbeiterImShop = Shop().loginUeberpruefungMitarbeiter(0, "123");
									gui.getMitarbeiter().SetMitarbeiter(mitarbeiterImShop);
									gui.ChangeScreen(ScreenState.Mitarbeiter);
								}
							});
							break;
						default: System.out.println("Ein Fehler ist aufgetreten.");
					}
					
				}
				
			}
		});
		
		
		add(button, BorderLayout.SOUTH);
		
		

		
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
}
