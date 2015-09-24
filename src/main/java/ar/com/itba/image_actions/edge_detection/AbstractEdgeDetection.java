package ar.com.itba.image_actions.edge_detection;

import ar.com.itba.utils.CustomBufferedImage;

import java.awt.image.BufferedImage;

abstract public class AbstractEdgeDetection {

    static int nw = 0, n = 0, ne = 0;
    static int w = 0, m = 0, e = 0;
    static int sw = 0, s = 0, se = 0;

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

    private void updateMasks() {
        NMask[0][0] = nw; NMask[0][1] = n; NMask[0][2] = ne;
        NMask[1][0] = w; NMask[1][1] = m; NMask[1][2] = e;
        NMask[2][0] = sw; NMask[2][1] = s; NMask[2][2] = se;

        NEMask[0][0] = w; NEMask[0][1] = nw; NEMask[0][2] = n;
        NEMask[1][0] = sw; NEMask[1][1] = m; NEMask[1][2] = ne;
        NEMask[2][0] = s; NEMask[2][1] = se; NEMask[2][2] = e;

        EMask[0][0] = sw; EMask[0][1] = w; EMask[0][2] = nw;
        EMask[1][0] = s; EMask[1][1] = m; EMask[1][2] = n;
        EMask[2][0] = se; EMask[2][1] = e; EMask[2][2] = ne;

        SEMask[0][0] = s; SEMask[0][1] = sw; SEMask[0][2] = w;
        SEMask[1][0] = se; SEMask[1][1] = m; SEMask[1][2] = nw;
        SEMask[2][0] = e; SEMask[2][1] = ne; SEMask[2][2] = n;

        SMask[0][0] = se; SMask[0][1] = s; SMask[0][2] = sw;
        SMask[1][0] = e; SMask[1][1] = m; SMask[1][2] = w;
        SMask[2][0] = ne; SMask[2][1] = n; SMask[2][2] = nw;

        SWMask[0][0] = e; SWMask[0][1] = se; SWMask[0][2] = s;
        SWMask[1][0] = ne; SWMask[1][1] = m; SWMask[1][2] = sw;
        SWMask[2][0] = n; SWMask[2][1] = nw; SWMask[2][2] = w;

        WMask[0][0] = ne; WMask[0][1] = e; WMask[0][2] = se;
        WMask[1][0] = n; WMask[1][1] = m; WMask[1][2] = s;
        WMask[2][0] = nw; WMask[2][1] = w; WMask[2][2] = sw;

        NWMask[0][0] = n; NWMask[0][1] = ne; NWMask[0][2] = e;
        NWMask[1][0] = nw; NWMask[1][1] = m; NWMask[1][2] = se;
        NWMask[2][0] = w; NWMask[2][1] = sw; NWMask[2][2] = s;
    }

    protected BufferedImage applyMasks(BufferedImage image) {
        updateMasks();
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
