package ar.com.itba.image_actions.noise;

import java.awt.Color;

import ar.com.itba.utils.random.UniformRandomGenerator;

@SuppressWarnings("serial")
public class SaltAndPepperNoise extends PerPixelOperation {
	private float intensity, firstValue, secondValue;

	public SaltAndPepperNoise(float intensity, float firstValue, float secondValue) {
		super("Sal y pimienta");
		this.intensity = intensity;
		this.firstValue = firstValue;
		this.secondValue = secondValue;
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
	public int modify(double value, float randomValue, float changePixel) {
		if (changePixel <= intensity) {
			if (randomValue <= firstValue) {
				return 0;
			} else if (randomValue >= secondValue) {
				return 255;
			}
		}
		return (int) value;
	}

	// Remove this
	@Override
	public int apply(int pixel) {
		return 0;
	}

}
