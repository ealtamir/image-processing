package ar.com.itba.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class QuickDrawPanel extends JPanel {

	private BufferedImage bufferedImage;
	private Dimension size = new Dimension();

	public QuickDrawPanel() {
	}

	public QuickDrawPanel(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		setComponentSize();
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.drawImage(bufferedImage, 0, 0, this);
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}

	public void image(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		setComponentSize();
		repaint();
	}

	public BufferedImage image() {
		return bufferedImage;
	}

	private void setComponentSize() {
		if (bufferedImage != null) {
			size.width = bufferedImage.getWidth();
			size.height = bufferedImage.getHeight();
			revalidate(); // signal parent/scrollpane
		}
	}
}
