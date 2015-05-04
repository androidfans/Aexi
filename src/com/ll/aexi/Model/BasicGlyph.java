package com.ll.aexi.Model;

/**
 * Created by Liuli on 2015/4/17.
 */
public abstract class BasicGlyph extends GlyphImpl {
    private int documentIndex = 0;

    public int getDocumentIndex() {
        return documentIndex;
    }

    public void setDocumentIndex(int documentIndex) {
        this.documentIndex = documentIndex;
    }

    @Override
    public boolean onClickEvent(MouseEvent e) {
        //必须创建新对象,如果不创建一个新的对象会导致相应的字符的frame被重新赋值,造成一些不希望的错误
        int indexSupply = 0;

        //计算frame的代码和设置行列的代码功能有点重复,

        Frame frame = new Frame(getFrame());
        if (e.getX() - frame.getX() >= (frame.getWidth() / 2)) {
            frame.setX(frame.getX() + frame.getWidth());
            indexSupply = 1;
        }
        Caret caret = Caret.getInstance();
        caret.setFrame(frame);
        Row row = (Row) getParent();
        Page page = (Page) row.getParent();
        GlyphImplGroup pageParent = (GlyphImplGroup) page.getParent();
        caret.setPageIndex(pageParent.getChildren().indexOf(page));
        caret.setRowIndex(page.getChildren().indexOf(row));
        caret.setColumnIndex(row.getChildren().indexOf(this) + indexSupply);
        return true;
    }
}
