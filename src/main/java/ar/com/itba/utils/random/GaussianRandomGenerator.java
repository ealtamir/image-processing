package ar.com.itba.utils.random;

import java.util.Random;

import com.google.common.base.Preconditions;

@SuppressWarnings("serial")
public class GaussianRandomGenerator implements RandomGenerator {

	private Random random = new Random();
	private float median, std;

	public GaussianRandomGenerator(float median, float std) {
		Preconditions.checkArgument(std > 0);
		this.median = median;
		this.std = std;
	}

	@Override
	public float get() {
		float normalGuassian = (float) random.nextGaussian();
		return normalGuassian * std + median;
	}
}