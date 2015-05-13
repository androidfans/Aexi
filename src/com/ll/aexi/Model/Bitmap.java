package com.ll.aexi.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by Liuli on 2015/3/21.
 */
public class Bitmap extends BasicGlyph implements ImageObserver{
    private Image image;

    public Bitmap(Image image) {
        this.image = image;
        frame.setWidth(image.getWidth(this));
        frame.setHeight(image.getHeight(this));
    }

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(image, frame.getX(), frame.getY(), frame.getWidth(),frame.getHeight(),this);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        System.out.println("imageupdate");
        if (width != -1 && height != -1) {
            frame.setWidth(width);
            frame.setHeight(height);
            if (listener != null)
                listener.glyphRefresh();
        }
        return (infoflags & (ALLBITS|ABORT)) == 0;
    }
}