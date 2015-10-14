package ar.com.itba.image_actions.canny.nomax;

import java.awt.image.BufferedImage;

import ar.com.itba.image_actions.edge_detection.SobelEdgeDetection;
import ar.com.itba.image_actions.masks.Masks;
import ar.com.itba.image_actions.noise.PerPixelOperation;
import ar.com.itba.utils.CustomBufferedImage;

@SuppressWarnings("serial")
public class NonMaxSupression extends PerPixelOperation {

	CustomBufferedImage suppresedImage;
	double[][] angles;
	double[][] xGradient;
	double[][] yGradient;

	public NonMaxSupression(BufferedImage image, double gaussianValue) {
		super("Non-Max Supression");
		CustomBufferedImage gaussian = new CustomBufferedImage(Masks.applyGaussianMask(image, gaussianValue));
		// CustomBufferedImage gx = new
		// CustomBufferedImage(SobelEdgeDetection.horizontalEdgeDetection(gaussian));
		// CustomBufferedImage gy = new
		// CustomBufferedImage(SobelEdgeDetection.verticalEdgeDetection(gaussian));
		suppresedImage = new CustomBufferedImage(SobelEdgeDetection.applyModule(image));
		int height = image.getHeight();
		int width = image.getWidth();
		angles = new double[width][height];
		xGradient = new double[width][height];
		yGradient = new double[width][height];
		for (int x = 1; x < width - 1; x++) {
			for (int y = 1; y < height - 1; y++) {
				xGradient[x][y] = (gaussian.getGray(x + 1, y) - gaussian.getGray(x - 1, y)) / 2;
				yGradient[x][y] = (gaussian.getGray(x, y + 1) - gaussian.getGray(x, y - 1)) / 2;
				double angle = Math.atan2(yGradient[x][y], xGradient[x][y]);
				System.out.println(Math.toDegrees(angle));
				angles[x][y] = angleTransform(angle);
				System.out.println(angles[x][y]);
				int magnitude = (int) Math.sqrt((xGradient[x][y] * xGradient[x][y]) + (yGradient[x][y] * yGradient[x][y]));
				suppresedImage.setGray(x, y, magnitude);
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
					//	currentColor = 0;
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
