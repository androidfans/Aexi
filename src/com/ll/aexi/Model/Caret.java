package com.ll.aexi.Model;

import com.ll.aexi.Interface.CaretListener;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Caret extends GlyphImpl {
    private static Caret instance = new Caret();
    public CaretListener caretListener;
    private int pageIndex = 0;
    private int rowIndex = 0;
    private int columnIndex = 0;
    private boolean show = true;
    private Composition composition;
    private Thread thread;
    private boolean run = true;

    private Caret() {
        this.thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (run) {
                    show = !show;
                    if (caretListener != null)
                        caretListener.CaretRefresh(Caret.this);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public static Caret getInstance() {
        return instance;
    }

    public int getInsertIndex() {
        //找到开头的Index
        //先找到对应的row
        Page page = (Page) composition.getChildren().get(pageIndex);
        Row row = (Row) page.getChildren().get(rowIndex);
        return row.getStartDocumentIndex() + columnIndex;
    }


    public void setCaretListener(CaretListener caretListener) {
        this.caretListener = caretListener;
    }

    public Position getPositioin() {
        return new Position(pageIndex, rowIndex, columnIndex);
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
        calculateFrame();
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
            g.drawLine(frame.getX(), frame.getY(), frame.getX(), frame.getY() + frame.getHeight());
    }

    @Override
    public void setFrame(Frame frame) {
        super.setFrame(frame);
    }

    /**
     * 按下向右键的时候
     * 如果到达已经在本行最后一个字符的后面,那么moveright的时候应该跳转到下一行的行首,
     * 而不是跳转到下一行行首字符之后
     * 打字时的跳转
     * 跟随文字,始终在文字后边即可
     *
     * @return 是否移动成功
     */
    public boolean moveRight() {
        Page page = (Page) composition.getChildren().get(pageIndex);
        Row row = (Row) page.getChildren().get(rowIndex);
        List<GlyphImpl> rowChildren = row.getChildren();
        //columIndex的范围 0 ~ rowChildren.size(), caret可以放置的位置比该行所有字符数多一个
        if (columnIndex == rowChildren.size())
            return false;
        columnIndex++;
        calculateFrame();
        return true;
    }

    public boolean moveLeft() {
        //先检查合法性
        if (columnIndex <= 0)
            return false;
        columnIndex--;
        calculateFrame();
        return true;
    }

    public boolean moveToNextRow() {
        Page page = (Page) composition.getChildren().get(pageIndex);
        //rowIndex的范围  0 ~ (pageChildren.size() - 1)
        if (rowIndex >= page.getChildren().size() - 1)
            return false;
        rowIndex++;
        columnIndex = 0;
        calculateFrame();
        return true;
    }

    public boolean moveDown() {
        //计算出一个中心点坐标 然后分发成点击事件
        //得到中心点的坐标
        int centerX = getFrame().getWidth() / 2 + getFrame().getX();
        int centerY = getFrame().getHeight() / 2 + getFrame().getY();
        //获得当前行的高度
        Page page = (Page) composition.getChildren().get(pageIndex);
        Row row = (Row) page.getChildren().get(rowIndex);
        //进行偏移
        centerY += row.getFrame().getHeight();
        composition.dispatchClickEvent(new MouseEvent(centerX, centerY));
        return false;
    }

    public boolean moveToPreviousRow() {
        if (rowIndex <= 0)
            return false;
        rowIndex--;
        columnIndex = 0;
        calculateFrame();
        return true;
    }

    public void moveToLineEnd() {
        Page page = (Page) composition.getChildren().get(pageIndex);
        Row row = (Row) page.getChildren().get(rowIndex);
        columnIndex = row.getChildren().size();
        calculateFrame();
    }


    public boolean moveUp() {
        //计算出一个中心点坐标 然后分发成点击事件
        //得到中心点的坐标
        int centerX = getFrame().getWidth() / 2 + getFrame().getX();
        //Caret没有高度
        int centerY = getFrame().getHeight() / 2 + getFrame().getY();
        //获得当前行的高度
        Page page = (Page) composition.getChildren().get(pageIndex);
        Row row = (Row) page.getChildren().get(rowIndex);
        //进行偏移
        centerY -= row.getFrame().getHeight();
        composition.dispatchClickEvent(new MouseEvent(centerX, centerY));
        return false;
    }


    /**
     * 根据所赋值的页,行,列的值计算出caret的高度、x值、y值
     */
    public void calculateFrame() {
//        ensureIndexAvailable();
        //根据三个index计算x,y值.难点:文字的大小无法确定
        //第一步:取到对应的row page column
        Page page = (Page) composition.getChildren().get(pageIndex);
        Row row = (Row) page.getChildren().get(rowIndex);
        //TODO 向下和其他的默认值需要查找
        List<GlyphImpl> rowChildren = row.getChildren();
        //TODO caret的高度的默认值需要设置
        if (columnIndex <= 0)
            setFrame(row.getFrame());
        else {
            GlyphImpl glyphImpl = rowChildren.get(columnIndex - 1);
            Frame frame = new Frame(glyphImpl.getFrame());
            frame.setX(frame.getX() + frame.getWidth());
            frame.setHeight(frame.getHeight());
            setFrame(frame);
        }
    }
}
