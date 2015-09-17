package ar.com.itba.image_actions.noise;

@SuppressWarnings("serial")
public class GaussianNoise extends PerPixelOperation {

	private float intensity;

	public GaussianNoise(float intensity) {
		super("Gaussian Noise");
		this.intensity = intensity;
	}

	@Override
	public int modify(double value, float randomValue, float changePixel) {
		if (changePixel <= intensity) {
			return (int) (value + randomValue);
		}
		return (int) value;
	}

	// Remove this
	@Override
	public int apply(int pixel) {
		return 0;
	}
}
