package shop.local.ui.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import shop.local.domain.EShop;
import shop.local.domain.Logbuch;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
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
	

