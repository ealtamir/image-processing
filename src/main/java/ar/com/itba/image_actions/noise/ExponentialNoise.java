package ar.com.itba.image_actions.noise;

import ar.com.itba.utils.random.UniformRandomGenerator;

@SuppressWarnings("serial")
public class ExponentialNoise extends AbstractImageNoise {

	private float intensity;

	public ExponentialNoise(float intensity) {
		super("Exponential Noise");
		this.intensity = intensity;
	}

	@Override
	public int modify(double value, float randomValue) {
		if (new UniformRandomGenerator(0, 1).get() <= intensity) {
			return (int) (value * randomValue);
		}
		return (int) value;
	}

	// Remove this
	@Override
	public int apply(int pixel) {
		return 0;
	}

}
