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

@SuppressWarnings("serial")
public class SaveFileAction extends AbstractAction {

	private JFrame parent;

	public SaveFileAction(JFrame parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser("resources/Imagenes");
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		FileFilter filter = new FileNameExtensionFilter("Image files", "bmp", "pgm", "ppm", "png", "jpeg", "jpg", "gif", "tiff", "raw");
		fileChooser.addChoosableFileFilter(filter);
		int ret = fileChooser.showSaveDialog(parent);
		if (ret == JFileChooser.APPROVE_OPTION) {
			BufferedImage bufferedImage = ((MainWindow) parent).getQuickDrawPanel().image();
			if (bufferedImage != null) {
				try {
					String filePath = fileChooser.getCurrentDirectory().toString();
					filePath += File.separator;
					filePath += fileChooser.getSelectedFile().getName() + ".png";

					ImageIO.write(bufferedImage, "PNG", new File(filePath));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
