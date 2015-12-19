package com.ll.aexi.Model;

import com.ll.aexi.Control.Compositor;
import com.ll.aexi.Control.StandardCompositor;
import com.ll.aexi.Interface.CaretListener;
import com.ll.aexi.Interface.CompositionListener;
import com.ll.aexi.Interface.GlyphListener;
import com.ll.aexi.View.PageStyle;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liuli on 2015/3/21.
 */
public class Composition extends GlyphImplGroup implements CaretListener,GlyphListener {
    private Compositor compositor;
    private Document document;
    private CompositionListener compositionListener;
    private Caret caret;
    private PageStyle pageStyle;
    private Selection selection = new Selection(this);
    private static Composition composition = new Composition();

    public Selection getSelection() {
        return selection;
    }

    public void setSelection(Selection selection) {
        this.selection = selection;
    }

    private Composition() {
        init();
    }

    @Override
    public void drawMe(Graphics g) {
        //画背景
        g.setColor(Color.WHITE);
        g.fillRect(frame.getX(),frame.getY(),frame.getWidth(),frame.getHeight());
        super.drawMe(g);
    }

    public void init() {
        document = new Document();
        //设置页面的大小
        pageStyle = new PageStyle();
        frame.setX(50);
        frame.setY(100);
        frame.setHeight(pageStyle.getHeight());
        frame.setWidth(100);
        //TODO 改成工厂模式,使用配置文件生成
        Compositor compositor = new StandardCompositor();
        compositor.setComposition(this);
        setCompositor(compositor);
        compositor.compose();
        Caret caret = Caret.getInstance();
        caret.setComposition(this);
        setCaret(caret);
        caret.setHostGlyph(null);
    }

    public void setCompositor(Compositor compositor) {
        this.compositor = compositor;
    }

    public Caret getCaret() {
        return caret;
    }

    public void setCaret(Caret caret) {
        this.caret = caret;
    }

    @Override
    public boolean append(GlyphImpl glyph) {
        //TODO 不可能只有一个page需要改进
        //TODO 不需要重新new一个frame
        glyph.setFrame(new Frame(frame.getX(),frame.getY(), 100 , pageStyle.getHeight()));
        super.append(glyph);
        if (compositionListener != null)
            compositionListener.documentRefresh(this);
        return true;
    }

    public void setCompositionListener(CompositionListener compositionListener) {
        this.compositionListener = compositionListener;
    }

    public Document getDocument() {
        return document;
    }

    @Override
    public void CaretRefresh(Glyph glyph) {
        if (compositor != null)
            compositor.compose();
        if (compositionListener != null)
            compositionListener.documentRefresh(this);
    }

    @Override
    public boolean insert(GlyphImpl glyph, int index) {
        if (compositor == null) {
            return false;
        }
        document.add(index,glyph);
        compositor.compose();
        caret.setHostGlyph(glyph);
        return true;
    }

    @Override
    public boolean hitRect(int x, int y) {
        List<GlyphImpl> children = getChildren();
        for (Glyph glyph : children) {
            glyph.hitRect(x, y);
        }
        return true;
    }

    @Override
    public Glyph remove(int index) {
        Glyph glyph = document.remove(index);
        if (compositor != null) {
            compositor.compose();
        }
        return glyph;
    }

    public static Composition getInstance() {
        return composition;
    }

    @Override
    public void glyphRefresh() {
        if (compositor != null) {
            compositor.compose();
        }
    }
}
