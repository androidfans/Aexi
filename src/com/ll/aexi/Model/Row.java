package com.ll.aexi.Model;

import java.util.List;

/**
 * Created by Liuli on 2015/3/21.
 */
public class Row extends GlyphImplGroup {
    private int startDocumentIndex;
    private int endDocumentIndex;

    public int getStartDocumentIndex() {
        return startDocumentIndex;
    }

    public void setStartDocumentIndex(int startDocumentIndex) {
        this.startDocumentIndex = startDocumentIndex;
    }

    public int getEndDocumentIndex() {
        return endDocumentIndex;
    }

    public void setEndDocumentIndex(int endDocumentIndex) {
        this.endDocumentIndex = endDocumentIndex;
    }

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
        if (x + frame.getWidth() > getFrame().getWidth())
            return false;
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
            BasicGlyph glyph = (BasicGlyph) glyphs.get(i);
            int y = frame.getHeight() + frame.getY() - glyph.getFrame().getHeight();
            glyph.getFrame().setY(y);
        }
    }

    @Override
    public boolean onClickEvent(MouseEvent e) {
        Caret caret = Caret.getInstance();
        //应该先给caret设置行号
        Page page = (Page) getParent();
        caret.setRowIndex(page.getChildren().indexOf(this));
        //进入这里说明本行中没有一个子图元能够处理该事件,或者本行中就没有子图元,应该设计一种默认的处理方式.
        if (getChildren().size() <= 0) {
            //TODO :这里又要设置columIndex了,一定要想办法解决 ,还有rowIndex 不然会出现莫名其妙的bug
            //没有子图元,就设置caret到行首
            //抛出异常
            frame = new Frame(getFrame());
        } else {
            //因为排版是从左到右排
            //所以没有子图元能够处理此事件是因为点击位置在本行最后一个子图元的右边,应该把caret设置过去
            BasicGlyph glyph = (BasicGlyph) getChildren().get(getChildren().size() - 1);
            //因为没有及时修改rowIndex导致这里出现Bug
            caret.setColumnIndex(getChildren().indexOf(glyph) + 1);
        }
        return super.onClickEvent(e);
    }
}
