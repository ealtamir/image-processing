package ar.com.itba.image_actions;

import ar.com.itba.utils.CustomBufferedImage;
import javafx.geometry.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

/**
 * Created by Enzo on 14.12.15.
 */
public class MeanShiftClustering implements Runnable {

    private final static double EPSILON = 0.001;
    private static final int MAX_CONVERGENCE_ITERS = 100;

    private final int radius;
    private final int bandwidth;
    private int clusterSize = 0;

    private Integer index;

    private CustomBufferedImage newImg;
    private int[][][] distribution;
    private int[][][] newDistribution;

    public MeanShiftClustering(int radius, int bandwidth) {
        this.radius = radius;
        this.bandwidth = bandwidth;
    }


    public CustomBufferedImage meanShiftClustering(CustomBufferedImage img) {
        newImg = new CustomBufferedImage(img.getWidth(), img.getHeight(), img.getType());
        distribution = new int[256][256][256];
        newDistribution = new int[256][256][256];
        int r, g, b;
        int[] rgb = new int[3];
        int[] newRGB;

        for (int h = 0; h < img.getHeight(); h++) {
            for (int w = 0; w < img.getWidth(); w++) {
                r = img.getRed(w, h);
                g = img.getGreen(w, h);
                b = img.getBlue(w, h);
                distribution[r][g][b] += 1;
            }
        }
        for (int h = 0; h < img.getHeight(); h++) {
            for (int w = 0; w < img.getWidth(); w++) {
                rgb[0] = img.getRed(w, h);
                rgb[1] = img.getGreen(w, h);
                rgb[2] = img.getBlue(w, h);
                newRGB = findMean(rgb, distribution, radius, bandwidth);
                newDistribution[newRGB[0]][newRGB[1]][newRGB[2]] += 1;
                newImg.setRGBCustom(w, h, newRGB[0], newRGB[1], newRGB[2]);
            }
        }

        System.out.println("Creating clusters...");
//        HashMap<Integer, Integer> clusters = createClusterMap();
//        if (clusters != null) {
//            int color, newColor;
//            for (int h = 0; h < img.getHeight(); h++) {
//                for (int w = 0; w < img.getWidth(); w++) {
//                    r = img.getRed(w, h);
//                    g = img.getGreen(w, h);
//                    b = img.getBlue(w, h);
//                    color = ((0xFF & r) << 16) | ((0xFF & g) << 8) | (0xFF & b);
//                    newColor = clusters.get(color);
//                    newImg.setRGBCustom(w, h, newColor);
//                }
//            }
//        }
        newImg.applyLinearTransform();
        return newImg;
    }

    private HashMap<Integer, Integer> createClusterMap() {
        ArrayList<Segment> segments = new ArrayList<Segment>();
        HashMap<Integer, Integer> clusterMap = new HashMap<>();
        for (int r = 0; r < 256; r++) {
            for (int g = 0; g < 256; g++) {
                for (int b = 0; b < 256; b++) {
                    if (newDistribution[r][g][b] >= clusterSize) {
                        segments.add(new Segment(r, g, b));
                    }
                }
            }
        }
        if (segments.isEmpty()) {
            return null;
        }
        segments.sort(new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                Segment s1 = (Segment) o1;
                Segment s2 = (Segment) o2;
                if (s1.norm < s2.norm) {
                    return -1;
                } else if (s1.norm > s2.norm) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        int color, newColor;
        for (int r = 0; r < 256; r++) {
            for (int g = 0; g < 256; g++) {
                for (int b = 0; b < 256; b++) {
                    color = ((0xFF & r) << 16) | ((0xFF & g) << 8) | (0xFF & b);
                    if (newDistribution[r][g][b] >= clusterSize) {
                        clusterMap.put(color, color);
                    } else {
                        newColor = findMostSimilarColor(segments, color);
                        clusterMap.put(color, newColor);
                    }
                }
            }
        }
        return clusterMap;
    }

    private int findMostSimilarColor(ArrayList<Segment> segments, int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        double colorNorm = Math.sqrt(r * r + g * g + b * b);
        Segment segment, lastSegment;
        double lastNorm = 1000;
        double first, second;
        lastSegment = segments.get(0);
        for (int i = 0; i < segments.size(); i++) {
            segment = segments.get(i);
            if (segment.norm == colorNorm) {
                return segment.getColor();
            }
            if (lastNorm < colorNorm && segment.norm > colorNorm) {
                first = Math.abs(lastNorm - colorNorm);
                second = Math.abs(segment.norm - colorNorm);
                if (first < second) {
                    return lastSegment.getColor();
                }
                return segment.getColor();
            }
            lastNorm = segment.norm;
            lastSegment = segment;
        }
        return lastSegment.getColor();
    }

    private int[] findMean(int[] rgb, int[][][] distribution, int radius, int bandwidth) {
        double distance;
        int[] original_point = rgb;
        int[] new_point = new int[3];
        int iters = 0;
        do {
            new_point = getConvergencePoint(rgb, distribution, radius, bandwidth);
            distance = euclideanDist(rgb, new_point);
            rgb = new_point;
            iters += 1;
        } while (distance > EPSILON && iters < MAX_CONVERGENCE_ITERS);
        return new_point;
    }

    private int[] getConvergencePoint(int[] rgb, int[][][] distribution, int radius, int bandwidth) {
        int r = rgb[0], g = rgb[1], b = rgb[2];
        int[] point = rgb;
        int[] new_point = new int[3];
        double[] result = new double[3];
        int multiplier = 1;


        double weight = 0, total_weight = 0;
        double result_value = 0;
        for (int ri = r - radius; ri < r + radius && ri < 256; ri++) {
            if (ri < 0) continue;

            for (int gi = g - radius; gi < g + radius && gi < 256; gi++) {
                if (gi < 0) continue;

                for (int bi = b - radius; bi < b + radius && bi < 256; bi++) {
                    if (bi < 0) continue;

                    if (distribution[ri][gi][bi] == 0) {
                        continue;
                    }
                    multiplier = distribution[ri][gi][bi];

                    new_point[0] = ri;
                    new_point[1] = gi;
                    new_point[2] = bi;

                    weight = getWeight(point, new_point, bandwidth);
                    result[0] += ri * weight * multiplier;
                    result[1] += gi * weight * multiplier;
                    result[2] += bi * weight * multiplier;
                    total_weight += weight * multiplier;
                }
            }
        }
        result[0] /= total_weight;
        result[1] /= total_weight;
        result[2] /= total_weight;
        new_point[0] = (int) Math.round(result[0]);
        new_point[1] = (int) Math.round(result[1]);
        new_point[2] = (int) Math.round(result[2]);
        return new_point;
    }

    private double getWeight(int[] point, int[] new_point, int bandwidth) {
        double euclidean_dist = euclideanDist(point, new_point);
        return Math.exp(-(euclidean_dist * euclidean_dist) / (bandwidth * bandwidth));
    }

    private double euclideanDist(int[] point, int[] new_point) {
        return Math.sqrt(Math.pow(point[0] - new_point[0], 2) +
                Math.pow(point[1] - new_point[1], 2) +
                Math.pow(point[2] - new_point[2], 2));
    }

    static private class Segment {

        public final double norm;
        public final int r, g, b;
        private int color;

        public Segment(int r, int g, int b) {
            norm = Math.sqrt(r * r + g * g + b * b);
            this.r = r;
            this.g = g;
            this.b = b;
        }

        public int getColor() {
            return ((0xFF & r) << 16) | ((0xFF & g) << 8) | (0xFF & b);
        }
    }

    @Override
    public void run() {

    }
}
