package ar.com.itba.image_actions.enhance;

import ar.com.itba.image_actions.noise.AbstractImageNoise;

@SuppressWarnings("serial")
public class ModifyGamma extends AbstractImageNoise {

	double gammaValue;

	public ModifyGamma(String name, String gammaValue) {
		super("Modify Gamma");
		this.gammaValue = Double.valueOf(gammaValue);
	}

	@Override
	public int modify(double value) {
		return (int) capped(255 * Math.pow((value / 255), (1 / gammaValue)));
	}

	// delete this
	@Override
	public int apply(int pixel) {
		return 0;
	}

}
