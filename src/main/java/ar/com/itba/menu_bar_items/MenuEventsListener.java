package ar.com.itba.menu_bar_items;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ar.com.itba.frame.MainWindow;
import ar.com.itba.image_actions.Thresholding.ThresholdManager;
import ar.com.itba.image_actions.corner_detection.CornerDetectionManager;
import ar.com.itba.image_actions.edge_detection.EdgeDetectionManager;
import ar.com.itba.image_actions.figure_detection.FigureDetectionManager;
import ar.com.itba.image_actions.masks.ImageMaskManager;
import ar.com.itba.image_actions.operations.ImageOperationManager;
import ar.com.itba.image_actions.transformations.ImageTransformationManager;
import ar.com.itba.panel.QuickDrawPanel;


public class MenuEventsListener implements ActionListener {

    public QuickDrawPanel quickDrawPanel;
    public MainWindow mainWindow;

    public MenuEventsListener(QuickDrawPanel quickDrawPanel, MainWindow window) {
        this.quickDrawPanel = quickDrawPanel;
        this.mainWindow = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (belongsToImageOperations(e)) {
            return;
        } else if (belongsToImageHistogram(e)) {
            return;
        } else if (belongsToMiscOperations(e)) {
            return;
        } else if (belongsToImageTransformations(e)) {
            return;
        } else if (belongsToImageMasks(e)) {
            return;
        } else if (belongsToEdgeDetection(e)) {
            return;
        } else if (belongsToShapeDetection(e)) {
            return;

        } else if (belongsToThresholding(e)) {
            return;
        }
    }

    private boolean belongsToShapeDetection(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.HOUGH_LINE_DETECTION)) {
            FigureDetectionManager.applyHoughLineDetection(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.HOUGH_CIRCLE_DETECTION)) {
            FigureDetectionManager.applyHoughCircleDetection(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.CONTOUR_DETECTION)) {
            FigureDetectionManager.applyContourDetection(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.START_VIDEO_CONTOUR_DETECTION)) {
            FigureDetectionManager.startVideoContourDetection(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.EXECUTE_VIDEO_CONTOUR_DETECTION)) {
            FigureDetectionManager.executeVideoContourDetection(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.HARRIS_CORNER_DETECTION)) {
            CornerDetectionManager.detectCornersWithHarris(quickDrawPanel, mainWindow);

        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToThresholding(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.GLOBAL_THRESHOLD)) {
            ThresholdManager.applyGlobalThreshold(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.OTSU_THRESHOLD)) {
            ThresholdManager.applyOtsuThreshold(quickDrawPanel, mainWindow);

        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToEdgeDetection(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.PREWITT_HORIZONTAL)) {
            EdgeDetectionManager.applyPrewittHorizontal(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.PREWITT_VERTICAL)) {
            EdgeDetectionManager.applyPrewittVertical(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.PREWITT_MODULE)) {
            EdgeDetectionManager.applyPrewittModule(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.PREWITT_DIRECTIONAL)) {
            EdgeDetectionManager.applyPrewittDirectional(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.DIRECTIONAL_KIRSH)) {
            EdgeDetectionManager.applyKirshDirectional(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.DEFAULT_EDGE_DETECTION)) {
            EdgeDetectionManager.applyDefaultDirectional(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.SOBEL_HORIZONTAL)) {
            EdgeDetectionManager.applySobelHorizontal(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.SOBEL_VERTICAL)) {
            EdgeDetectionManager.applySobelVertical(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.SOBEL_MODULE)) {
            EdgeDetectionManager.applySobelModule(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.SOBEL_DIRECTIONAL)) {
            EdgeDetectionManager.applySobelDirectional(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.LAPLACIAN)) {
            EdgeDetectionManager.applyLaplacian(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.LAPLACIAN_SLOPE)) {
            EdgeDetectionManager.applyLaplacianWithSlope(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.LAPLACIAN_GAUSSIAN)) {
            EdgeDetectionManager.applyLaplacianGaussian(quickDrawPanel, mainWindow);

        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToImageMasks(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.MEAN_MASK)) {
            ImageMaskManager.applyMeanMask(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.MEDIAN_MASK)) {
            ImageMaskManager.applyMedianMask(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.GAUSSIAN_MASK)) {
            ImageMaskManager.applyGaussianMask(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.HIGH_PASS_MASK)) {
            ImageMaskManager.applyHighPassMask(quickDrawPanel, mainWindow);

        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToImageTransformations(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.IMAGE_NEGATIVE)) {
            ImageTransformationManager.showNegative(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.DYNAMIC_RANGE_COMPRESS)) {
            ImageTransformationManager.showDynamicCompression(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.THRESHOLDING)) {
            ImageTransformationManager.showThresholdTransform(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.EQUALIZE)) {
            ImageTransformationManager.performEqualization(quickDrawPanel, mainWindow);

        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToMiscOperations(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.SHOW_IMAGE_OPTIONS)) {
            quickDrawPanel.toggleImageTools();
        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToImageHistogram(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.SHOW_HISTOGRAM)) {
            quickDrawPanel.toggleImageHistogram();
        } else {
            belongs = false;
        }
        return belongs;
    }

    private boolean belongsToImageOperations(ActionEvent e) {
        boolean belongs = true;
        if (e.getActionCommand().equals(ToolsMenu.IMAGE_ADDITION)) {
            ImageOperationManager.performAddition(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.IMAGE_SUBSTRACTION)) {
            ImageOperationManager.performSubstraction(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.IMAGE_MULTIPLICATION)) {
            ImageOperationManager.performMultiplication(quickDrawPanel, mainWindow);

        } else if (e.getActionCommand().equals(ToolsMenu.SCALAR_MULTIPLICATION)) {
            ImageOperationManager.imageScalarMult(quickDrawPanel, mainWindow);

        } else {
            belongs = false;
        }
        return belongs;
    }
}
