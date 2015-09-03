package ar.com.itba.image_actions.enhance;

import ar.com.itba.image_actions.noise.AbstractImageNoise;

@SuppressWarnings("serial")
public class EnhanceContrast extends AbstractImageNoise {

	int color1, color2, enhancement;

	public EnhanceContrast(String name, String color1, String color2, String enhancement) {
		super("Enhance Contrast");
		this.color1 = Integer.valueOf(color1);
		this.color2 = Integer.valueOf(color2);
		this.enhancement = Integer.valueOf(enhancement);
	}

	@Override
	public int modify(int value) {
		if (value <= color1) {
			return capped(value - enhancement);
		} else if (value >= color2) {
			return capped(value + enhancement);
		}
		return value;
	}

	// delete this
	@Override
	public int apply(int pixel) {
		return 0;
	}

}
