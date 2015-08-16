package ar.com.itba.utils;

import ar.com.itba.frame.ImageOptionsWindow;

import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.security.InvalidParameterException;

public class MouseTracker extends MouseInputAdapter {

    private Component parent;
    private Rectangle rectToDraw;
    private Rectangle oldRectDrawn = new Rectangle();
    private Rectangle currentSelection;
    private ImageOptionsWindow optionsWindow;
    private int canvasWidth;
    private int canvasHeight;

    private static final int MIN_RECT_WIDTH = 10;
    private static final int MIN_RECT_HEIGHT = 10;

    public MouseTracker(Component parent, ImageOptionsWindow optionsWindow,
                        int canvasWidth, int canvasHeight) {
        if (parent == null || optionsWindow == null) {
            throw new InvalidParameterException("Component and ImageOptionsWindow can't be null");
        }

        this.parent = parent;
        this.optionsWindow = optionsWindow;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        optionsWindow.updateRGBSliders(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        int x = e.getX();
        int y = e.getY();
        currentSelection = new Rectangle(x, y, 0, 0);
        updateSelectionDrawing(canvasWidth, canvasHeight);
    }

    private void updateSelectionDrawing(int imgWidth, int imgHeight) {
        int x = currentSelection.x;
        int y = currentSelection.y;
        int rectWidth = currentSelection.width;
        int rectHeight = currentSelection.height;

        //Make the width and height positive, if necessary.
        if (rectWidth < 0) {
            rectWidth = 0 - rectWidth;
            x = x - rectWidth + 1;
            if (x < 0) {
                rectWidth += x;
                x = 0;
            }
        }
        if (rectHeight < 0) {
            rectHeight = 0 - rectHeight;
            y = y - rectHeight + 1;
            if (y < 0) {
                rectHeight += y;
                y = 0;
            }
        }

        //The rectangle shouldn't extend past the drawing area.
        if ((x + rectWidth) > imgWidth) {
            rectWidth = imgWidth - x;
        }
        if ((y + rectHeight) > imgHeight) {
            rectHeight = imgHeight - y;
        }

        //Update rectToDraw after saving old value.
        if (rectToDraw != null) {
            oldRectDrawn.setBounds(rectToDraw.x, rectToDraw.y,
                    rectToDraw.width, rectToDraw.height);
            rectToDraw.setBounds(x, y, rectWidth, rectHeight);
        } else {
            rectToDraw = new Rectangle(x, y, rectWidth, rectHeight);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        updateSelection(e);
        if (hasDataToDraw()) {
            optionsWindow.updateRectangleSelection(rectToDraw);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        super.mouseDragged(e);
        updateSelection(e);
    }

    private void updateSelection(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        currentSelection.setSize(x - currentSelection.x, y - currentSelection.y);
        updateSelectionDrawing(canvasWidth, canvasHeight);
        Rectangle totalRepaint = rectToDraw.union(oldRectDrawn);
//			repaint(totalRepaint.x, totalRepaint.y,
//					totalRepaint.width, totalRepaint.height);
        parent.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        super.mouseMoved(e);
        if (optionsWindow != null) {
            sendPointValueToOptionsWindow(e.getPoint());
        }
    }

    private void sendPointValueToOptionsWindow(Point p) {
        if (p.getX() <= canvasWidth && p.getY() <= canvasHeight) {
            optionsWindow.setPointerLabelValues(p);
        } else {
            optionsWindow.setPointerLabelValues(null);
        }

    }

    public boolean hasDataToDraw() {
        return rectToDraw != null && rectToDrawAreaIsBigEnough();
    }

    private boolean rectToDrawAreaIsBigEnough() {
        int area = rectToDraw.width * rectToDraw.height;
        return area >= MIN_RECT_WIDTH * MIN_RECT_HEIGHT;
    }

    public Rectangle getRectToDraw() {
        return rectToDraw;
    }
}
