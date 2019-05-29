package shop.local.ui.gui.screens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import shop.local.ui.gui.ShopClientGUI;

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
				gui.ChangeScreen(0);
			}
		});
		add(button, BorderLayout.SOUTH);
	}

	
}
