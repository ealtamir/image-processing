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

	public AnisotropicDifussionAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		image = ((MainWindow) parent).getLeftQuickDrawPanel().image();
		JTextField iterations = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("Iterations: "));
		optionsPanel.add(iterations);

		String msg = "Choose iterations:";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			for (int i = 0; i < Integer.valueOf(iterations.getText()); i++) {
				image = new AnisotropicDifussion(new Leclerc()).apply(image);
			}
			((MainWindow) parent).updateLeftQuickDrawPanel(image);
		}
	}

	public class Leclerc implements Function<Double, Double> {
		@Override
		public Double apply(Double input) {
			double dividend = Math.pow(-input, 2);
			double divisor = Math.pow(100, 2);
			return Math.exp(dividend / divisor);
		}
	}

}
