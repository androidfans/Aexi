package com.ll.aexi.Model;

import com.ll.aexi.Control.Compositor;
import com.ll.aexi.Control.TestCompositor;
import com.ll.aexi.Interface.CaretListener;
import com.ll.aexi.Interface.CompositionListener;

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

    public Composition() {
        init();
    }

    public void init() {
        document = new Document();
        //TODO:这里append进去的东西会一直在最后
//        document.append(new Character('哈', new Font("宋体", Font.PLAIN, 20)));
        //换成insert试试,这里没问题,关键是add完了之后没有修改documentIndex
        document.append(new Character('\n', new Font("宋体", Font.PLAIN, 20)));
        //TODO 改成工厂模式,使用配置文件生成
        Compositor compositor = new TestCompositor();
        compositor.setComposition(this);
        setCompositor(compositor);
        compositor.compose();
        Caret caret = Caret.getInstance();
        caret.setComposition(this);
        setCaret(caret);
        caret.setColumnIndex(1);
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
        //TODO 要做健壮性检查
        Page page = (Page) glyph;
        glyph.setFrame(new Frame(0, 0, 600, 1200));
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
        int documentIndex = caret.getDocumentIndex();
        caret.setDocumentIndex(documentIndex + 1);
        if (!caret.moveRight()) {
            caret.moveToNextRow();
            caret.moveRight();
        }
        if (compositor != null) {
            compositor.compose();
        }
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
}