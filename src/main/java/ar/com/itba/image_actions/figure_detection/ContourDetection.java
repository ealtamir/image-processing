package ar.com.itba.image_actions.figure_detection;

import ar.com.itba.image_actions.masks.GaussianMask;
import ar.com.itba.utils.CustomBufferedImage;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;

import java.awt.Point;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * Created by Enzo on 15.10.15.
 */
public class ContourDetection {

    private static int maskSize = 7;
    private static int sigma = 2;
    private static int KColors = 1;
    private static int maxIters = 15;

    static public BufferedImage detectContour(BufferedImage img, Rectangle initialContour) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        CustomBufferedImage newImg = new CustomBufferedImage(customImg);
        int[] avgRGB = getAvgColor(customImg, initialContour);
        int[][] phi = getPhiFunction(customImg, initialContour);
        HashMap<Point, Boolean> lin = new HashMap<Point, Boolean>();
        HashMap<Point, Boolean> lout = new HashMap<Point, Boolean>();
        initBoundaryMaps(lin, lout, phi);
        if (avgRGB[0] != avgRGB[1] || avgRGB[1] != avgRGB[2] || avgRGB[0] != avgRGB[2]) {
            KColors = 3;
        } else {
            KColors = 1;
        }

        for (Point point : lin.keySet()) {
            phi[point.x][point.y] = -1;
        }
        for (Point point : lout.keySet()) {
            phi[point.x][point.y] = 1;
        }
        boolean stop = false;
        for (int i = 0; i < maxIters; i++) {
            stop = first_cycle(avgRGB, lin, lout, phi, newImg);
            stop &= second_cycle(lin, lout, phi, newImg);
        }
        drawContour(newImg, lin, lout);
        return newImg;
    }

//    static public BufferedImage detectContour(BufferedImage img, int[][] phi) {
//        CustomBufferedImage customImg = (CustomBufferedImage) img;
//        CustomBufferedImage newImg = new CustomBufferedImage(customImg);
//        int[] avgRGB = new int[3];
//        initBoundaryMaps(lin, lout, phi);
//
//        for (Point point : lin.keySet()) {
//            phi[point.x][point.y] = -1;
//        }
//        for (Point point : lout.keySet()) {
//            phi[point.x][point.y] = 1;
//        }
//        boolean stop = false;
//        for (int i = 0; i < maxIters; i++) {
//            stop = first_cycle(avgRGB, lin, lout, phi, newImg);
//            stop &= second_cycle(lin, lout, phi, newImg);
//        }
//        drawContour(newImg, lin, lout);
//        return newImg;
//    }

    private static int[][] getPhiFunction(CustomBufferedImage customImg,
                                          HashMap<Point, Boolean> lin,
                                          HashMap<Point, Boolean> lout) {
        return new int[0][];
    }

    private static void drawContour(CustomBufferedImage img,
                                    HashMap<Point, Boolean> lin,
                                    HashMap<Point, Boolean> lout ) {
        img.resetRGBBuffer();
        for (Point p : lin.keySet()) {
            img.setRGB(p.x, p.y, Color.BLUE.getRGB());
        }
        for (Point p : lout.keySet()) {
            img.setRGB(p.x, p.y, Color.GREEN.getRGB());
        }
    }

    private static boolean first_cycle(int[] avgRGB, HashMap<Point, Boolean> lin, HashMap<Point, Boolean> lout, int[][] phi,
                                    CustomBufferedImage img) {
        int NA = 20;
        boolean stop = false;
        List<Point> pointsToExpand = new ArrayList<Point>();
        List<Point> pointsToContract = new ArrayList<Point>();
        for (int i = 0; i < NA; i++) {
            pointsToExpand.clear();
            pointsToContract.clear();
            for (Point p: lout.keySet()) {
                if (calculateProbability(p, avgRGB, img) > 0) {
                    pointsToExpand.add(p);
                }
            }
            for (Point point : pointsToExpand) {
                expandBoundary(point, lin, lout, phi);
            }
            stop = pointsToExpand.isEmpty();
            updateInBoundary(lin, phi);

            for (Point p: lin.keySet()) {
                if (calculateProbability(p, avgRGB, img) < 0) {
                    pointsToContract.add(p);
                }
            }
            for (Point point : pointsToContract) {
                contractBoundary(point, lin, lout, phi);
            }
            stop |= pointsToContract.isEmpty();
            updateOutBoundary(lout, phi);
        }
        return stop;
    }

    private static boolean second_cycle(HashMap<Point, Boolean> lin,
                                    HashMap<Point, Boolean> lout,
                                    int[][] phi,
                                    CustomBufferedImage img) {
        boolean stop = false;
        GaussianMask mask = new GaussianMask(maskSize, sigma);
        Set<Point> pointsToExpand = new HashSet<Point>();
        Set<Point> pointsToContract = new HashSet<Point>();
        for (int i = 0; i < maskSize; i++) {
            pointsToExpand.clear();
            pointsToContract.clear();
            for (Point p : lout.keySet()) {
                if (mask.applyMask(p, phi) < 0)
                    pointsToExpand.add(p);
            }
            for (Point point : pointsToExpand)
                expandBoundary(point, lin, lout, phi);
            stop = pointsToExpand.isEmpty();
            updateInBoundary(lin, phi);

            for (Point p : lin.keySet()) {
                if (mask.applyMask(p, phi) > 0)
                    pointsToContract.add(p);
            }
            for (Point point : pointsToContract)
                contractBoundary(point, lin, lout, phi);
            stop |= pointsToContract.isEmpty();
            updateOutBoundary(lout, phi);
        }
        return stop;
    }

    private static double calculateProbability(Point p, int[] avgRGB, CustomBufferedImage img) {
        int r = img.getRed(p);
        int g = img.getGreen(p);
        int b = img.getBlue(p);
        double normVal = Math.sqrt((avgRGB[0] - r) * (avgRGB[0] - r) + (avgRGB[1] - g) * (avgRGB[1] - g) + (avgRGB[2] - b) * (avgRGB[2] - b));
        double finalVal = normVal / (256 * KColors);
        return (finalVal < 0.5)? 1: -1;
    }

    private static void updateOutBoundary(HashMap<Point, Boolean> lout, int[][] phi) {
        List<Point> updatePoints = new ArrayList<Point>();
        for (Point p: lout.keySet()) {
            if (!hasInteriorNeighbor(p.x, p.y, phi)) {
                updatePoints.add(p);
            }
        }
        for (Point p : updatePoints) {
            phi[p.x][p.y] = 3;
            lout.remove(p);
        }
    }

    private static void updateInBoundary(HashMap<Point, Boolean> lin, int[][] phi) {
        List<Point> updatePoints = new ArrayList<Point>();
        for (Point p: lin.keySet()) {
            if (!hasExteriorNeighbor(p.x, p.y, phi)) {
                updatePoints.add(p);
            }
        }
        for (Point p : updatePoints) {
            phi[p.x][p.y] = -3;
            lin.remove(p);
        }
    }

    private static void expandBoundary(Point p, HashMap<Point, Boolean> lin, HashMap<Point, Boolean> lout, int[][] phi) {
        if (!lout.containsKey(p))
            throw new IllegalArgumentException("point must be contained in lout");

        phi[p.x][p.y] = -1;
        switchMaps(p, lout, lin);
        Point pCheck = new Point();

        pCheck.setLocation(p.x - 1, p.y);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = 1;
            lout.put((Point) pCheck.clone(), true);
        }

        pCheck.setLocation(p.x, p.y - 1);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = 1;
            lout.put((Point) pCheck.clone(), true);
        }

        pCheck.setLocation(p.x + 1, p.y);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = 1;
            lout.put((Point) pCheck.clone(), true);
        }

        pCheck.setLocation(p.x, p.y + 1);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = 1;
            lout.put((Point) pCheck.clone(), true);
        }
    }

    private static void switchMaps(Point p, HashMap<Point, Boolean> from, HashMap<Point, Boolean> to) {
        from.remove(p);
        to.put(p, true);
    }

    private static void contractBoundary(Point p, HashMap<Point, Boolean> lin, HashMap<Point, Boolean> lout, int[][] phi) {
        if (!lin.containsKey(p))
            throw new IllegalArgumentException("point must be contained in lin");

        phi[p.x][p.y] = 1;
        switchMaps(p, lin, lout);
        Point pCheck = new Point();

        pCheck.setLocation(p.x - 1, p.y);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = -1;
            lin.put((Point) pCheck.clone(), true);
        }

        pCheck.setLocation(p.x, p.y - 1);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = -1;
            lin.put((Point) pCheck.clone(), true);
        }

        pCheck.setLocation(p.x + 1, p.y);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = -1;
            lin.put((Point) pCheck.clone(), true);
        }

        pCheck.setLocation(p.x, p.y + 1);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = -1;
            lin.put((Point) pCheck.clone(), true);
        }

    }

    private static void initBoundaryMaps(HashMap<Point, Boolean> lin, HashMap<Point, Boolean> lout, int[][] phi) {
        for (int x = 0; x < phi.length; x++) {
            for (int y = 0; y < phi[0].length; y++) {
                if (phi[x][y] > 0) {
                    if (hasInteriorNeighbor(x, y, phi)) {
                        lout.put(new Point(x, y), true);
                    }
                } else {
                    if (hasExteriorNeighbor(x, y, phi)) {
                        lin.put(new Point(x, y), true);
                    }
                }
            }
        }
    }

    private static boolean hasInteriorNeighbor(int x, int y, int[][] phi) {
        int width = phi.length;
        int height = phi[0].length;
        if (!(x > 0 && x < width - 1 && y > 0 && y < height - 1)) {
            return false;
        }
        if (phi[x - 1][y] < 0) {
            return true;
        } else if(phi[x][y - 1] < 0) {
            return true;
        } else if (phi[x + 1][y] < 0) {
            return true;
        } else if (phi[x][y + 1] < 0) {
            return true;
        }
        return false;
    }

    private static boolean hasExteriorNeighbor(int x, int y, int[][] phi) {
        int width = phi.length;
        int height = phi[0].length;
        if (!(x > 0 && x < width - 1 && y > 0 && y < height - 1)) {
            return false;
        }
        if (phi[x - 1][y] > 0) {
            return true;
        } else if(phi[x][y - 1] > 0) {
            return true;
        } else if (phi[x + 1][y] > 0) {
            return true;
        } else if (phi[x][y + 1] > 0) {
            return true;
        }
        return false;
    }

    private static int[][] getPhiFunction(CustomBufferedImage customImg, Rectangle initialContour) {
        int[][] phi = new int[customImg.getWidth()][customImg.getHeight()];
        for (int x = 0; x < customImg.getWidth(); x++) {
            for (int y = 0; y < customImg.getHeight(); y++) {
                if (initialContour.contains(x, y)) {
                    phi[x][y] = -3;
                } else {
                    phi[x][y] = 3;
                }
            }
        }
        return phi;
    }

    private static int[] getAvgColor(CustomBufferedImage customImg, Rectangle initialContour) {
        int[] rgb = new int[3];
        int minx = (int) initialContour.getX(), miny = (int) initialContour.getY();
        for (int x = minx; x < minx + initialContour.getWidth(); x++) {
            for (int y = miny; y < miny + initialContour.getHeight(); y++) {
                rgb[0] += customImg.getRed(x, y);
                rgb[1] += customImg.getGreen(x, y);
                rgb[2] += customImg.getBlue(x, y);
            }
        }
        int total = (int) (initialContour.getWidth() * initialContour.getHeight());
        rgb[0] /= total;
        rgb[1] /= total;
        rgb[2] /= total;
        return rgb;
    }
}
