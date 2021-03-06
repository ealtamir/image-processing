package ar.com.itba.image_actions.enhance;

import ar.com.itba.image_actions.noise.PerPixelOperation;

@SuppressWarnings("serial")
public class ModifyGamma extends PerPixelOperation {

	double gammaValue;

	public ModifyGamma(String name, String gammaValue) {
		super("Modify Gamma");
		this.gammaValue = Double.valueOf(gammaValue);
	}

	@Override
	public int modify(double value, float randomValue, float changePixel) {
		return (int) (255 * Math.pow((value / 255), (1 / gammaValue)));
	}

	// delete this
	@Override
	public int apply(int pixel) {
		return 0;
	}

}
