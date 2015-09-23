package ar.com.itba.action;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.google.common.base.Function;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.image_actions.denoise.AnisotropicDifussion;

@SuppressWarnings("serial")
public class AnisotropicDifussionAction extends AbstractAction {

	private JFrame parent;
	private BufferedImage image;
	private double sigma;

	public AnisotropicDifussionAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		image = ((MainWindow) parent).getLeftQuickDrawPanel().image();
		JTextField iterationsTF = new JTextField(3);
		JTextField sigmaTF = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("Iterations: "));
		optionsPanel.add(iterationsTF);
		optionsPanel.add(new JLabel("Sigma: "));
		optionsPanel.add(sigmaTF);

		String msg = "Choose iterations: ";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			sigma = Double.valueOf(sigmaTF.getText());
			for (int i = 0; i < Integer.valueOf(iterationsTF.getText()); i++) {
				image = new AnisotropicDifussion(new Lorentz()).apply(image);
			}
			((MainWindow) parent).updateLeftQuickDrawPanel(image);
		}
	}

	public class Leclerc implements Function<Double, Double> {
		@Override
		public Double apply(Double input) {
			return Math.exp((-input * input) / (sigma * sigma));
		}
	}

	public class Lorentz implements Function<Double, Double> {
		@Override
		public Double apply(Double input) {
			return 1 / ((input * input / sigma) + 1);
		}
	}

}
