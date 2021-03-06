package com.ll.aexi.Model;

import com.ll.aexi.Interface.CaretListener;
import com.ll.aexi.Interface.Global;

import java.awt.*;
import java.util.List;

public class Caret extends GlyphImpl {
    private static Caret instance = new Caret();
    public CaretListener caretListener;
    private boolean show = true;
    private Composition composition;
    private Thread thread;
    private boolean run = true;
    private GlyphImpl hostGlyph;

    private Caret() {
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (run) {
                    show = !show;
                    if (caretListener != null)
                        caretListener.CaretRefresh(Caret.this);
                    try {
                        //闪烁频率应该由配置文件来管理
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void setHostGlyph(GlyphImpl hostGlyph) {
        this.hostGlyph = hostGlyph;
        calculateFrame();
    }

    public GlyphImpl getHostGlyph() {
        return hostGlyph;
    }

    public static Caret getInstance() {
        return instance;
    }

    public int getInsertIndex() {
        int index = 0;
        if (hostGlyph != null) {
            index = composition.getDocument().indexOf(hostGlyph) + 1;
        }
        return index;
    }

    public void setCaretListener(CaretListener caretListener) {
        this.caretListener = caretListener;
    }

    public Composition getComposition() {
        return composition;
    }

    public void setComposition(Composition composition) {
        this.composition = composition;
    }

    @Override
    public void drawMe(Graphics g) {
        g.setColor(Color.BLACK);
        if (show)
            g.drawLine(x, y, x, y + height);
    }

    public boolean moveRight() {
        List<GlyphImpl> list = composition.getDocument().getChildren();
        if (hostGlyph == null) {
            if (list.size() == 0) {
                return false;
            }
            GlyphImpl glyph = list.get(0);
            setHostGlyph(glyph);
            return true;
        }
        GlyphImpl nextGlyph = composition.getDocument().getNext(hostGlyph);
        if (nextGlyph == null) {
            return false;
        }
        setHostGlyph(nextGlyph);
        return true;
    }

    public boolean moveLeft() {
        if (hostGlyph == null) {
            return false;
        }
        GlyphImpl nextGlyph = composition.getDocument().getPrevious(hostGlyph);
        setHostGlyph(nextGlyph);
        return true;
    }


    public boolean moveDown() {
        //计算出一个中心点坐标 然后分发成点击事件
        //这样不合理,应该设计一个通用的向某一行跳的方法,然后点击事件和按键都调用这个方法
        //得到中心点的坐标
        int centerX = width / 2 + x;
        int centerY = height / 2 + y;
        //获得当前行的高度
        Row row = (Row) hostGlyph.getParent();
        //进行偏移
        centerY += row.getHeight();
        composition.dispatchClickEvent(new MouseEvent(centerX, centerY));
        return false;
    }

    public void moveToLineEnd() {
        Row row = null;
        if (hostGlyph == null) {
            Page page = (Page) composition.getChildren().get(0);
            row = (Row) page.getChildren().get(0);
        } else {
            row = (Row) hostGlyph.getParent();
        }
        GlyphImpl glyph = null;
        if (row.getChildren().size() != 0) {
            glyph = row.getChildren().get(row.getChildren().size() - 1);
        }
        setHostGlyph(glyph);
    }


    public boolean moveUp() {
        //计算出一个中心点坐标 然后分发成点击事件
        //得到中心点的坐标
        int centerX = width / 2 + x;
        //Caret没有高度
        int centerY = height / 2 + y;
        //获得当前行的高度
        Row row = (Row) hostGlyph.getParent();
        //进行偏移
        centerY -= row.getHeight();
        composition.dispatchClickEvent(new MouseEvent(centerX, centerY));
        return false;
    }


    public void calculateFrame() {
        if (hostGlyph == null) {
            //如果是空应该到第一行的起始位置
            //TODO : 这种对composition的假设是否合理?应该加上泛型
            Page page = (Page) composition.getChildren().get(0);
            Row row = (Row) page.getChildren().get(0);
            x = row.getX();
            y = row.getY();
            width = row.getWidth();
            height = row.getHeight();
        } else {
            x = hostGlyph.getX() + hostGlyph.getWidth();
            y = hostGlyph.getY();
            width = hostGlyph.getWidth();
            height = hostGlyph.getHeight();
        }
        show = true;
        caretListener.CaretRefresh(this);
    }
}