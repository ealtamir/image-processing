package ar.com.itba.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 06.09.15.
 */
public class CustomBufferedImage extends BufferedImage {

    private int redMax;
    private int redMin;
    private int[] red;

    private int greenMax;
    private int greenMin;
    private int[] green;

    private int blueMax;
    private int blueMin;
    private int[] blue;

    private final int RED_MASK = 0x00FF0000;
    private final int GREEN_MASK = 0x0000FF00;
    private final int BLUE_MASK = 0x000000FF;

    public CustomBufferedImage(int width, int height, int imageType) {
        super(width, height, imageType);
        red = new int[width * height];
        green = new int[width * height];
        blue = new int[width * height];
    }

    public CustomBufferedImage(BufferedImage img) {
        super(img.getWidth(), img.getHeight(), img.getType());
        red = new int[img.getWidth() * img.getHeight()];
        green = new int[img.getWidth() * img.getHeight()];
        blue = new int[img.getWidth() * img.getHeight()];
        loadColorBuffers(img);
    }

    private void loadColorBuffers(BufferedImage img) {
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                setRGB(x, y, img.getRGB(x, y));
            }
        }
    }

    @Override
    public void setRGB(int x, int y, int rgb) {
        super.setRGB(x, y, rgb);
        int r = RED_MASK & rgb >>> 16;
        int g = GREEN_MASK & rgb >>> 8;
        int b = BLUE_MASK & rgb;
        red[getWidth() * x + y] = r;
        green[getWidth() * x + y] = g;
        blue[getWidth() * x + y] = b;
        updateMaxMinValues(r, g, b);
    }

    public void setRGBCustom(int x, int y, int r, int g, int b) {
        red[getWidth() * x + y] = r;
        green[getWidth() * x + y] = g;
        blue[getWidth() * x + y] = b;
        updateMaxMinValues(r, g, b);
    }

    public void applyLinearTransform() {
        Color color;
        for (int x = 0, i = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                i = x * getWidth() + y;
                color = new Color(linearTransform(red[i], redMin, redMax),
                        linearTransform(green[i], greenMin, greenMax),
                        linearTransform(blue[i], blueMin, blueMax));
                setRGB(x, y, color.getRGB());
            }
        }
    }

    private int linearTransform(int x, int min, int max) {
        return 255 * ((x - min) / (max - min));
    }

    private void updateMaxMinValues(int r, int g, int b) {
        if (r > redMax) {
            redMax = r;
        } else if (r < redMin) {
            redMin = r;
        }

        if (g > greenMax) {
            greenMax = g;
        } else if (g < greenMin) {
            greenMin = g;
        }

        if (b > blueMax) {
            blueMax = b;
        } else if (b < greenMin) {
            blueMin = b;
        }
    }

}
