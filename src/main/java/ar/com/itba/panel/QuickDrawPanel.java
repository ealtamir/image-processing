package ar.com.itba.panel;

import ar.com.itba.frame.ImageOptionsWindow;
import ar.com.itba.frame.MainWindow;
import sun.tools.jstat.Alignment;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class QuickDrawPanel extends JPanel implements MouseMotionListener, MouseListener {

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
		QuickDrawPanel quickDrawPanel = this;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				optionsWindow = new ImageOptionsWindow(quickDrawPanel);
			}
		});
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

	}
}
