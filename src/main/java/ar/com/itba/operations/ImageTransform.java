package ar.com.itba.operations;

import java.awt.image.BufferedImage;
import java.io.Serializable;

import com.google.common.base.Function;

public interface ImageTransform extends Function<BufferedImage, BufferedImage>, Serializable {

	// remove this, use Function apply
	public int apply(int pixel);

}
