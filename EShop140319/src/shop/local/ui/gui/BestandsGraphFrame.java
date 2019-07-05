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

// public class BestandsGraphFrame extends JFrame {

//	BestandsGraph canvas;
//	
//	public BestandsGraphFrame(Logbuch log, Artikel artikel) {
//		int[] bestand = log.getBestandsHistorie(artikel.getNummer());
//		
//		canvas = new BestandsGraph(bestand,30,50,20,10);
//		
//		
//		
//		
//		JScrollPane panel = new JScrollPane(canvas);
//		add(panel);
//		
//		setSize(640, 480);
//		pack();
//		setVisible(true);
//	}
	
	public class BestandsGraphFrame extends JPanel {

	    private int width = 800;
	    private int heigth = 400;
	    private int padding = 25;
	    private int labelPadding = 25;
	    private Color lineColor = new Color(44, 102, 230, 180);
	    private Color pointColor = new Color(100, 100, 100, 180);
	    private Color gridColor = new Color(200, 200, 200, 200);
	    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	    private int pointWidth = 4;
	    private int numberYDivisions = 10;
	    private List<Double> scores;
	    private EShop shop;
	    private int nummer;

	    public BestandsGraphFrame(EShop shop) {
	    	 this.shop = shop;
	    	this.setPreferredSize(new Dimension(550, 350));
	    	List<Double> scores = new ArrayList<>();
	    	for(int i = 0; i<31;i++) {
	    		scores.add(0.0);
	    	}
	    	this.scores = scores;	
	    }
	    


	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        Graphics2D g2 = (Graphics2D) g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	        double xScale = ((double) getWidth() - (2 * padding) - labelPadding) / (scores.size() - 1);
	        double yScale = ((double) getHeight() - 2 * padding - labelPadding) / (getMaxScore() - getMinScore());

	        List<Point> graphPoints = new ArrayList<>();
	        for (int i = 30; i >= 0 ; i--) {
	            int x1 = (int) (i * xScale + padding + labelPadding);
	            int y1 = (int) (((getMaxScore()) - scores.get(i)) * yScale + padding);
	            graphPoints.add(new Point(x1, y1));
	        }

	        // draw white background
	        g2.setColor(Color.WHITE);
	        g2.fillRect(padding + labelPadding, padding, getWidth() - (2 * padding) - labelPadding, getHeight() - 2 * padding - labelPadding);
	        g2.setColor(Color.BLACK);

	        // create hatch marks and grid lines for y axis.
	        for (int i = 0; i < numberYDivisions + 1; i++) {
	            int x0 = padding + labelPadding;
	            int x1 = pointWidth + padding + labelPadding;
	            int y0 = getHeight() - ((i * (getHeight() - padding * 2 - labelPadding)) / numberYDivisions + padding + labelPadding);
	            int y1 = y0;
	            if (scores.size() > 0) {
	                g2.setColor(gridColor);
	                g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
	                g2.setColor(Color.BLACK);
	                String yLabel = (int) ((getMaxScore()) * ((i * 1.0) / numberYDivisions) * 100)/ 100.0 + "";
	                //((int) ((getMinScore() + (getMaxScore() - getMinScore()) * ((i * 1.0) / numberYDivisions)) * 100)) / 100.0 + "";
	                FontMetrics metrics = g2.getFontMetrics();
	                int labelWidth = metrics.stringWidth(yLabel);
	                g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
	            }
	            g2.drawLine(x0, y0, x1, y1);
	        }

	        // and for x axis
	        for (int i = 0; i < scores.size(); i++) {
	            if (scores.size() > 0) {
	                int x0 = i * (getWidth() - padding * 2 - labelPadding) / (scores.size() - 1) + padding + labelPadding;
	                int x1 = x0;
	                int y0 = getHeight() - padding - labelPadding;
	                int y1 = y0 - pointWidth;
	                if ((i % ((int) ((scores.size() / 20.0)) + 1)) == 0) {
	                    g2.setColor(gridColor);
	                    g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
	                    g2.setColor(Color.BLACK);
	                    String xLabel = i + "";
	                    FontMetrics metrics = g2.getFontMetrics();
	                    int labelWidth = metrics.stringWidth(xLabel);
	                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
	                }
	                g2.drawLine(x0, y0, x1, y1);
	            }
	        }
	 
	        g2.drawString("Stück", 20, 10);
	        g2.drawString("Tag", getWidth()-20, getHeight()-40);

	        // create x and y axes 
	        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, padding + labelPadding, padding);
	        g2.drawLine(padding + labelPadding, getHeight() - padding - labelPadding, getWidth() - padding, getHeight() - padding - labelPadding);

	        Stroke oldStroke = g2.getStroke();
	        g2.setColor(lineColor);
	        g2.setStroke(GRAPH_STROKE);
	        for (int i = 0; i < graphPoints.size() - 1; i++) {
	            int x1 = graphPoints.get(i).x;
	            int y1 = graphPoints.get(i).y;
	            int x2 = graphPoints.get(i + 1).x;
	            int y2 = graphPoints.get(i + 1).y;
	            g2.drawLine(x1, y1, x2, y2);
	        }

	        g2.setStroke(oldStroke);
	        g2.setColor(pointColor);
	        for (int i = 0; i < graphPoints.size(); i++) {
	            int x = graphPoints.get(i).x - pointWidth / 2;
	            int y = graphPoints.get(i).y - pointWidth / 2;
	            int ovalW = pointWidth;
	            int ovalH = pointWidth;
	            g2.fillOval(x, y, ovalW, ovalH);
	        }
	    }


	    private double getMinScore() {
	        double minScore = Double.MAX_VALUE;
	        for (Double score : scores) {
	            minScore = Math.min(minScore, score);
	        }
	        return 0;
	    }

	    private double getMaxScore() {
	        double maxScore = Double.MIN_VALUE;
	        for (Double score : scores) {
	            maxScore = Math.max(maxScore, score);
	        }
	        return maxScore*1.2;
	    }

	    public void setScores(List<Double> scores) {
	        this.scores = scores;
	        invalidate();
	        this.repaint();
	    }

	    public List<Double> getScores() {
	        return scores;
	    }
	    
	    public void setNummer(int nummer) throws ArtikelExistiertNichtException {
	    	scores = nummerErmitteln(nummer);
	    	this.repaint();
	    }
	    
	    public List<Double> nummerErmitteln(int nummer) throws ArtikelExistiertNichtException {
	    	List<Double> liste = new ArrayList<>();
	    	SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
			Date now = new Date();
	       Calendar calendar = new GregorianCalendar();
	       calendar.setTime(now);
	       calendar.add(Calendar.DATE, 1);
	  	 	Date future = calendar.getTime();
	        double bestand = shop.aktuellerBestand(nummer);
	        System.out.println(bestand);
//	        y1 = bestand*5;
		     for(int i = 0; i < 31 ; i++) {
		    	 calendar.add(Calendar.DATE, -1);
		    	 future = calendar.getTime();
		    	 String d = format.format(future);
		    	 int datum = Integer.parseInt(d);
		    	 System.out.println(datum);
		    	 System.out.println(bestand);
		    	 liste.add(bestand);
		    	 bestand += shop.artikelStatistik(nummer, datum);
	       }

		   //Liste umdrehen, damit das aktuelle datum an letzter Stelle steht
		   List<Double> liste2 = new ArrayList<>();
		   for(int i = 0, j = liste.size()-1; i < liste.size(); i++, j--){
	           liste2.add(liste.get(j));
	       }
		   for(int i = 0; i < liste.size(); i++) {
			   System.out.println(liste2.get(i));
		   }
	       
		   return liste2;
	    }


	}


