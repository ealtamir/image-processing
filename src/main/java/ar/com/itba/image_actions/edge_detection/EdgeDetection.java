package ar.com.itba.image_actions.edge_detection;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

/**
 * Created by Enzo on 12.09.15.
 */
public class EdgeDetection {

    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;

    private static int[] totalRGB = new int[3];

    static public BufferedImage applyEdgeDetectionMask(CustomBufferedImage img, int[][] mask, int size) {
        CustomBufferedImage customImg = (CustomBufferedImage) applyEdgeDetectionWithoutNormalization(img, mask, size);
        customImg.applyLinearTransform();
        return customImg;
    }

    static public BufferedImage applyEdgeDetectionWithoutNormalization(CustomBufferedImage img, int[][] mask, int size) {
        CustomBufferedImage customImg = new CustomBufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int radius = size / 2;
        int[] rgbMaskVal;

        for (int x = radius; x < img.getWidth() - radius; x++) {
            for (int y = radius; y < img.getHeight() - radius; y++) {
                rgbMaskVal = getMaskValue(x, y, img, mask, radius);
                customImg.setRGBCustom(x, y, rgbMaskVal[RED], rgbMaskVal[GREEN], rgbMaskVal[BLUE]);
            }
        }
        return customImg;
    }

    private static int[] getMaskValue(int x, int y, CustomBufferedImage img, int[][] mask, int radius) {
        totalRGB[RED] = totalRGB[GREEN] = totalRGB[BLUE] = 0;

        for (int h = y - radius, j = 0; h <= y + radius; h++, j++) {
            for (int w = x - radius, i = 0; w <= x + radius; w++, i++) {
                totalRGB[RED] += mask[j][i] * img.getRed(w, h);
                totalRGB[GREEN] += mask[j][i] * img.getGreen(w, h);
                totalRGB[BLUE] += mask[j][i] * img.getBlue(w, h);
            }
        }
        return totalRGB;
    }
}
