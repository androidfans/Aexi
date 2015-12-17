package com.ll.aexi.Model;

import java.util.List;

/**
 * Created by Administrator on 2015/5/11.
 */
public class Selection {
    private int startIndex = UN_SELECTED;
    private int endIndex = UN_SELECTED;
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
        refreshGlyphsSelected(getMin(startIndex, endIndex), getMax(startIndex,endIndex));
    }

    private void refreshGlyphsSelected(int startIndex,int endIndex) {
        Document doc = composition.getDocument();
        clearGlyphSelected(doc);
        for (int i = startIndex; i <= endIndex; i++) {
            GlyphImpl basicGlyph = (GlyphImpl) doc.get(i);
            basicGlyph.setIsSelected(true);
        }
    }

    private void clearGlyphSelected(Document doc) {
        for (int i = 0; i < doc.size(); i++) {
           GlyphImpl basicGlyph = (GlyphImpl) doc.get(i);
            basicGlyph.setIsSelected(false);
        }
    }

    public boolean deleteSelection() {
        if (endIndex == UN_SELECTED) {
            return false;
        }
        int min = getMin(startIndex, endIndex);
        int max = getMax(startIndex, endIndex);
        for (int i = min; i <= max; i++) {
            composition.remove(min);
        }
        endIndex = UN_SELECTED;
        return false;
    }

    public void unSelected() {
        endIndex = UN_SELECTED;
        startIndex = UN_SELECTED;
        Document doc = composition.getDocument();
        clearGlyphSelected(doc);
    }

    private int getMax(int a, int b) {
        if (a >= b) {
            return a;
        }
        return b;
    }

    private int getMin(int a, int b) {
        if (a <= b) {
            return a;
        }
        return b;
    }

}
