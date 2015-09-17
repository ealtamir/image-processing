package ar.com.itba.image_actions.edge_detection;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

public class DefaultEdgeDetection extends AbstractEdgeDetection {

	static public BufferedImage applyEdgeDetection(BufferedImage image) {
		nw = 1;
		n = 1;
		ne = 1;
		w = 1;
		m = -2;
		e = 1;
		sw = -1;
		s = -1;
		se = -1;
		return DefaultEdgeDetection.applyMasks(image);
	}

}
