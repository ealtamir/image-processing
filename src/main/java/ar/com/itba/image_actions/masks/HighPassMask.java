package ar.com.itba.image_actions.masks;

import java.awt.*;
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
        for (int w = x - radius; w < x + radius; w++) {
            for (int h = y - radius; h < y + radius; h++) {
                rgb = BYTE_MASK & oldImg.getRGB(w, h);
                if (w != h) {
                    avg += rgb * -1;
                } else {
                    avg += rgb * 8;
                }
            }
        }

        avg = (int) avg / (n * n);
        setPixel(x, y, newImg, avg);
    }
}
