package ar.com.itba.image_actions.figure_detection;

import ar.com.itba.image_actions.Thresholding.OtsuThreshold;
import ar.com.itba.image_actions.operations.Operators;
import ar.com.itba.utils.CustomBufferedImage;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 14.10.15.
 */
public class HoughFigureDetection {

    private static final int MIN_ANG = -90;
    private static final int MAX_ANG = 90;

    private static final int ANG_STEP = 5;

    private static final double DRAW_COEFF = 0.3;

    private static final int xStep = 2;
    private static final int yStep = 2;
    private static final int rStep = 1;
    private static double threshold = 1;


    static public BufferedImage detectLine(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        int dimension = Math.max(customImg.getHeight(), customImg.getWidth());
        final int MIN_R = (int) -Math.sqrt(2) * dimension;
        final int MAX_R = (int) Math.sqrt(2) * dimension;
        final int RAD_STEP = (MAX_R - MIN_R) / 100;

        final int ANG_NUM = (MAX_ANG - MIN_ANG) / ANG_STEP;
        final int RADIUS_NUM = (MAX_R - MIN_R) / RAD_STEP;

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

//        Graphics2D g2d = customImg.createGraphics();
//        g2d.setColor(Color.red);
//        int ang = 0, rad = 0;
//        double bestCandidateValue = DRAW_COEFF * candidates[bestCandidate[0]][bestCandidate[1]];
//        for (int i = 0; i < ANG_NUM; i++) {
//            for (int j = 0; j < RADIUS_NUM; j++) {
//                if (candidates[i][j] < bestCandidateValue) {
//                    continue;
//                }
//                int x = 0, y = 0;
//                int[] firstPoint = new int[2];
//                int[] secondPoint = new int[2];
//                firstPoint[0] = secondPoint[0] = -1;
//                firstPoint[1] = secondPoint[1] = -1;
//
//                ang = i * ANG_STEP;
//                rad = j * RAD_STEP;
//                rads = Math.toRadians(ang);
//                for (x = 0, y = 0; x < customImg.getWidth(); x++) {
//                    result = Math.abs(x * Math.cos(rads) + y * Math.sin(rads) - rad);
//                    if (result > threshold) {
//                        continue;
//                    }
//                    firstPoint[0] = i;
//                    firstPoint[1] = j;
//                    break;
//                }
//
//                for (x = customImg.getWidth(), y = 0; y < customImg.getHeight()
//                        && secondPoint[0] == -1; y++) {
//                    result = Math.abs(x * Math.cos(rads) + y * Math.sin(rads) - rad);
//                    if (result > threshold) {
//                        continue;
//                    }
//                    if (firstPoint[0] == -1) {
//                        firstPoint[0] = x;
//                        firstPoint[1] = y;
//                        break;
//                    } else {
//                        secondPoint[0] = x;
//                        secondPoint[1] = y;
//                        break;
//                    }
//                }
//
//                for (x = customImg.getWidth(), y = customImg.getHeight(); x > 0
//                        && secondPoint[0] == -1; x--) {
//                    result = Math.abs(x * Math.cos(rads) + y * Math.sin(rads) - rad);
//                    if (result > threshold) {
//                        continue;
//                    }
//                    if (firstPoint[0] == -1) {
//                        firstPoint[0] = x;
//                        firstPoint[1] = y;
//                        break;
//                    } else {
//                        secondPoint[0] = x;
//                        secondPoint[1] = y;
//                        break;
//                    }
//                }
//
//                for (x = 0, y = customImg.getHeight(); y > 0
//                        && secondPoint[0] == -1; y--) {
//                    result = Math.abs(x * Math.cos(rads) + y * Math.sin(rads) - rad);
//                    if (result > threshold) {
//                        continue;
//                    }
//                    if (firstPoint[0] == -1) {
//                        firstPoint[0] = x;
//                        firstPoint[1] = y;
//                        break;
//                    } else {
//                        secondPoint[0] = x;
//                        secondPoint[1] = y;
//                        break;
//                    }
//                }
//
//                if (secondPoint[0] != -1) {
//                    g2d.draw(new Line2D.Double(firstPoint[0], firstPoint[1], secondPoint[0], secondPoint[1]));
//                }
//            }
//        }
//        g2d.dispose();

//        CustomBufferedImage newImg = new CustomBufferedImage(customImg);

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
                            customImg.setRGB(x, y, Color.CYAN.getRGB());
                        }
                    }
                }
            }
        }
        return customImg;
    }

//    static public BufferedImage detectLine(BufferedImage img) {
////        CustomBufferedImage customImg = (CustomBufferedImage) OtsuThreshold.applyTransformation(img);
//        CustomBufferedImage customImg = (CustomBufferedImage) img;
//        int dimension = Math.max(customImg.getHeight(), customImg.getWidth());
//        int minR = (int) -Math.sqrt(2) * dimension, maxR = (int) Math.sqrt(2) * dimension;
//        int angInterval = maxAng - minAng;
//        int rInterval = maxR - minR;
//        int ang, r;
//        int angStep = angInterval / ANG_NUM;
//        int rStep = rInterval / RADIUS_NUM;
//        int i, j;
//
//        int[][] candidates = new int[ANG_NUM][RADIUS_NUM];
//
//        double result = 0, rads = 0;
//        for (int x = 0; x < customImg.getWidth(); x++) {
//            for (int y = 0; y < customImg.getHeight(); y++) {
//                if (customImg.getGray(x, y) == 0) {
//                    continue;
//                }
//
//                for (ang = minAng, i = 0; i < ANG_NUM; ang += angStep, i++) {
//                    for (r = minR, j = 0; j < RADIUS_NUM; r += rStep, j++) {
//                        rads = Math.toRadians(ang);
//                        result = x * Math.cos(rads) + y * Math.sin(rads) - r;
//                        if (Math.abs(result) < threshold) {
//                            candidates[i][j] += 1;
//                        }
//                    }
//                }
//            }
//        }
//
//        int max1 = 0, max2 = 0, max3 = 0, max4 = 0;
//        int[][] bestCandidates = new int[4][2];
//        int val = 0;
//        for (ang = minAng, i = 0; i < ANG_NUM; ang += angStep, i++) {
//            for (r = minR, j = 0; j < RADIUS_NUM; r += rStep, j++) {
//                val = candidates[i][j];
//                if (val > max1) {
//                    max1 = val;
//                    bestCandidates[0][0] = i;
//                    bestCandidates[0][1] = j;
//                } else if (val > max2) {
//                    max2 = val;
//                    bestCandidates[1][0] = i;
//                    bestCandidates[1][1] = j;
//                } else if (val > max3) {
//                    max3 = val;
//                    bestCandidates[2][0] = i;
//                    bestCandidates[2][1] = j;
//                } else if (val > max4) {
//                    max4 = val;
//                    bestCandidates[3][0] = i;
//                    bestCandidates[3][1] = j;
//                }
//            }
//        }
//
//
//        CustomBufferedImage newImg = new CustomBufferedImage(customImg);
//        for (int y = 0; y < customImg.getHeight(); y++) {
//            for (int x = 0; x < customImg.getWidth(); x++) {
//                for (i = 0; i < bestCandidates.length; i++) {
//                    ang = minAng + angStep * bestCandidates[i][0];
//                    r = minR + rStep * bestCandidates[i][1];
//                    rads = Math.toRadians(ang);
//                    result = x * Math.cos(rads) + y * Math.sin(rads) - r;
//                    if (Math.abs(result) < threshold) {
//                        newImg.setRGB(x, y, Color.CYAN.getRGB());
//                    }
//                }
//            }
//        }
//
//        return newImg;
//    }

    static public BufferedImage detectCircle(BufferedImage img) {
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        int yInterval = img.getHeight();
        int xInterval = img.getWidth();
        int rInterval = Math.max(img.getHeight(), img.getWidth()) / 2;
        int xNum = xInterval / xStep, yNum = yInterval / yStep, rNum = rInterval / rStep;

        int[][][] candidates = new int[rNum][xNum][yNum];

        double result;
        int i, j, k;
        int rParam = 0, xParam = 0, yParam = 0;
        for (int x = 0; x < customImg.getWidth(); x++) {
            for (int y = 0; y < customImg.getHeight(); y++) {
                for (rParam = 0, k = 0; k < rNum; rParam += rStep, k++) {
                    for (xParam = 0, i = 0; i < xNum; xParam += xStep, i++) {
                        for (yParam = 0, j = 0; j < yNum; yParam += yStep, j++) {
                            if (customImg.getGray(x, y) == 0) {
                                continue;
                            }
                            result = (x - xParam) * (x - xParam) + (y - yParam) * (y - yParam) - rParam * rParam;
                            if (Math.abs(result) < threshold) {
//                                System.out.println(String.format("(%d, %d), r = %d, result = %f", xParam, yParam, rParam, Math.abs(result)));
                                candidates[k][i][j] += 1;
                            }
                        }
                    }
                }
            }
        }

        int max1 = 0, max2 = 0, max3 = 0, max4 = 0;
        int[][] bestCandidates = new int[4][3];
        int val = 0;

        for (k = 0; k < rNum; k++) {
            for (i = 0; i < xNum; i++) {
                for (j = 0; j < yNum; j++) {
                    val = candidates[k][i][j];
                    if (val > max1) {
                        max1 = val;
                        bestCandidates[0][0] = k;
                        bestCandidates[0][1] = i;
                        bestCandidates[0][2] = j;
                    } else if (val > max2) {
                        max2 = val;
                        bestCandidates[1][0] = k;
                        bestCandidates[1][1] = i;
                        bestCandidates[1][2] = j;
                    } else if (val > max3) {
                        max3 = val;
                        bestCandidates[2][0] = k;
                        bestCandidates[2][1] = i;
                        bestCandidates[2][2] = j;
                    } else if (val > max4) {
                        max4 = val;
                        bestCandidates[3][0] = k;
                        bestCandidates[3][1] = i;
                        bestCandidates[3][2] = j;
                    }
                }
            }
        }

//        CustomBufferedImage newImg = new CustomBufferedImage(customImg);
        int x, y;
        for (y = 0; y < customImg.getHeight(); y++) {
            for (x = 0; x < customImg.getWidth(); x++) {
                for (i = 0; i < bestCandidates.length; i++) {
                    rParam = bestCandidates[i][0] * rStep;
                    xParam = bestCandidates[i][1] * xStep;
                    yParam = bestCandidates[i][2] * yStep;
                    result = (x - xParam) * (x - xParam) + (y - yParam) * (y - yParam) - rParam * rParam;
                    if (Math.abs(result) < threshold) {
                        customImg.setRGB(x, y, Color.CYAN.getRGB());
                    }
                }
            }
        }

        return customImg;
    }
}
