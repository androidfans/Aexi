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
        if (children.size() <= 0) {
            x = this.x;
        } else {
            GlyphImpl preGlyph = children.get(children.size() - 1);
            x = preGlyph.getX() + preGlyph.getWidth();
        }
        //拿到空隙之后进行判断
        int space = this.width + this.x - x;
        if (space <= 0) {
            return false;
        }
        if (glyph instanceof Bitmap) {
            Bitmap bitmap = (Bitmap) glyph;
            bitmap.scaleWidth(space);
        } else {
            if (this.width > space) {
                return false;
            }
        }
        glyph.setX(x);
        if (height > glyph.getHeight()) {
            int y = height + this.y - glyph.getHeight();
            glyph.setY(y);
        } else {
            glyph.setY(this.y);
        }
        //每插入一个图元就要检查一下高度是否超过限制,并调整自身高度
        if (glyph.getHeight() > this.height) {
            setHeight(glyph.getHeight());
            fixHeight(getChildren().size());
        }
        return super.append(glyph);
    }


    private void fixHeight(int index) {
        List<GlyphImpl> glyphs = getChildren();
        for (int i = 0; i < index; i++) {
            GlyphImpl glyph = glyphs.get(i);
            int y = height + this.y - glyph.getHeight();
            glyph.setY(y);
        }
    }

    @Override
    public boolean onClickEvent(MouseEvent e) {
        Caret caret = Caret.getInstance();
        if (getChildren().size() > 0){
            List<GlyphImpl> list = getChildren();
            GlyphImpl glyph = list.get(list.size() - 1);
            caret.setHostGlyph(glyph);
        }
        return true;
    }
}
