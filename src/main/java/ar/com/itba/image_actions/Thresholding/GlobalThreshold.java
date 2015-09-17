package ar.com.itba.image_actions.Thresholding;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

public class GlobalThreshold {


    public static BufferedImage applyGlobalThreshold(BufferedImage image, int threshold, int delta) {
        CustomBufferedImage customImg = (CustomBufferedImage) image;
        CustomBufferedImage newImg = customImg;
        ImageWithThreshold buffer;

        int diff = threshold;

        while (diff > delta) {
            buffer = applyThreshold(customImg, threshold);
            diff = Math.abs(threshold - buffer.threshold);
            threshold = buffer.threshold;
            newImg = buffer.img;
        }
        return newImg;

    }

    private static ImageWithThreshold applyThreshold(CustomBufferedImage customImg, int threshold) {
        CustomBufferedImage newImg = new CustomBufferedImage(customImg.getWidth(), customImg.getHeight(), customImg.getType());
        float lowCount = 0, lowTotal = 0;
        float highCount = 0, highTotal = 0;
        int gray;

        for (int x = 0; x < customImg.getWidth(); x++) {
            for (int y = 0; y < customImg.getHeight(); y++) {
                gray = customImg.getGray(x, y);
                if (gray <= threshold) {
                    lowCount += 1;
                    lowTotal += gray;
                    newImg.setGray(x, y, 0);
                } else {
                    highCount += 1;
                    highTotal += gray;
                    newImg.setGray(x, y, 255);
                }
            }
        }
        threshold = (int) (highTotal / highCount + lowTotal / lowCount) / 2;
        return new ImageWithThreshold(newImg, threshold);
    }

    private static class ImageWithThreshold {
        public CustomBufferedImage img;
        public int threshold;

        public ImageWithThreshold(CustomBufferedImage img, int threshold) {
            this.img = img;
            this.threshold = threshold;
        }
    }
}
