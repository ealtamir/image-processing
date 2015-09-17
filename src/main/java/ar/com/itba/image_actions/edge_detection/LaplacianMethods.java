package ar.com.itba.image_actions.edge_detection;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

/**
 * Created by Enzo on 12.09.15.
 */
public class LaplacianMethods {

    static private int[][] laplacianMask = {
            {0, -1, 0},
            {-1, 4, -1},
            {0, -1, 0}
    };

    public static BufferedImage standardLaplacian(BufferedImage image) {
        CustomBufferedImage customImg = (CustomBufferedImage) image;
        CustomBufferedImage newImg = (CustomBufferedImage) EdgeDetection.applyEdgeDetectionWithoutNormalization(
                customImg, laplacianMask, laplacianMask[0].length);
        int slope = 0;

        int i, current, nextX, nextY, fnextX, fnextY;
        for (int x = 0; x < newImg.getWidth() - 2; x++) {
            for (int y = 0; y < newImg.getHeight() - 2; y++) {
                i = y * newImg.getWidth() + x;
                current = newImg.getGray(x, y);
                nextX = newImg.getGray(x + 1, y);
                nextY = newImg.getGray(x, y + 1);
                fnextX = newImg.getGray(x + 2, y);
                fnextY = newImg.getGray(x, y + 2);

                if (Math.signum(current) > Math.signum(nextX) ||
                        Math.signum(current) < Math.signum(nextX)) {
                    slope = 255;

                } else if (Math.signum(current) > Math.signum(nextY) ||
                        Math.signum(current) < Math.signum(nextY)) {
                    slope = 255;

                } else if (Math.signum(current) > Math.signum(fnextX) ||
                        Math.signum(current) < Math.signum(fnextX)) {
                    slope = 255;

                } else if (Math.signum(current) > Math.signum(fnextY) ||
                        Math.signum(current) < Math.signum(fnextY)) {
                    slope = 255;
                } else {
                    slope = 0;
                }
                newImg.setGray(x, y, slope);
            }
        }
        return newImg;
    }

    public static BufferedImage laplacianWithSlope(BufferedImage image, int threshold) {
        CustomBufferedImage customImg = (CustomBufferedImage) image;
        CustomBufferedImage newImg = (CustomBufferedImage) EdgeDetection.applyEdgeDetectionWithoutNormalization(
                customImg, laplacianMask, laplacianMask[0].length);
        int slope = 0;

        int i, current, nextX, nextY, fnextX, fnextY;
        for (int x = 0; x < newImg.getWidth() - 2; x++) {
            for (int y = 0; y < newImg.getHeight() - 2; y++) {
                i = y * newImg.getWidth() + x;
                current = newImg.getGray(x, y);
                nextX = newImg.getGray(x + 1, y);
                nextY = newImg.getGray(x, y + 1);
                fnextX = newImg.getGray(x + 2, y);
                fnextY = newImg.getGray(x, y + 2);

                if (Math.signum(current) > Math.signum(nextX) ||
                        Math.signum(current) < Math.signum(nextX)) {
                    slope = Math.abs(current - nextX);

                } else if (Math.signum(current) > Math.signum(nextY) ||
                        Math.signum(current) < Math.signum(nextY)) {
                    slope = Math.abs(current - nextY);

                } else if (Math.signum(current) > Math.signum(fnextX) ||
                        Math.signum(current) < Math.signum(fnextX)) {
                    slope = Math.abs(current - fnextX);

                } else if (Math.signum(current) > Math.signum(fnextY) ||
                        Math.signum(current) < Math.signum(fnextY)) {
                    slope = Math.abs(current - fnextY);
                }
                newImg.setRGBCustom(x, y, slope, slope, slope);
            }
        }
        newImg.applyLinearTransform();
        for (int x = 0; x < newImg.getWidth(); x++) {
            for (int y = 0; y < newImg.getHeight(); y++) {
                current = newImg.getGray(x, y);
                if (current > threshold) {
                    newImg.setGray(x, y, 255);
                } else {
                    newImg.setGray(x, y, 0);
                }
            }
        }
        return newImg;
    }

    public static BufferedImage laplacianOfGaussian(BufferedImage originalImage, int sigma) {
        CustomBufferedImage customImg = (CustomBufferedImage) originalImage;
        int maskSize = 7;
        int radius = (int) Math.floor((double) maskSize / 2);
        double[][] mask = new double[maskSize][maskSize];

        for (int x = -radius, i = 0; i < maskSize; x++, i++) {
            for (int y = -radius, j = 0; j < maskSize; y++, j++) {
                mask[j][i] = getLOGValue(x, y, sigma);
            }
        }

        CustomBufferedImage newImg = (CustomBufferedImage) EdgeDetection.applyEdgeDetectionMask(customImg, mask, maskSize);

        int slope = 0;
        int i, current, nextX, nextY, fnextX, fnextY;
        for (int x = 0; x < newImg.getWidth() - 2; x++) {
            for (int y = 0; y < newImg.getHeight() - 2; y++) {
                i = y * newImg.getWidth() + x;
                current = newImg.getGray(x, y);
                nextX = newImg.getGray(x + 1, y);
                nextY = newImg.getGray(x, y + 1);
                fnextX = newImg.getGray(x + 2, y);
                fnextY = newImg.getGray(x, y + 2);

                if (Math.signum(current) > Math.signum(nextX) ||
                        Math.signum(current) < Math.signum(nextX)) {
                    slope = 255;

                } else if (Math.signum(current) > Math.signum(nextY) ||
                        Math.signum(current) < Math.signum(nextY)) {
                    slope = 255;

                } else if (Math.signum(current) > Math.signum(fnextX) ||
                        Math.signum(current) < Math.signum(fnextX)) {
                    slope = 255;

                } else if (Math.signum(current) > Math.signum(fnextY) ||
                        Math.signum(current) < Math.signum(fnextY)) {
                    slope = 255;
                } else {
                    slope = 0;
                }
                newImg.setGray(x, y, slope);
            }
        }
        return newImg;
    }

    private static double getLOGValue(float x, float y, float sigma) {
        double fraction1 = (1 / ((Math.sqrt(2 * Math.PI) * (sigma * sigma * sigma))));
        double fraction2 = (2 - (((x*x) + (y*y)) / (sigma * sigma)));
        double fraction3 = Math.exp(-(((x*x) + (y*y)) / (2 * sigma * sigma)));
        return - fraction1 * fraction2 * fraction3;
    }
}
