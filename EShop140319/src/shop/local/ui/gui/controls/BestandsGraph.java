package shop.local.ui.gui.controls;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class BestandsGraph extends Canvas {
	
	int maxX;
	int maxY;
	int scaleX;
	int scaleY;
	int offsetXAchse;
	int offsetYAchse;
	int[] bestandsHistorie;
	
	public BestandsGraph(int[] historie, int maxX, int maxY, int scaleX, int scaleY) {
		this.bestandsHistorie = historie;
		this.maxX = maxX;
		this.maxY = maxY;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		offsetXAchse = 20;
		offsetYAchse = 20;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.black);
		
		//X-Achse
		g.drawLine(0, maxY * scaleY - offsetYAchse, maxX * scaleX, maxY * scaleY - offsetYAchse);
		
		//Y-Achse
		g.drawLine(0 + offsetXAchse, maxY * scaleY, 0 + offsetXAchse, 0);
		
		
		
		int x = offsetXAchse;
		int yWert = bestandsHistorie[0];
		int y = maxY * scaleY - yWert * scaleY;
        for (int i = 1; i < bestandsHistorie.length; i++) {
        	
        	int newX = i;
        	int newY = maxY * scaleY - bestandsHistorie[i] * scaleY;
        	g.drawLine(x, y, newX, newY);
        	x = newX;
        	y = newY;
        }
    }

}
