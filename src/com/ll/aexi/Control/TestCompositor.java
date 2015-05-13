package com.ll.aexi.Control;

import com.ll.aexi.Model.*;
import com.ll.aexi.Model.Character;

import java.awt.*;
import java.util.Iterator;

public class TestCompositor implements Compositor {

    public Composition composition;

    @Override
    public void compose() {
        composition.clear();
        Iterator it = composition.getDocument().getChildren().iterator();
        /**
         * 只考虑文字,不考虑每行的字符数限制,不考虑对行数进行分页时的格式化思路
         * 第一步:创建一个page一个row,并将row加入到page当中去
         * 第二步:依次取出文档中的字符进行判断,如果是普通字符,跳转第三步,如果是换行符跳转到第四步
         * 第三步:将取出的字符添加到当前的row中
         * 第四步:创建一个新的row,append到page中,并将该row的引用赋值给当前row
         */
        //page的初始化
        //TODO 如果document是空  那么应该恢复为初始状态
        Page page = new Page();
        composition.append(page);
        Row row = new Row();
        //当前的排版算法非常不安全
        for (int i = 0; it.hasNext(); i++) {
            BasicGlyph glyph = (BasicGlyph) it.next();
            glyph.setDocumentIndex(i);
            glyph.setListener(composition);
            if (glyph instanceof Character){
                if (((Character)glyph).getaChar() == '\n') {
                    row = new Row();
                    row.setStartDocumentIndex(i);
                    page.append(row);
                    row.append(glyph);
                    continue;
                }
            }
            if (!row.append(glyph)) {
                row = new Row();
                row.setStartDocumentIndex(i);
                page.append(row);
                row.append(glyph);
            }
        }
        if (page.getChildren().size() <= 0) {
            page.append(row);
        }
    }

    @Override
    public void setComposition(Composition composition) {
        this.composition = composition;
    }
}
