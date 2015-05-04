package com.ll.aexi.View;

/**
 * Created by Administrator on 2015/5/4.
 */
public class PageStyle {
    public static final int MIN_MARGIN = 12;

    public static final int MIN_BODY_WIDTH = 48;

    public static final int MIN_BODY_HEIGHT = 50;

    // member variables:
    private int width;
    private int height;

    private int leftMargin;
    private int rightMargin;

    private int topMargin;
    private int bottomMargin;

    public PageStyle() {
        reset(Paper.A4);
    }

    public PageStyle(Paper paper) {
        reset(paper);
    }

    public void reset(Paper paper) {
        this.width = paper.width;
        this.height = paper.height;
        this.leftMargin = paper.leftMargin;
        this.rightMargin = paper.rightMargin;
        this.topMargin = paper.topMargin;
        this.bottomMargin = paper.bottomMargin;
    }

    public int contentWidth() {
        return width - leftMargin - rightMargin;
    }

    public int contentHeight() {
        return height - topMargin - bottomMargin;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    public int getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(int rightMargin) {
        this.rightMargin = rightMargin;
    }

    public int getTopMargin() {
        return topMargin;
    }

    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }

    public int getBottomMargin() {
        return bottomMargin;
    }

    public void setBottomMargin(int bottomMargin) {
        this.bottomMargin = bottomMargin;
    }
}
