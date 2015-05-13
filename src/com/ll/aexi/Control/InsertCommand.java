package com.ll.aexi.Control;

import com.ll.aexi.Model.*;
import com.ll.aexi.Model.Character;

/**
 * Created by Administrator on 2015/4/29.
 */
public class InsertCommand implements Command {
    private Composition composition;

    private BasicGlyph glyph;

    public InsertCommand(Composition composition, BasicGlyph glyph) {
        this.composition = composition;
        this.glyph = glyph;
    }

    @Override
    public boolean excute() {
        if (composition == null || glyph == null)
            return false;
        //删除选中的字符
        //TODO 写的太狗屎了  亟待修改
        composition.getSelection().deleteSelection();
        composition.getCaret().moveToLineEnd();
        composition.getSelection().setEndIndex(Selection.UN_SELECTED);
        int insertIndex = composition.getCaret().getInsertIndex();
        return composition.insert(glyph, insertIndex);
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
