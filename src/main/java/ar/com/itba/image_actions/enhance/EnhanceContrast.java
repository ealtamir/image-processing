package ar.com.itba.image_actions.enhance;

import ar.com.itba.image_actions.noise.PerPixelOperation;

@SuppressWarnings("serial")
public class EnhanceContrast extends PerPixelOperation {

	int color1, color2, enhancement;

	public EnhanceContrast(String name, String color1, String color2, String enhancement) {
		super("Enhance Contrast");
		this.color1 = Integer.valueOf(color1);
		this.color2 = Integer.valueOf(color2);
		this.enhancement = Integer.valueOf(enhancement);
	}

	@Override
	public int modify(double value, float randomValue, float changePixel) {
		if (value <= color1) {
			return (int) value - enhancement;
		} else if (value >= color2) {
			return (int) value + enhancement;
		}
		return (int) value;
	}

	// delete this
	@Override
	public int apply(int pixel) {
		return 0;
	}

}
