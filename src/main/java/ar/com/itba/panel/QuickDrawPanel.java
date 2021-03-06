package ar.com.itba.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ar.com.itba.frame.ImageOptionsWindow;
import ar.com.itba.image_actions.ParameterizedImageAction;
import ar.com.itba.utils.ImageHistogram;
import ar.com.itba.utils.MouseTracker;

@SuppressWarnings("serial")
public class QuickDrawPanel extends JPanel {

	private BufferedImage bufferedImage;
	private Dimension size = new Dimension();
	private ImageOptionsWindow optionsWindow = null;
	private MouseTracker mouseTracker;
	private ImageHistogram histogram;
	private ParameterizedImageAction actionWindow = null;
	private Rectangle drawnRectangle = null;
	private File contourDetectionFolder = null;

	private void createImageHistogram(BufferedImage bufferedImage) {
		if (histogram != null) {
			histogram.close();
		}
		histogram = new ImageHistogram("test", bufferedImage);
	}

	private void createImageOptionsWindow() {
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

	public void paintImageImmediately(BufferedImage img) {
		this.bufferedImage = bufferedImage;
		repaint(200);

	}

	private void drawMouseSelectionRect(Graphics graphics) {
		Rectangle r = mouseTracker.getRectToDraw();
		drawnRectangle = r;
		Color oldColor = graphics.getColor();
		graphics.setColor(Color.MAGENTA);
		graphics.drawRect(r.x, r.y, r.width, r.height);
		graphics.setColor(oldColor);
	}

	public Rectangle getDrawnRectangle() {
		return drawnRectangle;
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}

	public void openNewImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		contourDetectionFolder = null;
		setComponentSize();
		createImageOptionsWindow();
		closeOpenActionWindows();
		repaint();
	}

	private void updateEqualizationObject() {
		boolean visibility;
		if (histogram == null) {
			return;
		} else {
			visibility = histogram.isVisible();
		}
		createImageHistogram(image());
		histogram.setVisibility(visibility);
	}

	public void modifyCurrentImage(BufferedImage bufferedImage) {
		this.bufferedImage = bufferedImage;
		updateEqualizationObject();
		repaint();
	}

	private void closeOpenActionWindows() {
		if (histogram != null) {
			histogram.close();
			histogram = null;
		}
		if (actionWindow != null) {
			actionWindow.close();
			actionWindow = null;
		}
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

	public void toggleImageHistogram() {
		if (histogram == null) {
			createImageHistogram(bufferedImage);
		}
		if (histogram.isVisible()) {
			histogram.hideHistogram();
		} else {
			histogram.showHistogram();
		}
	}

	public void toggleImageTools() {
		optionsWindow.toggleVisibility();
	}

	public void setParameterizedActionWindow(ParameterizedImageAction scalarMultOperation) {
		if (actionWindow == null) {
			actionWindow = scalarMultOperation;
		} else {
			actionWindow.close();
			actionWindow = scalarMultOperation;
		}
	}

	public void setContourDetectionFolder(File selectedFolder) {
		contourDetectionFolder = selectedFolder;
	}

	public File getContourDetectionFolder() {
		return contourDetectionFolder;
	}
}
