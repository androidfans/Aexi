package com.ll.aexi.Control;

import com.ll.aexi.Model.*;
import com.ll.aexi.Model.Character;

import java.util.List;

/**
 * Created by Liuli on 2015/5/5.
 */
public class DeleteCommand implements Command {

    private Composition composition;


    public DeleteCommand(Composition composition) {
        this.composition = composition;
    }

    @Override
    public boolean excute() {
        Caret caret = Caret.getInstance();
        if (composition.getSelection().getEndIndex() != Selection.UN_SELECTED) {
            composition.getSelection().deleteSelection();
            caret.moveToLineEnd();
            return true;
        }
        GlyphImpl glyph = caret.getHostGlyph();
        GlyphImpl nextGlyph = null;
        //更换到宿主前一个,然后删掉宿主
        Document list = composition.getDocument();
        if (glyph == null) {
            return false;
        }
        int index = list.indexOf(glyph);
        if (index != 0) {
            nextGlyph = list.get(index - 1);
        }
        caret.setHostGlyph(nextGlyph);
        //这里有点屎
        composition.remove(index);
        return true;
    }

    @Override
    public void unExcute() {

    }

    @Override
    public boolean canUndo() {
        return false;
    }
}
