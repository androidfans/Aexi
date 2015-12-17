package com.ll.aexi.Model;

import com.ll.aexi.Interface.GlyphListener;

/**
 * Created by Liuli on 2015/3/21.
 */
public abstract class GlyphImpl implements Glyph {
    protected Frame frame = new Frame();
    private Glyph parent;
    private int documentIndex = 0;
    private boolean isSelected = false;
    protected GlyphListener listener;

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
    public boolean interceptClickEvent(MouseEvent e) {
        return false;
    }

    public void setListener(GlyphListener listener) {
        this.listener = listener;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public int getDocumentIndex() {
        return documentIndex;
    }

    public void setDocumentIndex(int documentIndex) {
        this.documentIndex = documentIndex;
    }

    @Override
    public boolean onClickEvent(MouseEvent e) {
        Caret caret = Caret.getInstance();
        caret.setHostGlyph(this);
        Frame frame = new Frame(getFrame());
        if (e.getX() - frame.getX() <= (frame.getWidth() / 2)) {
            frame.setX(frame.getX() + frame.getWidth());
            //TODO : 把获取前一个和后一个的功能封装起来
        }
        caret.setFrame(frame);
        return true;
    }
}