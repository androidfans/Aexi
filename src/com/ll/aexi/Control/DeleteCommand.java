package com.ll.aexi.Control;

import com.ll.aexi.Model.*;
import com.ll.aexi.Model.Character;

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
        //TODO 这样的代码有点难以维护,需要修改
        Character character = (Character) composition.remove(index);

        if (character.getaChar() == '\n') {
            caret.moveToPreviousRow();
            caret.moveToLineEnd();
        }else if (!caret.moveLeft()) {
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
