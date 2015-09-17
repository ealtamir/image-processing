package ar.com.itba.image_actions.edge_detection;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

public class DefaultEdgeDetection {

	static int nw = 1, n = 1, ne = 1;
	static int w = 1, m = -2, e = 1;
	static int sw = -1, s = -1, se = -1;
	
	static private int[][] NMask = {
            {nw, n, ne},
            {w, m, e},
            {sw, s, se}
    };
	
	static private int[][] NEMask = {
            {w, nw, n},
            {sw, m, ne},
            {s, se, e}
    };
	
	static private int[][] EMask = {
            {sw, w, nw},
            {s, m, n},
            {se, e, ne}
    };
	
	static private int[][] SEMask = {
            {s, sw, w},
            {se, m, nw},
            {e, ne, n}
    };
	
	static private int[][] SMask = {
            {se, s, sw},
            {e, m, w},
            {ne, n, nw}
    };
	
	static private int[][] SWMask = {
            {e, se, s},
            {ne, m, sw},
            {n, nw, w}
    };
	
	static private int[][] WMask = {
            {ne, e, se},
            {n, m, s},
            {nw, w, sw}
    };
	
	static private int[][] NWMask = {
            {n, ne, e},
            {nw, m, se},
            {w, sw, s}
    };

    static public BufferedImage nEdgeDetection(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        return EdgeDetection.applyEdgeDetectionMask(customImg, NMask, NMask[0].length);
    }
    
    static public BufferedImage neEdgeDetection(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        return EdgeDetection.applyEdgeDetectionMask(customImg, NEMask, NEMask[0].length);
    }
    
    static public BufferedImage eEdgeDetection(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        return EdgeDetection.applyEdgeDetectionMask(customImg, EMask, EMask[0].length);
    }
    
    static public BufferedImage seEdgeDetection(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        return EdgeDetection.applyEdgeDetectionMask(customImg, SEMask, SEMask[0].length);
    }
    
    static public BufferedImage sEdgeDetection(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        return EdgeDetection.applyEdgeDetectionMask(customImg, SMask, SMask[0].length);
    }
    
    static public BufferedImage swEdgeDetection(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        return EdgeDetection.applyEdgeDetectionMask(customImg, SWMask, SWMask[0].length);
    }
    
    static public BufferedImage wEdgeDetection(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        return EdgeDetection.applyEdgeDetectionMask(customImg, WMask, WMask[0].length);
    }
    
    static public BufferedImage nwEdgeDetection(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        return EdgeDetection.applyEdgeDetectionMask(customImg, NWMask, NWMask[0].length);
    }

}
