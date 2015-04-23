package com.ll.aexi.Model;

/**
 * Created by Administrator on 2015/3/20.
 */
public class Position {
    private int pageIndex;
    private int rowIndex;
    private int columnIndex;

    public Position(int pageIndex, int rowIndex, int columnIndex) {
        this.pageIndex = pageIndex;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public int getPageIndex() {

        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
}