package ar.com.itba.frame;

import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ar.com.itba.panel.PixelColorChanger;
import ar.com.itba.panel.QuickDrawPanel;
import ar.com.itba.panel.RectSelectionPanel;


public class ImageOptionsWindow extends JFrame {

    private QuickDrawPanel owner;
    private JPanel panel;
    private JLabel pointerLabel;
    private PixelColorChanger pixelColorChanger;
    private RectSelectionPanel rectSelectionPanel;
    private BufferedImage image;

    public ImageOptionsWindow(QuickDrawPanel owner, BufferedImage image) {
        this.owner = owner;
        this.image = image;
        addWindowListener(new WindowEventsListener());
        createWindowContents();
        setFrameConfiguration();
        setVisible(false);
        pixelColorChanger.setElementsVisibility(true);
        rectSelectionPanel.setElementsVisibility(true);
    }

    private void createWindowContents() {
        panel = createWindowPanel();
        pointerLabel = createPointerPosLabel();
        pixelColorChanger = new PixelColorChanger(owner);
        rectSelectionPanel = new RectSelectionPanel(this);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        pointerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(pointerLabel);
        panel.add(pixelColorChanger);

//        rectSelectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
//        rectSelectionPanel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
//        panel.add(rectSelectionPanel);

    }

    private JLabel createPointerPosLabel() {
        return new JLabel();
    }

    private JPanel createWindowPanel() {
        JPanel newPanel = new JPanel();
        add(newPanel);
        return newPanel;
    }

    private void setFrameConfiguration() {
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public void setPointerLabelValues(Point point) {
        if (point != null) {
            String msg = "X: " + String.valueOf((int) point.getX());
            msg += ", Y: " + String.valueOf((int) point.getY());
            pointerLabel.setText(msg);
        } else {
            pointerLabel.setText("X: -, Y: -");
        }
    }

    public void updateRGBSliders(MouseEvent e) {
        int rgbPoint = image.getRGB(e.getX(), e.getY());
        int R = (rgbPoint & 0x00FF0000) >>> 16;
        int G = (rgbPoint & 0x0000FF00) >>> 8;
        int B = (rgbPoint & 0x000000FF);
        pixelColorChanger.setPixelValues(e.getPoint(), R, G, B);

    }

    public void updateRectangleSelection(Rectangle rectToDraw) {
        int[] avgColorValues = getAvgColorsInSelection(rectToDraw);
        rectSelectionPanel.setAreaLabelValue(rectToDraw.width * rectToDraw.height);
        rectSelectionPanel.setAvgLabelValue(avgColorValues[0], avgColorValues[1], avgColorValues[2]);
    }

    private int[] getAvgColorsInSelection(Rectangle rectToDraw) {
        int totalWidth = rectToDraw.x + rectToDraw.width;
        int totalHeight = rectToDraw.y + rectToDraw.height;
        int totalPixels = totalWidth * totalHeight;

        long r_total = 0, b_total = 0, g_total = 0;

        int rgb = 0;

        for (int x = rectToDraw.x; x < totalWidth ; x++) {
            for (int y = rectToDraw.y; y < totalHeight; y++) {
                rgb = image.getRGB(x, y);
                r_total += getRed(rgb);
                g_total += getGreen(rgb);
                b_total += getBlue(rgb);
            }
        }
        r_total = r_total / totalPixels;
        g_total = g_total / totalPixels;
        b_total = b_total / totalPixels;
        int[] values = {(int) r_total, (int) g_total, (int) b_total};
        return values;
    }

    static public int getRed(int rgb) {
        return (rgb & 0x00FF0000) >>> 16;
    }

    static public int getBlue(int rgb) {
        return (rgb & 0x0000FF00) >>> 8;
    }

    static public int getGreen(int rgb) {
        return rgb & 0x000000FF;
    }

    public void toggleVisibility() {
        if (isVisible()) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

    public void changeImage(QuickDrawPanel quickDrawPanel) {
        this.owner = quickDrawPanel;
        pixelColorChanger.changeImage(quickDrawPanel);
    }

    private class WindowEventsListener extends WindowAdapter {

        @Override
        public void windowClosed(WindowEvent e) {
            ImageOptionsWindow.this.setVisible(false);
        }

    }
}
