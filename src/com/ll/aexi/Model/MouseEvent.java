package com.ll.aexi.Model;

/**
 * 自定义的鼠标事件类,封装了鼠标的坐标,用来屏蔽java平台的细节
 * Created by Liuli on 2015/4/23.
 */
public class MouseEvent {
    private java.awt.event.MouseEvent mouseEvent;
    private int x;
    private int y;

    public MouseEvent(java.awt.event.MouseEvent mouseEvent) {
        setMouseEvent(mouseEvent);
    }

    public MouseEvent(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public java.awt.event.MouseEvent getMouseEvent() {
        return mouseEvent;
    }

    public void setMouseEvent(java.awt.event.MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        setX(mouseEvent.getX());
        setY(mouseEvent.getY());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
