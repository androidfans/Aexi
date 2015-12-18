package com.ll.aexi.View;

import com.ll.aexi.Interface.CaretListener;
import com.ll.aexi.Model.Caret;
import com.ll.aexi.Model.Composition;
import com.ll.aexi.Model.Glyph;
import javax.swing.*;
import java.awt.*;

public class TextView extends JPanel implements CaretListener {
    private Composition composition;
    private Caret caret;

    public void init() {
        Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
        setCursor(cursor);
    }

    public void setComposition(Composition composition) {
        this.composition = composition;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (composition == null || caret == null)
            return;
        drawBackGroud(g);
        composition.drawMe(g);
        caret.drawMe(g);
    }

    private void drawBackGroud(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setCaret(Caret caret) {
        this.caret = caret;
        caret.setCaretListener(TextView.this);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags, int x, int y, int w, int h) {
        return super.imageUpdate(img, infoflags, x, y, w, h);
    }

    @Override
    public void CaretRefresh(Glyph glyph) {
        repaint();
    }
}
