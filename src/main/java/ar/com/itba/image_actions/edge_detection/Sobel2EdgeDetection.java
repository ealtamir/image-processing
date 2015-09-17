package ar.com.itba.image_actions.edge_detection;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

public class Sobel2EdgeDetection extends AbstractEdgeDetection {
	
    public static BufferedImage applyEdgeDetection(BufferedImage image) {
        nw = 1;
        n = 2;
        ne = 1;
        w = 0;
        m = 0;
        e = 0;
        sw = -1;
        s = -2;
        se = -1;
        return DefaultEdgeDetection.applyMasks(image);
    }
}
