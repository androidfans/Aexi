package com.ll.aexi.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liuli on 2015/3/21.
 */
public abstract class GlyphImplGroup extends GlyphImpl {
    protected List<GlyphImpl> children;

    public GlyphImplGroup() {
        children = new ArrayList<GlyphImpl>();
    }

    public boolean insert(GlyphImpl glyph, int index) {
        children.add(index, glyph);
        return true;
    }

    public List<GlyphImpl> getChildren() {
        return children;
    }

    public boolean append(GlyphImpl glyph) {
        children.add(glyph);
        glyph.setParent(this);
        return true;
    }

    public boolean remove(int index) {
        children.remove(index);
        return true;
    }

    public boolean clear() {
        children.clear();
        return true;
    }

    @Override
    public void drawMe(Graphics g) {
        for (Glyph glyph : children) {
            glyph.drawMe(g);
        }
    }

    @Override
    public boolean hitRect(int x, int y) {
        if (!super.hitRect(x, y))
            return false;
        List<GlyphImpl> children = getChildren();
        for (GlyphImpl glyph : children) {
            glyph.hitRect(x, y);
        }
        return true;
    }

    @Override
    public boolean dispatchClickEvent(MouseEvent e) {
        if (interceptClickEvent(e))
            return true;
        List<GlyphImpl> children = getChildren();
        Glyph toBeHandledGlyph = null;
        for (GlyphImpl glyph : children) {
            if (glyph.hitRect(e.getX(), e.getY())) {
                toBeHandledGlyph = glyph;
                break;
            }
        }
        if (!toBeHandledGlyph.dispatchClickEvent(e))
            return onClickEvent(e);
        return true;
    }
}
