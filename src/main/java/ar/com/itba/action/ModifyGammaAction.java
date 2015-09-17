package ar.com.itba.action;

import java.awt.Component;
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
import ar.com.itba.image_actions.enhance.ModifyGamma;

@SuppressWarnings("serial")
public class ModifyGammaAction extends AbstractAction {

	private JFrame parent;
	private BufferedImage image;

	public ModifyGammaAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		image = ((MainWindow) parent).getLeftQuickDrawPanel().image();
		askUserValues(parent);
	}

	private void askUserValues(Component component) {
		JTextField gammaValue = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("Value 1: "));
		optionsPanel.add(gammaValue);

		String msg = "Choose gamma value:";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			BufferedImage img = new ModifyGamma("Modify Gamma", gammaValue.getText()).apply(image);
			((MainWindow) parent).updateLeftQuickDrawPanel(img);
		}
	}

}
