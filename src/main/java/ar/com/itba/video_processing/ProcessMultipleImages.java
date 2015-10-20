package ar.com.itba.video_processing;

import java.awt.image.BufferedImage;
import java.util.List;

import com.google.common.base.Function;

public class ProcessMultipleImages {

	public ProcessMultipleImages(List<BufferedImage> images, Function<BufferedImage, BufferedImage> function) {
		for (BufferedImage image : images) {
			function.apply(image);
		}
	}

}
