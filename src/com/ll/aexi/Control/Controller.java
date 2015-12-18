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
public class Controller implements CaretListener, KeyListener, CompositionListener, MouseListener,MouseMotionListener{

    private Composition composition;
    private TextView textView;

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
        CommandManager commandManager = CommandManager.getInstance();
        Command command = null;
        if (e.getKeyChar() == FormatCharacter.BACKSPACE) {
            command = new DeleteCommand(composition);
        }
        else {
            Character character = null;
            if (e.getKeyChar() == FormatCharacter.NEW_LINE) {
                Font font = new Font(Setting.fontName, Setting.fontStyle, Setting.fontSize);
                character = new FormatCharacter(FormatCharacter.NEW_LINE, font);
            } else {
                Font font = new Font(Setting.fontName, Setting.fontStyle, Setting.fontSize);
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
        GlyphImpl startGlyph = findHitedGlyph(e);
        if (startGlyph != null)
            composition.getSelection().setStartIndex(startGlyph.getDocumentIndex());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
        GlyphImpl endGlyph = findHitedGlyph(e);
        if (endGlyph != null)
            composition.getSelection().setEndIndex(endGlyph.getDocumentIndex());
        composition.dispatchClickEvent(new com.ll.aexi.Model.MouseEvent(e));
    }

    private GlyphImpl findHitedGlyph(MouseEvent e) {
        Document doc =  composition.getDocument();
        GlyphImpl hitedGlyph = null;
        for (GlyphImpl glyph : doc) {
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
        GlyphImpl endGlyph = findHitedGlyph(e);
        if (endGlyph != null)
            composition.getSelection().setEndIndex(endGlyph.getDocumentIndex());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

}
