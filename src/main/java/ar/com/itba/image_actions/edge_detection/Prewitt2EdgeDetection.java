package ar.com.itba.image_actions.edge_detection;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

public class Prewitt2EdgeDetection extends AbstractEdgeDetection {
	
    static public BufferedImage applyEdgeDetection(BufferedImage image) {
        nw = 1;
        n = 1;
        ne = 1;
        w = 0;
        m = 0;
        e = 0;
        sw = -1;
        s = -1;
        se = -1;
        return DefaultEdgeDetection.applyMasks(image);
    }
}
