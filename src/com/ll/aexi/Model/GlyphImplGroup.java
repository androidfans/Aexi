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

    public Glyph remove(int index) {
        return children.remove(index);
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
        //toBeHandledGlyph可能为空
        if (toBeHandledGlyph == null)
            //进入这里就表示,点到空白位置了,这里需要区别对待,如果是row就要把caret放到row的最右边,如果是page就要把caret放到最后一行.
            //应该直接分发给本类对象的onClickEvent()方法
            return onClickEvent(e);
        if (!toBeHandledGlyph.dispatchClickEvent(e))
            return onClickEvent(e);
        return true;
    }
}
