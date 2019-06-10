package shop.local.ui.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

import shop.local.valueobjects.Rechnung;

public class RechnungsFrame extends JFrame {

	private JPanel panel;
	private JTextArea output;

	public RechnungsFrame(Rechnung rechnung) {
		panel = new JPanel();
		setLayout(new BorderLayout());

		
		output = new JTextArea();
		if (rechnung != null) {
			output.setText(rechnung.Print());
		}
		JScrollPane scrollPane = new JScrollPane(output);
		panel.add(scrollPane);
		add(panel, BorderLayout.CENTER);
		
		
		setSize(640, 480);
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}

}
