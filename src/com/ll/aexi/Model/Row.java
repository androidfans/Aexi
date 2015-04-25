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
    public boolean onClickEvent(MouseEvent e) {
        //进入这里说明本行中没有一个子图元能够处理该事件,或者本行中就没有子图元,应该设计一种默认的处理方式.
        Caret caret = Caret.getInstance();
        Frame frame = null;
        if (getChildren().size() <= 0) {
            //没有子图元,就设置caret到行首
            frame = new Frame(getFrame());
            caret.setDocumentIndex(getStartDocumentIndex());
        } else {
            //因为排版是从左到右排
            //所以没有子图元能够处理此事件是因为点击位置在本行最后一个子图元的右边,应该把caret设置过去
            GlyphImpl glyph = getChildren().get(getChildren().size() - 1);
            frame = new Frame(glyph.getFrame());
            frame.setX(frame.getX() + frame.getWidth());
            caret.setDocumentIndex(getEndDocumentIndex());
        }
        caret.setFrame(frame);
        //设置caret的三个属性.
        return super.onClickEvent(e);
    }
}
