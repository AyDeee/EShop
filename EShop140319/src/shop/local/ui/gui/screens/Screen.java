package shop.local.ui.gui.screens;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;
import shop.local.domain.EShop;
import shop.local.ui.gui.ShopClientGUI;

public abstract class Screen extends JPanel {

	protected ShopClientGUI gui;
	
	public Screen(ShopClientGUI gui) {
		this.gui = gui;		
		InitializePanel();
	}
	
	
	protected abstract void InitializePanel();
	
	public EShop Shop() {
		return gui.GetShop();
	}
	
}
