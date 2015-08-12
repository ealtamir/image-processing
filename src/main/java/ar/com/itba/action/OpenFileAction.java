package ar.com.itba.action;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.utils.ImageFileTools;

@SuppressWarnings("serial")
public class OpenFileAction extends AbstractAction {

	private JFrame parent;

	public OpenFileAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser("resources/Imagenes");
		FileFilter filter = new FileNameExtensionFilter("Image files", "bmp", "pgm", "ppm", "png", "jpeg", "jpg", "gif", "tiff", "raw");
		fileChooser.addChoosableFileFilter(filter);
		int ret = fileChooser.showDialog(parent, "Open file");
		if (ret == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			loadImage(file);
		}
	}

	private void loadImage(File file) {
		BufferedImage image = null;
		try {
			image = ImageFileTools.loadImage(file, parent);
		} catch (IOException e) {
			System.out.println("Read error: " + e.getMessage());
		}
		((MainWindow) parent).quickDrawPanel().image(image);
	}
}
