package ar.com.itba.image_actions.transformations;

import java.awt.image.BufferedImage;
import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

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

    public static BufferedImage applyThreshold(BufferedImage img, int threshold) {
        return applyTransformation(img, new ThresholdTransform(threshold));
    }

    public static BufferedImage getEqualized(BufferedImage img) {
        BufferedImage newImg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        float[] relativeFreq = getRelativeFreqArr(img);
        float minFreq = getSmallestEl(relativeFreq);

        float buffer;
        int index, val;

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                index = BYTE_MASK & img.getRGB(x, y);
                buffer = relativeFreq[index];
                val = BYTE_MASK & (int) (((buffer - minFreq) / (1 - minFreq)) * GRAY_LEVELS + 0.5);
                val = val << 16 | val << 8 | val;
                newImg.setRGB(x, y, val);
            }
        }
        return newImg;
    }

    private static float getSmallestEl(float[] relativeFreq) {
        float smallest = 1;
        for (int i = 1; i < relativeFreq.length; i++) {
            if (smallest > relativeFreq[i]) {
                smallest = relativeFreq[i];
            }
        }
        return smallest;
    }

    private static float[] getRelativeFreqArr(BufferedImage image) {
        float[] relativeFreq = new float[GRAY_LEVELS];
        int totalPixels = image.getHeight() * image.getWidth();

        int index;

        // Assumes image is in grayscale
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                index = BLUE_MASK & image.getRGB(x, y);
                relativeFreq[index] += 1;
            }
        }
        relativeFreq[0] /= totalPixels;
        for (int i = 1; i < relativeFreq.length; i++) {
            relativeFreq[i] = relativeFreq[i] / totalPixels + relativeFreq[i - 1];
        }
        return relativeFreq;
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

    private static class ThresholdTransform implements ImageTransformation {

        private int threshold;

        public ThresholdTransform(int threshold) {
            if (threshold < 0 || threshold > 255) {
                throw new RuntimeException("Invalid gray levels value.");
            }
            this.threshold = threshold;
        }

        @Override
        public int apply(int pixel) {
            int r = (RED_MASK & pixel) >>> 16;
            int g = (GREEN_MASK & pixel) >>> 8;
            int b = (BLUE_MASK & pixel);

            r = (r <= threshold)? 0: GRAY_LEVELS - 1;
            g = (g <= threshold)? 0: GRAY_LEVELS - 1;
            b = (b <= threshold)? 0: GRAY_LEVELS - 1;
            return r << 16 | g << 8 | b;
        }
    }
}
