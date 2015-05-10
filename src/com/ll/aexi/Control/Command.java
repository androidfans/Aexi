package com.ll.aexi.Control;

/**
 * Created by Administrator on 2015/4/29.
 */
public interface Command {
    public boolean excute();

    public void unExcute();

    public boolean canUndo();
}