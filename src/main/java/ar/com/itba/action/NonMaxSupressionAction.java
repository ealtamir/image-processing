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
import ar.com.itba.image_actions.canny.nomax.NonMaxSupression;

@SuppressWarnings("serial")
public class NonMaxSupressionAction extends AbstractAction {

	private JFrame parent;
	private BufferedImage image;

	public NonMaxSupressionAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		image = ((MainWindow) parent).getLeftQuickDrawPanel().image();
		JTextField gaussianvalue = new JTextField(3);

		JPanel optionsPanel = new JPanel();
		optionsPanel.add(new JLabel("Gaussian: "));
		optionsPanel.add(gaussianvalue);

		String msg = "Intensidad de filtro gausiano: ";
		int result = JOptionPane.showConfirmDialog(null, optionsPanel, msg, JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			image = new NonMaxSupression(image, Double.valueOf(gaussianvalue.getText())).apply(image);
			((MainWindow) parent).updateLeftQuickDrawPanel(image);
		}
	}

}
