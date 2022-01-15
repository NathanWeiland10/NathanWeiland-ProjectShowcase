package com.company;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.DefaultCaret;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

// *** Note for grader ***
// All custom assets for this project (images, sounds, etc) are located in the 'resource' folder
// Font used for text: "NSimSun"; program will still work / compile if font is not found / installed

class Main extends JFrame {

    // Class variables are used so they can be used throughout the code without values being reset / initialized multiple times:
    private static String name = "";
    private static Player player;

    // Enables the player to begin a battle by typing in "1":
    private static boolean speedMode = true;

    private static boolean inBattle = false;
    private static boolean inShop = false;
    private static boolean isEquipping = false;
    private static boolean isUnequipping = false;
    private static boolean isRemoving = false;
    private static boolean inInn = false;
    private static boolean inMemoryForest = false;
    private static boolean playerIsDead = false;

    private static ImageIcon image = new ImageIcon();
    private static JLabel imageLabel = new JLabel();

    private static ImageIcon backgroundImage = new ImageIcon();
    private static JLabel backgroundImageLabel = new JLabel();

    private static boolean willPressEnter = false;
    private static boolean hasPressedEnter = true;
    private static boolean battleIsOver = false;
    private static boolean playerFinishedTurn = false;
    private static String userDifficulty;
    private static Enemy encEnemy;
    private static Shop shop = new Shop();

    private GraphicsDevice gDevice;
    private GraphicsEnvironment gEnvironent = GraphicsEnvironment.getLocalGraphicsEnvironment();

    private int currentScreenWidth;
    private int currentScreenHeight;
    private int Font24Pt;

    private Color userColor = Color.GREEN;
    private String userColorString = "Green";

    public Main() {

        // Begin Citation from: https://www.youtube.com/watch?v=PIEtxFEYrJQ&list=PL_QPQmz5C6WXNDfRjMDeUoiSXnczxA4-G&index=48
        currentScreenWidth = 1920;
        currentScreenHeight = 1080;

        gDevice = gEnvironent.getDefaultScreenDevice();

        // setFullScreen();
        // End Citation

        image = new ImageIcon(getClass().getClassLoader().getResource("Town.png"));
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Forest_Background_1.png"));

        // Player's initial difficulty is easy:
        player = new Player(name);
        userDifficulty = "Easy";

        Font24Pt = (int)Math.round(currentScreenWidth*0.0125); // Calculation 24 / 1920

        JPanel root = new JPanel();
        root.setBackground(Color.black);
        root.setLayout(new BorderLayout());

        Border greenBorder = BorderFactory.createLineBorder(Color.green);
        root.setLayout(new BorderLayout());

        int Border10Pt = (int)Math.round(currentScreenWidth*0.0052); // Calculation: Original Value (10) / currentScreenWidth (1920)

        root.setBorder(new EmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt));
        // root.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));

        JPanel namePanel = new JPanel();
        namePanel.setBackground(Color.black);

        /*
        int namePanelX = (int)Math.round(currentScreenWidth*0.0052);
        int namePanelY = (int)Math.round(currentScreenHeight*0.0052);
        int namePanelW = (int)Math.round(currentScreenWidth*0.0052);
        int namePanelH = (int)Math.round(currentScreenHeight*0.0052);;
        namePanel.setBorder(new EmptyBorder(namePanelX, namePanelY, namePanelW, namePanelH));
         */

        namePanel.setBorder(new EmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt));
        // namePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel nameLabel = new JLabel("Please Enter Your Name:");

        nameLabel.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        // nameLabel.setFont(new Font("NSimSun", Font.BOLD, 24));

        nameLabel.setForeground(Color.green);
        nameLabel.setBackground(Color.black);
        namePanel.add(nameLabel);

        JTextField tf = new JTextField("");
        tf.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        // tf.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        tf.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        tf.setBackground(Color.black);
        namePanel.add(tf);

        int tfX = (int)Math.round(currentScreenWidth*0.1563);
        int tfY = (int)Math.round(currentScreenHeight*0.0463);
        tf.setPreferredSize(new Dimension(tfX, tfY));
        // tf.setPreferredSize(new Dimension(300, 50));

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setForeground(Color.green);
        confirmButton.setBackground(Color.black);
        confirmButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        confirmButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        // confirmButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        int confirmButtonX = (int)Math.round(currentScreenWidth*0.1042);
        int confirmButtonY = (int)Math.round(currentScreenHeight*0.0925);

        confirmButton.setPreferredSize(new Dimension(confirmButtonX, confirmButtonY));
        // confirmButton.setPreferredSize(new Dimension(200,100));

        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(Color.black);

        colorPanel.setBorder(new EmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt));
        // colorPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel colorLabel = new JLabel("What is your favorite color?:");

        colorLabel.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        // colorLabel.setFont(new Font("NSimSun", Font.BOLD, 24));

        colorLabel.setForeground(Color.green);
        colorLabel.setBackground(Color.black);
        colorPanel.add(colorLabel);

        JTextField colortf = new JTextField("");

        colortf.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        // colortf.setFont(new Font("NSimSun", Font.BOLD, 24));

        colortf.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        // colortf.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        colortf.setForeground(Color.green);
        colortf.setCaretColor(Color.green);
        colortf.setBackground(Color.black);
        colorPanel.add(colortf);

        int colortfX = (int)Math.round(currentScreenWidth*0.1563);
        int colortfY = (int)Math.round(currentScreenHeight*0.0463);
        colortf.setPreferredSize(new Dimension(colortfX, colortfY));
        // colortf.setPreferredSize(new Dimension(300, 50));

        colorLabel.setForeground(Color.green);
        nameLabel.setForeground(Color.green);
        tf.setForeground(Color.green);
        tf.setCaretColor(Color.green);
        confirmButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        // confirmButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JButton colorButton = new JButton("Change");
        colorButton.setForeground(Color.green);
        colorButton.setBackground(Color.black);

        colorButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        // colorButton.setFont(new Font("NSimSun", Font.BOLD, 24));

        colorButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        // colorButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        int colortButtonX = (int)Math.round(currentScreenWidth*0.0521);
        int colorButtonY = (int)Math.round(currentScreenHeight*0.0463);
        colorButton.setPreferredSize(new Dimension(colortButtonX, colorButtonY)); // Orig: Width: 100, Height: 50

        colorPanel.add(colorButton);

        JPanel confirmPanel = new JPanel();
        confirmPanel.setBackground(Color.black);
        confirmPanel.add(confirmButton);

        JPanel difPanel = new JPanel();
        difPanel.setBackground(Color.black);
        difPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));

        JLabel difLabel = new JLabel("Select your difficulty:");

        difLabel.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        // difLabel.setFont(new Font("NSimSun", Font.BOLD, 24));

        difLabel.setForeground(Color.green);
        difLabel.setBackground(Color.black);
        difPanel.add(difLabel);

        int difButtonX = (int)Math.round(currentScreenWidth*0.0651); // Orig: 125
        int difButtonY = (int)Math.round(currentScreenHeight*0.0463); // Orig: 50

        JButton easyButton = new JButton("Easy");
        easyButton.setPreferredSize(new Dimension(difButtonX,difButtonY));
        easyButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        // easyButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        easyButton.setForeground(Color.green);
        easyButton.setBackground(Color.black);
        easyButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        difPanel.add(easyButton);

        JButton regularButton = new JButton("Regular");
        regularButton.setPreferredSize(new Dimension(difButtonX,difButtonY));
        // regularButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        regularButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        regularButton.setForeground(Color.green);
        regularButton.setBackground(Color.black);
        regularButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        difPanel.add(regularButton);

        JButton hardButton = new JButton("Hard");
        hardButton.setPreferredSize(new Dimension(difButtonX,difButtonY));
        hardButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        // hardButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        hardButton.setForeground(Color.green);
        hardButton.setBackground(Color.black);
        hardButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        difPanel.add(hardButton);

        JButton veteranButton = new JButton("Veteran");
        veteranButton.setPreferredSize(new Dimension(difButtonX,difButtonY));
        veteranButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        // veteranButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        veteranButton.setForeground(Color.green);
        veteranButton.setBackground(Color.black);
        veteranButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        difPanel.add(veteranButton);

        JPanel soundPanel = new JPanel();
        soundPanel.setBackground(Color.black);
        soundPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));

        JLabel soundLabel = new JLabel("Toggle sound effects:");
        soundLabel.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        soundPanel.add(soundLabel);

        JButton toggleSoundOn = new JButton("On");
        toggleSoundOn.setPreferredSize(new Dimension(difButtonX,difButtonY));
        toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));hardButton.setForeground(Color.green);
        toggleSoundOn.setBackground(Color.black);
        toggleSoundOn.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        soundPanel.add(toggleSoundOn);

        JButton toggleSoundOff = new JButton("Off");
        toggleSoundOff.setPreferredSize(new Dimension(difButtonX,difButtonY));
        toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));hardButton.setForeground(Color.green);
        toggleSoundOff.setBackground(Color.black);
        toggleSoundOff.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        soundPanel.add(toggleSoundOff);

        soundLabel.setForeground(userColor);
        toggleSoundOn.setForeground(userColor);
        toggleSoundOff.setForeground(userColor);

        ImageIcon javaQuestImage = new ImageIcon(getClass().getClassLoader().getResource("Java_Quest_Logo.png"));
        JLabel javaQuestLabel = new JLabel();
        JPanel javaQuestPanel = new JPanel();

        javaQuestPanel.setBackground(Color.green);
        javaQuestPanel.setForeground(Color.green);

        javaQuestLabel.setIcon(javaQuestImage);

        javaQuestPanel.add(javaQuestLabel);

        menuPanel.add(javaQuestPanel);
        menuPanel.add(namePanel);
        menuPanel.add(colorPanel);
        menuPanel.add(difPanel);
        menuPanel.add(soundPanel);
        menuPanel.add(confirmPanel);

        root.add(menuPanel, BorderLayout.NORTH);

        JPanel textPanelParent = new JPanel();
        textPanelParent.setLayout(new BorderLayout());

        JPanel userTextHolder = new JPanel();
        userTextHolder.setBackground(Color.black);
        userTextHolder.setLayout(new BoxLayout(userTextHolder, BoxLayout.Y_AXIS));

        JPanel userInpParent = new JPanel();

        int userInpParentX = (int)Math.round(currentScreenWidth*0.2186); // Orig: 420
        int userInpParentY = 1; // Orig: 1
        userInpParent.setMaximumSize(new Dimension(userInpParentX, userInpParentY));
        // userInpParent.setMaximumSize(new Dimension(420, 1));

        userInpParent.setLayout(new BoxLayout(userInpParent, BoxLayout.X_AXIS));
        userInpParent.setBackground(Color.black);

        JTextField userInp = new JTextField();
        userInp.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        userInp.setBackground(Color.black);
        userInp.setForeground(Color.green);
        userInp.setCaretColor(Color.green);
        userInp.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        // userInp.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        userInpParent.add(userInp);

        JPanel textOutParent = new JPanel();
        textOutParent.setBackground(Color.black);

        int outputTextRows = (int)Math.round(currentScreenWidth*0.0172); // Orig: 33
        int outputTextColumns = (int)Math.round(currentScreenHeight*0.0278); // Orig: 30
        JTextArea outputText = new JTextArea(outputTextRows, outputTextColumns);
        // JTextArea outputText = new JTextArea(33, 30);

        outputText.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

        outputText.setBackground(Color.black);
        outputText.setForeground(Color.green);
        outputText.setCaretColor(Color.green);
        outputText.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
        // outputText.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        outputText.setLineWrap(true);
        outputText.setWrapStyleWord(true);
        outputText.setEditable(false);
        JScrollPane scrollOut = new JScrollPane(outputText, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        DefaultCaret scrollCaret = (DefaultCaret)outputText.getCaret();
        scrollCaret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        outputText.setAutoscrolls(true);

        textOutParent.add(scrollOut);
        scrollOut.setBorder(greenBorder);
        scrollOut.setForeground(Color.green);
        scrollOut.getVerticalScrollBar().setBackground(Color.black);

        JPanel userPanelParent = new JPanel();
        userPanelParent.setLayout(new BorderLayout());

        JTextArea userPanelText = new JTextArea(outputTextRows, outputTextColumns);
        // JTextArea userPanelText = new JTextArea(33, 30);

        userPanelText.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
        userPanelText.setBackground(Color.black);
        userPanelText.setForeground(Color.green);
        userPanelText.setCaretColor(Color.green);
        userPanelText.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));

        // userPanelText.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        userPanelText.setLineWrap(true);
        userPanelText.setWrapStyleWord(true);
        userPanelText.setEditable(false);

        userPanelParent.add(userPanelText);

        userTextHolder.add(textOutParent);
        userTextHolder.add(userInpParent);

        textPanelParent.add(userTextHolder);

        JPanel imagePanel = new JPanel();
        textOutParent.setBackground(Color.BLACK);

        imageLabel.setIcon(image);
        imagePanel.setForeground(Color.black);
        imagePanel.setBackground(Color.black);
        imagePanel.add(imageLabel);

        JPanel backgroundImagePanel = new JPanel();
        backgroundImagePanel.add(backgroundImageLabel);

        JPanel overlayPanel = new JPanel();

        backgroundImagePanel.setOpaque(false);
        overlayPanel.setOpaque(false);
        imagePanel.setOpaque(false);

        overlayPanel.setLayout(new OverlayLayout(overlayPanel));
        overlayPanel.setForeground(Color.black);
        overlayPanel.setBackground(Color.black);
        overlayPanel.add(imagePanel);
        overlayPanel.add(backgroundImagePanel);

        ScreenShaker shaker = new ScreenShaker(imagePanel);

        easyButton.setBackground(Color.red);
        toggleSoundOn.setBackground(Color.red);

        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PlaySound.play((getClass().getClassLoader().getResource("Confirm.wav")));
                name = tf.getText();
                player.setName(name);
                player.setDifficulty(userDifficulty);
                outputText.setText("Hello, " + name + "! In this game, you will will fight enemies to increase your level and purchase items from the shop to work your way through the ranks. For help / a list of commands, try typing 'help'.");
                root.remove(menuPanel);
                userPanelText.setText(player.toString());

                root.add(textPanelParent, BorderLayout.EAST);
                root.add(userPanelParent, BorderLayout.WEST);

                if (!inMemoryForest) {
                    image = new ImageIcon(getClass().getClassLoader().getResource("Town.png"));
                } else {
                    image = new ImageIcon(getClass().getClassLoader().getResource("Memory_Forest_Background.png"));
                }

                int imageX = (int) Math.round(currentScreenWidth * 0.5208);
                int imageY = (int) Math.round(currentScreenHeight * 0.9259);
                Image newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                image = new ImageIcon(newImage);
                imageLabel.setIcon(image);

                backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Forest_Background_1.png"));

                Image newBackImage = backgroundImage.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                backgroundImage = new ImageIcon(newBackImage);
                backgroundImageLabel.setIcon(backgroundImage);

                root.add(overlayPanel, BorderLayout.CENTER);
                // root.add(imagePanel, BorderLayout.CENTER);
                root.validate();
                root.repaint();

            }
        });

        easyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                easyButton.setBackground(Color.red);
                regularButton.setBackground(Color.black);
                hardButton.setBackground(Color.black);
                veteranButton.setBackground(Color.black);

                userDifficulty = "Easy";
                if (player != null) {
                    player.setDifficulty("Easy");
                }
                PlaySound.play((getClass().getClassLoader().getResource("ButtonClick.wav")));
            }
        });

        regularButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                easyButton.setBackground(Color.black);
                regularButton.setBackground(Color.red);
                hardButton.setBackground(Color.black);
                veteranButton.setBackground(Color.black);

                userDifficulty = "Regular";
                if (player != null) {
                    player.setDifficulty("Regular");
                }
                PlaySound.play((getClass().getClassLoader().getResource("ButtonClick.wav")));
            }
        });

        hardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                easyButton.setBackground(Color.black);
                regularButton.setBackground(Color.black);
                hardButton.setBackground(Color.red);
                veteranButton.setBackground(Color.black);

                userDifficulty = "Hard";
                if (player != null) {
                    player.setDifficulty("Hard");
                }
                PlaySound.play((getClass().getClassLoader().getResource("ButtonClick.wav")));
            }
        });

        veteranButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                easyButton.setBackground(Color.black);
                regularButton.setBackground(Color.black);
                hardButton.setBackground(Color.black);
                veteranButton.setBackground(Color.red);

                userDifficulty = "Veteran";
                if (player != null) {
                    player.setDifficulty("Veteran");
                }
                PlaySound.play((getClass().getClassLoader().getResource("ButtonClick.wav")));
            }
        });

        toggleSoundOn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                toggleSoundOn.setBackground(Color.red);
                toggleSoundOff.setBackground(Color.black);

                PlaySound.setPlaySounds(true);
                PlaySound.play((getClass().getClassLoader().getResource("ButtonClick.wav")));
            }
        });

        toggleSoundOff.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                toggleSoundOn.setBackground(Color.black);
                toggleSoundOff.setBackground(Color.red);

                PlaySound.setPlaySounds(false);
            }
        });

        colorButton.addActionListener(new ActionListener() {

            final Border redBorder = BorderFactory.createLineBorder(Color.red);
            final Border blueBorder = BorderFactory.createLineBorder(Color.blue);
            final Border greenBorder = BorderFactory.createLineBorder(Color.green);
            final Border yellowBorder = BorderFactory.createLineBorder(Color.yellow);
            final Border purpleBorder = BorderFactory.createLineBorder(Color.magenta);
            final Border orangeBorder = BorderFactory.createLineBorder(Color.orange);
            final Border cyanBorder = BorderFactory.createLineBorder(Color.cyan);

            public void actionPerformed(ActionEvent e) {
                String choice = colortf.getText();
                if (choice.equalsIgnoreCase("red")) {
                    userColor = Color.red;
                    userColorString = "Red";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else if (choice.equalsIgnoreCase("orange")) {
                    userColor = Color.orange;
                    userColorString = "Orange";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else if (choice.equalsIgnoreCase("yellow") || choice.equalsIgnoreCase("gold")) {
                    userColor = Color.yellow;
                    userColorString = "Yellow";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else if (choice.equalsIgnoreCase("green") || choice.equalsIgnoreCase("lime")) {
                    userColor = Color.green;
                    userColorString = "Green";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else if (choice.equalsIgnoreCase("blue")) {
                    userColor = Color.blue;
                    userColorString = "Blue";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else if (choice.equalsIgnoreCase("cyan") || choice.equalsIgnoreCase("light blue")) {
                    userColor = Color.cyan;
                    userColorString = "Cyan";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else if (choice.equalsIgnoreCase("purple") || choice.equalsIgnoreCase("magenta")) {
                    userColor = Color.magenta;
                    userColorString = "Purple";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else if (choice.equalsIgnoreCase("pink")) {
                    userColor = Color.pink;
                    userColorString = "Pink";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else if (choice.equalsIgnoreCase("white")) {
                    userColor = Color.white;
                    userColorString = "White";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else if (choice.equalsIgnoreCase("gray") || choice.equalsIgnoreCase("grey")) {
                    userColor = Color.gray;
                    userColorString = "Gray";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else if (choice.equalsIgnoreCase("black")) {
                    userColor = Color.darkGray;
                    userColorString = "DarkGray";
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                } else {
                    if (!choice.equalsIgnoreCase("rainbow") && !choice.equalsIgnoreCase("colorful") && !choice.equalsIgnoreCase("warm") && !choice.equalsIgnoreCase("cool") && !choice.equalsIgnoreCase("festive")) {
                        PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                        colortf.setText("Unknown Color");
                    }
                }
                Border textBorder = BorderFactory.createLineBorder(userColor);
                colorLabel.setForeground(userColor);
                nameLabel.setForeground(userColor);
                tf.setForeground(userColor);
                tf.setCaretColor(userColor);
                difLabel.setForeground(userColor);
                tf.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                colorButton.setForeground(userColor);
                colorButton.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                confirmButton.setForeground(userColor);
                confirmButton.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                colorLabel.setForeground(userColor);
                easyButton.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                easyButton.setForeground(userColor);
                regularButton.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                regularButton.setForeground(userColor);
                hardButton.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                hardButton.setForeground(userColor);
                veteranButton.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                veteranButton.setForeground(userColor);

                soundLabel.setForeground(userColor);
                toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                toggleSoundOn.setForeground(userColor);
                toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                toggleSoundOff.setForeground(userColor);

                colortf.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                colortf.setForeground(userColor);
                colortf.setCaretColor(userColor);
                nameLabel.setForeground(userColor);
                userInp.setForeground(userColor);
                userInp.setCaretColor(userColor);
                userInp.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                outputText.setForeground(userColor);
                outputText.setCaretColor(userColor);
                outputText.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                scrollOut.setBorder(textBorder);
                scrollOut.setForeground(userColor);
                userPanelText.setForeground(userColor);
                userPanelText.setCaretColor(userColor);
                userPanelText.setBorder(BorderFactory.createCompoundBorder(textBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                javaQuestPanel.setBackground(userColor);
                javaQuestPanel.setForeground(userColor);

                if (choice.equalsIgnoreCase("rainbow") || choice.equalsIgnoreCase("colorful")) {
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                    userColorString = "Rainbow";

                    colorLabel.setForeground(Color.red);
                    nameLabel.setForeground(Color.red);
                    tf.setForeground(Color.red);
                    tf.setCaretColor(Color.red);
                    difLabel.setForeground(Color.yellow);
                    tf.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colorButton.setForeground(Color.blue);
                    colorButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    confirmButton.setForeground(Color.green);
                    confirmButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colorLabel.setForeground(Color.blue);
                    easyButton.setBorder(BorderFactory.createCompoundBorder(yellowBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    easyButton.setForeground(Color.yellow);
                    regularButton.setBorder(BorderFactory.createCompoundBorder(yellowBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    regularButton.setForeground(Color.yellow);
                    hardButton.setBorder(BorderFactory.createCompoundBorder(yellowBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    hardButton.setForeground(Color.yellow);
                    veteranButton.setBorder(BorderFactory.createCompoundBorder(yellowBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    veteranButton.setForeground(Color.yellow);

                    soundLabel.setForeground(Color.cyan);
                    toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(cyanBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    toggleSoundOn.setForeground(Color.cyan);
                    toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(cyanBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    toggleSoundOff.setForeground(Color.cyan);

                    colortf.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colortf.setForeground(Color.blue);
                    colortf.setCaretColor(Color.blue);
                    nameLabel.setForeground(Color.red);
                    userInp.setForeground(Color.cyan);
                    userInp.setCaretColor(Color.cyan);
                    userInp.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    outputText.setForeground(Color.yellow);
                    outputText.setCaretColor(Color.yellow);
                    outputText.setBorder(BorderFactory.createCompoundBorder(purpleBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    scrollOut.setBorder(purpleBorder);
                    scrollOut.setForeground(Color.magenta);
                    userPanelText.setForeground(Color.red);
                    userPanelText.setCaretColor(Color.red);
                    userPanelText.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    javaQuestPanel.setBackground(Color.red);
                    javaQuestPanel.setForeground(Color.red);
                } else if (choice.equalsIgnoreCase("warm")) {
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                    userColorString = "Warm";

                    colorLabel.setForeground(Color.red);
                    nameLabel.setForeground(Color.red);
                    tf.setForeground(Color.red);
                    tf.setCaretColor(Color.red);
                    difLabel.setForeground(Color.orange);
                    tf.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colorButton.setForeground(Color.orange);
                    colorButton.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    confirmButton.setForeground(Color.yellow);
                    confirmButton.setBorder(BorderFactory.createCompoundBorder(yellowBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colorLabel.setForeground(Color.orange);
                    easyButton.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    easyButton.setForeground(Color.orange);
                    regularButton.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    regularButton.setForeground(Color.orange);
                    hardButton.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    hardButton.setForeground(Color.orange);
                    veteranButton.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    veteranButton.setForeground(Color.orange);

                    soundLabel.setForeground(Color.orange);
                    toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    toggleSoundOn.setForeground(Color.orange);
                    toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    toggleSoundOff.setForeground(Color.orange);

                    colortf.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colortf.setForeground(Color.orange);
                    colortf.setCaretColor(Color.orange);
                    nameLabel.setForeground(Color.red);
                    userInp.setForeground(Color.yellow);
                    userInp.setCaretColor(Color.yellow);
                    userInp.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    outputText.setForeground(Color.yellow);
                    outputText.setCaretColor(Color.yellow);
                    outputText.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    scrollOut.setBorder(redBorder);
                    scrollOut.setForeground(Color.red);
                    userPanelText.setForeground(Color.yellow);
                    userPanelText.setCaretColor(Color.yellow);
                    userPanelText.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    javaQuestPanel.setBackground(Color.red);
                    javaQuestPanel.setForeground(Color.red);
                } else if (choice.equalsIgnoreCase("cool")) {
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                    userColorString = "Cool";

                    colorLabel.setForeground(Color.blue);
                    nameLabel.setForeground(Color.cyan);
                    tf.setForeground(Color.cyan);
                    tf.setCaretColor(Color.cyan);
                    difLabel.setForeground(Color.blue);
                    tf.setBorder(BorderFactory.createCompoundBorder(cyanBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colorButton.setForeground(Color.blue);
                    colorButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    confirmButton.setForeground(Color.magenta);
                    confirmButton.setBorder(BorderFactory.createCompoundBorder(purpleBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colorLabel.setForeground(Color.blue);
                    easyButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    easyButton.setForeground(Color.blue);
                    regularButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    regularButton.setForeground(Color.blue);
                    hardButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    hardButton.setForeground(Color.blue);
                    veteranButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    veteranButton.setForeground(Color.blue);

                    soundLabel.setForeground(Color.blue);
                    toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    toggleSoundOn.setForeground(Color.blue);
                    toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    toggleSoundOff.setForeground(Color.blue);

                    colortf.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colortf.setForeground(Color.blue);
                    colortf.setCaretColor(Color.blue);
                    nameLabel.setForeground(Color.cyan);
                    userInp.setForeground(Color.cyan);
                    userInp.setCaretColor(Color.cyan);
                    userInp.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    outputText.setForeground(Color.cyan);
                    outputText.setCaretColor(Color.cyan);
                    outputText.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    scrollOut.setBorder(blueBorder);
                    scrollOut.setForeground(Color.blue);
                    userPanelText.setForeground(Color.cyan);
                    userPanelText.setCaretColor(Color.cyan);
                    userPanelText.setBorder(BorderFactory.createCompoundBorder(purpleBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    javaQuestPanel.setBackground(Color.cyan);
                    javaQuestPanel.setForeground(Color.cyan);
                } else if (choice.equalsIgnoreCase("Festive")) {
                    PlaySound.play((getClass().getClassLoader().getResource("ButtonClick2.wav")));
                    colortf.setText("");
                    userColorString = "Festive";

                    colorLabel.setForeground(Color.red);
                    nameLabel.setForeground(Color.red);
                    tf.setForeground(Color.green);
                    tf.setCaretColor(Color.green);
                    difLabel.setForeground(Color.green);
                    tf.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colorButton.setForeground(Color.red);
                    colorButton.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    confirmButton.setForeground(Color.green);
                    confirmButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colorLabel.setForeground(Color.red);
                    easyButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    easyButton.setForeground(Color.green);
                    regularButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    regularButton.setForeground(Color.green);
                    hardButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    hardButton.setForeground(Color.green);
                    veteranButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    veteranButton.setForeground(Color.green);

                    soundLabel.setForeground(Color.red);
                    toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    toggleSoundOn.setForeground(Color.red);
                    toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    toggleSoundOff.setForeground(Color.red);

                    colortf.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    colortf.setForeground(Color.red);
                    colortf.setCaretColor(Color.red);
                    nameLabel.setForeground(Color.green);
                    userInp.setForeground(Color.green);
                    userInp.setCaretColor(Color.green);
                    userInp.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    outputText.setForeground(Color.green);
                    outputText.setCaretColor(Color.green);
                    outputText.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                    scrollOut.setBorder(redBorder);
                    scrollOut.setForeground(Color.green);
                    userPanelText.setForeground(Color.green);
                    userPanelText.setCaretColor(Color.green);
                    userPanelText.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    javaQuestPanel.setBackground(Color.red);
                    javaQuestPanel.setForeground(Color.red);
                }
                root.validate();
                root.repaint();
            }
        });




        userInp.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                outputText.setAutoscrolls(true);

                // If statements are used to determine the player's location;
                // ** While loops will cause major issues with Java Swing **
                if (!playerIsDead) {

                    String com = userInp.getText();

                    if (e.getKeyCode() == KeyEvent.VK_ENTER)

                        if (!inBattle && !inShop && !isEquipping && !isUnequipping && !isRemoving && !inInn && !inMemoryForest) {

                            if (com.equalsIgnoreCase("help") || com.equalsIgnoreCase("commands") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append(player.playerCommands());
                            } else if ((com.equalsIgnoreCase("fight") || com.equalsIgnoreCase("battle") || com.equalsIgnoreCase("1") && e.getKeyCode() == KeyEvent.VK_ENTER)) {

                                imagePanel.remove(imageLabel);
                                backgroundImagePanel.remove(backgroundImageLabel);
                                int imageX = (int) Math.round(currentScreenWidth * 0.5208);
                                int imageY = (int) Math.round(currentScreenHeight * 0.9259);
                                Image newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                                image = new ImageIcon(newImage);
                                imageLabel.setIcon(image);

                                Image newBackImage = backgroundImage.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                                backgroundImage = new ImageIcon(newBackImage);
                                backgroundImageLabel.setIcon(backgroundImage);

                                imagePanel.add(imageLabel);
                                backgroundImagePanel.add(backgroundImageLabel);

                                com = "";
                                inBattle = true;
                                ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
                                ArrayList<Enemy> enemiesWithinLevel = new ArrayList<Enemy>();
                                enemyList.add(new Slime());
                                enemyList.add(new Goblin());
                                enemyList.add(new Mimic());
                                enemyList.add(new MetalSlime());
                                enemyList.add(new RedSlime());
                                enemyList.add(new YellowFairy());
                                enemyList.add(new Swordsman());
                                enemyList.add(new GoblinChief());
                                enemyList.add(new BabyDragon());
                                enemyList.add(new MegaBlueSlime());
                                enemyList.add(new Shade());
                                enemyList.add(new SkeletonWarrior());
                                enemyList.add(new Robot());
                                enemyList.add(new Ogre());

                                Collections.shuffle(enemyList);
                                encEnemy = enemyList.get(0);

                                for (Enemy enemy : enemyList) {
                                    if ((enemy.getMinLevelAmount() <= player.getPlayerLevel()) && (enemy.getMaxLevelAmount() >= player.getPlayerLevel())) {
                                        enemiesWithinLevel.add(enemy);
                                    }
                                }

                                // Only allow player in battle if enemies within the level range exist:
                                if (enemiesWithinLevel.size() == 0) {
                                    // Player will be unable to battle if no enemies were found:
                                    outputText.append("\n\nError: No more enemies within player level in this area. You can still go to the Memory Forest to fight if you would like.");
                                    inBattle = false;
                                } else {
                                    boolean hasFoundEnemy = false;
                                    while (!hasFoundEnemy) {
                                        int enemyVal = -1;
                                        int hasEncounteredOne = 0;
                                        for (int i = 0; i < enemiesWithinLevel.size(); i++) {
                                            if (enemiesWithinLevel.get(i).tryEncounter()) {
                                                enemyVal = i;
                                                hasEncounteredOne++;
                                            }
                                        }
                                        if (hasEncounteredOne == 1) {
                                            encEnemy = enemiesWithinLevel.get(enemyVal);
                                            hasFoundEnemy = true;
                                        }
                                    }

                                    // Playing on 'hard' or 'veteran' difficulty will also increase enemy loot and experience drops:
                                    if (player.getDifficulty().equalsIgnoreCase("Easy")) {
                                        encEnemy.setMaxHealth(encEnemy.getMaxHealth() * 1.0);
                                        encEnemy.setHealth(encEnemy.getCurrentHealth() * 1.0);
                                    } else if (player.getDifficulty().equalsIgnoreCase("Regular")) {
                                        encEnemy.setMaxHealth(encEnemy.getMaxHealth() * 1.5);
                                        encEnemy.setHealth(encEnemy.getCurrentHealth() * 1.5);
                                    } else if (player.getDifficulty().equalsIgnoreCase("Hard")) {
                                        encEnemy.setMaxHealth(encEnemy.getMaxHealth() * 2);
                                        encEnemy.setHealth(encEnemy.getCurrentHealth() * 2);
                                        encEnemy.setDamage(encEnemy.getDamage() * 1.5);

                                        // Hard Mode bonuses:
                                        encEnemy.setLootAmount(encEnemy.getLootAmount() * 2);
                                        encEnemy.setXP(encEnemy.getXP() * 2);

                                    } else if (player.getDifficulty().equalsIgnoreCase("Veteran")) {
                                        encEnemy.setMaxHealth(encEnemy.getMaxHealth() * 3);
                                        encEnemy.setHealth(encEnemy.getCurrentHealth() * 3);
                                        encEnemy.setDamage(encEnemy.getDamage() * 2);

                                        // Veteran Mode bonuses:
                                        encEnemy.setLootAmount(encEnemy.getLootAmount() * 4);
                                        encEnemy.setXP(encEnemy.getXP() * 3);
                                        encEnemy.setItemDropChance(encEnemy.getItemDropChance() * 2);

                                    }


                                    backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Forest_Background_1.png"));
                                    newBackImage = backgroundImage.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                                    backgroundImage = new ImageIcon(newBackImage);
                                    backgroundImageLabel.setIcon(backgroundImage);

                                    userPanelText.setText(player.toString() + encEnemy.toString() + "\n\nWhat would you like to do? Available options:\n" + "1: Attack\n" + "2: Spells\n" + "3: Flee");
                                    outputText.append("\n\nYou encountered a wild " + encEnemy.getName().toLowerCase() + "!");
                                    PlaySound.play(encEnemy.getSpawnSoundFileName());

                                    root.validate();
                                    root.repaint();

                                    image = new ImageIcon(encEnemy.getPNGFileName());

                                    imageX = (int) Math.round(currentScreenWidth * 0.5208);
                                    imageY = (int) Math.round(currentScreenHeight * 0.9259);
                                    newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                                    image = new ImageIcon(newImage);
                                    imageLabel.setIcon(image);
                                }

                            } else if (com.equalsIgnoreCase("shop") || com.equalsIgnoreCase("store") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append("\n\nWelcome to the shop! To buy an item, enter the number in front of the item. To leave the store, type 'leave'.");
                                outputText.append(shop.listItems(outputText));
                                PlaySound.play((getClass().getClassLoader().getResource("EnterStore.wav")));
                                inShop = true;
                            } else if (com.equalsIgnoreCase("spells") || com.equalsIgnoreCase("spell") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append(player.checkSpells());
                            } else if (com.equalsIgnoreCase("inn") || com.equalsIgnoreCase("tavern") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                int goldCost = (player.getPlayerLevel() * 10) * 2;
                                outputText.append("\n\nWelcome to the inn! You can rest to restore your mana and health for " + goldCost + " gold by typing 'rest'. To leave the inn, type 'leave'.");
                                PlaySound.play((getClass().getClassLoader().getResource("EnterInn.wav")));
                                inInn = true;
                            } else if (com.equalsIgnoreCase("autoscroll") || com.equalsIgnoreCase("scroll") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                // Issue can sometimes occur; autoscroll no longer works, so text is reset and autoscroll is renabled:
                                outputText.setText("Autoscroll has been enabled / fixed.");
                                outputText.setAutoscrolls(true);
                            } else if (com.equalsIgnoreCase("memory forest") || com.equalsIgnoreCase("memoryforest") || com.equalsIgnoreCase("forest") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                if (player.getPlayerLevel() >= 9) {
                                    inMemoryForest = true;
                                    PlaySound.play((getClass().getClassLoader().getResource("EnterMagicForest.wav")));
                                    outputText.append("\n\nYou have made it to the Memory Forest! Type 'battle' to start a battle or type 'exit' to leave the Memory Forest.");
                                } else {
                                    outputText.append("\n\nYou have not unlocked access to the Memory Forest yet!");
                                }
                            } else if (com.equalsIgnoreCase("menu") || (com.equalsIgnoreCase("options")) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                // Player's progress will be saved / not be reset when switching back to the main menu:
                                root.add(menuPanel, BorderLayout.NORTH);
                                root.remove(textPanelParent);
                                root.remove(userPanelParent);
                                root.remove(imagePanel);
                                root.remove(overlayPanel);
                                root.validate();
                                root.repaint();
                            } else if (com.equalsIgnoreCase("heal") || com.equalsIgnoreCase("recover") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                if (player.getSpells().contains("Heal") && (player.getCurrentPlayerHealth() != player.getMaxPlayerHealth() && (player.checkIfCanHeal(outputText))) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    player.heal();
                                    outputText.append("\n\nYou have healed: HP: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                    userPanelText.setText(player.toString());
                                } else if (player.getSpells().contains("Heal") && (player.getCurrentPlayerHealth() == player.getMaxPlayerHealth()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou are currently at maximum health: HP: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have not learned this spell yet.");
                                }

                            } else if (com.equalsIgnoreCase("fireball") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                if (player.getSpells().contains("fireball") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou cannot cast fireball right now; you are not in battle.");
                                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have not learned this spell yet.");
                                }

                            } else if (com.equalsIgnoreCase("midheal") || com.equalsIgnoreCase("mid-heal") || com.equalsIgnoreCase("mheal") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                if (player.getSpells().contains("Mid-Heal") && (player.getCurrentPlayerHealth() != player.getMaxPlayerHealth() && (player.checkIfCanMidHeal(outputText))) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    player.midHeal();
                                    outputText.append("\n\nYou have healed: HP: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                    userPanelText.setText(player.toString());
                                } else if (player.getSpells().contains("Mid-Heal") && (player.getCurrentPlayerHealth() == player.getMaxPlayerHealth()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou are currently at maximum health: HP: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have not learned this spell yet.");
                                }

                            } else if (com.equalsIgnoreCase("blast") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                if (player.getSpells().contains("blast") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou cannot cast blast right now; you are not in battle.");
                                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have not learned this spell yet.");
                                }

                            } else if (com.equalsIgnoreCase("remove") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                isRemoving = true;

                                if (player.getInventory().size() == 0) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have no items in your inventory to remove!");
                                    isRemoving = false;
                                } else {
                                    outputText.append("\n\nPlease enter the number next to the item you would like to remove, or type return if you no longer wish to remove an item:\n");
                                    outputText.append(player.equipToString());
                                }
                            } else if (com.equalsIgnoreCase("removeall") || com.equalsIgnoreCase("remove all") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                if (player.getInventory().size() == 0) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have no items in your inventory to remove!");
                                } else {
                                    player.removeAllItems(outputText);
                                    userPanelText.setText(player.toString());
                                }
                            } else if (com.equalsIgnoreCase("equip") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                isEquipping = true;

                                if (player.getInventory().size() == 0) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have no items in your inventory to equip!");
                                    isEquipping = false;
                                } else {
                                    outputText.append("\n\nPlease enter the number next to the item you would like to equip, or type return if you no longer wish to equip an item:\n");
                                    outputText.append(player.equipToString());
                                }

                            } else if (com.equalsIgnoreCase("unequip") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                isUnequipping = true;

                                if (player.getEquippedItems().size() == 0) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have no items to unequip!");
                                    isUnequipping = false;
                                } else {
                                    outputText.append("\n\nPlease enter the number next to the item you would like to unequip, or type return if you no longer wish to unequip an item:\n");
                                    outputText.append(player.UnequipToString());
                                }

                            } else if (com.equalsIgnoreCase("inventory") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append("\n\n" + player.checkInventory());
                            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append("\n\nUnknown command");
                            }

                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                userInp.setText("");
                            }
                        }

                    if (inMemoryForest) {

                        if (e.getKeyCode() == KeyEvent.VK_ENTER && !inBattle) {

                            image = new ImageIcon(getClass().getClassLoader().getResource("Memory_Forest_Background.png"));

                            int imageX = (int) Math.round(currentScreenWidth * 0.5208);
                            int imageY = (int) Math.round(currentScreenHeight * 0.9259);
                            Image newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                            image = new ImageIcon(newImage);
                            imageLabel.setIcon(image);

                            if ((com.equalsIgnoreCase("fight") || com.equalsIgnoreCase("battle") || com.equalsIgnoreCase("1") && e.getKeyCode() == KeyEvent.VK_ENTER) && !isRemoving && !isUnequipping && !isEquipping) {

                                imagePanel.remove(imageLabel);
                                backgroundImagePanel.remove(backgroundImageLabel);
                                int imageX2 = (int) Math.round(currentScreenWidth * 0.5208);
                                int imageY2 = (int) Math.round(currentScreenHeight * 0.9259);
                                Image newImage2 = image.getImage().getScaledInstance(imageX2, imageY2, Image.SCALE_DEFAULT);
                                image = new ImageIcon(newImage2);
                                imageLabel.setIcon(image);

                                Image newBackImage = backgroundImage.getImage().getScaledInstance(imageX2, imageY2, Image.SCALE_DEFAULT);
                                backgroundImage = new ImageIcon(newBackImage);
                                backgroundImageLabel.setIcon(backgroundImage);

                                imagePanel.add(imageLabel);
                                backgroundImagePanel.add(backgroundImageLabel);


                                com = "";
                                inBattle = true;
                                ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
                                ArrayList<Enemy> enemiesWithinOrBelowLevel = new ArrayList<Enemy>();
                                enemyList.add(new Slime());
                                enemyList.add(new Goblin());
                                enemyList.add(new Mimic());
                                enemyList.add(new MetalSlime());
                                enemyList.add(new RedSlime());
                                enemyList.add(new YellowFairy());
                                enemyList.add(new Swordsman());
                                enemyList.add(new GoblinChief());
                                enemyList.add(new BabyDragon());
                                enemyList.add(new MegaBlueSlime());
                                enemyList.add(new Shade());
                                enemyList.add(new SkeletonWarrior());
                                enemyList.add(new Robot());
                                enemyList.add(new Ogre());

                                Collections.shuffle(enemyList);
                                encEnemy = enemyList.get(0);

                                for (Enemy enemy : enemyList) {
                                    if ((enemy.getMinLevelAmount() <= player.getPlayerLevel())) {
                                        enemiesWithinOrBelowLevel.add(enemy);
                                    }
                                }

                                boolean hasFoundEnemy = false;
                                while (!hasFoundEnemy) {
                                    int enemyVal = -1;
                                    int hasEncounteredOne = 0;
                                    for (int i = 0; i < enemiesWithinOrBelowLevel.size(); i++) {
                                        if (enemiesWithinOrBelowLevel.get(i).tryEncounter()) {
                                            enemyVal = i;
                                            hasEncounteredOne++;
                                        }
                                    }
                                    if (hasEncounteredOne == 1) {
                                        encEnemy = enemiesWithinOrBelowLevel.get(enemyVal);
                                        hasFoundEnemy = true;
                                    }
                                }

                                // Playing on 'hard' or 'veteran' difficulty will also increase enemy loot and experience drops:
                                if (player.getDifficulty().equalsIgnoreCase("Easy")) {
                                    encEnemy.setMaxHealth(encEnemy.getMaxHealth() * 1.0);
                                    encEnemy.setHealth(encEnemy.getCurrentHealth() * 1.0);
                                } else if (player.getDifficulty().equalsIgnoreCase("Regular")) {
                                    encEnemy.setMaxHealth(encEnemy.getMaxHealth() * 1.5);
                                    encEnemy.setHealth(encEnemy.getCurrentHealth() * 1.5);
                                } else if (player.getDifficulty().equalsIgnoreCase("Hard")) {
                                    encEnemy.setMaxHealth(encEnemy.getMaxHealth() * 2);
                                    encEnemy.setHealth(encEnemy.getCurrentHealth() * 2);
                                    encEnemy.setDamage(encEnemy.getDamage() * 1.5);

                                    // Hard Mode bonuses:
                                    encEnemy.setLootAmount(encEnemy.getLootAmount() * 2);
                                    encEnemy.setXP(encEnemy.getXP() * 2);

                                } else if (player.getDifficulty().equalsIgnoreCase("Veteran")) {
                                    encEnemy.setMaxHealth(encEnemy.getMaxHealth() * 3);
                                    encEnemy.setHealth(encEnemy.getCurrentHealth() * 3);
                                    encEnemy.setDamage(encEnemy.getDamage() * 2);

                                    // Veteran Mode bonuses:
                                    encEnemy.setLootAmount(encEnemy.getLootAmount() * 4);
                                    encEnemy.setXP(encEnemy.getXP() * 2);
                                    encEnemy.setItemDropChance(encEnemy.getItemDropChance() * 2);

                                }

                                userPanelText.setText(player.toString() + encEnemy.toString() + "\n\nWhat would you like to do? Available options:\n" + "1: Attack\n" + "2: Spells\n" + "3: Flee");
                                outputText.append("\n\nYou encountered a wild " + encEnemy.getName().toLowerCase() + "!");
                                PlaySound.play(encEnemy.getSpawnSoundFileName());

                                image = new ImageIcon(encEnemy.getPNGFileName());

                                imageX = (int) Math.round(currentScreenWidth * 0.5208);
                                imageY = (int) Math.round(currentScreenHeight * 0.9259);
                                newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                                image = new ImageIcon(newImage);
                                imageLabel.setIcon(image);


                                backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Memory_Forest_Battleground.png"));
                                newBackImage = backgroundImage.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                                backgroundImage = new ImageIcon(newBackImage);
                                backgroundImageLabel.setIcon(backgroundImage);


                                root.validate();
                                root.repaint();



                            } else if (com.equalsIgnoreCase("exit") || com.equalsIgnoreCase("leave") || com.equalsIgnoreCase("quit") || com.equalsIgnoreCase("back") || com.equalsIgnoreCase("return") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                image = new ImageIcon(getClass().getClassLoader().getResource("Town.png"));

                                imageX = (int) Math.round(currentScreenWidth * 0.5208);
                                imageY = (int) Math.round(currentScreenHeight * 0.9259);
                                newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                                image = new ImageIcon(newImage);
                                imageLabel.setIcon(image);

                                outputText.append("\n\nYou have left the Magic Forest.");
                                PlaySound.play((getClass().getClassLoader().getResource("LeaveStore.wav")));
                                inMemoryForest = false;

                            } else if (com.equalsIgnoreCase("spells") || com.equalsIgnoreCase("spell") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append(player.checkSpells());
                            } else if (com.equalsIgnoreCase("inn") || com.equalsIgnoreCase("tavern") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append("\n\nTo get to the inn, you must first go back to town. Type 'exit' to leave the Memory Forest.");
                            } else if (com.equalsIgnoreCase("autoscroll") || com.equalsIgnoreCase("scroll") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                // Issue can sometimes occur; autoscroll no longer works, so text is reset and autoscroll is renabled:
                                outputText.setText("Autoscroll has been enabled / fixed.");
                                outputText.setAutoscrolls(true);
                            } else if (com.equalsIgnoreCase("memory forest") || com.equalsIgnoreCase("memoryforest") || com.equalsIgnoreCase("forest") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append("\n\nYou are currently in the Memory Forest.");
                            } else if (com.equalsIgnoreCase("menu") || (com.equalsIgnoreCase("options")) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                // Player's progress will be saved / not be reset when switching back to the main menu:
                                root.add(menuPanel, BorderLayout.NORTH);
                                root.remove(textPanelParent);
                                root.remove(userPanelParent);
                                root.remove(imagePanel);
                                root.remove(overlayPanel);
                                root.validate();
                                root.repaint();
                            } else if (com.equalsIgnoreCase("heal") || com.equalsIgnoreCase("recover") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                if (player.getSpells().contains("Heal") && (player.getCurrentPlayerHealth() != player.getMaxPlayerHealth() && (player.checkIfCanHeal(outputText))) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    player.heal();
                                    outputText.append("\n\nYou have healed: HP: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                    userPanelText.setText(player.toString());
                                } else if (player.getSpells().contains("Heal") && (player.getCurrentPlayerHealth() == player.getMaxPlayerHealth()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou are currently at maximum health: HP: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have not learned this spell yet.");
                                }

                            } else if (com.equalsIgnoreCase("fireball") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                if (player.getSpells().contains("fireball") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou cannot cast fireball right now; you are not in battle.");
                                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have not learned this spell yet.");
                                }

                            } else if (com.equalsIgnoreCase("midheal") || com.equalsIgnoreCase("mid-heal") || com.equalsIgnoreCase("mheal") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                if (player.getSpells().contains("Mid-Heal") && (player.getCurrentPlayerHealth() != player.getMaxPlayerHealth() && (player.checkIfCanMidHeal(outputText))) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    player.midHeal();
                                    outputText.append("\n\nYou have healed: HP: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                    userPanelText.setText(player.toString());
                                } else if (player.getSpells().contains("Mid-Heal") && (player.getCurrentPlayerHealth() == player.getMaxPlayerHealth()) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou are currently at maximum health: HP: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have not learned this spell yet.");
                                }

                            } else if (com.equalsIgnoreCase("blast") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                if (player.getSpells().contains("blast") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou cannot cast blast right now; you are not in battle.");
                                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have not learned this spell yet.");
                                }

                            } else if (com.equalsIgnoreCase("remove") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                isRemoving = true;

                                if (player.getInventory().size() == 0) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have no items in your inventory to remove!");
                                    isRemoving = false;
                                } else {
                                    outputText.append("\n\nPlease enter the number next to the item you would like to remove, or type return if you no longer wish to remove an item:\n");
                                    outputText.append(player.equipToString());
                                }
                            } else if (com.equalsIgnoreCase("removeall") || com.equalsIgnoreCase("remove all") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                if (player.getInventory().size() == 0) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have no items in your inventory to remove!");
                                } else {
                                    player.removeAllItems(outputText);
                                    userPanelText.setText(player.toString());
                                }
                            } else if (com.equalsIgnoreCase("equip") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                isEquipping = true;

                                if (player.getInventory().size() == 0) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have no items in your inventory to equip!");
                                    isEquipping = false;
                                } else {
                                    outputText.append("\n\nPlease enter the number next to the item you would like to equip, or type return if you no longer wish to equip an item:\n");
                                    outputText.append(player.equipToString());
                                }

                            } else if (com.equalsIgnoreCase("unequip") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                isUnequipping = true;

                                if (player.getEquippedItems().size() == 0) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou have no items to unequip!");
                                    isUnequipping = false;
                                } else {
                                    outputText.append("\n\nPlease enter the number next to the item you would like to unequip, or type return if you no longer wish to unequip an item:\n");
                                    outputText.append(player.UnequipToString());
                                }

                            } else if (com.equalsIgnoreCase("inventory") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append("\n\n" + player.checkInventory());
                            } else if (com.equalsIgnoreCase("shop") || com.equalsIgnoreCase("store") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append("\n\nTo get to the shop, you must first go back to town. Type 'exit' to leave the Memory Forest.");
                            } else if (com.equalsIgnoreCase("help") || com.equalsIgnoreCase("commands") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append("\n\nType 'battle' to start a battle or type 'exit' to leave the Memory Forest.");
                            } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                                if (!isEquipping && !isUnequipping && !isRemoving) {
                                    outputText.append("\n\nUnknown command");
                                }

                            }
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                userInp.setText("");
                            }


                        }

                    }

                    if (isRemoving) {

                        boolean foundItem = false;

                        // For loop to set up and determine player's items to remove:
                        for (int i = 0; i < player.getInventory().size(); i++) {
                            if (com.equalsIgnoreCase(String.valueOf(i)) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                player.removeItem(player.getInventory().get(i), outputText);
                                userPanelText.setText(player.toString());
                                foundItem = true;
                                isRemoving = false;
                            }
                        }
                        if (com.equalsIgnoreCase("list") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                            outputText.append("\n\nPlease enter the number next to the item you would like to remove, or type return if you no longer wish to remove an item:\n");

                            if (player.getInventory().size() == 0) {
                                PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                outputText.append("\n\nYou have no items in your inventory to remove!");
                                isRemoving = false;
                            } else {
                                outputText.append("\n\nPlease enter the number next to the item you would like to remove, or type return if you no longer wish to remove an item:\n");
                                outputText.append(player.equipToString());
                            }

                        } else if ((com.equalsIgnoreCase("exit") || com.equalsIgnoreCase("leave") || com.equalsIgnoreCase("quit") || com.equalsIgnoreCase("back") || com.equalsIgnoreCase("return") && e.getKeyCode() == KeyEvent.VK_ENTER)) {
                            outputText.append("\n\nYou have stopped removing items.");
                            isRemoving = false;
                        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !com.isEmpty() && !com.equalsIgnoreCase("remove") && !foundItem) {
                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                            outputText.append("\n\nCannot find item");
                        }
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            userInp.setText("");
                        }

                    }

                    if (isEquipping) {

                        boolean foundItem = false;

                        // For loop for to set up and determine player's items to equip:
                        for (int i = 0; i < player.getInventory().size(); i++) {
                            if (com.equalsIgnoreCase(String.valueOf(i)) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                player.equipItem(player.getInventory().get(i), outputText);
                                userPanelText.setText(player.toString());
                                foundItem = true;
                                isEquipping = false;
                            }
                        }
                        if (com.equalsIgnoreCase("list") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                            outputText.append("\n\nPlease enter the number next to the item you would like to equip, or type return if you no longer wish to equip an item:\n");

                            if (player.getInventory().size() == 0) {
                                PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                outputText.append("\n\nYou have no items in your inventory to equip!");
                                isEquipping = false;
                            } else {
                                outputText.append("\n\nPlease enter the number next to the item you would like to equip, or type return if you no longer wish to equip an item:\n");
                                outputText.append(player.equipToString());
                            }

                        } else if ((com.equalsIgnoreCase("exit") || com.equalsIgnoreCase("leave") || com.equalsIgnoreCase("quit") || com.equalsIgnoreCase("back") || com.equalsIgnoreCase("return") && e.getKeyCode() == KeyEvent.VK_ENTER)) {
                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                            outputText.append("\n\nYou have stopped equipping items.");
                            isEquipping = false;
                        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !com.isEmpty() && !com.equalsIgnoreCase("equip") && !foundItem) {
                            isEquipping = false;
                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                            outputText.append("\n\nCannot find item");
                        }
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            userInp.setText("");
                        }


                    }

                    if (isUnequipping) {

                        boolean foundItem = false;

                        // For loop for to set up and determine player's items to unequip:
                        for (int i = 0; i < player.getEquippedItems().size(); i++) {
                            if (com.equalsIgnoreCase(String.valueOf(i)) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                isUnequipping = false;
                                player.unequipItem(player.getEquippedItems().get(i), outputText);
                                userPanelText.setText(player.toString());
                                foundItem = true;
                            }
                        }

                        if (com.equalsIgnoreCase("list") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                            outputText.append("\n\nPlease enter the number next to the item you would like to unequip, or type return if you no longer wish to unequip an item:\n");

                            if (player.getEquippedItems().size() == 0) {
                                PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                outputText.append("\n\nYou have no items in your inventory to equip!");
                                isUnequipping = false;
                            } else {
                                outputText.append("\n\nPlease enter the number next to the item you would like to unequip, or type return if you no longer wish to unequip an item:\n");
                                outputText.append(player.UnequipToString());
                            }

                        } else if ((com.equalsIgnoreCase("exit") || com.equalsIgnoreCase("leave") || com.equalsIgnoreCase("quit") || com.equalsIgnoreCase("back") || com.equalsIgnoreCase("return") && e.getKeyCode() == KeyEvent.VK_ENTER)) {
                            outputText.append("\n\nYou have stopped unequipping items.");
                            isUnequipping = false;
                        } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !com.isEmpty() && !com.equalsIgnoreCase("unequip") && !foundItem) {
                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                            outputText.append("\n\nCannot find item");
                            isUnequipping = false;
                        }
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            userInp.setText("");
                        }

                    }

                    if (inBattle) {

                        boolean playerTurn = true;
                        boolean enemyIsDead = false;

                        if (e.getKeyCode() == KeyEvent.VK_ENTER)

                            if (!willPressEnter) {

                                if (encEnemy.getCurrentHealth() >= 0 && player.getCurrentPlayerHealth() >= 0 && inBattle) {

                                    if (com.equalsIgnoreCase("attack") || com.equalsIgnoreCase("1") || com.equalsIgnoreCase("fight") || com.equalsIgnoreCase("battle") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                        player.hit(encEnemy, outputText, shaker);
                                        playerTurn = false;
                                        player.advanceTurn();
                                    } else if (com.equalsIgnoreCase("spells") || com.equalsIgnoreCase("2") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                        outputText.append(player.checkSpells());
                                    } else if (com.equalsIgnoreCase("flee") || com.equalsIgnoreCase("3") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                        if (player.playerTryFlee()) {

                                            image = new ImageIcon();
                                            imageLabel.setIcon(image);

                                            outputText.append("\n\nYou have fled the battle successfully!\n\nPress enter to continue");
                                            PlaySound.play((getClass().getClassLoader().getResource("Flee.wav")));
                                            battleIsOver = true;
                                            willPressEnter = true;
                                        } else {
                                            outputText.append("\n\nYou tried to flee, but failed!");
                                            playerTurn = false;
                                            player.advanceTurn();
                                        }

                                    } else if (com.equalsIgnoreCase("heal") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                        if (player.getSpells().contains("Heal") && (player.getCurrentPlayerHealth() != player.getMaxPlayerHealth() && (player.checkIfCanHeal(outputText)))) {
                                            player.heal();
                                            outputText.append("\n\nYou have healed: HP: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                            playerTurn = false;
                                            player.advanceTurn();
                                        } else if (player.getSpells().contains("Heal") && (player.getCurrentPlayerHealth() == player.getMaxPlayerHealth())) {
                                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                            outputText.append("\n\nYou are already at maximum health: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                        } else if (!player.getSpells().contains("Heal")) {
                                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                            outputText.append("\n\nYou have not learned this spell yet.");
                                        }

                                    } else if (com.equalsIgnoreCase("Fireball") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                        if (player.getSpells().contains("Fireball") && (player.checkIfCanFireball(outputText))) {
                                            player.fireball(encEnemy, outputText, shaker);
                                            playerTurn = false;
                                            player.advanceTurn();
                                        } else if (!player.getSpells().contains("Fireball")) {
                                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                            outputText.append("\n\nYou have not learned this spell yet.");
                                        }

                                    } else if (com.equalsIgnoreCase("Blast") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                        if (player.getSpells().contains("Blast") && (player.checkIfCanBlast(outputText))) {
                                            player.blast(encEnemy, outputText, shaker);
                                            playerTurn = false;
                                            player.advanceTurn();
                                        } else if (!player.getSpells().contains("Blast")) {
                                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                            outputText.append("\n\nYou have not learned this spell yet.");
                                        }

                                    } else if (com.equalsIgnoreCase("Midheal") || com.equalsIgnoreCase("Mid-Heal") && e.getKeyCode() == KeyEvent.VK_ENTER) {

                                        if (player.getSpells().contains("Mid-Heal") && (player.getCurrentPlayerHealth() != player.getMaxPlayerHealth() && (player.checkIfCanMidHeal(outputText)))) {
                                            player.midHeal();
                                            outputText.append("\n\nYou have healed: HP: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                            playerTurn = false;
                                            player.advanceTurn();
                                        } else if (player.getSpells().contains("Mid-Heal") && (player.getCurrentPlayerHealth() == player.getMaxPlayerHealth())) {
                                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                            outputText.append("\n\nYou are already at maximum health: " + player.getCurrentPlayerHealth() + " / " + player.getMaxPlayerHealth());
                                        } else if (!player.getSpells().contains("Mid-Heal")) {
                                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                            outputText.append("\n\nYou have not learned this spell yet.");
                                        }

                                    } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !com.isEmpty()) {
                                        outputText.append("\n\nUnknown battle command");
                                    }
                                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                        userInp.setText("");
                                    }

                                }

                                if (!playerTurn) {
                                    player.takeStatusEffect(outputText);
                                    userPanelText.setText(player.toString() + encEnemy.toString() + "\n\nWhat would you like to do? Available options:\n" + "1: Attack\n" + "2: Flee\n" + "3: Spells");
                                    if (encEnemy.getCurrentHealth() <= 0) {
                                        enemyIsDead = true;
                                    }
                                }

                                if (encEnemy.getCurrentHealth() >= 0 && player.getCurrentPlayerHealth() >= 0 && inBattle && !playerTurn && !enemyIsDead && ((encEnemy.getCurrentHealth() / encEnemy.getMaxHealth()) >= encEnemy.getHealthPercentToFleeAt())) {

                                    if (encEnemy.trySpecialMove()) {
                                        encEnemy.specialMove(player, outputText);
                                        if (player.getCurrentPlayerHealth() <= 0) {
                                            outputText.append("\n\nYou have died!");
                                            playerIsDead = true;
                                            userInp.setEditable(false);
                                        }
                                    } else {
                                        encEnemy.hit(player, outputText);
                                        if (player.getCurrentPlayerHealth() <= 0) {
                                            outputText.append("\n\nYou have died!");
                                            playerIsDead = true;
                                            userInp.setEditable(false);
                                        }
                                    }

                                    // User panel is set each time to update the player and enemies stats each time a turn is finished:
                                    userPanelText.setText(player.toString() + encEnemy.toString() + "\n\nWhat would you like to do? Available options:\n" + "1: Attack\n" + "2: Flee\n" + "3: Spells");
                                    playerTurn = true;

                                } else if (encEnemy.getCurrentHealth() >= 0 && player.getCurrentPlayerHealth() >= 0 && inBattle && !playerTurn && !enemyIsDead) {
                                    if (encEnemy.tryFlee()) {

                                        image = new ImageIcon();
                                        imageLabel.setIcon(image);

                                        outputText.append("\n\nThe enemy fled successfully!\n\nPress enter to continue");
                                        PlaySound.play((getClass().getClassLoader().getResource("Flee.wav")));
                                        battleIsOver = true;
                                        willPressEnter = true;
                                    } else {
                                        outputText.append("\n\nThe enemy tried to flee, but failed!");
                                        playerTurn = true;
                                    }

                                }

                                if (enemyIsDead) {

                                    int lootDrop = encEnemy.getLootAmount();
                                    if (player.getRingSlot().equalsIgnoreCase("Luxurious Ring")) {
                                        lootDrop = lootDrop * 2;
                                    }

                                    outputText.append("\n\nYou have won! You gained " + encEnemy.getXP() + "XP and " + lootDrop + " gold!\n\nPress enter to continue");
                                    battleIsOver = true;
                                    player.increaseMoney( encEnemy.getLootAmount());
                                    if (encEnemy.tryItemDrop() && !player.getItemNames().contains(encEnemy.getItemDropName())) {
                                        encEnemy.dropItem(player, outputText);
                                    }
                                    player.increaseXP(encEnemy.getXP());

                                    image = new ImageIcon();
                                    imageLabel.setIcon(image);

                                    willPressEnter = true;

                                    if (player.getXpPUntilLevel() <= 0) {
                                        PlaySound.play((getClass().getClassLoader().getResource("LevelUp.wav")));
                                        player.levelUp(outputText, shop);
                                    }

                                    userPanelText.setText(player.toString());
                                }
                            }

                        // Force player to press enter after completing a battle:
                        if (willPressEnter && e.getKeyCode() == KeyEvent.VK_ENTER && inBattle && !hasPressedEnter) {

                            player.resetTurn();
                            inBattle = false;
                            if (!inMemoryForest) {
                                image = new ImageIcon(getClass().getClassLoader().getResource("Town.png"));
                            } else {
                                image = new ImageIcon(getClass().getClassLoader().getResource("Memory_Forest_Background.png"));

                            }
                            userPanelText.setText(player.toString());

                            int imageX = (int) Math.round(currentScreenWidth * 0.5208);
                            int imageY = (int) Math.round(currentScreenHeight * 0.9259);
                            Image newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                            image = new ImageIcon(newImage);
                            imageLabel.setIcon(image);

                            hasPressedEnter = true;
                            battleIsOver = false;
                            willPressEnter = false;
                            userInp.setText("");
                            com = "";

                        }
                        if (battleIsOver) {
                            hasPressedEnter = false;
                        }

                    }

                    if (inShop) {

                        boolean foundItem = false;

                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                            image = new ImageIcon(getClass().getClassLoader().getResource("Shop.png"));

                            int imageX = (int) Math.round(currentScreenWidth * 0.5208);
                            int imageY = (int) Math.round(currentScreenHeight * 0.9259);
                            Image newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                            image = new ImageIcon(newImage);
                            imageLabel.setIcon(image);

                            // For loop to determine and set up the items currently in the shop:
                            for (int i = 0; i < shop.getItemsInShop().size(); i++) {
                                if (com.equalsIgnoreCase(String.valueOf(i)) && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                    int cost = shop.getItemsInShop().get(i).getCost();
                                    if (player.getPlayerGold() >= cost) {
                                        if (shop.getItemsInShop().get(i).getLevelRequirement() <= player.getPlayerLevel()) {
                                            PlaySound.play((getClass().getClassLoader().getResource("Buy.wav")));
                                            outputText.append("\n\nYou have bought the " + shop.getItemsInShop().get(i).getName() + "!");
                                            player.addToInventory(shop.getItemsInShop().get(i));
                                            player.changeGold(shop.getItemsInShop().get(i).getCost());
                                            shop.removeItem(i);
                                            userPanelText.setText(player.toString());
                                            outputText.append(shop.listItems(outputText));
                                            foundItem = true;
                                        } else {
                                            foundItem = true;
                                            outputText.append("\n\nYou are not a high enough level to buy this item!");
                                            PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                        }
                                    } else {
                                        foundItem = true;
                                        outputText.append("\n\nYou cannot afford to buy the " + shop.getItemsInShop().get(i).getName());
                                        PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    }
                                }
                            }
                            if (com.equalsIgnoreCase("exit") || com.equalsIgnoreCase("leave") || com.equalsIgnoreCase("quit") || com.equalsIgnoreCase("back") || com.equalsIgnoreCase("return") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                image = new ImageIcon(getClass().getClassLoader().getResource("Town.png"));

                                imageX = (int) Math.round(currentScreenWidth * 0.5208);
                                imageY = (int) Math.round(currentScreenHeight * 0.9259);
                                newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                                image = new ImageIcon(newImage);
                                imageLabel.setIcon(image);

                                outputText.append("\n\nYou have left the store.");
                                PlaySound.play((getClass().getClassLoader().getResource("LeaveStore.wav")));
                                inShop = false;
                            } else if (com.equalsIgnoreCase("list") || com.equalsIgnoreCase("items") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                outputText.append(shop.listItems(outputText));
                            } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !com.isEmpty() && !com.equalsIgnoreCase("shop") && !com.equalsIgnoreCase("store") && !foundItem) {
                                outputText.append("\n\nCannot find item / unknown command");
                            }
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                userInp.setText("");
                            }
                            foundItem = false;
                        }

                    }

                    if (inInn) {

                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                            image = new ImageIcon(getClass().getClassLoader().getResource("Inn.png"));

                            int imageX = (int) Math.round(currentScreenWidth * 0.5208);
                            int imageY = (int) Math.round(currentScreenHeight * 0.9259);
                            Image newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                            image = new ImageIcon(newImage);
                            imageLabel.setIcon(image);

                            int sleepCost = (player.getPlayerLevel() * 10) * 2;

                            if (com.equalsIgnoreCase("rest") || com.equalsIgnoreCase("sleep") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                if (player.getPlayerGold() >= sleepCost) {
                                    PlaySound.play((getClass().getClassLoader().getResource("Heal.wav")));
                                    outputText.append("\n\nYou have rested. Your health and mana have been fully restored.");
                                    player.changeGold(sleepCost);
                                    player.setCurrentPlayerHealth(player.getMaxPlayerHealth());
                                    player.setMaxPlayerMana(player.getMaxPlayerMana());
                                    userPanelText.setText(player.toString());
                                } else {
                                    PlaySound.play((getClass().getClassLoader().getResource("Denied.wav")));
                                    outputText.append("\n\nYou do not have enough money!");
                                }
                            } else if (com.equalsIgnoreCase("exit") || com.equalsIgnoreCase("leave") || com.equalsIgnoreCase("quit") || com.equalsIgnoreCase("back") || com.equalsIgnoreCase("return") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                                image = new ImageIcon(getClass().getClassLoader().getResource("Town.png"));

                                imageX = (int) Math.round(currentScreenWidth * 0.5208);
                                imageY = (int) Math.round(currentScreenHeight * 0.9259);
                                newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                                image = new ImageIcon(newImage);
                                imageLabel.setIcon(image);

                                outputText.append("\n\nYou have left the inn.");
                                PlaySound.play((getClass().getClassLoader().getResource("LeaveStore.wav")));
                                inInn = false;
                            } else if (e.getKeyCode() == KeyEvent.VK_ENTER && !com.isEmpty() && !com.equalsIgnoreCase("inn") && !com.equalsIgnoreCase("tavern")) {
                                outputText.append("\n\nUnknown command");
                            }
                            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                                userInp.setText("");
                            }

                        }

                    }

                }


            }

        });


        this.getContentPane().add(root);
        this.setTitle("Pixel RPG Game");
        this.setSize(currentScreenWidth, currentScreenHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {

                imagePanel.remove(imageLabel);
                backgroundImagePanel.remove(backgroundImageLabel);
                int imageX = (int) Math.round(currentScreenWidth * 0.5208);
                int imageY = (int) Math.round(currentScreenHeight * 0.9259);
                Image newImage = image.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                image = new ImageIcon(newImage);
                imageLabel.setIcon(image);

                Image newBackImage = backgroundImage.getImage().getScaledInstance(imageX, imageY, Image.SCALE_DEFAULT);
                backgroundImage = new ImageIcon(newBackImage);
                backgroundImageLabel.setIcon(backgroundImage);

                imagePanel.add(imageLabel);
                backgroundImagePanel.add(backgroundImageLabel);

                if (userColorString != "Rainbow" && userColorString != "Warm" && userColorString != "Cool" && userColorString != "Festive") {
                    Border customBorder = BorderFactory.createLineBorder(userColor);

                    root.setBorder(new EmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt));
                    // root.setBorder(new EmptyBorder(10, 10, 10, 10));

                    namePanel.setBorder(new EmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt));
                    // namePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

                    tf.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // tf.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    confirmButton.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // confirmButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    difPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));

                    colorPanel.setBorder(new EmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt));
                    // colorPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

                    colortf.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // colortf.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    confirmButton.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // confirmButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    colorButton.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // colorButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    easyButton.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // easyButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    regularButton.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // regularButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    hardButton.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // hardButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));;

                    veteranButton.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // veteranButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));

                    toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));

                    userInp.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // userInp.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    outputText.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // outputText.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                    userPanelText.setBorder(BorderFactory.createCompoundBorder(customBorder, BorderFactory.createEmptyBorder(Border10Pt, Border10Pt, Border10Pt, Border10Pt)));
                    // userPanelText.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                } else {

                    final Border redBorder = BorderFactory.createLineBorder(Color.red);
                    final Border blueBorder = BorderFactory.createLineBorder(Color.blue);
                    final Border greenBorder = BorderFactory.createLineBorder(Color.green);
                    final Border yellowBorder = BorderFactory.createLineBorder(Color.yellow);
                    final Border purpleBorder = BorderFactory.createLineBorder(Color.magenta);
                    final Border orangeBorder = BorderFactory.createLineBorder(Color.orange);
                    final Border cyanBorder = BorderFactory.createLineBorder(Color.cyan);

                    if (userColorString.equalsIgnoreCase("Rainbow")) {
                        colorLabel.setForeground(Color.red);
                        nameLabel.setForeground(Color.red);
                        tf.setForeground(Color.red);
                        tf.setCaretColor(Color.red);
                        difLabel.setForeground(Color.yellow);
                        tf.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colorButton.setForeground(Color.blue);
                        colorButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        confirmButton.setForeground(Color.green);
                        confirmButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colorLabel.setForeground(Color.blue);
                        easyButton.setBorder(BorderFactory.createCompoundBorder(yellowBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        easyButton.setForeground(Color.yellow);
                        regularButton.setBorder(BorderFactory.createCompoundBorder(yellowBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        regularButton.setForeground(Color.yellow);
                        hardButton.setBorder(BorderFactory.createCompoundBorder(yellowBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        hardButton.setForeground(Color.yellow);
                        veteranButton.setBorder(BorderFactory.createCompoundBorder(yellowBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        veteranButton.setForeground(Color.yellow);

                        soundLabel.setForeground(Color.cyan);
                        toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(cyanBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        toggleSoundOn.setForeground(Color.cyan);
                        toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(cyanBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        toggleSoundOff.setForeground(Color.cyan);

                        colortf.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colortf.setForeground(Color.blue);
                        colortf.setCaretColor(Color.blue);
                        nameLabel.setForeground(Color.red);
                        userInp.setForeground(Color.cyan);
                        userInp.setCaretColor(Color.cyan);
                        userInp.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        outputText.setForeground(Color.yellow);
                        outputText.setCaretColor(Color.yellow);
                        outputText.setBorder(BorderFactory.createCompoundBorder(purpleBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        scrollOut.setBorder(purpleBorder);
                        scrollOut.setForeground(Color.magenta);
                        userPanelText.setForeground(Color.red);
                        userPanelText.setCaretColor(Color.red);
                        userPanelText.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                        javaQuestPanel.setBackground(Color.red);
                        javaQuestPanel.setForeground(Color.red);

                    } else if (userColorString.equalsIgnoreCase("Warm")) {
                        colorLabel.setForeground(Color.red);
                        nameLabel.setForeground(Color.red);
                        tf.setForeground(Color.red);
                        tf.setCaretColor(Color.red);
                        difLabel.setForeground(Color.orange);
                        tf.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colorButton.setForeground(Color.orange);
                        colorButton.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        confirmButton.setForeground(Color.yellow);
                        confirmButton.setBorder(BorderFactory.createCompoundBorder(yellowBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colorLabel.setForeground(Color.orange);
                        easyButton.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        easyButton.setForeground(Color.orange);
                        regularButton.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        regularButton.setForeground(Color.orange);
                        hardButton.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        hardButton.setForeground(Color.orange);
                        veteranButton.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        veteranButton.setForeground(Color.orange);

                        soundLabel.setForeground(Color.orange);
                        toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        toggleSoundOn.setForeground(Color.orange);
                        toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        toggleSoundOff.setForeground(Color.orange);

                        colortf.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colortf.setForeground(Color.orange);
                        colortf.setCaretColor(Color.orange);
                        nameLabel.setForeground(Color.red);
                        userInp.setForeground(Color.yellow);
                        userInp.setCaretColor(Color.yellow);
                        userInp.setBorder(BorderFactory.createCompoundBorder(orangeBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        outputText.setForeground(Color.yellow);
                        outputText.setCaretColor(Color.yellow);
                        outputText.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        scrollOut.setBorder(redBorder);
                        scrollOut.setForeground(Color.red);
                        userPanelText.setForeground(Color.yellow);
                        userPanelText.setCaretColor(Color.yellow);
                        userPanelText.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                        javaQuestPanel.setBackground(Color.red);
                        javaQuestPanel.setForeground(Color.red);
                    }  else if (userColorString.equalsIgnoreCase("Festive")) {
                        colorLabel.setForeground(Color.red);
                        nameLabel.setForeground(Color.red);
                        tf.setForeground(Color.green);
                        tf.setCaretColor(Color.green);
                        difLabel.setForeground(Color.green);
                        tf.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colorButton.setForeground(Color.red);
                        colorButton.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        confirmButton.setForeground(Color.green);
                        confirmButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colorLabel.setForeground(Color.red);
                        easyButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        easyButton.setForeground(Color.green);
                        regularButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        regularButton.setForeground(Color.green);
                        hardButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        hardButton.setForeground(Color.green);
                        veteranButton.setBorder(BorderFactory.createCompoundBorder(greenBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        veteranButton.setForeground(Color.green);

                        soundLabel.setForeground(Color.red);
                        toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        toggleSoundOn.setForeground(Color.red);
                        toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        toggleSoundOff.setForeground(Color.red);

                        colortf.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colortf.setForeground(Color.red);
                        colortf.setCaretColor(Color.red);
                        nameLabel.setForeground(Color.green);
                        userInp.setForeground(Color.green);
                        userInp.setCaretColor(Color.green);
                        userInp.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        outputText.setForeground(Color.green);
                        outputText.setCaretColor(Color.green);
                        outputText.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        scrollOut.setBorder(redBorder);
                        scrollOut.setForeground(Color.green);
                        userPanelText.setForeground(Color.green);
                        userPanelText.setCaretColor(Color.green);
                        userPanelText.setBorder(BorderFactory.createCompoundBorder(redBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                        javaQuestPanel.setBackground(Color.red);
                        javaQuestPanel.setForeground(Color.red);
                    } else if (userColorString.equalsIgnoreCase("Cool")) {
                        colorLabel.setForeground(Color.blue);
                        nameLabel.setForeground(Color.cyan);
                        tf.setForeground(Color.cyan);
                        tf.setCaretColor(Color.cyan);
                        difLabel.setForeground(Color.blue);
                        tf.setBorder(BorderFactory.createCompoundBorder(cyanBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colorButton.setForeground(Color.blue);
                        colorButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        confirmButton.setForeground(Color.magenta);
                        confirmButton.setBorder(BorderFactory.createCompoundBorder(purpleBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colorLabel.setForeground(Color.blue);
                        easyButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        easyButton.setForeground(Color.blue);
                        regularButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        regularButton.setForeground(Color.blue);
                        hardButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        hardButton.setForeground(Color.blue);
                        veteranButton.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        veteranButton.setForeground(Color.blue);

                        soundLabel.setForeground(Color.blue);
                        toggleSoundOn.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        toggleSoundOn.setForeground(Color.blue);
                        toggleSoundOff.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        toggleSoundOff.setForeground(Color.blue);

                        colortf.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        colortf.setForeground(Color.blue);
                        colortf.setCaretColor(Color.blue);
                        nameLabel.setForeground(Color.cyan);
                        userInp.setForeground(Color.cyan);
                        userInp.setCaretColor(Color.cyan);
                        userInp.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        outputText.setForeground(Color.cyan);
                        outputText.setCaretColor(Color.cyan);
                        outputText.setBorder(BorderFactory.createCompoundBorder(blueBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
                        scrollOut.setBorder(blueBorder);
                        scrollOut.setForeground(Color.blue);
                        userPanelText.setForeground(Color.cyan);
                        userPanelText.setCaretColor(Color.cyan);
                        userPanelText.setBorder(BorderFactory.createCompoundBorder(purpleBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

                        javaQuestPanel.setBackground(Color.cyan);
                        javaQuestPanel.setForeground(Color.cyan);
                    }
                }

                currentScreenWidth = getScreenWidth();
                currentScreenHeight = getScreenHeight();


                // See here from the txt file (Put everything that resizes all of the UI components here, so that everything)
                // is initialized at that start and any time the screen changes size, this method is called to adjust the components

                int Font24Pt = (int)Math.round(currentScreenWidth*0.0125);
                int Border10Pt = (int)Math.round(currentScreenWidth*0.0052);

                nameLabel.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
                // nameLabel.setFont(new Font("NSimSun", Font.BOLD, 24));

                tf.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                int tfX = (int)Math.round(currentScreenWidth*0.1563);
                int tfY = (int)Math.round(currentScreenHeight*0.0463);
                tf.setPreferredSize(new Dimension(tfX, tfY));
                // tf.setPreferredSize(new Dimension(300, 50));

                confirmButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                int confirmButtonX = (int)Math.round(currentScreenWidth*0.1042);
                int confirmButtonY = (int)Math.round(currentScreenHeight*0.0925);

                confirmButton.setPreferredSize(new Dimension(confirmButtonX, confirmButtonY));
                // confirmButton.setPreferredSize(new Dimension(200,100));

                colorLabel.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
                // colorLabel.setFont(new Font("NSimSun", Font.BOLD, 24));

                colortf.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
                // colortf.setFont(new Font("NSimSun", Font.BOLD, 24));

                int colortfX = (int)Math.round(currentScreenWidth*0.1563);
                int colortfY = (int)Math.round(currentScreenHeight*0.0463);
                colortf.setPreferredSize(new Dimension(colortfX, colortfY));
                // colortf.setPreferredSize(new Dimension(300, 50));

                colorButton.setBackground(Color.black);

                colorButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
                // colorButton.setFont(new Font("NSimSun", Font.BOLD, 24));

                int colortButtonX = (int)Math.round(currentScreenWidth*0.0521);
                int colorButtonY = (int)Math.round(currentScreenHeight*0.0463);
                colorButton.setPreferredSize(new Dimension(colortButtonX, colorButtonY)); // Orig: Width: 100, Height: 50

                int difButtonX = (int)Math.round(currentScreenWidth*0.0651); // Orig: 125
                int difButtonY = (int)Math.round(currentScreenHeight*0.0463); // Orig: 50

                easyButton.setPreferredSize(new Dimension(difButtonX,difButtonY));
                regularButton.setPreferredSize(new Dimension(difButtonX,difButtonY));
                hardButton.setPreferredSize(new Dimension(difButtonX,difButtonY));
                veteranButton.setPreferredSize(new Dimension(difButtonX,difButtonY));


                soundLabel.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                toggleSoundOn.setPreferredSize(new Dimension(difButtonX,difButtonY));
                toggleSoundOn.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                toggleSoundOff.setPreferredSize(new Dimension(difButtonX,difButtonY));
                toggleSoundOff.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));


                difLabel.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));
                // difLabel.setFont(new Font("NSimSun", Font.BOLD, 24));

                easyButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                regularButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                hardButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                veteranButton.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                int userInpParentX = (int)Math.round(currentScreenWidth*0.2186); // Orig: 420
                int userInpParentY = 1; // Orig: 1
                userInpParent.setMaximumSize(new Dimension(userInpParentX, userInpParentY));
                // userInpParent.setMaximumSize(new Dimension(420, 1));

                userInp.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                int outputTextRows = (int)Math.round(currentScreenWidth*0.0172); // Orig: 33 (0.0172)
                int outputTextColumns = (int)Math.round(currentScreenHeight*0.0278); // Orig: 30

                // System.out.println(outputTextRows + ", " + outputTextColumns + ". Screen Res: " + currentScreenWidth + ", " + currentScreenHeight);

                outputText.setRows(outputTextRows);
                outputText.setColumns(outputTextColumns);
                // JTextArea outputText = new JTextArea(33, 30);
                outputText.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                int scrollOutX = (int)Math.round(currentScreenWidth*0.224); // Orig: 430
                int scrollOutY = (int)Math.round((currentScreenHeight*0.8796)-((1080-currentScreenHeight)*0.09)); // Orig: 950

                scrollOut.setPreferredSize(new Dimension(scrollOutX, scrollOutY)); // 430, 950

                userPanelText.setPreferredSize(new Dimension(10, 10));

                int userPanelRows = (int)Math.round(currentScreenWidth*0.0172); // Orig: 33
                int userPanelColumns = (int)Math.round(currentScreenHeight*0.0278); // Orig: 30

                userPanelText.setRows(userPanelRows);
                userPanelText.setColumns(userPanelColumns);
                // JTextArea userPanelText = new JTextArea(33, 30);
                userPanelText.setFont(new Font("NSimSun", Font.BOLD, Font24Pt));

                root.validate();
                root.repaint();


            }
        });

    }

    public static void main(String[] args) throws FileNotFoundException {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public void setFullScreen() {
        // gDevice.setFullScreenWindow(this);

        currentScreenWidth = this.getWidth();
        currentScreenHeight = this.getHeight();
    }

    public int getScreenWidth() {
        return this.getWidth();
    }

    public int getScreenHeight() {
        return this.getHeight();
    }

    public void paintUI() {

    }

}