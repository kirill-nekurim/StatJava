package org.example;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.example.UserInterface.GUI;
public class Main {
    public static void main(String[] args) throws IOException{
        try {
            UIManager.setLookAndFeel(new FlatMacDarkLaf());
            //UIManager.setLookAndFeel(new FlatMacLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        (new GUI()).setVisible(true);
    }

    }
