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
        //更换到宿主前一个,然后删掉宿主
        List<GlyphImpl> list = composition.getDocument().getChildren();
        if (list.size() == 0) {
            return false;
        }
        
        //TODO 这样的代码有点难以维护,需要修改
        if (glyph instanceof Character && ((Character) glyph).getaChar() == '\n') {
            caret.moveToLineEnd();
        } else if (!caret.moveLeft()) {
            caret.moveToLineEnd();
        }
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
