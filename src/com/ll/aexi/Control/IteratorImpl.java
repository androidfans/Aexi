package com.ll.aexi.Control;

import com.ll.aexi.Model.Glyph;

/**
 * Created by Liuli on 2015/3/25.
 */
public abstract class IteratorImpl implements iterator {
    private Glyph glyph;

    public void setGlyph(Glyph glyph) {
        this.glyph = glyph;
    }
}
