package ar.com.itba.image_actions.masks;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

public class Susan {

	static protected int[][] susanMask = {
		{0, 0, 1, 1, 1, 0, 0},
		{0, 1, 1, 1, 1, 1, 0},
		{1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1},
		{1, 1, 1, 1, 1, 1, 1},
		{0, 1, 1, 1, 1, 1, 0},
		{0, 0, 1, 1, 1, 0, 0}
    };
	
	public BufferedImage applyMask(BufferedImage image) {
		CustomBufferedImage oldImage = new CustomBufferedImage(image);
		int height = oldImage.getHeight();
		int width = oldImage.getWidth();
		CustomBufferedImage newImage = new CustomBufferedImage(width, height, oldImage.getType());
		double s = 0;
		for (int x = 3; x < width - 3; x++) {
			for (int y = 3; y < height - 3; y++) {
				int currentColor = oldImage.getGray(x, y);
				double pixelsWithinColorRange = 0;
				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 7; j++) {
						int currentMaskColor = oldImage.getGray(x + i - 3, y + j - 3);
						if(susanMask[i][j] != 0 && (Math.abs(currentMaskColor - currentColor) < 27)) {
							pixelsWithinColorRange++;
						}
					}
				}
				s = 1 - (pixelsWithinColorRange / 49d);
				System.out.println(s);
				if(Math.abs(s - 0d) < 0.01d) {
					newImage.setGray(x, y, 0);
				} else if(Math.abs(s - 0.5d) <= 0.02d) {
					oldImage.setGray(x, y, 255);
				} else if(Math.abs(s - 0.75d) <= 0.02d) {
					oldImage.setGray(x, y, 255);
				}
			}
		}
		return oldImage;
	}
}
