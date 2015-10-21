package ar.com.itba.video_processing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.google.common.collect.Lists;

public class LoadMultipleImages {

	private List<BufferedImage> images = Lists.newLinkedList();
	private final int imageCount = 20;

	public LoadMultipleImages(String path) {
		for (int id = 0; id < imageCount; id++) {
			BufferedImage image;
			try {
				image = ImageIO.read(new File("img" + id + ".jpg"));
				images.add(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
