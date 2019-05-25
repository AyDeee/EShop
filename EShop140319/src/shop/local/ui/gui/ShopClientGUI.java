package shop.local.ui.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class ShopClientGUI {
	public ShopClientGUI () {
		
		JFrame mainwindow = new JFrame();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new MigLayout("","[10:80:120]","40!"));
			
		JLabel label = new JLabel("Vorgang fortsetzen?");
		JButton okButton = new JButton("ok");
		okButton.addActionListener(new ActionListener()
		{
			  public void actionPerformed(ActionEvent e)
			  {
			    // display/center the jdialog when the button is pressed
			    JDialog d = new JDialog(mainwindow, "Hello", true);
			    d.setLocationRelativeTo(mainwindow);
			    d.setVisible(true);
			  }
			});
		
		JButton ok2Button = new JButton("abort");
		JButton ok3Button = new JButton("cancel");
			
		mainPanel.add(label, "span, align center, wrap");	
		mainPanel.add(okButton);
		mainPanel.add(ok2Button);
		mainPanel.add(ok3Button);

		mainwindow.add(mainPanel);
		mainwindow.pack();
		mainwindow.setVisible(true);
		mainwindow.setLocationRelativeTo(null);
	}
	
	public static void main(String[]args) {
		
		ShopClientGUI GUI = new ShopClientGUI();
	}
}
