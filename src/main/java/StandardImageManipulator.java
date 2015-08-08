import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class StandardImageManipulator extends JFrame {

    private final BufferedImage originalImage;

    public StandardImageManipulator() {
        originalImage = loadImage();

        if (originalImage != null)
            System.out.print("Loaded image :)");
    }

    private BufferedImage loadImage() {
        try {
            return ImageFileTools.loadImage(this);
        } catch (IOException e) {
            tellUserImgLoadFailed(e);
        }
        return null;
    }

    private void closeFrame() {
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    private void tellUserImgLoadFailed(IOException e) {
        String msg = "Falló la carga de la imagen por la siguiente razón: \n";
        msg += e.getLocalizedMessage();
        JOptionPane.showMessageDialog(this, msg,
                "Error al cargar la imagen", JOptionPane.ERROR_MESSAGE);
    }
}
