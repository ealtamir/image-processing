package ar.com.itba.image_actions.transformations;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 22.08.15.
 */
public class Transformations {

    static private final int RED_MASK = 0x00FF0000;
    static private final int GREEN_MASK = 0x0000FF00;
    static private final int BLUE_MASK = 0x000000FF;
    static private final int BYTE_MASK = BLUE_MASK;

    static private final int GRAY_LEVELS = 256;


    public static BufferedImage getNegative(BufferedImage img) {
        return applyTransformation(img, new NegativeTransform());
    }

    public static BufferedImage applyDynamicCompression(BufferedImage img, int levels) {
        return applyTransformation(img, new DynamicRangeCompressionTransform(levels));
    }

    private static BufferedImage applyTransformation(BufferedImage img, ImageTransformation imgTrans) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int width = 0; width < img.getWidth(); width++) {
            for (int height = 0; height < img.getHeight(); height++) {
                newImg.setRGB(width, height, imgTrans.apply(img.getRGB(width, height)));
            }
        }
        return newImg;
    }

    private static class NegativeTransform implements ImageTransformation {

        @Override
        public int apply(int pixel) {
            int r = (RED_MASK & pixel) >>> 16;
            int g = (GREEN_MASK & pixel) >>> 8;
            int b = (BLUE_MASK & pixel);

            r = GRAY_LEVELS - r - 1;
            g = GRAY_LEVELS - g - 1;
            b = GRAY_LEVELS - b - 1;
            return r << 16 | g << 8 | b;
        }
    }

    private static class DynamicRangeCompressionTransform implements ImageTransformation {

        private int compressedLevels;
        private double c;

        public DynamicRangeCompressionTransform(int compressedLevels) {
            if (compressedLevels < 0 || compressedLevels > 256) {
                throw new RuntimeException("Invalid gray levels value.");
            }
            this.compressedLevels = compressedLevels;
            c = (GRAY_LEVELS - 1.0) / Math.log(1 + compressedLevels);
        }

        @Override
        public int apply(int pixel) {
            int r = (RED_MASK & pixel) >>> 16;
            int g = (GREEN_MASK & pixel) >>> 8;
            int b = (BLUE_MASK & pixel);

            r = (int) (c * Math.log(1 + r));
            g = (int) (c * Math.log(1 + g));
            b = (int) (c * Math.log(1 + b));
            return r << 16 | g << 8 | b;
        }
    }
}
