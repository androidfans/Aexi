package com.ll.aexi.View;

import javax.swing.*;

/**
 * Created by Administrator on 2015/5/5.
 */
public class AexiMenu extends JMenuBar {
    public AexiMenu() {
        JMenu fileMenu = new JMenu("文件");
        JMenuItem newItem = new JMenuItem("新建");
        JMenuItem openItem = new JMenuItem("打开");
        JMenuItem closeItem = new JMenuItem("关闭");
        JMenuItem saveItem = new JMenuItem("保存");
        JMenuItem saveAsItem = new JMenuItem("另存为");
        JMenuItem propertiesItem = new JMenuItem("属性");
        JMenuItem exitItem = new JMenuItem("退出");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(closeItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.add(propertiesItem);
        fileMenu.add(exitItem);

        JMenu editMenu = new JMenu("编辑");
        JMenuItem undoItem = new JMenuItem("撤销");
        JMenuItem redoItem = new JMenuItem("重做");
        JMenuItem cutItem = new JMenuItem("剪切");
        JMenuItem copyItem = new JMenuItem("复制");
        JMenuItem pasteItem = new JMenuItem("粘贴");
        JMenuItem deleteItem = new JMenuItem("删除");
        JMenuItem selectAllItem = new JMenuItem("全选");
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.add(cutItem);
        editMenu.add(copyItem);
        editMenu.add(pasteItem);
        editMenu.add(deleteItem);
        editMenu.add(selectAllItem);

        JMenu insertMenu = new JMenu("插入");
        JMenuItem pictureItem = new JMenuItem("图片");
        insertMenu.add(pictureItem);


        JMenu formatMenu = new JMenu("格式化");
        JMenuItem fontItem = new JMenuItem("字体");
        JMenuItem paragraphItem = new JMenuItem("段落");
        formatMenu.add(fontItem);
        formatMenu.add(paragraphItem);

        JMenu toolsMenu = new JMenu("工具");
        JMenuItem spellingCheckItem = new JMenuItem("拼写检查");
        JMenuItem wordCountItem = new JMenuItem("字数统计");
        toolsMenu.add(spellingCheckItem);
        toolsMenu.add(wordCountItem);


        JMenu helpMenu = new JMenu("帮助");
        JMenuItem aexiOnlineItem = new JMenuItem("Aexi官网");
        JMenuItem aboutItem = new JMenuItem("关于");
        helpMenu.add(aexiOnlineItem);
        helpMenu.add(aboutItem);


        add(fileMenu);
        add(editMenu);
        add(insertMenu);
        add(formatMenu);
        add(toolsMenu);
        add(helpMenu);




    }
}
