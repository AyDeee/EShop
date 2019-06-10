package shop.local.ui.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import shop.local.domain.Logbuch;
import shop.local.valueobjects.Rechnung;

public class LogbuchFrame extends JFrame {
	private JPanel panel;
	private JTextArea output;

	public LogbuchFrame(Logbuch logbuch) {
		panel = new JPanel();
		setLayout(new BorderLayout());

		
		output = new JTextArea();
		if (logbuch != null) {
			output.setText(logbuch.Print());
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
