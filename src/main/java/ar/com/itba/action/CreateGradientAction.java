package ar.com.itba.action;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.utils.CustomBufferedImage;

@SuppressWarnings("serial")
public class CreateGradientAction extends AbstractAction {

	private JFrame parent;
	private Color color1;
	private Color color2;

	public CreateGradientAction(JFrame parent, Color color1, Color color2) {
		this.parent = parent;
		this.color1 = color1;
		this.color2 = color2;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		BufferedImage image = new CustomBufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
		GradientPaint gradient = new GradientPaint(0, 0, color1, 0, 512, color2, false);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setPaint(gradient);
		g2.fillRect(0, 0, 512, 512);
		((MainWindow) parent).updateLeftQuickDrawPanel(image);
	}

}
