package com.ll.aexi.Control;

import com.ll.aexi.Interface.CaretListener;
import com.ll.aexi.Interface.CompositionListener;
import com.ll.aexi.Model.Caret;
import com.ll.aexi.Model.Character;
import com.ll.aexi.Model.Composition;
import com.ll.aexi.Model.Glyph;
import com.ll.aexi.View.TextView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *处理键盘及鼠标逻辑的控制器类
 * Created by Liuli on 2015/3/19.
 */
public class Controller implements CaretListener, KeyListener, CompositionListener, MouseListener {

    private Composition composition;
    private TextView textView;
    //当前设置的字体信息,先设置一个默认值
    private Font font = new Font("宋体", Font.PLAIN, 20);

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
        composition.insert(new Character(e.getKeyChar(), font), composition.getCaret().getDocumentIndex());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //方向键的按下不会触发keyTyped事件
        System.out.println("key preesed");
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
    }
}
