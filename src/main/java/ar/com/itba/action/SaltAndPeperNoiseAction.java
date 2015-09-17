package ar.com.itba.action;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.image_actions.noise.SaltAndPepperNoise;
import ar.com.itba.utils.CustomBufferedImage;
import ar.com.itba.utils.random.UniformRandomGenerator;

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
		JTextField intensity = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("Intensity: "));
		optionsPanel.add(intensity);

		String msg = "Select filter itensity:";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			BufferedImage img = new SaltAndPepperNoise(Float.valueOf(intensity.getText()), 0.3f, 0.7f).setRandomGenerator(new UniformRandomGenerator(0, 1)).apply(image);
			((MainWindow) parent).updateLeftQuickDrawPanel(img);
		}
	}
}
