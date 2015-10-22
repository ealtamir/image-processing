package ar.com.itba.image_actions.figure_detection;

import ar.com.itba.image_actions.Thresholding.OtsuThreshold;
import ar.com.itba.image_actions.operations.Operators;
import ar.com.itba.utils.CustomBufferedImage;

import javax.print.DocFlavor;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 14.10.15.
 */
public class HoughFigureDetection {

    private static final int MIN_ANG = -90;
    private static final int MAX_ANG = 90;

    private static final int ANG_STEP = 5;

    private static final double DRAW_COEFF = 0.15;
    private static final double CIRCLE_DRAW_COEFF = 1;

    private static final int xStep = 8;
    private static final int yStep = 8;
    private static final int rStep = 8;
    private static double threshold = 0.1;
    private static double circleThreshold = 2;


    static public BufferedImage detectLine(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        CustomBufferedImage newImg = new CustomBufferedImage(customImg);
        int dimension = Math.max(customImg.getHeight(), customImg.getWidth());
        final int MIN_R = (int) -Math.sqrt(2) * dimension;
        final int MAX_R = (int) Math.sqrt(2) * dimension;
        final int RAD_STEP = (MAX_R - MIN_R) / 200;

        final int ANG_NUM = (MAX_ANG - MIN_ANG) / ANG_STEP + 1;
        final int RADIUS_NUM = (MAX_R - MIN_R) / RAD_STEP + 1;

        int[][] candidates = new int[ANG_NUM][RADIUS_NUM];
        int[] bestCandidate = {0, 0};

        double result = 0, rads = 0;
        for (int x = 0; x < customImg.getWidth(); x++) {
            for (int y = 0; y < customImg.getHeight(); y++) {
                if (customImg.getGray(x, y) == 0) {
                    continue;
                }
                for (int ang = MIN_ANG, i = 0; i < ANG_NUM; ang += ANG_STEP, i++) {
                    for (int rad = MIN_R, j = 0; j < RADIUS_NUM; rad += RAD_STEP, j++) {
                        rads = Math.toRadians(ang);
                        result = Math.abs(x * Math.cos(rads) + y * Math.sin(rads) - rad);
                        if (result < threshold) {
                            candidates[i][j] += 1;
                            if (candidates[i][j] > candidates[bestCandidate[0]][bestCandidate[1]]) {
                                bestCandidate[0] = i;
                                bestCandidate[1] = j;
                            }
                        }
                    }
                }
            }
        }

        double bestCandidateValue = DRAW_COEFF * candidates[bestCandidate[0]][bestCandidate[1]];
        for (int y = 0; y < customImg.getHeight(); y++) {
            for (int x = 0; x < customImg.getWidth(); x++) {
                for (int ang = MIN_ANG, i = 0; i < ANG_NUM; ang += ANG_STEP, i++) {
                    for (int rad = MIN_R, j = 0; j < RADIUS_NUM; rad += RAD_STEP, j++) {
                        if (candidates[i][j] < bestCandidateValue) {
                            continue;
                        }
                        rads = Math.toRadians(ang);
                        result = Math.abs(x * Math.cos(rads) + y * Math.sin(rads) - rad);
                        if (result < threshold) {
                            newImg.setRGB(x, y, Color.CYAN.getRGB());
                        }
                    }
                }
            }
        }
        return newImg;
    }

    static public BufferedImage detectCircle(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        int yInterval = img.getHeight();
        int xInterval = img.getWidth();
        int rInterval = Math.max(img.getHeight(), img.getWidth()) / 2;
        int xNum = xInterval / xStep, yNum = yInterval / yStep, rNum = rInterval / rStep;

        int[][][] candidates = new int[rNum][xNum][yNum];
        int[] best = {0, 0, 0};

        double result;
        int i = 0, j = 0, k = 0;
        int rParam = 0, xParam = 0, yParam = 0;
        rParam = 32;
        xParam = 64;
        yParam = 64;
        for (int x = 0; x < customImg.getWidth(); x++) {
            for (int y = 0; y < customImg.getHeight(); y++) {
                for (rParam = 0, k = 0; k < rNum; rParam += rStep, k++) {
                    for (xParam = 0, i = 0; i < xNum; xParam += xStep, i++) {
                        for (yParam = 0, j = 0; j < yNum; yParam += yStep, j++) {
                            if (customImg.getGray(x, y) == 0) {
                                continue;
                            }
                            result = (x - xParam) * (x - xParam) + (y - yParam) * (y - yParam) - rParam * rParam;
                            if (Math.abs(result) < circleThreshold) {
                                candidates[k][i][j] += 1;
                                if (candidates[k][i][j] > candidates[best[0]][best[1]][best[2]]) {
                                    best[0] = k;
                                    best[1] = i;
                                    best[2] = j;
                                }
                            }
                        }
                    }
                }
            }
        }

        int x, y, r;
        double bestCandidateValue = CIRCLE_DRAW_COEFF * candidates[best[0]][best[1]][best[2]];
        System.out.println(String.format("Best candidate: %f", bestCandidateValue));
        Graphics2D g2 = customImg.createGraphics();
        g2.setColor(Color.CYAN);
        for (rParam = 0, k = 0; k < rNum; rParam += rStep, k++) {
            for (xParam = 0, i = 0; i < xNum; xParam += xStep, i++) {
                for (yParam = 0, j = 0; j < yNum; yParam += yStep, j++) {
                    if (candidates[k][i][j] < bestCandidateValue) {
                        continue;
                    }
                    r = rStep * k;
                    x = xStep * i;
                    y = yStep * j;
                    System.out.println(String.format("r: %d, x: %d, y: %d", r, x, y));
                    g2.drawOval(x - r, y - r, 2 * r, 2 * r);
                }
            }
        }
        g2.dispose();
        return customImg;
    }
}
