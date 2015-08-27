package ar.com.itba.image_actions.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class SaltAndPepperNoise extends AbstractImageNoise {
	private float intensity, firstValue, secondValue;

	public SaltAndPepperNoise(float intensity, float firstValue, float secondValue) {
		super("Sal y pimienta");
		this.intensity = intensity;
		this.firstValue = firstValue;
		this.secondValue = secondValue;
	}

	@Override
	public BufferedImage apply(BufferedImage input) {
		int height = input.getHeight();
		int width = input.getWidth();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int r = (input.getRGB(x, y)) & 0xFF;
				int g = (input.getRGB(x, y) >> 8) & 0xFF;
				int b = (input.getRGB(x, y) >> 16) & 0xFF;
				Color color = new Color(r, g, b);
				input.setRGB(x, y, modifyWithSameProbabilities(color.getRGB(), generator().get(), generator().get()));
			}
		}
		return input;
	}

	// Remove this
	@Override
	public int apply(int pixel) {
		return 0;
	}

	public int modifyWithSameProbabilities(int pixelValue, float intensityRandom, float uniformRandom) {
		if (intensityRandom < intensity) {
			if (uniformRandom <= firstValue) {
				return Color.white.getRGB();
			} else if (uniformRandom >= secondValue) {
				return Color.black.getRGB();
			}
		}
		return pixelValue;
	}

	@Override
	public int modify(int value) {
		return 0;
	}

}
