package shop.local.ui.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class ArtikelListe extends Screen {

	JList liste;
	JButton button;
	
	public ArtikelListe(ShopClientGUI gui) {
		super(gui);
	}
	
	@Override
	protected void InitializePanel() {
		String week[]= { "Monday","Tuesday","Wednesday", 
                "Thursday","Friday","Saturday","Sunday"}; 
 
		
		liste = new JList(week);
		liste.setSize(new Dimension(600, 600));
		
		JScrollPane listScroller = new JScrollPane(liste);
		listScroller.setPreferredSize(new Dimension(700, 500));
		
		button = new JButton("Login");
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				gui.ChangeScreen(1);
			}
		});
		
		panel.add(listScroller);
		panel.add(button);
	}
	
	
	
}
