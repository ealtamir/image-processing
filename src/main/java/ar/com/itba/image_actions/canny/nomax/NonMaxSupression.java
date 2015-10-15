package ar.com.itba.image_actions.canny.nomax;

import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.edge_detection.SobelEdgeDetection;
import ar.com.itba.image_actions.masks.Masks;
import ar.com.itba.image_actions.noise.PerPixelOperation;
import ar.com.itba.utils.CustomBufferedImage;

@SuppressWarnings("serial")
public class NonMaxSupression extends PerPixelOperation {

	CustomBufferedImage suppresedImage;
	int[][] angles;

	public NonMaxSupression(BufferedImage image, double gaussianValue) {
		super("Non-Max Supression");
		CustomBufferedImage gaussian = new CustomBufferedImage(Masks.applyGaussianMask(image, gaussianValue));
		CustomBufferedImage gx = new CustomBufferedImage(SobelEdgeDetection.horizontalEdgeDetection(gaussian));
		CustomBufferedImage gy = new CustomBufferedImage(SobelEdgeDetection.verticalEdgeDetection(gaussian));
		suppresedImage = new CustomBufferedImage(SobelEdgeDetection.applyModule(image));
		int height = image.getHeight();
		int width = image.getWidth();
		angles = new int[width][height];
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				double angle = Math.atan2(gy.getGray(x, y), gx.getGray(x, y));
				angles[x][y] = angleTransform(Math.toDegrees(angle));
			}
		}
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
	public BufferedImage apply(BufferedImage image) {
		int height = suppresedImage.getHeight();
		int width = suppresedImage.getWidth();
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				double currentColor = suppresedImage.getGray(x, y);
				if (currentColor != 0) {
					double angle = angles[x][y];
					double left;
					double right;
					if (angle == 0) {
						left = suppresedImage.getGray(x - 1, y);
						right = suppresedImage.getGray(x + 1, y);
					} else if (angle == 45) {
						left = suppresedImage.getGray(x - 1, y - 1);
						right = suppresedImage.getGray(x + 1, y + 1);
					} else if (angle == 90) {
						left = suppresedImage.getGray(x, y - 1);
						right = suppresedImage.getGray(x, y + 1);
					} else { // if (angle == 135) {
						left = suppresedImage.getGray(x + 1, y - 1);
						right = suppresedImage.getGray(x - 1, y + 1);
					}
					if (left > currentColor || right > currentColor) {
						suppresedImage.setGray(x, y, 0);
					}
				}
			}
		}
		return suppresedImage;
	}

	private int angleTransform(double angle) {
		if (angle <= 22.5d && angle > 157.5d) {
			return 0;
		} else if (angle > 22.5d && angle <= 67.5d) {
			return 45;
		} else if (angle > 67.5d && angle <= 112.5d) {
			return 90;
		} else if (angle > 112.5d && angle < 157.5d) {
			return 135;
		}
		return 0;
	}
}
