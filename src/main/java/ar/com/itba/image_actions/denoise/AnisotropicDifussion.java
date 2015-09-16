package ar.com.itba.image_actions.denoise;

import java.awt.Color;
import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.noise.PerPixelOperation;

@SuppressWarnings("serial")
public class AnisotropicDifussion extends PerPixelOperation {

	float value;
	BufferedImage originalImage;

	public AnisotropicDifussion(float value) {
		super("Anisotropic Difussion");
		this.value = value;
	}

	@Override
	public BufferedImage apply(BufferedImage input) {
		originalImage = input;
		BufferedImage newImage = new BufferedImage(input.getHeight(), input.getWidth(), input.getType());
//		int height = input.getHeight();
//		int width = input.getWidth();
//		for (int i = 0; i < value; i++) {
//			for (int x = 1; x < width - 1; x++) {
//				for (int y = 1; y < height - 1; y++) {
//					int r = (input.getRGB(x, y)) & 0xFF;
//					int g = (input.getRGB(x, y) >> 8) & 0xFF;
//					int b = (input.getRGB(x, y) >> 16) & 0xFF;
//					int rup = (input.getRGB(x, y - 1)) & 0xFF;
//					int rdown = (input.getRGB(x, y + 1)) & 0xFF;
//					int rleft = (input.getRGB(x - 1, y)) & 0xFF;
//					int rright = (input.getRGB(x + 1, y)) & 0xFF;
//					int gup = (input.getRGB(x, y - 1) >> 8) & 0xFF;
//					int gdown = (input.getRGB(x, y + 1) >> 8) & 0xFF;
//					int gleft = (input.getRGB(x - 1, y) >> 8) & 0xFF;
//					int gright = (input.getRGB(x + 1, y) >> 8) & 0xFF;
//					int bup = (input.getRGB(x, y - 1) >> 16) & 0xFF;
//					int bdown = (input.getRGB(x, y + 1) >> 16) & 0xFF;
//					int bleft = (input.getRGB(x - 1, y) >> 16) & 0xFF;
//					int bright = (input.getRGB(x + 1, y) >> 16) & 0xFF;
//					Color color = new Color(modify(r, rup, rdown, rleft, rright), modify(g, gup, gdown, gleft, gright), modify(b, bup, bdown, bleft, bright));
//					input.setRGB(x, y, color.getRGB());
//				}
//			}
//		}
		return input;
	}

	public int modify(double value, float up, float down, float left, float right) {
		return (int) Math.round((value + (up - value + down - value + left - value + right - value) / 4));
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
