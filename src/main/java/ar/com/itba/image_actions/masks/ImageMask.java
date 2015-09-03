package ar.com.itba.image_actions.masks;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 02.09.15.
 */
public interface ImageMask {

    public void applyMask(Point p, BufferedImage oldImg, BufferedImage newImg);

    public int getMaskDimension();
}
