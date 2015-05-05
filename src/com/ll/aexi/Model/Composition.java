package com.ll.aexi.Model;

import com.ll.aexi.Control.Compositor;
import com.ll.aexi.Control.TestCompositor;
import com.ll.aexi.Interface.CaretListener;
import com.ll.aexi.Interface.CompositionListener;
import com.ll.aexi.View.PageStyle;

import java.awt.*;
import java.util.List;

/**
 * Created by Liuli on 2015/3/21.
 */
public class Composition extends GlyphImplGroup implements CaretListener {
    private Compositor compositor;
    private Document document;
    private CompositionListener compositionListener;
    private Caret caret;
    private PageStyle pageStyle;

    public Composition() {
        init();
    }

    public void init() {
        document = new Document();
        //设置页面的大小
        //这里不应该给textView设置大小,textView的大小应该由屏幕决定,而页面内部应该可以画滚动条
        pageStyle = new PageStyle();
        document.append(new Character('\n', new Font("宋体", Font.PLAIN, 20)));
        //TODO 改成工厂模式,使用配置文件生成
        Compositor compositor = new TestCompositor();
        compositor.setComposition(this);
        setCompositor(compositor);
        compositor.compose();
        Caret caret = Caret.getInstance();
        caret.setComposition(this);
        setCaret(caret);
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
        Page page = (Page) glyph;
        glyph.setFrame(new Frame(pageStyle.getLeftMargin(), pageStyle.getTopMargin(), pageStyle.getWidth(), pageStyle.getHeight()));
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
        document.insert(glyph, index);
        compositor.compose();
        int insertIndex = caret.getInsertIndex();
        if (!caret.moveRight()) {
            caret.moveToNextRow();
            caret.moveRight();
        }
        if (compositor != null)
            compositor.compose();
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
    public boolean remove(int index) {
        document.remove(index);
        if (compositor != null) {
            compositor.compose();
        }
        return true;
    }
}