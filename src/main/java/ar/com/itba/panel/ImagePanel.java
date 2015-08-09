package ar.com.itba.panel;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImagePanel extends JPanel {

	private BufferedImage image;
	
	 public ImagePanel() {
	       try {                
	          image = ImageIO.read(new File("image name and path"));
	       } catch (IOException ex) {
	            // handle exception...
	       }
	    }
	 
	 @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawImage(image, 0, 0, null);         
	    }
	
}
