package com.company;

import java.awt.Point;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

// Begin citation:
// http://geekonjava.blogspot.com/2014/06/how-to-use-shaking-effect-in-swing.html

public class ScreenShaker
{
    // Variables:
    private JPanel frame;
    public static final int UPDATE_TIME = 1;
    public static final int DURATION = 100;

    private Point primaryLocation;
    private long startTime;
    private Timer time;

    public ScreenShaker(JPanel f)
    {
        this.frame = f;
    }

    public void startShake()
    {
        primaryLocation = frame.getLocation();
        startTime = System.currentTimeMillis();
        time= new Timer(UPDATE_TIME,timeListener);
        time.start();
    }

    // Stops shake/puts back in original place:
    public void stopShake()
    {
        // Code to stop the screen shaking:
        time.stop();
        frame.setLocation(primaryLocation);
        frame.setVisible(true);
        frame.repaint();
    }

    private class ActionTime implements ActionListener
    {
        private int xOffset, yOffset;
        // Every interval the timer ticks, this is performed:
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // Get elapsed time(running time):
            long elapsedTime = System.currentTimeMillis() - startTime;
            Random r = new Random();
            int op = r.nextInt(5);

            if ( op > 0)
            {
                // Change x and y offset then reallocate frame:
                xOffset = primaryLocation.x + (r.nextInt(20));
                yOffset = primaryLocation.y + (r.nextInt(20));
                frame.setLocation(xOffset,yOffset);
                frame.setVisible(false);
                frame.repaint();
            }
            else
            {
                // Change x and y offset then reallocate frame:
                xOffset = primaryLocation.x - (r.nextInt(20));
                yOffset = primaryLocation.y - (r.nextInt(20));
                frame.setLocation(xOffset,yOffset);
                frame.setVisible(true);
                frame.repaint();
            }
            // ElapsedTime exceed  DURATION, so stop now:
            if(elapsedTime > DURATION)
            {
                stopShake();
            }
        }
    }
    // Listener/instance of ActionTime:
    private ActionTime timeListener = new ActionTime();

    // End citation
}