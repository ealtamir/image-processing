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

    public GaussianMask(int radius) {
        super(radius);
        deviation = 2;
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

    public void applyMask(CustomBufferedImage img) {
        int r, g, b;

        double val = 0, div = 0;
        double avgR = 0, avgG = 0, avgB = 0;
        for (int y = radius; y + radius < img.getHeight(); y++) {
            for (int x = radius; x + radius < img.getWidth(); x++) {

                for(int h = -radius; h <= radius; h++) {
                    for (int w = -radius; w <= radius; w++) {
                        val = getGaussianValue(w, h);
                        avgR += val * img.getRed(w + x, h + y);
                        avgG += val * img.getGreen(w + x, h + y);
                        avgB += val * img.getBlue(w + x, h + y);
                    }
                }

                r = (int) (avgR / div);
                g = (int) (avgG / div);
                b = (int) (avgB / div);
                img.setRGBCustom(x, y, r, g, b);
            }
        }
    }

    public double applyMask(Point p, int[][] phi) {
        int x = (int) p.getX(), y = (int) p.getY();
        int width = phi.length;
        int height = phi[0].length;

        if (x - radius < 0 || x + radius >= width || y - radius < 0 || y + radius >= height)
            return 0;

        double val = 0;
        double avg = 0;
        double div = 0;
        for (int w = x - radius, i = -radius; w <= x + radius; w++, i++) {
            for (int h = y - radius, j = -radius; h <= y + radius; h++, j++) {
                val = getGaussianValue(i, j);
                avg += val * phi[w][h];
                div += val;
            }
        }
        return Math.signum(avg / div);
    }

    private double getGaussianValue(int x, int y) {
        return 1 / (2 * Math.PI * (deviation * deviation)) * Math.exp(-(x * x + y * y) / (deviation * deviation));
    }
}
