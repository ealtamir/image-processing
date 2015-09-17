package ar.com.itba.image_actions.operations;

import java.awt.image.BufferedImage;

import ar.com.itba.utils.CustomBufferedImage;

public class Operators {

    static private final int RED_MASK = 0x00FF0000;
    static private final int GREEN_MASK = 0x0000FF00;
    static private final int BLUE_MASK = 0x000000FF;
    static private final int BYTE_MASK = BLUE_MASK;

    static public BufferedImage imageScalarMult(float c, BufferedImage img) {
        CustomBufferedImage result = new CustomBufferedImage(img.getWidth(), img.getHeight(), img.getType());

        CustomBufferedImage oldImg = (CustomBufferedImage) img;
        int r, g, b;
        int buffer = 0;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                r = (int) (oldImg.getRed(x, y) * c);
                g = (int) (oldImg.getGreen(x, y) * c);
                b = (int) (oldImg.getBlue(x, y) * c);
                result.setRGBCustom(x, y, r, g, b);
            }
        }
        result.applyLinearTransform();
        return result;
    }

    private static int multiplyPixelByScalar(float c, int pixel) {
        int r = (RED_MASK & pixel) >>> 16;
        int g = (GREEN_MASK & pixel) >>> 8;
        int b = (BLUE_MASK & pixel);

        float r1 = c * r;
        float g1 = c * g;
        float b1 = c * b;

        if (r1 < 255.0) {
            r = BYTE_MASK & ((int) r1);
        } else {
            r = 255;
        }
        if (g1 < 255.0) {
            g = BYTE_MASK & ((int) g1);
        } else {
            g = 255;
        }
        if (b1 < 255.0) {
            b = BYTE_MASK & ((int) b1);
        } else {
            b = 255;
        }
        return r << 16 | g << 8 | b;
    }

    static public BufferedImage imageAddition(BufferedImage img1, BufferedImage img2) {
        return imageOperation("addition", img1, img2);
    }

    static public BufferedImage imageSubstraction(BufferedImage img1, BufferedImage img2) {
        return imageOperation("substraction", img1, img2);
    }

    static public BufferedImage imageMultiplication(BufferedImage img1, BufferedImage img2) {
        return imageOperation("multiplication", img1, img2);
    }

    static private BufferedImage imageOperation(String op, BufferedImage img1, BufferedImage img2) {
        int minWidth = Math.min(img1.getWidth(), img2.getWidth());
        int minHeight = Math.min(img1.getHeight(), img2.getHeight());
        int buffer = 0;

        BufferedImage resultImg = new BufferedImage(minWidth, minHeight, img1.getType());

        ImageOperation operation = chooseImageOperation(op);

        for (int width = 0; width < minWidth; width++) {
            for (int height = 0; height < minHeight; height++) {
                buffer = operation.applyOperation(img1.getRGB(width, height), img2.getRGB(width, height));
                resultImg.setRGB(width, height, buffer);
            }
        }
        return resultImg;
    }

    private static ImageOperation chooseImageOperation(String op) {
        if (op.equals("addition")) {
            return new ImageAddition();
        } else if (op.equals("substraction")) {
            return new ImageSubstraction();
        } else if (op.equals("multiplication")) {
            return new ImageMultiplication();
        } else {
            throw new IllegalArgumentException();
        }
    }

    static private class ImageAddition implements ImageOperation {

        @Override
        public int applyOperation(int pixel1, int pixel2) {
            int r1 = (RED_MASK & pixel1) >>> 16, r2 = (RED_MASK & pixel2) >>> 16;
            int g1 = (GREEN_MASK & pixel1) >>> 8, g2 = (GREEN_MASK & pixel2) >>> 8;
            int b1 = (BLUE_MASK & pixel1), b2 = (BLUE_MASK & pixel2);
            int r, g, b;
            if (r1 + r2 < 255) {
                r = BYTE_MASK & (r1 + r2);
            } else {
                r = 255;
            }
            if (g2 + g2 < 255) {
                g = BYTE_MASK & (g1 + g2);
            } else {
                g = 255;
            }
            if (b1 + b2 < 255) {
                b = BYTE_MASK & (b1 + b2);
            } else {
                b = 255;
            }
            return r << 16 | g << 8 | b;
        }
    }

    static private class ImageSubstraction implements ImageOperation {

        @Override
        public int applyOperation(int pixel1, int pixel2) {
            int r1 = (RED_MASK & pixel1) >>> 16, r2 = (RED_MASK & pixel2) >>> 16;
            int g1 = (GREEN_MASK & pixel1) >>> 8, g2 = (GREEN_MASK & pixel2) >>> 8;
            int b1 = (BLUE_MASK & pixel1), b2 = (BLUE_MASK & pixel2);
            int r, g, b;
            if (r1 - r2 > 0) {
                r = BYTE_MASK & (r1 - r2);
            } else {
                r = 0;
            }
            if (g2 - g2 > 0) {
                g = BYTE_MASK & (g1 - g2);
            } else {
                g = 0;
            }
            if (b1 - b2 > 0) {
                b = BYTE_MASK & (b1 - b2);
            } else {
                b = 0;
            }
            return r << 16 | g << 8 | b;
        }
    }

    static private class ImageMultiplication implements ImageOperation {

        @Override
        public int applyOperation(int pixel1, int pixel2) {
            int r1 = (RED_MASK & pixel1) >>> 16, r2 = (RED_MASK & pixel2) >>> 16;
            int g1 = (GREEN_MASK & pixel1) >>> 8, g2 = (GREEN_MASK & pixel2) >>> 8;
            int b1 = (BLUE_MASK & pixel1), b2 = (BLUE_MASK & pixel2);
            int r, g, b;
            if (r1 * r2 < 255) {
                r = BYTE_MASK & (r1 * r2);
            } else {
                r = 255;
            }
            if (g2 * g2 < 255) {
                g = BYTE_MASK & (g1 * g2);
            } else {
                g = 255;
            }
            if (b1 * b2 < 255) {
                b = BYTE_MASK & (b1 * b2);
            } else {
                b = 255;
            }
            return r << 16 | g << 8 | b;
        }
    }

}
