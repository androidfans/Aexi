package com.ll.aexi.Model;

import java.util.List;

/**
 * Created by Administrator on 2015/5/11.
 */
public class Selection {
    private int startIndex;
    private int endIndex;
    private Composition composition;
    public static final int UN_SELECTED = -1;

    public Selection(Composition composition) {
        this.composition = composition;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
        if (endIndex == UN_SELECTED)
            return;
        if (startIndex < endIndex) {
            refreshGlyphsSelected(startIndex,endIndex);
        }else {
            refreshGlyphsSelected(endIndex,startIndex);
        }
    }

    private void refreshGlyphsSelected(int startIndex,int endIndex) {
        List<GlyphImpl> glyphs = composition.getDocument().getChildren();
        clearGlyphSelected(glyphs);
        for (int i = startIndex; i <= endIndex; i++) {
            BasicGlyph basicGlyph = (BasicGlyph) glyphs.get(i);
            basicGlyph.setIsSelected(true);
        }
    }

    private void clearGlyphSelected(List glyphs) {
        for (int i = 0; i < glyphs.size(); i++) {
            BasicGlyph basicGlyph = (BasicGlyph) glyphs.get(i);
            basicGlyph.setIsSelected(false);
        }
    }

    public void unSelected() {
        endIndex = UN_SELECTED;
        startIndex = UN_SELECTED;
        List<GlyphImpl> glyphs = composition.getDocument().getChildren();
        clearGlyphSelected(glyphs);
    }
}
