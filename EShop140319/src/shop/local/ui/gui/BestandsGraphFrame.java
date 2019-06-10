package shop.local.ui.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import shop.local.domain.Logbuch;
import shop.local.ui.gui.controls.BestandsGraph;
import shop.local.valueobjects.Artikel;

public class BestandsGraphFrame extends JFrame {

	BestandsGraph canvas;
	
	public BestandsGraphFrame(Logbuch log, Artikel artikel) {
		int[] bestand = log.getBestandsHistorie(artikel.getNummer());
		
		canvas = new BestandsGraph(bestand,30,50,20,10);
		
		
		
		
		JScrollPane panel = new JScrollPane(canvas);
		add(panel);
		
		setSize(640, 480);
		pack();
		setVisible(true);
	}
	
}
