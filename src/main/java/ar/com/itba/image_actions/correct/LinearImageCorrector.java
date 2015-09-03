package ar.com.itba.image_actions.correct;

import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.noise.AbstractImageNoise;

@SuppressWarnings("serial")
public class LinearImageCorrector extends AbstractImageNoise {

	private float _maxR, _maxG, _maxB;
	private float _minR, _minG, _minB;

	public LinearImageCorrector() {
		super("Compresión lineal");
	}

	@Override
	public BufferedImage apply(BufferedImage input) {
		_maxR = -Float.MAX_VALUE;
		_minR = Float.MAX_VALUE;
		_maxG = -Float.MAX_VALUE;
		_minG = Float.MAX_VALUE;
		_maxB = -Float.MAX_VALUE;
		_minB = Float.MAX_VALUE;

		int height = input.getHeight();
		int width = input.getWidth();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int r = (input.getRGB(x, y)) & 0xFF;
				int g = (input.getRGB(x, y) >> 8) & 0xFF;
				int b = (input.getRGB(x, y) >> 16) & 0xFF;
				_maxR = Math.max(_maxR, r);
				_minR = Math.min(_minR, r);
				_maxG = Math.max(_maxG, g);
				_minG = Math.min(_minG, g);
				_maxB = Math.max(_maxB, b);
				_minB = Math.min(_minB, b);
			}
		}
		return super.apply(input);
	}

	@Override
	public int modify(double value, float randomValue) {
		return (int) normalize(value, _minR, _maxR);
	}

	private double normalize(double r, float min, float max) {
		if (max == min) {
			return r;
		}
		return 255 * (r - min) / (max - min);
	}

	// Delete this
	@Override
	public int apply(int pixel) {
		return 0;
	}

}
