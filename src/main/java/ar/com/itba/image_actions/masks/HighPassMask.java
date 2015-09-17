package ar.com.itba.image_actions.masks;

import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 03.09.15.
 */
public class HighPassMask extends AbstractMask implements ImageMask {

    public HighPassMask(int n) {
        super(n);
    }

    @Override
    public void applyMask(Point p, BufferedImage oldImg, BufferedImage newImg) {
        int x = (int) p.getX(), y = (int) p.getY();

        int avg = 0;
        int rgb = 0;
        for (int w = x - radius; w <= x + radius; w++) {
            for (int h = y - radius; h <= y + radius; h++) {
                rgb = BYTE_MASK & oldImg.getRGB(w, h);
                if (w == x && h == y) {
                    avg += rgb * (n * n - 1);
                } else {
                    avg += rgb * -1;
                }
            }
        }

        avg = BYTE_MASK & (int) avg / (n * n);
        setPixel(x, y, newImg, avg << 16 | avg << 8 | avg);
    }
}
