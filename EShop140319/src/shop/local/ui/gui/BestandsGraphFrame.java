package shop.local.ui.gui;

//import javax.swing.JFrame;
//import javax.swing.JScrollPane;
//
//import shop.local.domain.Logbuch;
//import shop.local.ui.gui.controls.BestandsGraph;
//import shop.local.valueobjects.Artikel;
//
//public class BestandsGraphFrame extends JFrame {
//
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
//	
//}

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import shop.local.domain.EShop;
import shop.local.domain.exceptions.ArtikelExistiertNichtException;
import shop.local.valueobjects.LogbuchEintrag;




public class BestandsGraphFrame extends JPanel {

	private EShop shop;
	private String chartTitle = "Artikel";
	private ChartPanel chartPanel;
	private JFreeChart chart;
	private JPanel topWrapper;
	private DefaultCategoryDataset data;

	public BestandsGraphFrame(EShop shop) {
		this.shop = shop;
		this.setLayout(new BorderLayout());
		JButton artikelAnzeigen = new JButton("Artikel Anzeigen");
		JTextField artikelNummer = new JTextField("Artikelnummer");
		data = new DefaultCategoryDataset();

		topWrapper = new JPanel();
		topWrapper.setLayout(new GridLayout(5, 4));

		chart = ChartFactory.createLineChart(null, "Tag", "Bestand", data, PlotOrientation.VERTICAL, false, false,
				false);

		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 367));

		chart.setBackgroundPaint(null);
		chartPanel.setBackground(null);

		JLabel artikelNameAnzeigen = new JLabel();

		topWrapper.add(new JLabel());
		topWrapper.add(new JLabel());
		topWrapper.add(new JLabel());
		topWrapper.add(new JLabel());

		topWrapper.add(new JLabel());
		topWrapper.add(artikelNummer);
		topWrapper.add(artikelAnzeigen);
		topWrapper.add(new JLabel());

		topWrapper.add(new JLabel());
		topWrapper.add(new JLabel());
		topWrapper.add(new JLabel());
		topWrapper.add(new JLabel());

		topWrapper.add(new JLabel());
		topWrapper.add(new JLabel("Aktuelle Artikel", SwingConstants.CENTER));
		topWrapper.add(artikelNameAnzeigen);
		artikelNameAnzeigen.setHorizontalAlignment(SwingConstants.CENTER);
		topWrapper.add(new JLabel());

		topWrapper.add(new JLabel());
		topWrapper.add(new JSeparator(JSeparator.HORIZONTAL));
		topWrapper.add(new JSeparator(JSeparator.HORIZONTAL));
		topWrapper.add(new JLabel());

		this.add(chartPanel, BorderLayout.CENTER);
		this.add(topWrapper, BorderLayout.NORTH);

		artikelNummer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				artikelNummer.setText("");
			}
		});

		artikelAnzeigen.addActionListener(new ActionListener()  {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean check = artikelNummer.getText().matches("[1234567890]*");
				int nummer = 0;
				System.out.println(check);
				if (check) {
					nummer = Integer.parseInt(artikelNummer.getText());
					System.out.println(nummer);

				} else
					try {
						if (shop.sucheNachNummer(nummer) == null) {
							JOptionPane.showMessageDialog(BestandsGraphFrame.this,
									"Falsche Eingabe - Oder Artikel Existiert nicht", "Fehler", JOptionPane.ERROR_MESSAGE);
							return;
						}
					} catch (HeadlessException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (ArtikelExistiertNichtException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				System.out.println("test1");
				data.clear();
				try {
					createDataset(nummer);
				} catch (ArtikelExistiertNichtException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					artikelNameAnzeigen.setText(shop.sucheNachNummer(nummer).getTitel());
				} catch (ArtikelExistiertNichtException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				chart.fireChartChanged();

			}
		});

	}

	private void createDataset(int i) throws ArtikelExistiertNichtException {
		chartTitle = shop.sucheNachNummer(i).getTitel();

		LogbuchEintrag eintrag = shop.sucheNachLogbuchEintrag(i);


			//String datumTag = eintrag.getDatum().substring(3, 5);
			int anzahl = eintrag.getAnzahl();
			//data.addValue(anzahl, "artikel", datumTag);
		
	}

}