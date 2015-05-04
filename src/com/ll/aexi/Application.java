package com.ll.aexi;

import com.ll.aexi.Control.Controller;
import com.ll.aexi.Model.Composition;
import com.ll.aexi.View.TextView;

import javax.swing.*;


/**
 * Created by Liuli on 2015/3/8 0008.
 */
public class Application {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Aexi");
        frame.setBounds(500, 200, 500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TextView textView = new TextView();
        textView.init();
        frame.add(textView);

        Controller controller = new Controller();
        controller.setTextView(textView);
        frame.addKeyListener(controller);
        textView.addMouseListener(controller);

        Composition composition = new Composition();
        composition.setCompositionListener(controller);
        controller.setComposition(composition);
        textView.setComposition(composition);
    }
}
