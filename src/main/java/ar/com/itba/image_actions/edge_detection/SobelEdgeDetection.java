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
}
