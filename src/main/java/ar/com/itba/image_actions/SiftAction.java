package ar.com.itba.image_actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.highgui.Highgui;

import com.google.common.collect.Lists;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.utils.ImageFileTools;

@SuppressWarnings("serial")
public class SiftAction extends AbstractAction {

	private JFrame parent;
	private BufferedImage bufferedImage1;
	private BufferedImage bufferedImage2;

	public SiftAction(JFrame parent) {
		this.parent = parent;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// bufferedImage1 = ((MainWindow)
		// parent).getLeftQuickDrawPanel().image();
		// File newFile = OpenFileAction.getFile(parent);
		// if (newFile != null) {
		// bufferedImage2 = loadImage(newFile, parent);
//		Mat mat1 = Highgui.imread("C:\\Users\\Federico\\Desktop\\ITBA\\multi\\image-processing\\image-processing\\resources\\Imagenes\\adam0.png");
//		Mat mat2 = Highgui.imread("C:\\Users\\Federico\\Desktop\\ITBA\\multi\\image-processing\\image-processing\\resources\\Imagenes\\adam1.png");
		Mat mat1 = Highgui.imread("C:\\Users\\Federico\\Desktop\\ITBA\\multi\\image-processing\\image-processing\\resources\\Imagenes\\1lenab&w.png");
		Mat mat2 = Highgui.imread("C:\\Users\\Federico\\Desktop\\ITBA\\multi\\image-processing\\image-processing\\resources\\Imagenes\\1lenab&w90.png");
//		Mat mat1 = Highgui.imread("C:\\Users\\Federico\\Desktop\\ITBA\\multi\\image-processing\\image-processing\\resources\\Imagenes\\1lenab&wnoise2.png");
//		Mat mat2 = Highgui.imread("C:\\Users\\Federico\\Desktop\\ITBA\\multi\\image-processing\\image-processing\\resources\\Imagenes\\1lenab&wnoise.png");

		FeatureDetector siftDetector = FeatureDetector.create(FeatureDetector.SIFT);
		final MatOfKeyPoint keyPoints1 = new MatOfKeyPoint();
		final MatOfKeyPoint keyPoints2 = new MatOfKeyPoint();
		siftDetector.detect(mat1, keyPoints1);
		siftDetector.detect(mat2, keyPoints2);

		Mat descriptors1 = new Mat();
		Mat descriptors2 = new Mat();
		DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		extractor.compute(mat1, keyPoints1, descriptors1);
		extractor.compute(mat2, keyPoints2, descriptors2);

		MatOfDMatch matches = new MatOfDMatch();
		DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
		matcher.match(descriptors1, descriptors2, matches);

		// filtering
		MatOfDMatch matchesFiltered = new MatOfDMatch();
		List<DMatch> matchesList = matches.toList();
		List<DMatch> bestMatches = Lists.newArrayList();
		float max_dist = 0.0f;
		float min_dist = 100.0f;
		for (DMatch dMatch : matchesList) {
			float dist = dMatch.distance;
			if (dist < min_dist && dist != 0) {
				min_dist = dist;
			} else if (dist > max_dist) {
				max_dist = dist;
			}
		}
		double threshold = 3 * min_dist;
		double threshold2 = 2 * min_dist;

		if (threshold2 >= max_dist) {
			threshold = min_dist * 1.1;
		} else if (threshold >= max_dist) {
			threshold = threshold2 * 1.4;
		}
		for (int i = 0; i < matchesList.size(); i++) {
			Double dist = (double) matchesList.get(i).distance;
			if (dist < threshold) {
				bestMatches.add(matches.toList().get(i));
			}
		}
		System.out.println(threshold);
		matchesFiltered.fromList(bestMatches);

		Mat outputMat = new Mat();
		// Features2d.drawMatches(mat1, keyPoints1, mat2, keyPoints2, matches,
		// outputMat);
		Features2d.drawMatches(mat1, keyPoints1, mat2, keyPoints2, matchesFiltered, outputMat);
		((MainWindow) parent).updateLeftQuickDrawPanel(mat2Img(outputMat, BufferedImage.TYPE_3BYTE_BGR));
	}

	private static BufferedImage loadImage(File file, Component parent) {
		BufferedImage image = null;
		try {
			image = ImageFileTools.loadImage(file, parent);
		} catch (IOException e) {
			System.out.println("Read error: " + e.getMessage());
		}
		return image;
	}

	public static BufferedImage mat2Img(Mat mat, int type) {
		// BufferedImage image = new BufferedImage(mat.width(), mat.height(),
		// BufferedImage.TYPE_3BYTE_BGR);
		BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		mat.get(0, 0, data);
		return image;
	}

}
