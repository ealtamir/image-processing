package ar.com.itba.operations;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 22.08.15.
 */
public class Transformations {

    static private final int RED_MASK = 0x00FF0000;
    static private final int GREEN_MASK = 0x0000FF00;
    static private final int BLUE_MASK = 0x000000FF;

    static private final int GRAY_LEVELS = 256;


    public static BufferedImage getNegative(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int width = 0; width < img.getWidth(); width++) {
            for (int height = 0; height < img.getHeight(); height++) {
                newImg.setRGB(width, height, img.getRGB(width, height));
            }
        }
        return newImg;
    }

    private static class NegativeTransform implements ImageTransformation {

        @Override
        public int apply(int pixel) {
            int r = GRAY_LEVELS - (RED_MASK & pixel) - 1;
            int g = GRAY_LEVELS - (GREEN_MASK & pixel) - 1;
            int b = GRAY_LEVELS - (BLUE_MASK & pixel) - 1;
            return r | g | b;
        }
    }
}
