package ar.com.itba.image_actions.masks;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 02.09.15.
 */
public class Masks {


    public static BufferedImage applyMeanMask(BufferedImage img, int n) {
        return applyMask(img, new MeanMask(n));
    }

    public static BufferedImage applyMedianMask(BufferedImage img, int n) {
        return applyMask(img, new MedianMask(n));
    }

    public static BufferedImage applyGaussianMask(BufferedImage img, double deviation) {
        return applyMask(img, new GaussianMask(0, deviation));
    }

    public static BufferedImage applyHighPassMask(BufferedImage img, int n) {
//        boolean normalize = n >= 3;
        boolean normalize = false;
        return applyMask(img, new HighPassMask(n), normalize);

    }

    private static BufferedImage applyMask(BufferedImage img, ImageMask mask) {
        if (mask.getMaskDimension() < 3)
            return img;

        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        Point p = new Point();

        int radius = (int) mask.getMaskDimension() / 2;
        boolean x_border, y_border;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                x_border = x < radius || (x >= img.getWidth() - radius && x < img.getWidth());
                y_border = y < radius || (y >= img.getHeight() - radius && y < img.getHeight());

                if (x_border || y_border) {
                    newImg.setRGB(x, y, img.getRGB(x, y));
                } else {
                    p.setLocation(x, y);
                    mask.applyMask(p, img, newImg);
                }
            }
        }
        for(int x = 0; x < radius || (x >= img.getWidth() - radius && x < img.getWidth()); x++) {
            for (int y = 0; y < radius || (y >= img.getHeight() - radius && y < img.getHeight()); y++) {

            }
        }

        return newImg;
    }

    private static BufferedImage applyMask(BufferedImage img, ImageMask mask, boolean normalize) {
        BufferedImage newImg = applyMask(img, mask);
        if (normalize) {
            return normalize(newImg);
        }
        return newImg;
    }


    private static BufferedImage normalize(BufferedImage img) {
        int max = 0;
        int min = 0;
        int val = 0;

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                val = img.getRGB(x, y);
                min = Math.min(val, min);
                max = Math.max(val, max);
            }
        }
        return applyLinearCompression(min, max, img);
    }

    private static BufferedImage applyLinearCompression(int min, int max, BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int val = 0;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                val = 0xFF & img.getRGB(x, y);
                val = 0xFF & (255 / (max - min)) * val - (255 / (max - min)) * min;
                newImg.setRGB(x, y, val << 16 | val << 8 | val);
            }
        }
        return newImg;
    }


}
