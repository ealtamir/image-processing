package ar.com.itba.panel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import ar.com.itba.frame.ImageOptionsWindow;
import ar.com.itba.utils.MouseTracker;

@SuppressWarnings("serial")
public class QuickDrawPanel extends JPanel {

	private BufferedImage bufferedImage;
	private Dimension size = new Dimension();
	private ImageOptionsWindow optionsWindow = null;
	private MouseTracker mouseTracker;
	private Rectangle currentSelection;
	private Object threadSynchroniztionObj = new Object();

	public QuickDrawPanel() {}

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
                optionsWindow = new ImageOptionsWindow(quickDrawPanel, bufferedImage);
                mouseTracker = new MouseTracker(quickDrawPanel, optionsWindow,
						bufferedImage.getWidth(), bufferedImage.getHeight());
                addMouseMotionListener(mouseTracker);
                addMouseListener(mouseTracker);
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
		if (mouseTracker != null && mouseTracker.hasDataToDraw()) {
			drawMouseSelectionRect(graphics);
		}
	}

	private void drawMouseSelectionRect(Graphics graphics) {
		Rectangle r = mouseTracker.getRectToDraw();
		Color oldColor = graphics.getColor();
		graphics.setColor(Color.MAGENTA);
		graphics.drawRect(r.x, r.y, r.width, r.height);
		graphics.setColor(oldColor);
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}

	public void image(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		setComponentSize();
		createImageOptionsWindow();
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
