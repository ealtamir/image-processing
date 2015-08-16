package ar.com.itba.panel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ar.com.itba.frame.ImageOptionsWindow;

@SuppressWarnings("serial")
public class QuickDrawPanel extends JPanel implements MouseMotionListener, MouseListener, ImageObserver {

	private BufferedImage bufferedImage;
	private Dimension size = new Dimension();
	private ImageOptionsWindow optionsWindow = null;

	public QuickDrawPanel() {
		addMouseMotionListener(this);
		addMouseListener(this);
	}

	public QuickDrawPanel(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		setComponentSize();
		createImageOptionsWindow();
	}

	private void createImageOptionsWindow() {
		closePreviousOptionsWindow();
		QuickDrawPanel quickDrawPanel = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				optionsWindow = new ImageOptionsWindow(quickDrawPanel);
			}
		});
	}

	private void closePreviousOptionsWindow() {
		if (optionsWindow != null) {
			optionsWindow.setVisible(false);
			optionsWindow.dispose();
		}
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		graphics.drawImage(bufferedImage, 0, 0, null);
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}

	public void image(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		setComponentSize();
		repaint();
		createImageOptionsWindow();
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

	@Override
	public void mouseClicked(MouseEvent e) {
		optionsWindow.updateRGBSliders(e, bufferedImage);
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mouseDragged(MouseEvent e) {

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (optionsWindow != null) {
			sendPointValueToOptionsWindow(e.getPoint());
		}
	}

	private void sendPointValueToOptionsWindow(Point p) {
		if (p.getX() <= size.width && p.getY() <= size.height) {
			optionsWindow.setPointerLabelValues(p);
		} else {
			optionsWindow.setPointerLabelValues(null);
		}

	}

	public void changePixelColor(Point pixel, int r, int g, int b) {
		int rgb = createColorInteger(r, g, b);
		int x = (int) pixel.getX();
		int y = (int) pixel.getY();
		bufferedImage.setRGB(x, y, rgb);
		repaint(x - 1, y - 1, 3, 3);
	}

	private int createColorInteger(int r, int g, int b) {
		int rgb = 0;
		r = r << 16;
		g = g << 8;
		rgb = r | g | b;
		return rgb;
	}
}
