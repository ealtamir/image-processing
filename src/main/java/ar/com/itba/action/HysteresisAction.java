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
import ar.com.itba.image_actions.canny.hysteresis.Hysteresis;

@SuppressWarnings("serial")
public class HysteresisAction extends AbstractAction {

	private JFrame parent;
	private BufferedImage image;

	public HysteresisAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		image = ((MainWindow) parent).getLeftQuickDrawPanel().image();
		JTextField maxValue = new JTextField(3);
		JTextField minValue = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("maxValue: "));
		optionsPanel.add(maxValue);
		optionsPanel.add(new JLabel("minValue: "));
		optionsPanel.add(minValue);

		String msg = "Elija min y max: ";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			image = new Hysteresis(image, Integer.valueOf(maxValue.getText()), Integer.valueOf(minValue.getText())).apply(image);
			((MainWindow) parent).updateLeftQuickDrawPanel(image);
		}
	}

}
