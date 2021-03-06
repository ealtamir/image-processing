package ar.com.itba.image_actions.masks;

import ar.com.itba.utils.CustomBufferedImage;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 02.09.15.
 */
abstract public class AbstractMask implements ImageMask {

    protected final int BYTE_MASK = 0x000000FF;
    protected final int GRAY_LEVELS = 256;
    protected int radius;
    protected int n;

    public AbstractMask(int n ) {
        this.n = n;
        this.radius = (int) n / 2;
    }

    protected void setPixel(int x, int y, BufferedImage img, int grayLevel) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        customImg.setGray(x, y, grayLevel);
    }

    @Override
    public int getMaskDimension() {
        return n;
    }

    protected void updateRadiusValue(int n) {
        this.n = n;
        radius = (int) this.n / 2;
    }
}
