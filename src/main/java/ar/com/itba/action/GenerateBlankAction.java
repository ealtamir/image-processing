package ar.com.itba.action;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.com.itba.frame.MainWindow;

@SuppressWarnings("serial")
public class GenerateBlankAction extends AbstractAction {

	private JFrame parent;

	public GenerateBlankAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JTextField widthTextField = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("Width: "));
		optionsPanel.add(widthTextField);

		String msg = "Choose width:";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		int width = Integer.valueOf(widthTextField.getText());
		if (result == JOptionPane.OK_OPTION) {
			BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, width / 2);
			((MainWindow) parent).updateLeftQuickDrawPanel(image);
		}
	}

}
