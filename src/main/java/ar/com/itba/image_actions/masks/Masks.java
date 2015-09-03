package ar.com.itba.image_actions.masks;

import java.awt.*;
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
        BufferedImage newImg = applyMask(img, new HighPassMask(n));
        return newImg;
    }

    private static BufferedImage applyMask(BufferedImage img, ImageMask mask) {
        if (mask.getMaskDimension() < 3)
            return img;

        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        Point p = new Point();

        int radius = (int) mask.getMaskDimension() / 2;
        for (int x = radius; x < img.getWidth() - radius; x++) {
            for (int y = radius; y < img.getHeight() - radius; y++) {
                p.setLocation(x, y);
                mask.applyMask(p, img, newImg);
            }
        }
        return newImg;
    }

}
