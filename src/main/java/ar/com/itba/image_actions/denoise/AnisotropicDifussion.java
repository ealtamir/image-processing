package ar.com.itba.image_actions.denoise;

import java.awt.image.BufferedImage;

import com.google.common.base.Function;

import ar.com.itba.image_actions.noise.PerPixelOperation;
import ar.com.itba.utils.CustomBufferedImage;

@SuppressWarnings("serial")
public class AnisotropicDifussion extends PerPixelOperation {

	Function<Double, Double> g;

	public AnisotropicDifussion(Function<Double, Double> g) {
		super("Anisotropic Difussion");
		this.g = g;
	}

	@Override
	public BufferedImage apply(BufferedImage input) {
		CustomBufferedImage image = new CustomBufferedImage(input);
		CustomBufferedImage newImage = new CustomBufferedImage(new BufferedImage(input.getWidth(), input.getHeight(), input.getType()));
		int height = image.getHeight();
		int width = image.getWidth();
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				int r = image.getRed(x, y);
				int g = image.getGreen(x, y);
				int b = image.getBlue(x, y);
				int rup = image.getRed(x, y - 1);
				int rdown = image.getRed(x, y + 1);
				int rleft = image.getRed(x - 1, y);
				int rright = image.getRed(x + 1, y);
				int gup = image.getGreen(x, y - 1);
				int gdown = image.getGreen(x, y + 1);
				int gleft = image.getGreen(x - 1, y);
				int gright = image.getGreen(x + 1, y);
				int bup = image.getBlue(x, y - 1);
				int bdown = image.getBlue(x, y + 1);
				int bleft = image.getBlue(x - 1, y);
				int bright = image.getBlue(x + 1, y);
				newImage.setRGBCustom(x, y, modify(r, rup, rdown, rleft, rright), modify(g, gup, gdown, gleft, gright), modify(b, bup, bdown, bleft, bright));
			}
		}
		newImage.applyLinearTransform();
		return newImage;
	}

	public int modify(int value, double up, double down, double left, double right) {
		return (int) (value
				+ Math.round(((up * g.apply(up - value) + down * g.apply(down - value) + left * g.apply(left - value) + right * g.apply(right - value)) / 4)));
	}

	// Remove this
	@Override
	public int apply(int pixel) {
		return 0;
	}

	// do nothing
	@Override
	public int modify(double value, float randomValue, float changePixel) {
		return 0;
	}

}
