/**
 *
 * @author es-ahmedalizakaryah2
 */
package View;

import Model.EasyDifficulty;
import Model.HardDifficulty;
import Model.MediumDifficulty;
import View.ButtonFactory.ButtonCreation;
import World.Game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;


public class MenuFrame  {
    private Clip backgroundMusicClip;
    JFrame frame = new JFrame();
    JPanel buttonsPanel = new JPanel();
    public Image backImage;
    JLayeredPane layeredPane = new JLayeredPane();
    JButton EasyBtn = new JButton("Easy Level");
    JButton MediumBtn = new JButton("Medium Level");
    JButton HardBtn = new JButton("Hard Level");
    public MenuFrame() throws LineUnavailableException, UnsupportedAudioFileException {
        frame.setTitle("Circus");
        frame.setSize(new Dimension(600,600));
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buttonsPanel.setLayout(new GridLayout(3,1));
        EasyBtn = ButtonCreation.createButton(EasyBtn,frame);
        MediumBtn = ButtonCreation.createButton(MediumBtn,frame);
        HardBtn = ButtonCreation.createButton(HardBtn,frame);
        this.playBackgroundMusic("src\\pics\\music.wav");
     
        buttonsPanel.add(EasyBtn);
        buttonsPanel.add(MediumBtn);
        buttonsPanel.add(HardBtn);
        
        layeredPane.setBounds(0, 0, 600, 600);
        
        try {
            backImage = javax.imageio.ImageIO.read(new File("src/pics/circuis2.jpg")).getScaledInstance(600, 600, Image.SCALE_DEFAULT);
        } catch (IOException e) {
            //throw new RuntimeException(e);
           backImage = null;
        }

        JPanel backgroundPanel = new JPanel(new BorderLayout()) {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(backImage, 0, 0, null);
            }
        };

     
        backgroundPanel.setBounds(0,0,600,600);
        layeredPane.add(backgroundPanel);
        buttonsPanel.setBounds(240, 200, 150, 150);
        layeredPane.add(buttonsPanel);
        frame.add(layeredPane);
        
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            
            @Override
            public void run() {
                try {
                    new MenuFrame();
                } catch (LineUnavailableException ex) {
                    Logger.getLogger(MenuFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(MenuFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void playBackgroundMusic(String filePath) throws LineUnavailableException, UnsupportedAudioFileException {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInputStream);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusicClip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }
    }
   

}

