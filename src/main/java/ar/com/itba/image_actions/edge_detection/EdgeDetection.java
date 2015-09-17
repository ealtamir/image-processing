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

    private static double[] totalRGBdouble = new double[3];

    static public BufferedImage applyEdgeDetectionMask(CustomBufferedImage img, int[][] mask, int size) {
        CustomBufferedImage customImg = (CustomBufferedImage) applyEdgeDetectionWithoutNormalization(img, mask, size);
        customImg.applyLinearTransform();
        return customImg;
    }

    static public BufferedImage applyEdgeDetectionMask(CustomBufferedImage img, double[][] mask, int size) {
        CustomBufferedImage customImg = new CustomBufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int radius = size / 2;
        double[] rgbMaskVal;

        for (int x = radius; x < img.getWidth() - radius; x++) {
            for (int y = radius; y < img.getHeight() - radius; y++) {
                rgbMaskVal = getMaskValue(x, y, img, mask, radius);
                customImg.setRGBCustom(x, y, (int) rgbMaskVal[RED], (int) rgbMaskVal[GREEN], (int) rgbMaskVal[BLUE]);
            }
        }
        customImg.applyLinearTransform();
        return customImg;
    }

    private static double[] getMaskValue(int x, int y, CustomBufferedImage img, double[][] mask, int radius) {
        totalRGBdouble[RED] = totalRGBdouble[GREEN] = totalRGBdouble[BLUE] = 0;

        int r, g, b;
        for (int h = y - radius, j = 0; h <= y + radius; h++, j++) {
            for (int w = x - radius, i = 0; w <= x + radius; w++, i++) {
                r = img.getRed(w, h);
                g = img.getGreen(w, h);
                b = img.getBlue(w, h);
                totalRGBdouble[RED] += mask[j][i] * r;
                totalRGBdouble[GREEN] += mask[j][i] * g;
                totalRGBdouble[BLUE] += mask[j][i] * b;
            }
        }
        return totalRGBdouble;
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

    public static int[] getMaskValue(int x, int y, CustomBufferedImage img, int[][] mask, int radius) {
        int[] totalRGB = new int[3];

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
