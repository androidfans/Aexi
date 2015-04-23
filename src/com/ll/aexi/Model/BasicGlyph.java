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
    public boolean hitRect(int x, int y) {
        if (!super.hitRect(x, y))
            return false;
        /**
         * 在这里应该修改caret的position.
         * 可是会出现问题
         * 问题一:在这里修改了position的话,那么对应的documentIndex也应该修改,怎么样通过position计算出DocumentIndex
         * 问题二:每个图元怎么知道自己的页,行,列
         */
        /**
         *修改caret的Frame,这里应该分为两步
         * 第一步:根据坐标点判断点击的位置更加接近当前图元的左边还是右边
         * 第二步:如果更加接近左边就把caret的frame赋值为glyph的x和
         *        如果更加接近右边就要把caret的frame赋值为x,+frame.width,y
         */
        /**
         * 修改完Frame之后需要修改documentIndex,还有三个Inex,最好能够封装到caret的内部
         * 要获取该glyph对象在整个document对象中的位置index
         * 如果插入在左边,就直接赋值为index
         * 在右边就赋值为index + 1
         * 关键在于每个glyph的documentIndex和换行符的关系需要在compositor的时候进行确定
         */
        //必须创建新对象,如果不创建一个新的对象会导致相应的字符的frame被重新赋值,造成一些不希望的错误

        int indexSupply = 0;

        Frame frame = new Frame(getFrame());
        if (x - frame.getX() >= (frame.getWidth() / 2)) {
            frame.setX(frame.getX() + frame.getWidth());
            indexSupply = 1;
        }

        //这部分代码放在这个函数里面有点不是很适合
        Caret caret = Caret.getInstance();
        caret.setFrame(frame);
        caret.setDocumentIndex(documentIndex + indexSupply);
        Row row = (Row) getParent();
        Page page = (Page) row.getParent();
        GlyphImplGroup pageParent = (GlyphImplGroup) page.getParent();
        caret.setPageIndex(pageParent.getChildren().indexOf(page));
        caret.setRowIndex(page.getChildren().indexOf(row));
        //这句代码要根据左边还是右边来判断
        caret.setColumnIndex(row.getChildren().indexOf(this) + indexSupply);
        return true;
    }
}
