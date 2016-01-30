package com.ll.aexi.Model;

import com.ll.aexi.Interface.GlyphListener;

/**
 * Created by Liuli on 2015/3/21.
 */
public abstract class GlyphImpl implements Glyph {
    private Glyph parent;
    private int documentIndex = 0;
    private boolean isSelected = false;
    protected GlyphListener listener;
    protected int x;
    protected int y;
    protected int width;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    protected int height;

    public Glyph getParent() {
        return parent;
    }

    public void setParent(Glyph parent) {
        this.parent = parent;
    }

    public boolean hitRect(int x, int y) {
        if (x > (x + width) || x < this.x)
            return false;
        return !(y > (y + height) || y < this.y);
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
        if (e.getX() - x <= (width / 2)) {
            GlyphImpl glyph = Composition.getInstance().getDocument().getPrevious(this);
            caret.setHostGlyph(glyph);
        }
        return true;
    }
}