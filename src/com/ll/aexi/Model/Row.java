package com.ll.aexi.Model;

import java.awt.*;
import java.util.List;

/**
 * Created by Liuli on 2015/3/21.
 */
public class Row extends GlyphImplGroup {

    @Override
    public boolean append(GlyphImpl glyph) {
        //TODO 这里还需要动态修改该行自己的行高
        List<GlyphImpl> children = getChildren();
        int x = 0;
        Frame frame = glyph.getFrame();
        if (children.size() <= 0) {
            x = this.frame.getX();
        } else {
            Frame preFrame = children.get(children.size() - 1).getFrame();
            x = preFrame.getX() + preFrame.getWidth();
        }
        frame.setX(x);
        frame.setY(getFrame().getY());
        glyph.setFrame(frame);
        //每插入一个图元就要检查一下高度是否超过限制,并调整自身高度
        if (frame.getHeight() > getFrame().getHeight())
            getFrame().setHeight(frame.getHeight());
        return super.append(glyph);
    }

    @Override
    public void drawMe(Graphics g) {
        super.drawMe(g);
    }
}
