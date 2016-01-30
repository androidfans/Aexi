package com.ll.aexi;

import com.ll.aexi.Control.Controller;
import com.ll.aexi.Model.Caret;
import com.ll.aexi.Model.Composition;
import com.ll.aexi.View.AexiMenu;
import com.ll.aexi.View.TextView;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


/**
 * Created by Liuli on 2015/3/8 0008.
 */
public class Application {
    public static void main(String[] args) {
        try {
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        JFrame frame = new JFrame("Aexi");
        frame.setBounds(500, 200, 1000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Composition composition = Composition.getInstance();
                int Space = e.getComponent().getWidth() - composition.getWidth();
                if (Space > 0) {
                    composition.setX(Space / 2);
                } else {
                    composition.setX(composition.getPageStyle().getLeftMargin());
                }
            }
        });

        TextView textView = new TextView();
        textView.init();
        frame.add(textView);

        Controller controller = new Controller();
        controller.setTextView(textView);
        frame.addKeyListener(controller);
        textView.addMouseListener(controller);
        textView.addMouseMotionListener(controller);
        textView.setCaret(Caret.getInstance());

        Composition composition = Composition.getInstance();
        composition.setCompositionListener(controller);
        controller.setComposition(composition);
        textView.setComposition(composition);

        JMenuBar aexiMenu = new AexiMenu();
        frame.setJMenuBar(aexiMenu);
        frame.setVisible(true);
    }
}
