package ar.com.itba.image_actions.figure_detection;

import ar.com.itba.utils.CustomBufferedImage;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enzo on 15.10.15.
 */
public class ContourDetection {

    private static int maskSize = 7;

    static public BufferedImage detectContour(BufferedImage img, Rectangle initialContour) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        int[] avgRGB = getAvgColor(customImg, initialContour);
        int[][] phi = getPhiFunction(customImg, initialContour);
        HashMap<Point, Boolean> lin = new HashMap<Point, Boolean>();
        HashMap<Point, Boolean> lout = new HashMap<Point, Boolean>();
        initBoundaryMaps(lin, lout, phi);

        for (Point point : lin.keySet()) {
            phi[point.x][point.y] = -1;
        }
        for (Point point : lout.keySet()) {
            phi[point.x][point.y] = 1;
        }

        // armar conjuntos Lin Lout y llenar la matriz phi
    }

    private static void first_cycle(int[] avgRGB, HashMap<Point, Boolean> lin,
                                    HashMap<Point, Boolean> lout, int[][] phi,
                                    CustomBufferedImage img) {
        int NA = 20;
        for (int i = 0; i < NA; i++) {
            for (Point p : lout.keySet()) {
                if (calculateProbability(p, avgRGB, img) > 0) {
                    expandBoundary(p, lin, lout, phi);
                }
            }
            updateBoundaries(lin, lout, phi);
            for (Point p : lin.keySet()) {
                if (calculateProbability(p, avgRGB, img) < 0) {
                    contractBoundary(p, lin, lout, phi);
                }
            }
            updateBoundaries(lin, lout, phi);
        }
    }

    private static void second_cycle(HashMap<Point, Boolean> lin,
                                    HashMap<Point, Boolean> lout, int[][] phi,
                                    CustomBufferedImage img) {

    }

    private static double calculateProbability(Point p, int[] avgRGB, CustomBufferedImage img) {
        return 0;
    }

    private static void updateBoundaries(HashMap<Point, Boolean> lin, HashMap<Point, Boolean> lout, int[][] phi) {
        for (Point p: lin.keySet()) {
            if (!hasExteriorNeighbor(p.x, p.y, phi)) {
                phi[p.x][p.y] = -3;
                lin.remove(p);
            }
        }
        for (Point p: lout.keySet()) {
            if (!hasInteriorNeighbor(p.x, p.y, phi)) {
                phi[p.x][p.y] = 3;
                lout.remove(p);
            }
        }
    }

    private static void expandBoundary(Point p, HashMap<Point, Boolean> lin, HashMap<Point, Boolean> lout, int[][] phi) {
        if (!lout.get(p))
            return;

        phi[p.x][p.y] = -1;
        switchMaps(p, lout, lin);
        Point pCheck = new Point();

        pCheck.setLocation(p.x - 1, p.y);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = 1;
            lout.put(pCheck, true);
        }

        pCheck.setLocation(p.x, p.y - 1);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = 1;
            lout.put(pCheck, true);
        }

        pCheck.setLocation(p.x + 1, p.y);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = 1;
            lout.put(pCheck, true);
        }

        pCheck.setLocation(p.x, p.y + 1);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = 1;
            lout.put(pCheck, true);
        }
    }

    private static void switchMaps(Point p, HashMap<Point, Boolean> from, HashMap<Point, Boolean> to) {
        if (!from.get(p))
            return;
        from.remove(p);
        to.put(p, true);
    }

    private static void contractBoundary(Point p, HashMap<Point, Boolean> lin, HashMap<Point, Boolean> lout, int[][] phi) {
        if (!lin.get(p))
            return;

        phi[p.x][p.y] = 1;
        switchMaps(p, lin, lout);
        Point pCheck = new Point();

        pCheck.setLocation(p.x - 1, p.y);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = -1;
            lin.put(pCheck, true);
        }

        pCheck.setLocation(p.x, p.y - 1);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = -1;
            lin.put(pCheck, true);
        }

        pCheck.setLocation(p.x + 1, p.y);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = -1;
            lin.put(pCheck, true);
        }

        pCheck.setLocation(p.x, p.y + 1);
        if (!lout.containsKey(pCheck) && !lin.containsKey(pCheck)) {
            phi[pCheck.x][pCheck.y] = -1;
            lin.put(pCheck, true);
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
        if (!(x > 0 && x < width && y > 0 && y < height)) {
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
        if (!(x > 0 && x < width && y > 0 && y < height)) {
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
        for (int x = 0; x < initialContour.getWidth(); x++) {
            for (int y = 0; y < initialContour.getHeight(); y++) {
                rgb[0] = customImg.getRed(minx + x, miny + y);
                rgb[1] = customImg.getGreen(minx + x, miny + y);
                rgb[2] = customImg.getBlue(minx + x, miny + y);
            }
        }
        int total = (int) (initialContour.getWidth() * initialContour.getHeight());
        rgb[0] /= total;
        rgb[1] /= total;
        rgb[2] /= total;
        return rgb;
    }
}
