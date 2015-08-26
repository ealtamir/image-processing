package ar.com.itba.action;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.com.itba.frame.MainWindow;

@SuppressWarnings("serial")
public class SaltAndPeperNoiseAction extends AbstractAction {

	private JFrame parent;
	private BufferedImage image;

	public SaltAndPeperNoiseAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		image = ((MainWindow) parent).getLeftQuickDrawPanel().image();
		JTextField density = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("Intensity: "));
		optionsPanel.add(density);

		String msg = "Select filter itensity:";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			int height = image.getHeight();
			int width = image.getWidth();
			Random randomGenerator = new Random();
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					float randomNumber = randomGenerator.nextFloat();
					if (randomNumber < Float.valueOf(density.getText())) {
						randomNumber = randomGenerator.nextFloat();
						if (randomNumber < 0.3) {
							image.setRGB(x, y, Color.white.getRGB());
						} else if (randomNumber > 0.7) {
							image.setRGB(x, y, Color.black.getRGB());
						}
					}
				}
			}
			((MainWindow) parent).updateLeftQuickDrawPanel(image);
		}

	}
}
