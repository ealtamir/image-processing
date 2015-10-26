package ar.com.itba.action;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.common.collect.Lists;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.utils.ImageFileTools;

@SuppressWarnings("serial")
public class OpenVideoAction extends AbstractAction {

	private JFrame parent;
	private List<BufferedImage> images = Lists.newLinkedList();
	//Aca va la cantidad de frames
	private int FRAME_COUNT = 3;
	//Aca va el Path de los archivos
	private String VIDEO_PATH = "C:\\Users\\Federico\\Desktop\\ITBA\\git\\ATI\\image-processing\\resources\\Imagenes\\";
	private int index = 0;
	private Timer timer;
	//Velocidad del video
	private int frameSpeed = 500;

	public OpenVideoAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer = new Timer(frameSpeed, timerTask);
		File newFile = OpenFileAction.getFile(parent);
		if (newFile != null) {
			for (int i = 0; i < FRAME_COUNT; i++) {
				System.out.println(newFile.getName());
				BufferedImage image = loadImage(newFile, parent);
				images.add(image);
				newFile = new File(VIDEO_PATH + newFile.getName().substring(0, 8) + i + ".png");
			}
			timer.start();
		}
	}

	ActionListener timerTask = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
			try {
				BufferedImage image = images.get(index);
				((MainWindow) parent).updateLeftQuickDrawPanel(image);
				index++;
			} catch (Exception e2) {
				timer.stop();
				System.out.println("shit");
			}
		}
	};

	private static BufferedImage loadImage(File file, Component parent) {
		BufferedImage image = null;
		try {
			image = ImageFileTools.loadImage(file, parent);
		} catch (IOException e) {
			System.out.println("Read error: " + e.getMessage());
		}
		return image;
	}

	static public File getFile(Component parent) {
		JFileChooser fileChooser = new JFileChooser("resources/Imagenes");
		FileFilter filter = new FileNameExtensionFilter("Image files", "bmp", "pgm", "ppm", "png", "jpeg", "jpg", "gif", "tiff", "raw");
		fileChooser.addChoosableFileFilter(filter);
		int ret = fileChooser.showDialog(parent, "Open file");
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			return file;
		}
		return null;
	}

	static public BufferedImage getBufferedImageFromFile(Component parent) {
		File newfile = getFile(parent);
		if (newfile != null) {
			return loadImage(newfile, parent);
		}
		return null;
	}
}
