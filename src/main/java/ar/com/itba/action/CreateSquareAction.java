package ar.com.itba.action;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import ar.com.itba.frame.MainWindow;

@SuppressWarnings("serial")
public class CreateSquareAction extends AbstractAction {

	private JFrame parent;

	public CreateSquareAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BufferedImage image = new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(128, 128, 256, 256);
		((MainWindow) parent).quickDrawPanel().image(image);

	}
}
