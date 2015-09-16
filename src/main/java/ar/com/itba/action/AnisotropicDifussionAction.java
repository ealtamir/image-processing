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
		JTextField delta = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("Delta: "));
		optionsPanel.add(delta);

		String msg = "Choose delta:";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			// new LinearImageCorrector().apply(image);
			((MainWindow) parent).updateLeftQuickDrawPanel(new AnisotropicDifussion(Float.valueOf(delta.getText()), new One()).apply(image));
		}
	}

	public class One implements Function<Float, Float> {

		@Override
		public Float apply(Float input) {
			return 1f;
		}
	}
}
