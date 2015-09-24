package ar.com.itba.image_actions.masks;

import ar.com.itba.utils.CustomBufferedImage;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;


public class MedianMask extends AbstractMask implements ImageMask {

    public MedianMask(int n) {
        super(n);
    }

    @Override
    public void applyMask(Point p, BufferedImage oldImg, BufferedImage newImg) {
        CustomBufferedImage customImg = (CustomBufferedImage) newImg;
        int x = (int) p.getX(), y = (int) p.getY();
        ArrayList<Integer> maskPoints = new ArrayList<Integer>();

        int count = 0;
        for (int w = x - radius; w <= x + radius; w++, count++) {
            for (int h = y - radius; h <= y + radius; h++, count++) {
                maskPoints.add(BYTE_MASK & oldImg.getRGB(w, h));
            }
        }

        maskPoints.sort(Comparator.naturalOrder());
        setPixel(x, y, newImg, maskPoints.get(n * n / 2));
    }
}
