package ar.com.itba.image_actions.masks;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 02.09.15.
 */
public class MeanMask extends AbstractMask {


    public MeanMask(int n) {
        super(n);
    }

    @Override
    public void applyMask(Point p, BufferedImage oldImg, BufferedImage newImg) {
        int x = (int) p.getX(), y = (int) p.getY();

        int avg = 0;
        for (int w = x - radius; w <= x + radius; w++) {
            for (int h = y - radius; h <= y + radius; h++) {
                avg += BYTE_MASK & oldImg.getRGB(w, h);
            }
        }
        avg = (int) avg / (n * n);
        newImg.setRGB(x, y, avg << 16 | avg << 8 | avg);
    }
}
