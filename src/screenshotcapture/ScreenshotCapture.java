/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package screenshotcapture;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.event.InputEvent;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

import java.net.ServerSocket;
import java.net.Socket;

import javax.bluetooth.*;
import javax.microedition.io.*;

/**
 *
 * @author rglover3
 */
public class ScreenshotCapture implements Runnable {

    ServerSocket listener;
    Socket clientSocket;
    
    public ScreenshotCapture(JLabel _imgLabel) {
        imgLabel = _imgLabel;
    }
    
    JLabel imgLabel;
    /**
     * @param args the command line arguments
     */
    public void run() {
        OutputStream output = null;
        /* Connection */
        try {
            System.out.println("Waiting for connection...");
            listener = new ServerSocket(5506);
            clientSocket = listener.accept();
            System.out.println("Connected.");
            output = clientSocket.getOutputStream();
        } catch (IOException e) {
            System.out.println("Server problem");
        }
        
        
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        Robot robot = null;
        Boolean capturing = true;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            System.out.println(e.getMessage());
        }
        int i = 0;
        while(capturing) {
            System.out.println("Capturing...");
            BufferedImage capture = robot.createScreenCapture(screenRect);
            System.out.println("Captured.");
            try{
               // imgLabel.setIcon(new ImageIcon(capture));
                ImageIO.write(capture, "PNG", output);
                i++;
                System.out.println(i);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
