package ar.com.itba.utils.random;

@SuppressWarnings("serial")
public class ExponentialRandomGenerator implements RandomGenerator {
	private float mean;

	public ExponentialRandomGenerator(float mean) {
		this.mean = mean;
	}

	@Override
	public float get() {
		float x = random(0.01f, 1);
		float random = ((float) -Math.log(x)) / mean;
		return random;
	}

	private float random(float min, float max) {
		float x = (float) Math.random();
		return x * (max - min) + min;
	}
}
