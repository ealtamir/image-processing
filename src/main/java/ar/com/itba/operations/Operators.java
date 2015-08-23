package ar.com.itba.operations;

import java.awt.image.BufferedImage;

public class Operators {

    static private final int RED_MASK = 0x00FF0000;
    static private final int GREEN_MASK = 0x0000FF00;
    static private final int BLUE_MASK = 0x000000FF;

    static public BufferedImage imageScalarMult(float c, BufferedImage img) {
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        int buffer = 0;
        for (int width = 0; width < img.getWidth(); width++) {
            for (int height = 0; height < img.getHeight(); height++) {
                buffer = multiplyPixelByScalar(c, img.getRGB(width, height));
                result.setRGB(width, height, buffer);
            }
        }
        return result;
    }

    private static int multiplyPixelByScalar(float c, int rgb) {
        int r = (int) ((RED_MASK & rgb) * c) & RED_MASK;
        int g = (int) ((GREEN_MASK & rgb) * c) & GREEN_MASK;
        int b = (int) ((BLUE_MASK & rgb) * c) & BLUE_MASK;
        return r | g | b;
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
            int r = RED_MASK & ((RED_MASK & pixel1) + (RED_MASK & pixel2));
            int g = GREEN_MASK & ((GREEN_MASK & pixel1) + (GREEN_MASK & pixel2));
            int b = BLUE_MASK & ((BLUE_MASK & pixel1) + (BLUE_MASK & pixel2));
            return r | g | b;
        }
    }

    static private class ImageSubstraction implements ImageOperation {

        @Override
        public int applyOperation(int pixel1, int pixel2) {
            int r = RED_MASK & ((RED_MASK & pixel1) - (RED_MASK & pixel2));
            int g = GREEN_MASK & ((GREEN_MASK & pixel1) - (GREEN_MASK & pixel2));
            int b = BLUE_MASK & ((BLUE_MASK & pixel1) - (BLUE_MASK & pixel2));
            return r | g | b;
        }
    }

    static private class ImageMultiplication implements ImageOperation {

        @Override
        public int applyOperation(int pixel1, int pixel2) {
            int r = RED_MASK & ((RED_MASK & pixel1) * (RED_MASK & pixel2));
            int g = GREEN_MASK & ((GREEN_MASK & pixel1) * (GREEN_MASK & pixel2));
            int b = BLUE_MASK & ((BLUE_MASK & pixel1) * (BLUE_MASK & pixel2));
            return r | g | b;
        }
    }

}
