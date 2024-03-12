/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import eg.edu.alexu.csd.oop.game.GameEngine;
import eg.edu.alexu.csd.oop.game.GameObject;
import eg.edu.alexu.csd.oop.game.World;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author es-ahmedalizakaryah2
 */
public class CircusWorld {

    private final Supplier<World> gameSupplier;
    private JFrame gameFrame;
    private GameEngine.GameController gameController;
    private JFrame parent;

    public CircusWorld(Supplier<World> gameSupplier, JFrame parent) {
        this.gameSupplier = gameSupplier;
        this.parent = parent;
    }

    public void startGame() {
        JMenuBar menuBar = createMenuBar();
        World game = gameSupplier.get();
        this.gameController = GameEngine.start("Circus Game", game, menuBar, Color.BLACK);
        this.gameFrame = (JFrame) menuBar.getParent().getParent().getParent();
        gameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.gameFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(gameFrame, "Are you sure you want to close this game?",
                        "End Game?", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                    gameFrame.dispose();
                    parent.setVisible(true);
                    
                }
            }
        });
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu1 = new JMenu("Game");
        JMenuItem menuItem1 = new JMenuItem("Pause");
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               gameController.pause();
            }
        });
        JMenuItem menuItem2 = new JMenuItem("Resume");
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                gameController.resume();
            }
        });
        JMenuItem menuItem3 = new JMenuItem("New Game");
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                 gameFrame.dispose();
                  startGame();
            }
        });
        menu1.add(menuItem3);
        menu1.add(menuItem1);
        menu1.add(menuItem2);
        menuBar.add(menu1);

        return menuBar;
    }

    public JFrame getFrame() {
        return gameFrame;
    }

    public GameEngine.GameController getController() {
        return gameController;
    }
}
