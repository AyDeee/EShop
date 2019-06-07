package shop.local.ui.gui.screens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import shop.local.ui.gui.ShopClientGUI;

public class LoginButtonScreen extends Screen {
	private JButton button;
	
	public LoginButtonScreen(ShopClientGUI gui) {
		super(gui);
	}

	@Override
	protected void InitializePanel() {
		// TODO Auto-generated method stub

		button = new JButton("Login");

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				gui.ChangeScreen(ScreenState.Login);
			}
		});

		add(button, BorderLayout.SOUTH);
	}
}
	
	