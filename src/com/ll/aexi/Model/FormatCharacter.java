package com.ll.aexi.Model;

import java.awt.*;

/**
 * Created by liuli on 15-12-17.
 */
public class FormatCharacter extends Character {

    //暂时先只处理换行
    public static final char NEW_LINE = '\n';

    public static final char BACKSPACE = '\b';
    public FormatCharacter(char aChar, Font font) {
        super(aChar, font);
    }

}
