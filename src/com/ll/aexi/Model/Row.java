package com.ll.aexi.Model;

import java.util.List;

/**
 * Created by Liuli on 2015/3/21.
 */
public class Row extends GlyphImplGroup {

    @Override
    public boolean append(GlyphImpl glyph) {
        List<GlyphImpl> children = getChildren();
        int x = 0;
        Frame frame = glyph.getFrame();
        if (children.size() <= 0) {
            x = this.frame.getX();
        } else {
            Frame preFrame = children.get(children.size() - 1).getFrame();
            x = preFrame.getX() + preFrame.getWidth();
        }
        //如果最后一个图元的右边超过本行的最大宽度则不允许插入
        //TODO:word中,如果超过左边的最大宽度还是允许插入的
        if (x + frame.getWidth() > getFrame().getX() + getFrame().getWidth()) {
            return false;
        }
        frame.setX(x);
        if (getFrame().getHeight() > glyph.getFrame().getHeight()) {
            int y = getFrame().getHeight() + getFrame().getY() - glyph.getFrame().getHeight();
            glyph.getFrame().setY(y);
        } else {
            frame.setY(getFrame().getY());
        }
        glyph.setFrame(frame);
        //每插入一个图元就要检查一下高度是否超过限制,并调整自身高度
        if (frame.getHeight() > getFrame().getHeight()) {
            getFrame().setHeight(frame.getHeight());
            fixHeight(getChildren().size());
        }
        return super.append(glyph);
    }


    private void fixHeight(int index) {
        List<GlyphImpl> glyphs = getChildren();
        for (int i = 0; i < index; i++) {
            GlyphImpl glyph = glyphs.get(i);
            int y = frame.getHeight() + frame.getY() - glyph.getFrame().getHeight();
            glyph.getFrame().setY(y);
        }
    }

    @Override
    public boolean onClickEvent(MouseEvent e) {
        Caret caret = Caret.getInstance();
        if (getChildren().size() <= 0) {
            frame = new Frame(getFrame());
        } else {
            List<GlyphImpl> list = getChildren();
            GlyphImpl glyph = list.get(list.size() - 1);
            caret.setHostGlyph(glyph);
        }
        return true;
    }
}
