package ar.com.itba.image_actions.figure_detection;

import ar.com.itba.image_actions.Thresholding.OtsuThreshold;
import ar.com.itba.image_actions.operations.Operators;
import ar.com.itba.utils.CustomBufferedImage;

import java.awt.image.BufferedImage;

/**
 * Created by Enzo on 14.10.15.
 */
public class HoughFigureDetection {

    private static final int ANG_NUM = 20;
    private static final int RADIUS_NUM = 20;
    private static double threshold = 1;

    static public BufferedImage detectLine(BufferedImage img) {
//        CustomBufferedImage customImg = (CustomBufferedImage) OtsuThreshold.applyTransformation(img);
        CustomBufferedImage customImg = (CustomBufferedImage) img;
        CustomBufferedImage newImg = new CustomBufferedImage(img.getWidth(), img.getHeight(), img.getType());
        int dimension = Math.max(customImg.getHeight(), customImg.getWidth());
        int minAng = -90, maxAng = 90;
        int minR = (int) -Math.sqrt(2) * dimension, maxR = (int) Math.sqrt(2) * dimension;
        int angInterval = maxAng - minAng;
        int rInterval = maxR - minR;
        int ang, r;
        int angStep = angInterval / 10;
        int rStep = rInterval / 10;
        int i, j;

        int[][] candidates = new int[ANG_NUM][RADIUS_NUM];

        double result = 0, rads = 0;
        for (int x = 0; x < customImg.getWidth(); x++) {
            for (int y = 0; y < customImg.getHeight(); y++) {
                if (customImg.getGray(x, y) == 0) {
                    continue;
                }

                for (ang = minAng, i = 0; i < ANG_NUM; ang += angStep, i++) {
                    for (r = minR, j = 0; j < RADIUS_NUM; r += rStep, j++) {
                        rads = Math.toRadians(ang);
                        result = x * Math.cos(rads) + y * Math.sin(rads) - r;
                        if (Math.abs(result) < threshold) {
                            candidates[i][j] += 1;
                        }
                    }
                }
            }
        }

        int max1 = 0, max2 = 0, max3 = 0, max4 = 0;
        int[][] bestCandidates = new int[4][2];
        int val = 0;
        for (ang = minAng, i = 0; i < ANG_NUM; ang += angStep, i++) {
            for (r = minR, j = 0; j < RADIUS_NUM; r += rStep, j++) {
                val = candidates[i][j];
                if (val > max1) {
                    max1 = val;
                    bestCandidates[0][0] = i;
                    bestCandidates[0][1] = j;
                } else if (val > max2) {
                    max2 = val;
                    bestCandidates[1][0] = i;
                    bestCandidates[1][1] = j;
                } else if (val > max3) {
                    max3 = val;
                    bestCandidates[2][0] = i;
                    bestCandidates[2][1] = j;
                } else if (val > max4) {
                    max4 = val;
                    bestCandidates[3][0] = i;
                    bestCandidates[3][1] = j;
                }
            }
        }

        for (int y = 0; y < customImg.getHeight(); y++) {
            for (int x = 0; x < customImg.getWidth(); x++) {
                if (customImg.getGray(x, y) == 0) {
                    continue;
                }
                for (i = 0; i < bestCandidates.length; i++) {
                    ang = minAng + angStep * bestCandidates[i][0];
                    r = minR + rStep * bestCandidates[i][1];
                    rads = Math.toRadians(ang);
                    result = x * Math.cos(rads) + y * Math.sin(rads) - r;
                    if (Math.abs(result) < threshold) {
                        newImg.markPoint(x, y);
                    }
                }
            }
        }

        newImg.applyLinearTransform();
        return Operators.imageAddition(customImg, newImg);
    }
}
