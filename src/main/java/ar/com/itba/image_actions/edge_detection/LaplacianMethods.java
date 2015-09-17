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
        return EdgeDetection.applyEdgeDetectionMask(customImg, laplacianMask, laplacianMask[0].length);
    }

    public static BufferedImage laplacianWithSlope(BufferedImage image) {
        return null;
    }
}
