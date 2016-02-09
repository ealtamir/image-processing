package ar.com.itba.image_actions;

import ar.com.itba.utils.CustomBufferedImage;

import java.awt.*;

/**
 * Created by Enzo on 14.12.15.
 */
public class Clustering {

    private final static double EPSILON = 0.001;
    private static final int MAX_CONVERGENCE_ITERS = 100;


    public static CustomBufferedImage meanShiftClustering(CustomBufferedImage img, int radius, int bandwidth) {
        CustomBufferedImage newImg = new CustomBufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int[][][] distribution = new int[256][256][256];
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
                newImg.setRGBCustom(w, h, newRGB[0], newRGB[1], newRGB[2]);
            }
        }
        newImg.applyLinearTransform();

        return newImg;
    }

    private static int[] findMean(int[] rgb, int[][][] distribution, int radius, int bandwidth) {
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

    private static int[] getConvergencePoint(int[] rgb, int[][][] distribution, int radius, int bandwidth) {
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

    private static double getWeight(int[] point, int[] new_point, int bandwidth) {
        double euclidean_dist = euclideanDist(point, new_point);
        return Math.exp(-(euclidean_dist * euclidean_dist) / (bandwidth * bandwidth));
    }

    private static double euclideanDist(int[] point, int[] new_point) {
        return Math.sqrt(Math.pow(point[0] - new_point[0], 2) +
                Math.pow(point[1] - new_point[1], 2) +
                Math.pow(point[2] - new_point[2], 2));
    }
}
