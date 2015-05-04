package com.ll.aexi.Model;

/**
 * Created by Administrator on 2015/3/21.
 */
public class Document extends GlyphImplGroup {
    public void debug() {
        for (Glyph glyph : getChildren()) {
            Character character = (Character) glyph;
            String string = character.getaChar() + "";
            if (character.getaChar() == '\n')
                string = "\\n";
            System.out.print(string);
            System.out.println();
        }
    }

    @Override
    public boolean insert(GlyphImpl glyph, int index) {
        super.insert(glyph, index);
        debug();
        return true;
    }
}