package ar.com.itba.action;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.com.itba.frame.MainWindow;

@SuppressWarnings("serial")
public class EnchanceContrastAction extends AbstractAction {

	private JFrame parent;
	private BufferedImage image;

	public EnchanceContrastAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		image = ((MainWindow) parent).getLeftQuickDrawPanel().image();
		askUserValues(parent);
	}

	private void askUserValues(Component component) {
		JTextField value1 = new JTextField(3);
		JTextField value2 = new JTextField(3);
		JTextField enhancement = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("Value 1: "));
		optionsPanel.add(value1);
		optionsPanel.add(Box.createHorizontalStrut(15));
		optionsPanel.add(new JLabel("Value 2: "));
		optionsPanel.add(value2);
		optionsPanel.add(Box.createHorizontalStrut(15));
		optionsPanel.add(new JLabel("enchancement: "));
		optionsPanel.add(enhancement);

		String msg = "Choose value 1, value 2 and enchancement:";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			int height = image.getHeight();
			int width = image.getWidth();

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int r = (image.getRGB(x, y)) & 0xFF;
					int g = (image.getRGB(x, y) >> 8) & 0xFF;
					int b = (image.getRGB(x, y) >> 16) & 0xFF;

					if (r < Integer.valueOf(value1.getText())) {
						r = r - Integer.valueOf(enhancement.getText()) < 0 ? 0 : r - Integer.valueOf(enhancement.getText());
					} else if (r > Integer.valueOf(value2.getText())) {
						r = r + Integer.valueOf(enhancement.getText()) > 255 ? 255 : r + Integer.valueOf(enhancement.getText());
					}
					if (g < Integer.valueOf(value1.getText())) {
						g = g - Integer.valueOf(enhancement.getText()) < 0 ? 0 : g - Integer.valueOf(enhancement.getText());
					} else if (g > Integer.valueOf(value2.getText())) {
						g = g + Integer.valueOf(enhancement.getText()) > 255 ? 255 : g + Integer.valueOf(enhancement.getText());
					}
					if (b < Integer.valueOf(value1.getText())) {
						b = b - Integer.valueOf(enhancement.getText()) < 0 ? 0 : b - Integer.valueOf(enhancement.getText());
					} else if (b > Integer.valueOf(value2.getText())) {
						b = b + Integer.valueOf(enhancement.getText()) > 255 ? 255 : b + Integer.valueOf(enhancement.getText());
					}
					Color color = new Color(r, g, b);
					image.setRGB(x, y, color.getRGB());
				}
			}
			System.out.println(value1.getText());
			System.out.println(value2.getText());
			System.out.println(enhancement.getText());
			((MainWindow) parent).updateLeftQuickDrawPanel(image);
		}
	}

}
