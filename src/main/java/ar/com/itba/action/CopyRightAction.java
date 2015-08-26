package ar.com.itba.action;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import ar.com.itba.frame.MainWindow;

@SuppressWarnings("serial")
public class CopyRightAction extends AbstractAction {

	private JFrame parent;

	public CopyRightAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		peformCopyRightAction((MainWindow) parent);
	}

	public static void peformCopyRightAction(MainWindow window) {
		BufferedImage leftImage = window.getLeftQuickDrawPanel().image();
		window.updateRightQuickDrawPanel(leftImage);
	}
}
