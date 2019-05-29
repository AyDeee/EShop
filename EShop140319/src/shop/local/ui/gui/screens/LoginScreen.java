package shop.local.ui.gui.screens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import shop.local.ui.gui.ShopClientGUI;
import shop.local.valueobjects.Kunde;

public class LoginScreen extends Screen {

	JButton button;
	
	public LoginScreen(ShopClientGUI gui) {
		super(gui);
	}

	@Override
	protected void InitializePanel() {
		
		button = new JButton("Ok");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Kunde kundeImShop = Shop().loginUeberpruefungKunde(0, "123");
				gui.getKunde().SetKunde(kundeImShop);
				
				gui.ChangeScreen(ScreenState.Kunde);
			}
		});
		add(button, BorderLayout.SOUTH);
	}

	
}
