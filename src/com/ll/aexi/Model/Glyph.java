package com.ll.aexi.Model;

import java.awt.*;

/**
 * Created by Administrator on 2015/3/19.
 */
public interface Glyph {
    void drawMe(Graphics g);

    boolean hitRect(int x, int y);

    boolean dispatchClickEvent(MouseEvent e);

    boolean onClickEvent(MouseEvent e);

    boolean interceptClickEvent(MouseEvent e);
}
