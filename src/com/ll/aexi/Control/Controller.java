package com.ll.aexi.Control;

import com.ll.aexi.Interface.CaretListener;
import com.ll.aexi.Interface.CompositionListener;
import com.ll.aexi.Model.*;
import com.ll.aexi.Model.Character;
import com.ll.aexi.View.TextView;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *处理键盘及鼠标逻辑的控制器类
 * Created by Liuli on 2015/3/19.
 */
public class Controller implements CaretListener, KeyListener, CompositionListener, MouseListener,MouseMotionListener {

    private Composition composition;
    private TextView textView;
    //当前设置的字体信息,先设置一个默认值
    private Font font = new Font("微软雅黑", Font.BOLD, 20);

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public void setComposition(Composition composition) {
        composition.setCompositionListener(this);
        this.composition = composition;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println("key typed");
        //TODO 需要把这里抽象出一层,按下特殊功能键时,keytyped仍然会被调用,keychar为\n
//        composition.insert(new Character(e.getKeyChar(), font), composition.getCaret().getDocumentIndex());
        CommandManager commandManager = CommandManager.getInstance();
        Command command = null;

        //现在存在的问题:代码太多插入一个字符需要修改太多的代码.
        //步骤太过于复杂
        //但是不可以把诸如insert之类的command封装到commandManager的内部,如果需要增加新的操作就需要修改commandMannager和command两个地方
        //但是可以考虑的是对于的文档的操作的数量基本上是固定的.
        //先不进行修改,先完成其他的地方
        if (e.getKeyChar() == '\b') {
            command = new DeleteCommand(composition);
        }
        else {
            Character character = null;
            if (e.getKeyChar() == FormatCharacter.NEW_LINE) {
                character = new FormatCharacter(FormatCharacter.NEW_LINE, font);
            } else {
                character = new Character(e.getKeyChar(), font);
            }
            command = new InsertCommand(composition,character);
        }
        commandManager.setCurrentCommand(command);
        commandManager.excuteCommand();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Caret caret = Caret.getInstance();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                caret.moveRight();
                break;
            case KeyEvent.VK_LEFT:
                caret.moveLeft();
                break;
            case KeyEvent.VK_DOWN:
                caret.moveDown();
                break;
            case KeyEvent.VK_UP:
                caret.moveUp();
                break;
        }
        System.out.println("key preesed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("key released");
    }

    @Override
    public void documentRefresh(Composition composition) {
        textView.setComposition(composition);
        textView.repaint();
    }

    @Override
    public void CaretRefresh(Glyph glyph) {
        textView.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");
        composition.dispatchClickEvent(new com.ll.aexi.Model.MouseEvent(e));
        composition.getSelection().unSelected();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
        //找到遍历找到哪个字符被击中了
        BasicGlyph startGlyph = findHitedGlyph(e);
        if (startGlyph != null)
            composition.getSelection().setStartIndex(startGlyph.getDocumentIndex());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
        BasicGlyph endGlyph = findHitedGlyph(e);
        if (endGlyph != null)
            composition.getSelection().setEndIndex(endGlyph.getDocumentIndex());
        composition.dispatchClickEvent(new com.ll.aexi.Model.MouseEvent(e));
    }

    private BasicGlyph findHitedGlyph(MouseEvent e) {
        ArrayList<BasicGlyph> characters = (ArrayList) composition.getDocument();
        BasicGlyph hitedGlyph = null;
        for (BasicGlyph glyph : characters) {
            if (glyph.hitRect(e.getX(), e.getY())) {
                hitedGlyph = glyph;
                break;
            }
        }
        return hitedGlyph;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("mouseReleased");
        BasicGlyph endGlyph = findHitedGlyph(e);
        if (endGlyph != null)
            composition.getSelection().setEndIndex(endGlyph.getDocumentIndex());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
