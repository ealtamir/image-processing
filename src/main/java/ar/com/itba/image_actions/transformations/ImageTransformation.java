package ar.com.itba.image_actions.transformations;

import ar.com.itba.utils.CustomBufferedImage;

/**
 * Created by Enzo on 22.08.15.
 */
public interface ImageTransformation {

    public int apply(int x, int y, CustomBufferedImage oldImg, CustomBufferedImage newImg);

    public int apply(int pixel);
}
