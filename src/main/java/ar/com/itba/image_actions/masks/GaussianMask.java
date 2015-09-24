package ar.com.itba.image_actions.masks;

import ar.com.itba.utils.CustomBufferedImage;

import java.awt.Point;
import java.awt.image.BufferedImage;


public class GaussianMask extends AbstractMask {

    private final double deviation;

    public GaussianMask(int n, double dev) {
        super(n);
        if (dev <= 1) {
            updateRadiusValue(3);
        } else if (dev >= 7) {
            updateRadiusValue(20);
        } else {
            updateRadiusValue((int) dev * 2 + 1);
        }
        deviation = dev;
    }

    @Override
    public void applyMask(Point p, BufferedImage oldImg, BufferedImage newImg) {
        int x = (int) p.getX(), y = (int) p.getY();

        double val = 0;
        double avg = 0;
        double div = 0;
        for (int w = x - radius, i = -radius; w <= x + radius; w++, i++) {
            for (int h = y - radius, j = -radius; h <= y + radius; h++, j++) {
                val = getGaussianValue(i, j);
                avg += val * (BYTE_MASK & oldImg.getRGB(w, h));
                div += val;
            }
        }
        int result = BYTE_MASK & (int) (avg / div);
        setPixel(x, y, newImg, result << 16 | result << 8 | result);
    }

    private double getGaussianValue(int x, int y) {
        return 1 / (2 * Math.PI * (deviation * deviation)) * Math.exp(-(x * x + y * y) / (deviation * deviation));
    }
}
