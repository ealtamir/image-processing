package ar.com.itba.action;

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
import ar.com.itba.image_actions.correct.LinearImageCorrector;
import ar.com.itba.image_actions.enhance.EnhanceContrast;

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
			BufferedImage img = new EnhanceContrast("Enchance Contrast", value1.getText(), value2.getText(), enhancement.getText()).apply(image);
			((MainWindow) parent).updateLeftQuickDrawPanel(img);
		}
	}

}
