package ar.com.itba.image_actions.corner_detection;

import ar.com.itba.image_actions.edge_detection.SobelEdgeDetection;
import ar.com.itba.image_actions.masks.GaussianMask;
import ar.com.itba.utils.CustomBufferedImage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Enzo on 09.11.15.
 */
public class HarrisCornerDetection {

    private static final double K = 0.04;
    private static final double COEFF = 0.60;

    public static BufferedImage harrisCornerDetection(BufferedImage img) {
        CustomBufferedImage newImg = new CustomBufferedImage(img);
        CustomBufferedImage sobelIx = (CustomBufferedImage) SobelEdgeDetection.horizontalEdgeDetection(newImg);
        CustomBufferedImage sobelIy = (CustomBufferedImage) SobelEdgeDetection.verticalEdgeDetection(newImg);
        CustomBufferedImage Ix2 = multiplyImages(sobelIx, sobelIx);
        CustomBufferedImage Iy2 = multiplyImages(sobelIy, sobelIy);
        CustomBufferedImage Ixy = multiplyImages(sobelIx, sobelIy);

        double[][][] result = new double[img.getHeight()][img.getWidth()][3];
        double[] max = new double[3];


        double r, g, b;
        double value;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                value = (Ix2.getRed(x, y) + Iy2.getRed(x, y));
                r = ((Ix2.getRed(x, y) * Iy2.getRed(x, y) - Ixy.getRed(x, y) * Ixy.getRed(x, y)) - K * value * value);

                value = (Ix2.getGreen(x, y) + Iy2.getGreen(x, y));
                g = ((Ix2.getGreen(x, y) * Iy2.getGreen(x, y) - Ixy.getGreen(x, y) * Ixy.getGreen(x, y)) - K * value * value);

                value = (Ix2.getBlue(x, y) + Iy2.getBlue(x, y));
                b = ((Ix2.getBlue(x, y) * Iy2.getBlue(x, y) - Ixy.getBlue(x, y) * Ixy.getBlue(x, y)) - K * value * value);

                result[y][x][0] = r;
                result[y][x][1] = g;
                result[y][x][2] = b;
//                System.out.println(String.format("red: %f, green: %f, blue: %f", r, g, b));
                if (max[0] < r) {
                    max[0] = r;
                }
                if (max[1] < g) {
                    max[1] = g;
                }
                if (max[2] < b) {
                    max[2] = b;
                }
            }
        }

        ArrayList<Point> corners = new ArrayList<Point>();
        for (int y = 0; y < result.length; y++) {
            for (int x = 0; x < result[0].length; x++) {
                r = result[y][x][0];
                g = result[y][x][1];
                b = result[y][x][2];
                if (r >= COEFF * max[0] || g >= COEFF * max[1] || b >= COEFF * max[2]) {
                    corners.add(new Point(x, y));
                }
            }
        }

        Graphics2D g2 = newImg.createGraphics();
        g2.setColor(Color.CYAN);
        for (Point p : corners) {
            g2.drawOval(p.x - 2, p.y - 2, 4, 4);
        }
        g2.dispose();
        return newImg;
    }

    private static CustomBufferedImage multiplyImages(CustomBufferedImage img1, CustomBufferedImage img2) {
        CustomBufferedImage newImg = new CustomBufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());
        int r, g, b;
        int r1, g1, b1;
        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                r = img1.getRed(x, y);
                g = img1.getGreen(x, y);
                b = img1.getBlue(x, y);
                r1 = img2.getRed(x, y);
                g1 = img2.getGreen(x, y);
                b1 = img2.getBlue(x, y);
                newImg.setRGBCustom(x, y, r * r1, g * g1, b * b1);
            }
        }
        GaussianMask mask = new GaussianMask(3);
        mask.applyMask(newImg);
        return newImg;
    }
}
