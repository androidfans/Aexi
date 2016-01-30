package com.ll.aexi.Model;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * Created by Liuli on 2015/3/21.
 */
public class Bitmap extends GlyphImpl implements ImageObserver{
    private Image image;
    private float ratio;

    public Bitmap(Image image) {
        this.image = image;
        setWidth(image.getWidth(this));
        setHeight(image.getHeight(this));
        if (height != 0) {
            ratio = (float) width / (float)height;
        }
    }

    public void shrinkWidth(int width) {
        if (width >= this.width) {
            return;
        }
        setWidth(width);
        setHeight((int) (width / ratio));
    }

    public void scaleHeight(int height) {
        setHeight(height);
        setWidth((int) (height * ratio));
    }

    @Override
    public void drawMe(Graphics g) {
        g.drawImage(image, x, y, width,height,this);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
        System.out.println("imageupdate");
        if (width != -1 && height != -1) {
            setWidth(width);
            setHeight(height);
            ratio = (float) width / (float)height;
            if (listener != null)
                listener.glyphRefresh();
        }
        return (infoflags & (ALLBITS|ABORT)) == 0;
    }
}