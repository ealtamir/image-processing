package ar.com.itba.image_actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;

import ar.com.itba.action.OpenFileAction;
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
		bufferedImage1 = ((MainWindow) parent).getLeftQuickDrawPanel().image();
		File newFile = OpenFileAction.getFile(parent);
		if (newFile != null) {
			bufferedImage2 = loadImage(newFile, parent);
			Mat mat = img2Mat(bufferedImage2, bufferedImage2.getType());
			Mat siftMat = sift(mat);
			BufferedImage img = mat2Img(siftMat, bufferedImage2.getType());
			((MainWindow) parent).updateLeftQuickDrawPanel(img);
		}

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

	private Mat sift(Mat mat) {
		FeatureDetector siftDetector = FeatureDetector.create(FeatureDetector.SIFT);
		MatOfKeyPoint mokp = new MatOfKeyPoint();
		siftDetector.detect(mat, mokp);
		Mat descriptors = new Mat();
		DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.SIFT);
		extractor.compute(mat, mokp, descriptors);
		return descriptors;
	}

	public static BufferedImage mat2Img(Mat mat, int type) {
		// BufferedImage image = new BufferedImage(mat.width(), mat.height(),
		// BufferedImage.TYPE_3BYTE_BGR);
		BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		mat.get(0, 0, data);
		return image;
	}

	public static Mat img2Mat(BufferedImage image, int type) {
		byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		int curCVtype = CvType.CV_8UC4; // Default type
		switch (type) {
			case BufferedImage.TYPE_3BYTE_BGR:
				curCVtype = CvType.CV_8UC3;
				break;
			case BufferedImage.TYPE_BYTE_GRAY:
			case BufferedImage.TYPE_BYTE_BINARY:
				curCVtype = CvType.CV_8UC1;
				break;
			case BufferedImage.TYPE_INT_BGR:
			case BufferedImage.TYPE_INT_RGB:
				curCVtype = CvType.CV_32SC3;
				break;
			case BufferedImage.TYPE_INT_ARGB:
			case BufferedImage.TYPE_INT_ARGB_PRE:
				curCVtype = CvType.CV_32SC4;
				break;
			case BufferedImage.TYPE_USHORT_GRAY:
				curCVtype = CvType.CV_16UC1;
				break;
			case BufferedImage.TYPE_4BYTE_ABGR:
			case BufferedImage.TYPE_4BYTE_ABGR_PRE:
				curCVtype = CvType.CV_8UC4;
				break;
			default:
				// BufferedImage.TYPE_BYTE_INDEXED;
				// BufferedImage.TYPE_CUSTOM;
		}
		System.out.println(image.getType());
		System.out.println(curCVtype);
		Mat mat = new Mat(image.getHeight(), image.getWidth(), curCVtype);
		mat.put(0, 0, data);
		return mat;
	}

}
