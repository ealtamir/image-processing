package ar.com.itba.image_actions.edge_detection;

import ar.com.itba.utils.CustomBufferedImage;

import java.awt.image.BufferedImage;

abstract public class AbstractEdgeDetection {

    static int nw = 1, n = 1, ne = 1;
    static int w = 0, m = 0, e = 0;
    static int sw = -1, s = -1, se = -1;

    static protected int[][] NMask = {
            {nw, n, ne},
            {w, m, e},
            {sw, s, se}
    };

    static protected int[][] NEMask = {
            {w, nw, n},
            {sw, m, ne},
            {s, se, e}
    };

    static protected int[][] EMask = {
            {sw, w, nw},
            {s, m, n},
            {se, e, ne}
    };

    static protected int[][] SEMask = {
            {s, sw, w},
            {se, m, nw},
            {e, ne, n}
    };

    static protected int[][] SMask = {
            {se, s, sw},
            {e, m, w},
            {ne, n, nw}
    };

    static protected int[][] SWMask = {
            {e, se, s},
            {ne, m, sw},
            {n, nw, w}
    };

    static protected int[][] WMask = {
            {ne, e, se},
            {n, m, s},
            {nw, w, sw}
    };

    static protected int[][] NWMask = {
            {n, ne, e},
            {nw, m, se},
            {w, sw, s}
    };

    static public BufferedImage applyMasks(BufferedImage image) {
        CustomBufferedImage customImg = (CustomBufferedImage) image;
        CustomBufferedImage newImg = new CustomBufferedImage(image.getWidth(), image.getHeight(), image.getType());
        int radius = 1;
        int[] nmask, nemask, emask, semask, smask, swmask, wmask, nwmask;
        int[] rgb = new int[3];


        for (int x = radius; x < image.getWidth() - radius; x++) {
            for (int y = radius; y < image.getHeight() - radius; y++) {
                nmask = EdgeDetection.getMaskValue(x, y, customImg, NMask, radius);
                nemask = EdgeDetection.getMaskValue(x, y, customImg, NEMask, radius);
                emask = EdgeDetection.getMaskValue(x, y, customImg, EMask, radius);
                semask = EdgeDetection.getMaskValue(x, y, customImg, SEMask, radius);
                smask = EdgeDetection.getMaskValue(x, y, customImg, SMask, radius);
                swmask = EdgeDetection.getMaskValue(x, y, customImg, SWMask, radius);
                wmask = EdgeDetection.getMaskValue(x, y, customImg, WMask, radius);
                nwmask = EdgeDetection.getMaskValue(x, y, customImg, NWMask, radius);
                for (int c = 0; c < 3; c++) {
                    rgb[c] = Math.max(nmask[c], Math.max(nemask[c], Math.max(emask[c], Math.max(semask[c], Math.max(smask[c], Math.max(swmask[c], Math.max(wmask[c], nwmask[c])))))));
                }
                newImg.setRGBCustom(x, y, rgb[0], rgb[1], rgb[2]);
            }
        }
        newImg.applyLinearTransform();
        return newImg;
    }

}
