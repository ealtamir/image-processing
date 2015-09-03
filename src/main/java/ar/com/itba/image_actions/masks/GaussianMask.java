package ar.com.itba.image_actions.masks;

import java.awt.*;
import java.awt.image.BufferedImage;


public class GaussianMask extends AbstractMask {

    private final double deviation;

    public GaussianMask(int n, double dev) {
        super(n);
        if (dev <= 0.1 && dev > 0.001) {
            n = 15;
        } else if (dev <= 0.001 && dev > 0.0001) {
            n = 9;
        } else if (dev <= 0.00001 && dev > 0.000001) {
            n = 5;
        }
        deviation = dev;
    }

    @Override
    public void applyMask(Point p, BufferedImage oldImg, BufferedImage newImg) {
        int x = (int) p.getX(), y = (int) p.getY();


        double val = 0;
        double avg = 0;
        double div = 0;
        for (int w = x - radius, i = -radius; w < x + radius; w++, i++) {
            for (int h = y - radius, j = -radius; h < y + radius; h++, j++) {
                val = getGaussianValue(i, j);
                avg += val * (BYTE_MASK & oldImg.getRGB(w, h));
                div += val;
            }
        }
        setPixel(x, y, newImg, BYTE_MASK & (int) avg);
    }

    private double getGaussianValue(int x, int y) {
        return 1 / (Math.sqrt(2 * Math.PI) * deviation) * Math.exp(-(x * x + y * y) / (deviation * deviation));
    }
}
