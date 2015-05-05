package com.ll.aexi.Control;

import com.ll.aexi.Model.Caret;
import com.ll.aexi.Model.Composition;

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
        int index = caret.getInsertIndex() - 1;
        if (index < 0)
            return false;
        composition.remove(index);
        if (!caret.moveLeft()) {
            caret.moveToPreviousRow();
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
