package ar.com.itba.image_actions.canny.histeresis;

import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.noise.PerPixelOperation;

@SuppressWarnings("serial")
public class Histeresis extends PerPixelOperation {

	int threshold1, threshold2;
	BufferedImage image;

	public Histeresis(BufferedImage image, int threshold1, int threshold2) {
		super("Histeresis");
		this.threshold1 = threshold1;
		this.threshold2 = threshold2;
		this.image = image;
	}

	@Override
	public int apply(int pixel) {
		// donothing
		return 0;
	}

	@Override
	public int modify(double value, float randomValue, float changePixel) {
		// donothing
		return 0;
	}

	@Override
	public BufferedImage apply(BufferedImage input) {
		// TODO Auto-generated method stub
		return super.apply(input);
	}

}
