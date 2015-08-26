package ar.com.itba.utils.random;

import com.google.common.base.Preconditions;

@SuppressWarnings("serial")
public class UniformRandomGenerator implements RandomGenerator {

	private float min, max;

	public UniformRandomGenerator(float min, float max) {
		Preconditions.checkArgument(min <= max);
		this.min = min;
		this.max = max;
	}

	@Override
	public float get() {
		float x = (float) Math.random();
		return x * (max - min) + min;
	}
}
