package ar.com.itba.image_actions.noise;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;
import ar.com.itba.utils.random.RandomGenerator;
import ar.com.itba.utils.random.UniformRandomGenerator;

@SuppressWarnings("serial")
public abstract class PerPixelOperation implements NoiseGenerator {

	private RandomGenerator generator;
	private String name;

	protected float intensity;

	public PerPixelOperation(String name) {
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
		CustomBufferedImage image = new CustomBufferedImage(input);
		int height = image.getHeight();
		int width = image.getWidth();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int r = image.getRed(x, y);
				int g = image.getGreen(x, y);
				int b = image.getBlue(x, y);
				float random = generator == null ? 0 : generator.get();
				float changePixel = new UniformRandomGenerator(0, 1).get();
                Color color = new Color(capped(modify(r, random, changePixel)), capped(modify(g, random, changePixel)), capped(modify(b, random, changePixel)));
				image.setRGBCustom(x, y, color.getRGB());
			}
		}
		image.applyLinearTransform();
		return image;
	}

	public int capped(double value) {
		if (value <= 0) {
			return 0;
		} else if (value >= 255) {
			return 255;
		}
		return (int) value;
	}

	public abstract int modify(double value, float randomValue, float changePixel);

}
