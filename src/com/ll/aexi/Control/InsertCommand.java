package com.ll.aexi.Control;

import com.ll.aexi.Model.*;
import com.ll.aexi.Model.Character;

/**
 * Created by Administrator on 2015/4/29.
 */
public class InsertCommand implements Command {
    private Composition composition;

    private com.ll.aexi.Model.Character character;

    public InsertCommand(Composition composition, Character character) {
        this.composition = composition;
        this.character = character;
    }

    @Override
    public boolean excute() {
        if (composition == null || character == null)
            return false;
        return composition.insert(character, composition.getCaret().getInsertIndex());
    }

    @Override
    public void unExcute() {
        //TODO 实现插入的撤销方法实现
    }

    @Override
    public boolean canUndo() {
        return true;
    }
}
