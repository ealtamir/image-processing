package ar.com.itba.action;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.image_actions.masks.Susan;

@SuppressWarnings("serial")
public class SusanAction extends AbstractAction {

	private JFrame parent;
	private BufferedImage image;

	public SusanAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		image = ((MainWindow) parent).getLeftQuickDrawPanel().image();

		image = new Susan().applyMask(image);
		((MainWindow) parent).updateLeftQuickDrawPanel(image);
	}

}
