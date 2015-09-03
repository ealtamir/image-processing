package ar.com.itba.image_actions.masks;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 02.09.15.
 */
abstract public class AbstractMask implements ImageMask {

    protected final int BYTE_MASK = 0x000000FF;
    protected final int GRAY_LEVELS = 256;
    protected final int radius;
    protected int n;

    public AbstractMask(int n ) {
        this.n = n;
        this.radius = (int) n / 2;
    }

    protected void setPixel(int x, int y, BufferedImage img, int grayLevel) {
        img.setRGB(x, y, grayLevel << 16 | grayLevel << 8 | grayLevel);
    }

    @Override
    public int getMaskDimension() {
        return n;
    }
}
