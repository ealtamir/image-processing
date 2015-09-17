package ar.com.itba.image_actions.Thresholding;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

/**
 * Created by Enzo on 16.09.15.
 */
public class OtsuThreshold {

    public static BufferedImage applyTransformation(BufferedImage image) {
        CustomBufferedImage customImg = (CustomBufferedImage) image;
        CustomBufferedImage newImg = new CustomBufferedImage(image.getWidth(), image.getHeight(), customImg.getType());
        int threshold = getOtsuThreshold(customImg);

        for (int x = 0; x < customImg.getWidth(); x++) {
            for (int y = 0; y < customImg.getHeight(); y++) {
                if (customImg.getGray(x, y) >= threshold) {
                    newImg.setGray(x, y, 255);
                } else {
                    newImg.setGray(x, y, 0);
                }
            }
        }
        return newImg;
    }

    private static int getOtsuThreshold(CustomBufferedImage customImg) {
        int[] histogram = customImg.getHistogram();
        int total = customImg.getWidth() * customImg.getHeight();

        float sum = 0;
        for (int t = 0; t < 256; t++) {
            sum += t * histogram[t];
        }

        float sumB  = 0;
        int wB = 0;
        int wF = 0;

        float varMax = 0;
        int threshold = 0;

        for (int t = 0; t < 256; t++) {
            wB += histogram[t];
            if (wB == 0) continue;

            wF = total - wB;
            if (wF == 0) break;

            sumB += (float) (t * histogram[t]);

            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;

            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);

            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = t;
            }
        }
        return threshold;
    }

}
