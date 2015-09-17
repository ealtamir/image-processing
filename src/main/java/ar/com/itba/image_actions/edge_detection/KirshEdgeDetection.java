package ar.com.itba.image_actions.edge_detection;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

public class KirshEdgeDetection extends AbstractEdgeDetection {


    static public BufferedImage applyEdgeDetection(BufferedImage image) {
        nw = 5;
        n = 5;
        ne = 5;
        w = -3;
        m = 0;
        e = -3;
        sw = -3;
        s = -3;
        se = -3;
        return DefaultEdgeDetection.applyMasks(image);
    }

}
