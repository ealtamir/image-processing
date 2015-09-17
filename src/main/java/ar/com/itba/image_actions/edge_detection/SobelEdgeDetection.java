package ar.com.itba.image_actions.edge_detection;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

/**
 * Created by Enzo on 12.09.15.
 */
public class SobelEdgeDetection {

    static private int[][] horizontalMask = {
            {-1, -2, -1},
            {0, 0, 0},
            {1, 2, 1}
    };

    static private int[][] verticalMask = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };

    static public BufferedImage horizontalEdgeDetection(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        return EdgeDetection.applyEdgeDetectionMask(customImg, horizontalMask, horizontalMask[0].length);
    }

    static public BufferedImage verticalEdgeDetection(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        return EdgeDetection.applyEdgeDetectionMask(customImg, verticalMask, verticalMask[0].length);
    }

    public static BufferedImage applyModule(BufferedImage image) {
        CustomBufferedImage customImg = (CustomBufferedImage) image;
        CustomBufferedImage img1 = (CustomBufferedImage) EdgeDetection.applyEdgeDetectionMask(customImg, horizontalMask, horizontalMask[0].length);
        CustomBufferedImage img2 = (CustomBufferedImage) EdgeDetection.applyEdgeDetectionMask(customImg, verticalMask, verticalMask[0].length);
        CustomBufferedImage newImg = new CustomBufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int x = 0; x < customImg.getWidth(); x++) {
            for (int y = 0; y < customImg.getHeight(); y++) {
                int r = (int) Math.round(Math.sqrt((img1.getRed(x, y) * img1.getRed(x, y)) + (img2.getRed(x, y) * img2.getRed(x, y))));
                int g = (int) Math.round(Math.sqrt(img1.getGreen(x, y) * img1.getGreen(x, y) + img2.getGreen(x, y) * img2.getGreen(x, y)));
                int b = (int) Math.round(Math.sqrt(img1.getBlue(x, y) * img1.getBlue(x, y) + img2.getBlue(x, y) * img2.getBlue(x, y)));
                newImg.setRGBCustom(x, y, r, g, b);
            }
        }
        newImg.applyLinearTransform();
        return newImg;
    }
}
