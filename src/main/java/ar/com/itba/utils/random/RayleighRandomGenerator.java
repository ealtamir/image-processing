package ar.com.itba.utils.random;

@SuppressWarnings("serial")
public class RayleighRandomGenerator implements RandomGenerator {
	private float eta;

	public RayleighRandomGenerator(float eta) {
		this.eta = eta;
	}

	@Override
	public float get() {
		float x = (float) Math.random();
		float random = (float) Math.sqrt(-2 * Math.log(1 - x));
		return eta * random;
	}
}
