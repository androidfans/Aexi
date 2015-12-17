package com.ll.aexi.Model;

/**
 * Created by Administrator on 2015/3/21.
 */
public class Frame {
    private int x = 0;
    private int y = 0;
    private int width = 0;
    private int height = 0;

    public Frame(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Frame(Frame frame) {
        this(frame.getX(), frame.getY(), frame.getWidth(), frame.getHeight());
    }

    public void reset(Frame frame) {
        setX(frame.getX());
        setY(frame.getY());
        setHeight(frame.getHeight());
        setWidth(frame.getWidth());
    }

    public Frame() {
        this(0, 0, 0, 0);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        System.out.println("set x : "+ x);
        this.x = x;
    }

    @Override
    public String toString() {
        return "x : " + x + " y : " + y + " height : " + height + " width : " + width;
    }
}
