package ar.com.itba.image_actions.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ar.com.itba.utils.random.RandomGenerator;

@SuppressWarnings("serial")
public abstract class AbstractImageNoise implements NoiseGenerator {

	private RandomGenerator generator;
	private String name;

	public AbstractImageNoise(String name) {
		this.name = name;
	}

	@Override
	public NoiseGenerator setRandomGenerator(RandomGenerator generator) {
		this.generator = generator;
		return this;
	}

	public RandomGenerator generator() {
		return generator;
	}

	public String name() {
		return name;
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

	public double capped(double value) {
		if (value <= 0) {
			return 0;
		} else if (value >= 255) {
			return 255;
		}
		return value;
	}

	public abstract int modify(double value);

}
