package com.ll.aexi.Model;

/**
 * Created by Liuli on 2015/3/21.
 */
public abstract class GlyphImpl implements Glyph {
    protected Frame frame = new Frame();
    private Glyph parent;

    public Glyph getParent() {
        return parent;
    }

    public void setParent(Glyph parent) {
        this.parent = parent;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public boolean hitRect(int x, int y) {
        Frame frame = getFrame();
        if (x > (frame.getX() + frame.getWidth()) || x < frame.getX())
            return false;
        return !(y > (frame.getY() + frame.getHeight()) || y < frame.getY());
    }

    @Override
    public boolean dispatchClickEvent(MouseEvent e) {
        if (interceptClickEvent(e))
            return true;
        return onClickEvent(e);
    }

    @Override
    public boolean onClickEvent(MouseEvent e) {
        return false;
    }

    @Override
    public boolean interceptClickEvent(MouseEvent e) {
        return false;
    }
}