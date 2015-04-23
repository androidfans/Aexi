package com.ll.aexi.Model;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by Administrator on 2015/3/21.
 */
public class Bitmap extends BasicGlyph implements ImageObserver {
    private Image image;

    @Override
    public void drawMe(Graphics g) {
        Frame frame = getFrame();
        g.drawImage(image, frame.getX(), frame.getY(), frame.getWidth(), frame.getWidth(), this);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        return false;
    }
}