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
import ar.com.itba.image_actions.correct.LinearImageCorrector;
import ar.com.itba.image_actions.noise.ExponentialNoise;
import ar.com.itba.utils.random.ExponentialRandomGenerator;

@SuppressWarnings("serial")
public class ExponentialNoiseAction extends AbstractAction {

	private JFrame parent;
	private BufferedImage image;

	public ExponentialNoiseAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		image = ((MainWindow) parent).getLeftQuickDrawPanel().image();
		JTextField intensity = new JTextField(3);
		JTextField delta = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("Intensity: "));
		optionsPanel.add(intensity);
		optionsPanel.add(new JLabel("Mean: "));
		optionsPanel.add(delta);

		String msg = "Choose intensity:";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			BufferedImage img = new ExponentialNoise(Float.valueOf(intensity.getText())).setRandomGenerator(new ExponentialRandomGenerator(Float.valueOf(delta.getText()))).apply(image);
			((MainWindow) parent).updateLeftQuickDrawPanel(img);
		}
	}
}
