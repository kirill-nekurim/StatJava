    package org.example;

    import com.formdev.flatlaf.themes.FlatMacDarkLaf;
    import javax.swing.UIManager;
    import javax.swing.UnsupportedLookAndFeelException;
    import org.example.UserInterface.GUI;
    public class Main {
        public static void main(String[] args){
            try {
                UIManager.setLookAndFeel(new FlatMacDarkLaf());
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }

            (new GUI()).setVisible(true);
        }
        }
