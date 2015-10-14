package ar.com.itba.image_actions.canny.nomax;

import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.edge_detection.SobelEdgeDetection;
import ar.com.itba.image_actions.masks.Masks;
import ar.com.itba.image_actions.noise.PerPixelOperation;

@SuppressWarnings("serial")
public class NonMaxSupression extends PerPixelOperation {

	BufferedImage suppresedImage;
	double[][] angles;

	public NonMaxSupression(BufferedImage image, double gaussianValue) {
		super("Non-Max Supression");
		BufferedImage gaussian = Masks.applyGaussianMask(image, gaussianValue);
		BufferedImage gx = SobelEdgeDetection.horizontalEdgeDetection(gaussian);
		BufferedImage gy = SobelEdgeDetection.verticalEdgeDetection(gaussian);
		suppresedImage = SobelEdgeDetection.applyModule(image);
		int height = image.getHeight();
		int width = image.getWidth();
		angles = new double[height][width];
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				if (gx.getRGB(x, y) == 0) {
					angles[x][y] = 0;
				} else {
					angles[x][y] = angleTransform(Math.atan2(gy.getRGB(x, y), gx.getRGB(x, y)));
				}
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
				double currentColor = suppresedImage.getRGB(x, y);
				if (suppresedImage.getRGB(x, y) != 0) {
					double angle = angles[x][y];
					double left;
					double right;
					if (angle == 0) {
						left = suppresedImage.getRGB(x - 1, y);
						right = suppresedImage.getRGB(x + 1, y);
					} else if (angle == 45) {
						left = suppresedImage.getRGB(x - 1, y - 1);
						right = suppresedImage.getRGB(x + 1, y + 1);
					} else if (angle == 90) {
						left = suppresedImage.getRGB(x, y - 1);
						right = suppresedImage.getRGB(x, y + 1);
					} else { // if (angle == 135) {
						left = suppresedImage.getRGB(x + 1, y - 1);
						right = suppresedImage.getRGB(x - 1, y + 1);
					}
					if (left > currentColor || right > currentColor) {
						currentColor = 0;
					}
				}
			}
		}

		return suppresedImage;
	}

	private double angleTransform(double angle) {
		if (angle <= 22.5 && angle > 157.5) {
			return 0;
		} else if (angle > 22.5 && angle <= 67.5) {
			return 45;
		} else if (angle > 67.5 && angle <= 112.5) {
			return 90;
		} else if (angle > 112.5 && angle < 157.5) {
			return 135;
		}
		return 0;
	}
}
