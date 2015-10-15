package ar.com.itba.image_actions.canny.hysteresis;

import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.noise.PerPixelOperation;
import ar.com.itba.utils.CustomBufferedImage;

@SuppressWarnings("serial")
public class Hysteresis extends PerPixelOperation {

	int maxVal, minVal;
	CustomBufferedImage image;

	public Hysteresis(BufferedImage image, int threshold1, int threshold2) {
		super("Histeresis");
		this.maxVal = threshold1;
		this.minVal = threshold2;
		this.image = new CustomBufferedImage(image);
	}

	@Override
	public int apply(int pixel) {
		// donothing
		return 0;
	}

	@Override
	public int modify(double value, float randomValue, float changePixel) {
		// donothing
		return 0;
	}

	@Override
	public BufferedImage apply(BufferedImage input) {
		int height = image.getHeight();
		int width = image.getWidth();
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				double currentColor = image.getGray(x, y);
				if (currentColor >= maxVal) {
					image.setGray(x, y, 255);
				} else if (currentColor <= minVal) {
					image.setGray(x, y, 0);
				}
			}
		}
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				double currentColor = image.getGray(x, y);
				if (currentColor < maxVal && currentColor > minVal) {
					int up = image.getGray(x, y - 1);
					int down = image.getGray(x, y + 1);
					int left = image.getGray(x - 1, y);
					int right = image.getGray(x + 1, y);
					if (up == 255 || down == 255 || left == 255 || right == 255) {
						image.setGray(x, y, 255);
					} else {
						image.setGray(x, y, 0);
					}
				}
			}
		}
		return image;
	}

}
