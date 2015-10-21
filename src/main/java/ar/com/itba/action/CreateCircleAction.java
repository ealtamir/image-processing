package ar.com.itba.action;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.utils.CustomBufferedImage;

@SuppressWarnings("serial")
public class CreateCircleAction extends AbstractAction {

	private JFrame parent;

	public CreateCircleAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		CustomBufferedImage image = new CustomBufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillOval(32, 32, 64, 64);
		image.loadColorBuffers();
		((MainWindow) parent).updateLeftQuickDrawPanel(image);
	}
}
