package com.ll.aexi.Model;

import java.util.List;

/**
 * Created by Administrator on 2015/3/21.
 */
public class Page extends GlyphImplGroup {
    @Override
    public boolean append(GlyphImpl glyph) {
        super.append(glyph);
        Row row = (Row) glyph;
        List<GlyphImpl> children = getChildren();
        Frame frame = new Frame();
        frame.setWidth(getFrame().getWidth());
        frame.setHeight(20);
        frame.setX(getFrame().getX());
        if (children.size() <= 1) {
            frame.setY(getFrame().getY());
        } else {
            //TODO super导致的索引位置问题
            Row preRow = (Row) children.get(children.size() - 2);
            Frame preRowFrame = preRow.getFrame();
            frame.setY(preRowFrame.getY() + preRowFrame.getHeight());
        }
        row.setFrame(frame);
        return true;
    }
}
