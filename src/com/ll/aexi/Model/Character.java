package com.ll.aexi.Model;

import sun.font.FontDesignMetrics;

import java.awt.*;

/**
 * Created by Liuli on 2015/3/21.
 */
public class Character extends BasicGlyph {
    private char aChar;
    private Font font;
    private FontMetrics fm;

    public Character(char aChar, Font font) {
        this.font = font;
        this.aChar = aChar;
        //初始化frame
        fm = FontDesignMetrics.getMetrics(font);
        Frame frame = getFrame();
        frame.setWidth(fm.charWidth(aChar));
        frame.setHeight(fm.getAscent() + fm.getDescent());
    }

    public char getaChar() {
        return aChar;
    }

    public void setaChar(char aChar) {
        this.aChar = aChar;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public void drawMe(Graphics g) {
        if (isSelected()) {
            g.setColor(new Color(0x46,0xa3,0xff));
            g.fillRect(frame.getX(),frame.getY(),frame.getWidth(),frame.getHeight());
        }
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawString(aChar + "", frame.getX(), frame.getY() + fm.getAscent());
    }


    @Override
    public boolean hitRect(int x, int y) {
        if (!super.hitRect(x, y))
            return false;
        /**
         * 在这里应该修改caret的position.
         * 可是会出现问题
         * 问题一:在这里修改了position的话,那么对应的documentIndex也应该修改,怎么样通过position计算出DocumentIndex
         * 问题二:每个"基本图元",都要对点击事件响应以便修改caret的坐标,但是又不能写在glyphImpl里面,因为glyphImplGroup也是继承自glyphImpl,
         * 难道每增加一种"基本图元"类,就要复写一个完全相同的东西吗
         */
        System.out.println("击中字符" + aChar);
        return true;
    }

    @Override
    public boolean onClickEvent(MouseEvent e) {
        System.out.println("onClickEvent:" + aChar);
        return super.onClickEvent(e);
    }
}
