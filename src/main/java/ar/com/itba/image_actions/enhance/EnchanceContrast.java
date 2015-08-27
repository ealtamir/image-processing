package ar.com.itba.image_actions.enhance;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.noise.AbstractImageNoise;

@SuppressWarnings("serial")
public class EnchanceContrast extends AbstractImageNoise {

	int color1, color2, enhancement;

	public EnchanceContrast(String name, String color1, String color2, String enhancement) {
		super("Enhance Contrast");
		this.color1 = Integer.valueOf(color1);
		this.color2 = Integer.valueOf(color2);
		this.enhancement = Integer.valueOf(enhancement);
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
				Color color = new Color(modify(r), modify(g), modify(b));
				input.setRGB(x, y, color.getRGB());
			}
		}
		return input;
	}

	@Override
	public int modify(int value) {
		if (value < color1) {
			return (value - enhancement < 0) ? 0 : value - enhancement;
		} else if (value > color2) {
			return (value + enhancement > 255) ? 255 : value + enhancement;
		}
		return value;
	}

	@Override
	public int modifyWithSameProbabilities(int pixelValue, float intensityRandom, float uniformRandom) {
		return 0;
	}

	// delete this
	@Override
	public int apply(int pixel) {
		return 0;
	}

}
