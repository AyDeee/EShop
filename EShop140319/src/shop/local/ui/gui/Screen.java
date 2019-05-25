package shop.local.ui.gui;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;
import shop.local.domain.EShop;

public abstract class Screen {

	protected JPanel panel;
	protected ShopClientGUI gui;
	
	public Screen(ShopClientGUI gui) {
		this.gui = gui;
		panel = new JPanel();
		panel.setLayout(new MigLayout("","[10:80:120]","40!"));
		InitializePanel();
	}
	
	
	protected abstract void InitializePanel();
	
	public EShop Shop() {
		return gui.GetShop();
	}
	
	public JPanel GetPanel() {
		return panel;
	}
}
